package com.hisoka.filmreview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//解决跨域问题：1.方法一，该配置类，方法2：在controller中添加注解@CrossOrigin
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**") //允许跨域访问的路径
                .allowedOriginPatterns("*") //允许跨域访问的源
                .allowedMethods("POST","GET","PUT","OPTIONS","DELETE")
                .maxAge(168000)    //预检间隔时间
                .allowedHeaders("*") //允许头部设置
                .allowCredentials(true); //是否允许发送cookie
    }
}
