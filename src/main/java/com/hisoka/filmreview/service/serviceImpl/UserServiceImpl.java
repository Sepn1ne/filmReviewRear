package com.hisoka.filmreview.service.serviceImpl;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hisoka.filmreview.dto.LoginByPwdForm;
import com.hisoka.filmreview.dto.Result;
import com.hisoka.filmreview.dto.SignUpForm;
import com.hisoka.filmreview.dto.UserDTO;
import com.hisoka.filmreview.entity.User;
import com.hisoka.filmreview.mapper.UserMapper;
import com.hisoka.filmreview.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hisoka.filmreview.utils.QiNiu;
import com.hisoka.filmreview.utils.RedisConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hisoka
 * @since 2024-04-13
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public Result getCode(String email) {
        //1.如果邮箱为空则返回错误
        if(email == null){
            return Result.fail("邮箱为空！");
        }
        //2. 判断该邮箱是否已经注册
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("email", email));
        if(count >= 1){
            return Result.fail("该邮箱已注册！");
        }
        //3.生成一个验证码
        String code = RandomUtil.randomNumbers(6);
        System.out.println(code);
        //4.将该验证码作为redis的值生成Redis的键
        String key = RedisConstants.LOGIN_CODE + email;
        //5.将该键值对存入Redis，并设置有效时间
        stringRedisTemplate.opsForValue().set(key,code, Duration.ofSeconds(60));
        return Result.ok();
    }

    @Override
    public Result signUp(SignUpForm form) {
        //1.校验验证码是否正确
        String key = RedisConstants.LOGIN_CODE + form.getEmail();
        String s = stringRedisTemplate.opsForValue().get(key);
        log.info(s);
        if(s == null){
            log.error("验证码过期！");
            return Result.fail("验证码已过期!");
        }else if(Integer.parseInt(s) != form.getCode()){
            log.error("验证码错误!");
            return Result.fail("验证码错误!");
        }
        //2. 判断该邮箱是否已经有人注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",form.getEmail());
        int count = userMapper.selectCount(queryWrapper);
        if(count >= 1){
            return Result.fail("该邮箱已经被注册!");
        }
        //3.将form存入user，然后存入数据库
        User user = new User();
        BeanUtils.copyProperties(form,user);
        int i = userMapper.insert(user);
        System.out.println(i);
        if(i == 1){
            return Result.ok();
        }else{
            return Result.fail("注册失败！");
        }
    }

    @Override
    public Result loginByPwd(LoginByPwdForm form) throws UnsupportedEncodingException {
        //1.先查询是否存在该用户
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",form.getEmail()).eq("password",form.getPassword());
        User user = userMapper.selectOne(userQueryWrapper);
        if(user == null){
            return Result.fail("登陆失败！不存在该用户");
        }
        // else{
        //     //2. 若用户存在，则渲染用户的头像
        //     String url = QiNiu.getUrl("head",user.getIcon());
        //     System.out.println("头像链接:" + url);
        //     user.setIcon(url);
        //     return Result.ok(user);
        // }
        return Result.ok(user);
    }

    @Override
    public Result getToken(Long userId) {
        //1.先查询该用户是否存在
        User user = query().eq("id", userId).one();
        if(user == null){
            return Result.fail("登录失败！不存在该用户");
        }
        //2.若该用户存在，则生成一段随机的UUID，作为Redis中存放用户信息的key
        String token = UUID.randomUUID().toString(true);
        String key = RedisConstants.LOGIN_TOKEN + token;
        log.info("用户登录的凭证的键是:" + key);

        //3.将用户信息转为hash，并作为value存入Redis
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user,userDTO);
        System.out.println(userDTO.toString());
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("id",userDTO.getId().toString());
        hashMap.put("nickName",userDTO.getNickName());
        hashMap.put("icon",userDTO.getIcon());
        stringRedisTemplate.opsForHash().putAll(key,hashMap);

        //4.设置token有效期为 7 天
        stringRedisTemplate.expire(key,RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);

        //5.将返回给前端
        return Result.ok(token);
    }

    @Override
    public Result judgeToken(String token) {
        String key = RedisConstants.LOGIN_TOKEN + token;
        Boolean hasKey = stringRedisTemplate.hasKey(key);
        System.out.println(hasKey ? "token未过期" : "token已经过期");
        return Result.ok(hasKey);
    }


}
