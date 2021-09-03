package com.mall.oms.controller;

import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.service.CartService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CartControllerTest {

    @Autowired
    CartService orderCartService;

    @Test
    void addCart() {
        CartSkuDto orderCart = new CartSkuDto();
        orderCart.setSkuId(Long.valueOf(1));
        orderCart.setSkuNum(2);
       // orderCartService.addCart(1L,orderCart.getSkuId() , orderCart.getSkuNum());
    }
}