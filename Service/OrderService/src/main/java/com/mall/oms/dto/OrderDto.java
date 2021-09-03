package com.mall.oms.dto;

import com.mall.oms.entity.OrderItem;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderDto
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/26 22:08
 * @Version 1.0
 **/
public class OrderDto {

    private Long id;

    //支付信息
    private BigDecimal orderAmount;

    private BigDecimal payAmount;

    private BigDecimal discountAmount;

    private BigDecimal couponsDiscountAmout;

    private BigDecimal marketDiscountAmout;

    //收货信息
    private String orderUserName;

    private List<OrderItem> orderItemList;

    private String address;

    private String phone;

    //物流信息

    private String shipNo;

    private String shipName;
}
