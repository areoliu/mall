package com.mall.common.enums;

public enum SkuEnmu {

    ON(1,"已上架"),
    OFF(2,"已下架"),
    DELETE(3, "已删除");

    private Integer code;
    private String message;

    SkuEnmu(Integer code, String message) {
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
