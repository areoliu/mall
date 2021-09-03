package com.mall.oms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (CartSku)实体类
 *
 * @author liu
 * @since 2021-07-10 21:07:03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("order_cart")
@ApiModel("商品")
public class Sku implements Serializable {
    private static final long serialVersionUID = -15133155671544883L;

    private Long id;

    private Long spuId;

    private Long shopId;

    private String skuCode;

    private String skuName;

    private Double skuPrice;

    private String skuImg;

    private String skuSpec;

    private String skuIntroduce;

    private Integer catagoryId;

    private String brand;

    private Integer stock;

    private Date createDate;

    private Date updateDate;

    private String isDelete;

    private Integer max;

    private Integer status;



}
