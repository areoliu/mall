package com.mall.oms.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSkuVo {

    private Long skuId;

    private Integer skuNum;

    private Long shopId;

    private Long spuId;

    private String shopName;

    private String skuCode;

    private String skuName;

    private Double skuPrice;

    private String skuImg;

    private String skuSpec;

    private String skuIntroduce;

    private String brand;

    private Integer stock;

    private Integer max;

    private Integer  isChoose;

    private Integer  status;

}
