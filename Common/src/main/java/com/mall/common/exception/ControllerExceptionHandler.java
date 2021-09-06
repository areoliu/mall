package com.mall.common.exception;

import com.mall.common.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import com.mall.common.model.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author liu
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public Result handleExcepiotn(Exception e){
        log.error(e.getMessage(),e);
        return new Result(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage(),false);
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeExcepiotn(RuntimeException e){
        log.error(e.getMessage(),e);
        return new Result(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage(),false);
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleArgumentException(MethodArgumentNotValidException e){
        log.error(e.getMessage(),e);
        return new Result(ResultCodeEnum.VALIDATE_FAILED.getCode(), e.getBindingResult().getFieldError().getDefaultMessage(),false);
    }


    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessExcepiotn(BusinessException e){
        log.error(e.getMessage(),e);
        return new Result(e.getCode(),e.getMessage(),false);
    }
}
