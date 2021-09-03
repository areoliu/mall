package com.mall.common.model;


import com.mall.common.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 返回消息体
 * @param <T>
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> implements Serializable {
    //返回码
    private Integer code;
    //返回提示
    private String message;
    //返回数据
    private T data;
    //返回成功标志
    private Boolean flag;


    public Result(Integer code, String message, Boolean flag) {
        this.code = code;
        this.message = message;
        this.flag = flag;
    }

    public Result<T> Success(){
        Result result = new Result();
        result.setFlag(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        return result;

    }

    public Result<T> Success(T data){
        Result result = new Result();
        result.setFlag(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;

    }

    public Result<T> Fail(){
        Result result = new Result();
        result.setFlag(true);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(ResultCodeEnum.FAIL.getMessage());
        return result;

    }

    public Result<T> Fail(String message){
        Result result = new Result();
        result.setFlag(true);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(message);
        return result;

    }


}
