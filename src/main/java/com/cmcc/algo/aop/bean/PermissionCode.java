package com.cmcc.algo.aop.bean;



import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionCode {

    String value();
}
