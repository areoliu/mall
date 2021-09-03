package com.mall.oms.service;

public interface OrderInfoService {

    //创建订单
    public void create(Long userId);

    //取消订单
    public void cancel(Long orderId);

    //支付订单
    public void pay(Long orderId);
}
