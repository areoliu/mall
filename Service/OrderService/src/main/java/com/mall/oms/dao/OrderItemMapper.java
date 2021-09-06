package com.mall.oms.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mall.oms.entity.OrderItem;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


public interface OrderItemMapper extends BaseMapper<OrderItem> {

    public void insertBatch(@Param(value = "list") List<OrderItem> orderItemList);
}
