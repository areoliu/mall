package com.mall.common.enums;

public enum PayTypeEnum {
    WEICHAT_PAY(1,"微信支付"),
    ALIPAY_PAY(2,"支付宝支付"),
    UNION_PAY(3, "银联支付");

    private Integer code;
    private String message;

    PayTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
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
}
