package com.mall.oms.service.impl;

import com.mall.common.util.RedisUtil;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

class OrderCartServiceImplTest {

//    @Autowired
//    CartService orderCartService;

//    @Autowired
//    CartMapper orderCartMapper;

    @Resource
    RedisUtil redisUtil;

    @Test
    void addCart() {
//        CartSkuDto orderCart = new CartSkuDto();
//        orderCart.setSkuId(Long.valueOf(1));
//        orderCart.setSkuNum(2);
//        orderCartService.addCart(orderCart);
        redisUtil.hSet("Sku:1","skuCode","11011");

    }
}