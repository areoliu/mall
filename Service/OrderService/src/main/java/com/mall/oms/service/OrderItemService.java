package com.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.oms.dto.CartSkuDto;
import com.mall.oms.entity.OrderItem;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface OrderItemService extends IService<OrderItem>{

    public void insertBatch(@Param(value = "list") List<OrderItem> orderItemList);



}
