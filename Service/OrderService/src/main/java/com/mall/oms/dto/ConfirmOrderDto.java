package com.mall.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmOrderDto {


    private List<ReceiveAddress> receiveAddressList;

    private List<CartSkuDto> cartSkuDtoList;

    private List<Coupon> couponList;


}
