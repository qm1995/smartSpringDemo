package com.qm.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于返回json数据，该注解可用于类和方法上
 * @author qiumin
 * @create 2018/8/25 20:11
 * @desc
 **/
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QResponseBody {
}
