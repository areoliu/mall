package com.mall.oms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem implements Serializable {
    private static final long serialVersionUID = -15133155671544883L;

    //商品信息
    private Long id;

    private Long orderInfoId;

    private Long skuId;

    private String skuCode;

    private String skuName;

    private String skuImgs;

    private String shopId;

    private String shopName;

    private String shopImg;

    private BigDecimal skuPrice;

    private Integer quantity;

    private BigDecimal discountAmount;

    private BigDecimal marketAmount;

    private BigDecimal payAmount;

    private Long user_id;

}
