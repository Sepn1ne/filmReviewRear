package com.hisoka.filmreview.config;

import com.hisoka.filmreview.utils.LoginInterceptor;
import com.hisoka.filmreview.utils.RefreshTokenInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/5/2 21:39
 */
@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // token刷新的拦截器，拦截所有请求
        // 如果当前线程没有token，则直接放行
        // 如果当前线程有token，则查询redis数据库获取用户，如果用户存在，则刷新token，并将用户放入ThreadLocal
        registry.addInterceptor(
                new RefreshTokenInterceptor(stringRedisTemplate)
        ).addPathPatterns("/**").order(0);

        // 登录拦截器,没有登陆的用户只能访问以下请求之外的请求
        // .order是拦截器的执行优先级，数值越低执行优先级越高
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/film/**",
                        "/user/**",
                        "/filmScore/**",
                        "/filmChainScore/**",
                        "/filmComment/**"
                ).order(1);



    }
}
