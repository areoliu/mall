package com.mall.common.log;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogAnno {
    String fun() default "";
    String des() default "";
    String type() default "";
}
