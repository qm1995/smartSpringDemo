package com.qm.framework.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.qm.framework.handlerMapping.DefaultHandlerMapping;
import com.qm.framework.handlerMapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	}
	
}
