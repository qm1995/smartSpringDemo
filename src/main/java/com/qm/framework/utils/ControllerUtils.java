package com.qm.framework.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.qm.framework.chain.HandlerChain;
import com.qm.framework.handlerMapping.DefaultHandlerMapping;
import com.qm.framework.handlerMapping.HandlerAdaptor;
import com.qm.framework.handlerMapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qm.framework.annotation.QRequestMapping;
import com.qm.framework.annotation.QRequestParam;
import com.qm.framework.beanFactory.ListBeanFactory;
import com.qm.framework.handlerMapping.QRequest;

/**
 * 用来封QController注解
 * @author qiumin
 *
 */
public class ControllerUtils {
	
	private static final Map<QRequest, DefaultHandlerMapping> CONTROLLER_BEANMAP = new ConcurrentHashMap<>();
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerUtils.class);

	/**
	 * @Author qiumin
	 * @Description 初始化 controller类中的requestMapping的路径及其所有方法的路径和method
	 * @Date 2018/8/20 10:27
	 * @Param []
	 * @return void
	 **/
	public static void init(HandlerMapping handlerMapping){
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
                    String method = annotation.method().getMethod();
                    QRequest qr = new QRequest(controllerMapping+url, method.toLowerCase());
					LOGGER.debug("路径{}被添加", controllerMapping+url);
					Parameter[] parameters = m.getParameters();
					Map<String,Class<?>> map = new LinkedHashMap<>();
					for(Parameter parameter:parameters){
						QRequestParam param = parameter.getAnnotation(QRequestParam.class);
						if(param != null){
							String value = param.value();
							map.put(value, parameter.getType());
						}else{

                        }
					}
                    HandlerAdaptor adaptor = new HandlerAdaptor(m,null);
				}
                HandlerChain chain = new HandlerChain(ListBeanFactory.getInterceptorList(),ReflectUtil.getInstances(c));
                handlerMapping.put(qr,chain);
            }
		}
	}
	
}
