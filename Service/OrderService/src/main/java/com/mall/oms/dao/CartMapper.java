package com.mall.oms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.entity.Cart;
import com.mall.oms.entity.OrderItem;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


public interface CartMapper extends BaseMapper<Cart> {


}
