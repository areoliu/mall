package com.mall.oms.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartSkuDto {

    @NotNull(message="user编号不能为空")
    private Long userId;

    @NotNull(message="sku编号不能为空")
    private Long skuId;

    @NotNull(message="sku数量不能为空")
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

    private Integer  status;

    private Integer  isChoose;


}
