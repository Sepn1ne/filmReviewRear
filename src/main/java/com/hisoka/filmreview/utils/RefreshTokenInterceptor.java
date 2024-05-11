package com.hisoka.filmreview.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.hisoka.filmreview.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/2 21:44
 */
@Slf4j
@Component
public class RefreshTokenInterceptor implements HandlerInterceptor {

    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.获取请求头中的token
        String token = request.getHeader("authorization");
        log.info("token为:"+token);
        if (StrUtil.isBlank(token)) {
            log.info("此次请求未携带token！");
            return true;
        }
        // 2.基于TOKEN获取redis中的用户
        String key  = RedisConstants.LOGIN_TOKEN + token;
        Map<Object, Object> userMap = stringRedisTemplate.opsForHash().entries(key);
        // 3.判断用户是否存在
        if (userMap.isEmpty()) {
            return true;
        }
        // 5.将查询到的hash数据转为UserDTO
        UserDTO userDTO = BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false);
        // 6.用户存在，则保存用户信息到 ThreadLocal
        UserHolder.saveUser(userDTO);
        log.info("添加用户！");
        // 7.刷新token有效期
        log.info("刷新token的有效期");
        stringRedisTemplate.expire(key, RedisConstants.LOGIN_TOKEN_TTL, TimeUnit.MINUTES);
        // 8.放行
        return true;
    }

    // 一次请求完全结束之后，移除ThreadLocal中的User
    // 由于ThreadLocal的key是弱引用，故在gc时，key会被回收掉，
    // 但是value是强引用没有被回收，所以在我们拦截器的方法里必须手动remove()。
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 移除用户
        log.info("移除用户");
        UserHolder.removeUser();
    }
}
