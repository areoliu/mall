package com.mall.common.config;


import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 1. swagger配置类
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .apiInfo(apiInfo()).pathMapping("/").select()
                // 选择那些路径和api会生成document
                .apis(RequestHandlerSelectors.any())
                // 对所有api进行监控
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                // 错误路径不监控
                .paths(PathSelectors.regex("/.*"))
                // 对根下所有路径进行监控
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //设置文档标题(API名称)
                .title("SpringBoot中使用Swagger2接口规范")
                //文档描述
                .description("接口说明")
                //服务条款URL
                .termsOfServiceUrl("www.baidu.com")
                //版本号
                .version("1.0.0")
                .build();
    }
}
