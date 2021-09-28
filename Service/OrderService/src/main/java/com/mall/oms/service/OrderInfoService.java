package com.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.oms.entity.OrderInfo;
import org.apache.rocketmq.client.exception.MQClientException;

public interface OrderInfoService extends IService<OrderInfo> {

    //确认订单
    public void confirmOrder();

    //创建订单
    public void createOrder(Long userId) ;

    //取消订单
    public void cancelTimeOutOOrder(Long orderId);

    //支付订单
    public void pay(Long orderId);

}
