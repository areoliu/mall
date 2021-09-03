package com.mall.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private Integer code;
    private String message;



    public BusinessException(Throwable throwable, Integer code, String message){
        super(throwable);
        this.code = code;
        this.message = message;

    }
}
