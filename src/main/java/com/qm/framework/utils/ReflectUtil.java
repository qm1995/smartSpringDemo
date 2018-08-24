package com.qm.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ReflectUtil {

	public static Object getInstances(Class<?> clazz){
		if(clazz==null){
			throw new NullPointerException();
		}
		Object bean = null;
		try {
			bean = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	}
	public static Object invokeMethod(Object target,Method method,List<Object> params){
		Object result = null;
		try {
			if(params.size() == 0){
				result = method.invoke(target);
			}else{
				result = method.invoke(target, params.toArray());
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static void setField(Object targetClass,Field field,Object fieldValue){
		try {
			field.setAccessible(true);
			field.set(targetClass, fieldValue);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
