package com.qm.framework.handlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 封装每个controller中对应的每个方法，其方法必有@QRequestMapping注解
 * @author qiumin
 *
 */
public class HandlerAdaptor {
	
	private Method method;
	//method对应的参数，参数名称，和类型
	private Map<String,Class<?>> param;

	public HandlerAdaptor(Method method, Map<String, Class<?>> param) {
		super();
		this.method = method;
		this.param = param;
	}


	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Map<String, Class<?>> getParam() {
		return param;
	}

	public void setParam(Map<String, Class<?>> param) {
		this.param = param;
	}

	
	
	
	
}
