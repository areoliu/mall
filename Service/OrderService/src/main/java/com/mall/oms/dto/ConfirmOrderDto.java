package com.mall.oms.dto;

import com.mall.oms.entity.Coupon;
import com.mall.oms.entity.ReceiveAddress;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderDto {


    private List<ReceiveAddress> receiveAddressList;

    private List<CartSkuDto> cartSkuDtoList;

    private List<Coupon> couponList;

    private BigDecimal orderAmount;

    private BigDecimal payAmount;

    private BigDecimal discountAmount;

    private BigDecimal couponsDiscountAmout;

    private BigDecimal marketDiscountAmout;


}
