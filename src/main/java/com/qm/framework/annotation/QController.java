package com.qm.framework.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.lang.model.element.Element;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface QController {
	String value() default "";
}
