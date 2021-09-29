package com.mall.oms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.mall.oms.dto.ConfirmOrderDto;
import com.mall.oms.dto.OrderPara;
import com.mall.oms.entity.OrderInfo;

import java.util.Map;

public interface OrderInfoService extends IService<OrderInfo> {

    //确认订单
    public ConfirmOrderDto confirmOrder();

    //创建订单
    public Map<String, Object> createOrder(OrderPara orderPara) ;

    //取消订单
    public void cancelTimeOutOOrder(Long orderId);

    //取消订单
    public void cancelOrder(Long orderId);

    //支付订单
    public Map<String, Object> pay(Long orderId);

    //删除订单
    public void deleteOrder(Long orderId);

}
