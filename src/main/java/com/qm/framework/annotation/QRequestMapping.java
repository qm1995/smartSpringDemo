package com.qm.framework.annotation;

import com.qm.framework.enumType.QRequestMethod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface QRequestMapping {
	String value() default "";

	QRequestMethod method() default QRequestMethod.ALL;
}
