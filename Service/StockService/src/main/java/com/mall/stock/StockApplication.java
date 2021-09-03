package com.mall.stock;

import com.alibaba.cloud.nacos.client.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @ClassName StockApplication
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/27 22:05
 * @Version 1.0
 **/
@SpringBootApplication
@EnableDiscoveryClient
public class StockApplication {
    public static void main(String[] args) {
        SpringApplication.run(StockApplication.class, args);
    }
}
