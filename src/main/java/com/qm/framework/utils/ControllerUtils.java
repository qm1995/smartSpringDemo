package com.qm.framework.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qm.framework.annotation.QRequestMapping;
import com.qm.framework.annotation.QRequestParam;
import com.qm.framework.beanFactory.ListBeanFactory;
import com.qm.framework.handlerMapping.Handler;
import com.qm.framework.handlerMapping.QRequest;

/**
 * 用来封QController注解
 * @author qiumin
 *
 */
public class ControllerUtils {
	
	private static final Map<QRequest, Handler> CONTROLLER_BEANMAP = new ConcurrentHashMap<>();
	private static final Logger logger = LoggerFactory.getLogger(ControllerUtils.class);
	
	public static void init(){
		Set<Class<?>> controllerClassSet = ListBeanFactory.getControllerClass();
		for(Class<?> c:controllerClassSet){
			QRequestMapping qRequest = c.getAnnotation(QRequestMapping.class);
			String controllerMapping = (qRequest == null?"":qRequest.value());
			StringBuilder requestPath = new StringBuilder();
			if(StringUtils.isNotEmpty(controllerMapping)){
				requestPath.append(controllerMapping);
			}else{
				controllerMapping = "";
			}
			Method[] methods = c.getDeclaredMethods();
			for(Method m:methods){
				QRequestMapping annotation = m.getAnnotation(QRequestMapping.class);
				if(annotation != null){
					String url = annotation.value();
					QRequest qr = new QRequest(controllerMapping+url, "get");
					logger.debug("路径{}被添加", controllerMapping+url);
					Parameter[] parameters = m.getParameters();
					Map<String,Class<?>> map = new LinkedHashMap<>();
					for(Parameter parameter:parameters){
						QRequestParam param = parameter.getAnnotation(QRequestParam.class);
						if(param != null){
							String value = param.value();
							map.put(value, parameter.getType());
						}
					}
					Handler handler = new Handler(c, m,map);
					CONTROLLER_BEANMAP.put(qr, handler);
				}
			}
		}
	}
	
	public static Handler getHandler(String url,String method){
		QRequest request = new QRequest(url, method);
		return CONTROLLER_BEANMAP.get(request);
	}
}
