package com.mall.oms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("oms_order_info")
public class OrderInfo implements Serializable {
    private static final long serialVersionUID = -15133155671544883L;

    //订单信息
    private Long id;

   // private String orderNo;

    private BigDecimal orderAmount;

    //支付信息
    private BigDecimal payAmount;

    private BigDecimal discountAmount;

    private BigDecimal couponsDiscountAmount;

    private BigDecimal marketDiscountAmount;



    //收货信息
    private String orderUserName;

    private Integer orderUserId;

    private String province;

    private String city;

    private String region;

    private String address;

    private String phone;

    //物流信息

    private String shipNo;

    private String shipName;

    //支付信息

    private Date payDate;

    private String payTypeName;

    private Integer payStatus;

    private Integer orderSource;

    private Date createDate;

    private Date updateDate;

    private Integer orderStatus;

    private Integer isDelete;


}
