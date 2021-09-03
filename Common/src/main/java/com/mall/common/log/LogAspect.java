package com.mall.common.log;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Slf4j
@Aspect
@Component
public class LogAspect {
    ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(com.mall.common.log.LogAnno)")
    public void log(){

    }

    @Before("log()")
    @Order(1)
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        threadLocal.set(System.currentTimeMillis());
        log.info("{}### request###请求url：{},请求方法：{}，请求参数：{},请求ip:{}",new Date(),request.getRequestURL(),joinPoint.getSignature().getDeclaringTypeName()
        +"."+joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()),request.getRemoteAddr());
    }

    @AfterReturning(returning = "ret",pointcut = "log()")
    @Order(10)
    public void doAfter(Object ret) throws Throwable{
        log.info("{}### response###返回结果：{},耗时:{}ms",new Date(),ret,System.currentTimeMillis()-threadLocal.get());

    }


}
