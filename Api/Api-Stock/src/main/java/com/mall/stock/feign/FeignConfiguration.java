package com.mall.stock.feign;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import feign.Logger;

/**
 * @ClassName FeignConfiguration
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/2 22:17
 * @Version 1.0
 **/
@Configuration
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS;
    }
}
