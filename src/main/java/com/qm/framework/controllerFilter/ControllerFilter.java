package com.qm.framework.controllerFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * controller类拦截器
 * @author qiumin
 *
 */
public interface ControllerFilter {
	
	/**
	 * 拦截前所做的操作
	 * @param request
	 * @param response
	 */
	void preHandler(HttpServletRequest request,HttpServletResponse response);
	
	void afterHandler(HttpServletRequest request,HttpServletResponse response);
}
