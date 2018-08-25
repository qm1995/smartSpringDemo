package com.qm.framework.controller;

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qm.framework.chain.HandlerChain;
import com.qm.framework.handlerMapping.DefaultHandlerMapping;
import com.qm.framework.handlerMapping.HandlerMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qm.framework.constant.Constant;
import com.qm.framework.utils.ClassCastUtil;
import com.qm.framework.utils.ControllerUtils;
import com.qm.framework.utils.IOCUtils;
import com.qm.framework.utils.StringUtils;
import com.qm.framework.utils.WebUtils;

/**
 * Servlet implementation class ManagerServlet
 */
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ManagerServlet.class);
	private static final String PACKAGE_PATH = "PACKAGE_PATH";
	private static HandlerMapping handlerMapping;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
        
    }

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		logger.debug("{}正在初始化--------------", ManagerServlet.class.getName());
        ServletConfig servletConfig = this.getServletConfig();
        //获取扫描包路径
        String package_path = servletConfig.getInitParameter(PACKAGE_PATH);
        if(StringUtils.isEmpty(package_path)){
        	package_path = "com.qm";
        }
        logger.debug("获取的扫描包路径为{}", package_path);
        if(StringUtils.isEmpty(package_path)){
        	throw new RuntimeException("the package_path is not null");
        }
        Constant.getInstance().setPackagePath(package_path);
        //依赖注入
        IOCUtils.dependInjectOject();
        handlerMapping = new DefaultHandlerMapping();
		super.init();
		logger.debug("{}初始化完成--------------", ManagerServlet.class.getName());
	}





	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getMethod().toLowerCase();
		String requestURI = request.getRequestURI();
		logger.debug("请求方式{},请求路径{}",method,requestURI);
        HandlerChain handlerChain = handlerMapping.getHandler(request);
        try {
            if (handlerChain != null) {
                boolean preHandler = handlerChain.applyPreHandler(request, response);
                if (!preHandler) {
                    return;
                }
                String view = handlerChain.invokeMethod(request, response);
                handlerChain.applyAfterHandler(request, response);
                WebUtils.forward(request,response,view);
            } else {
                WebUtils.sendError(response, 404, "页面不存在");
            }
        } catch (Exception e){
            WebUtils.sendError(response,500,e.toString());
        }
    }
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
