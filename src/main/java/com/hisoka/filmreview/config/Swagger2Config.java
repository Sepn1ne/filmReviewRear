package com.hisoka.filmreview.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author Miaobu
 * @version 1.0
 * @description: TODO
 * @date 2024/2/23 15:51
 */

//http://localhost:8081/swagger-ui.html
@Configuration
@EnableSwagger2  //启动swagger2
public class Swagger2Config {
    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com"))
                .build();
    }

    //API文档显示信息
    private ApiInfo apiInfo() {
        // Contact contact = new Contact("koko", "http://xxx.xxx.com/联系人访问链接", "联系人邮箱");
        // return new ApiInfo(
        //         "Swagger学习", // 标题
        //         "学习演示如何配置Swagger", // 描述
        //         "v1.0", // 版本
        //         "http://terms.service.url/组织链接", // 组织链接
        //         contact, // 联系人信息
        //         "Apache 2.0 许可", // 许可
        //         "许可链接", // 许可连接
        //         new ArrayList<>()// 扩展
        // );
        return new ApiInfoBuilder().title("vueJava").description("学习Vue和Java").build();
    }

}
