package com.mall.stock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {


    @NotNull(message="sku编号不能为空")
    private Long skuId;

    @NotNull(message="sku数量不能为空")
    private Integer skuNum;



}
