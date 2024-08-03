package com.hisoka.filmreview.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.config.RabbitConfig;
import com.hisoka.filmreview.dto.CinemaScreeningDto;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.UserDTO;
import com.hisoka.filmreview.entity.CinemaScreening;
import com.hisoka.filmreview.entity.FilmOrder;
import com.hisoka.filmreview.mapper.CinemaScreeningMapper;
import com.hisoka.filmreview.mapper.FilmOrderMapper;
import com.hisoka.filmreview.producer.EventProducer;
import com.hisoka.filmreview.service.FilmOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.SnowFlakeUtil;
import com.hisoka.filmreview.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@Service
@Slf4j
public class FilmOrderServiceImpl extends ServiceImpl<FilmOrderMapper, FilmOrder> implements FilmOrderService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private CinemaScreeningMapper cinemaScreeningMapper;

    @Resource
    private FilmOrderMapper filmOrderMapper;

    @Resource
    private EventProducer eventProducer;

    private static final DefaultRedisScript<Long> CREATE_ORDER_SCRIPT;

    static {
        CREATE_ORDER_SCRIPT = new DefaultRedisScript<>();
        CREATE_ORDER_SCRIPT.setLocation(new ClassPathResource("createOrder.lua"));
        CREATE_ORDER_SCRIPT.setResultType(Long.class);
    }

    //创建订单。
    @Override
    public Result createOrder(Long csId) {
        //1.查询当前请求的用户
        UserDTO user = UserHolder.getUser();
        if(user == null){
            return Result.fail("用户未登录！");
        }
        //2.利用lua脚本判断当前的上映电影是否有库存,若有库存则扣减
        //lua脚本所需要的参数为:csId
        Long result = stringRedisTemplate.execute(
                CREATE_ORDER_SCRIPT,
                Collections.emptyList(),
                csId.toString(),user.getId().toString()
        );
        int r = result.intValue();
        // 2.判断结果是否为0
        if (r != 0) {
            // 2.1.不为0 ，代表没有购买资格
            return Result.fail(r == 1 ? "不存在该上映信息" : "库存不足");
        }
        // 3. 结果为0表示成功下单
        //生成订单
        FilmOrder order = new FilmOrder();
        order.setUserId(user.getId());
        order.setCinemaScreeningId(csId);
        //利用雪花算法生成订单id
        long orderId = SnowFlakeUtil.getID();
        String s = String.valueOf(orderId);
        order.setId(s);
        log.info("订单创建成功");

        // 4. 利用EventProducer发送消息给rabbitmq，让rabbitmq对数据库进行修改
        eventProducer.sendMessage(RabbitConfig.ORDER_EXCHANGE_NAME, RabbitConfig.ORDER_ROUTING_KEY, order);

        // 5. 返回订单id
        return Result.ok(orderId);
    }

    @Override
    public Integer insertOrder2Database(FilmOrder order) {
        Long csId = order.getCinemaScreeningId();
        //2.若当前用户已经登录，则判断该csId对应的电影票是否还有库存
        CinemaScreening cinemaScreening = cinemaScreeningMapper.selectOne(new QueryWrapper<CinemaScreening>().eq("id", csId));
        if(cinemaScreening == null){
            //未找到当前电影！
            log.error("未找到当前电影！");
            return 1;
        }else if(cinemaScreening.getStock() <= 0){
            //该场的电影票已售罄！
            log.error("该场的电影票已售罄！");
            return 2;
        }else{
            //3.库存减1
            cinemaScreening.setStock(cinemaScreening.getStock()-1);
            cinemaScreeningMapper.updateById(cinemaScreening);
            //填充订单剩余信息
            order.setPurchaseTime(LocalDateTime.now());
            order.setCinemaScreeningId(csId);
            order.setStatus(0);
            int i = filmOrderMapper.insert(order);
            if(i == 0){
                //创建订单失败！
                log.error("创建订单失败！");
                return 3;
            }else{
                //创建订单成功！
                log.info("订单被插入到数据库！");
                return 0;
            }
        }
    }
}
