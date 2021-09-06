package com.mall.common.enums;

/**
 * @ClassName PayEnum
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/24 22:08
 * @Version 1.0
 **/
public enum PayEnum {
    WAIT_PAY(1,"待支付"),
    SUCCESS_PAY(2,"已下架"),
    FAILED_PAY(3, "已删除");

    private Integer code;
    private String message;

    PayEnum(Integer code, String message) {
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
