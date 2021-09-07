package com.mall.common.enums;

/**
 * @ClassName PayEnum
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/24 22:08
 * @Version 1.0
 **/
public enum OrderEnum {
    ORDER_NEW(1,"待支付","已下单"),
    ORDER_PAY(2,"待审核",""),
    ORDER__AUDIT(3, "待分拣",""),
    ORDER__SORT(4, "待出库",""),
    ORDER__OUT(5, "待配送",""),
    ORDER__DELVERY(6, "待收货",""),
    ORDER__FINISH(7, "交易完成",""),
    ORDER__CANCEL(8, "交易取消","");

    private Integer code;
    private String message;
    private String description;


    OrderEnum(Integer code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription(){
        return description;
    }


}
