package com.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.entity.Cart;

import java.util.List;

public interface CartService extends IService<Cart>{

    public void addCart(CartSkuDto cartDto);

    void clear(Long userId);

    void delete(CartSkuDto cartDto);

    void check(CartSkuDto cartDto);

    void checkShop(Long userId, Long shopId);

    void checkAll(Long userId,Integer isChoose);

    List<CartSkuDto> getCartList(Long userId);


}
