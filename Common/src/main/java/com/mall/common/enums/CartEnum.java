package com.mall.common.enums;

public enum CartEnum {

    CHECKED(1,"选中"),
    UNCHECK(0, "未选");

    private Integer code;
    private String message;

    CartEnum(Integer code, String message) {
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
