package com.hisoka.filmreview.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.CinemaScreeningDto;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.UserDTO;
import com.hisoka.filmreview.entity.CinemaScreening;
import com.hisoka.filmreview.entity.FilmOrder;
import com.hisoka.filmreview.mapper.CinemaScreeningMapper;
import com.hisoka.filmreview.mapper.FilmOrderMapper;
import com.hisoka.filmreview.service.FilmOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.SnowFlakeUtil;
import com.hisoka.filmreview.utils.UserHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-05-11
 */
@Service
public class FilmOrderServiceImpl extends ServiceImpl<FilmOrderMapper, FilmOrder> implements FilmOrderService {

    @Resource
    private CinemaScreeningMapper cinemaScreeningMapper;

    @Resource
    private FilmOrderMapper filmOrderMapper;

    @Override
    public Result createOrder(Long csId) {
        //1.查询当前请求的用户
        UserDTO user = UserHolder.getUser();
        if(user == null){
            return Result.fail("用户未登录！");
        }
        //2.若当前用户已经登录，则判断该csId对应的电影票是否还有库存
        CinemaScreening cinemaScreening = cinemaScreeningMapper.selectOne(new QueryWrapper<CinemaScreening>().eq("id", csId));
        if(cinemaScreening == null){
            return Result.fail("未找到当前电影！");
        }else if(cinemaScreening.getStock() <= 0){
            return Result.fail("该场的电影票已售罄！");
        }else{
            //3.库存减1，且创建新订单
            cinemaScreening.setStock(cinemaScreening.getStock()-1);
            cinemaScreeningMapper.updateById(cinemaScreening);
            //创建新订单
            //利用雪花算法生成订单id
            long orderId = SnowFlakeUtil.getID();
            String s = String.valueOf(orderId);
            FilmOrder filmOrder = new FilmOrder();

            filmOrder.setId(s);
            filmOrder.setUserId(user.getId());
            filmOrder.setPurchaseTime(LocalDateTime.now());
            filmOrder.setCinemaScreeningId(csId);
            filmOrder.setStatus(0);
            int i = filmOrderMapper.insert(filmOrder);
            if(i == 0){
                return Result.fail("创建订单失败！");
            }else{
                return Result.ok(orderId);
            }
        }

    }
}
