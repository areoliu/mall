package com.mall.common.enums;

/**
 * @ClassName PayEnum
 * @Description TODO
 * @Author liupanda
 * @Date 2021/8/24 22:08
 * @Version 1.0
 **/
public enum ClientEnum {
    WEB(1,"web端"),
    IOS_APP(2,"ios"),
    ANDROID_APP(3, "android"),
    WECHAT_H5(3, "公众号"),
    WECHAT_APPLET(4, "微信小程序");

    private Integer code;
    private String message;

    ClientEnum(Integer code, String message) {
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
