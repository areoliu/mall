package com.mall.oms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mall.oms.dao.OrderItemMapper;
import com.mall.oms.entity.OrderItem;
import com.mall.oms.service.OrderItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName OrderItemServiceImpl
 * @Description TODO
 * @Author liupanda
 * @Date 2021/9/14 21:04
 * @Version 1.0
 **/
@Slf4j
@Service("orderItemService")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Override
    public void insertBatch(List<OrderItem> orderItemList) {
        orderItemMapper.insertBatch(orderItemList);

    }
}
