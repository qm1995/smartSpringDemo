package com.qm.framework.controller;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qm.framework.beanFactory.ListBeanFactory;
import com.qm.framework.constant.Constant;
import com.qm.framework.handlerMapping.Handler;
import com.qm.framework.utils.ClassCastUtil;
import com.qm.framework.utils.ControllerUtils;
import com.qm.framework.utils.IOCUtils;
import com.qm.framework.utils.ReflectUtil;
import com.qm.framework.utils.StringUtils;
import com.qm.framework.utils.WebUtils;

/**
 * Servlet implementation class ManagerServlet
 */
public class ManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(ManagerServlet.class);
	private static final String PACKAGE_PATH = "PACKAGE_PATH";
	
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
        //用来封装每个controller的信息
        ControllerUtils.init(); 
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
		String contextPath = request.getContextPath();
		if(contextPath == null || contextPath.length() <= 0){
			WebUtils.sendError(response, 404, "页面不存在");
		}
		String url = requestURI.substring(contextPath.length());
		logger.debug("请求方式{},请求路径{},",method,requestURI);
		Handler handler = ControllerUtils.getHandler(url.substring(0,url.lastIndexOf(".")==-1?url.length():url.lastIndexOf(".")), method);
		if(handler != null){
			
			Class<?> controllerClass = handler.getControllerClass();
			Method method2 = handler.getMethod();
			Object object = ListBeanFactory.getObject(controllerClass);
			List<Object> paramValue = getValue(request, handler.getParam(),response);
			Object view = ReflectUtil.invokeMethod(object, method2, paramValue);
			logger.debug("类{}中的{}方法被访问，参数param{}，返回视图结果{}",controllerClass.getName(),method2.getName(),paramValue.toArray(),(String)view);
			//response.sendRedirect(request.getContextPath()+"/"+(String)view+".jsp");
			request.getRequestDispatcher("/"+(String)view+".jsp").forward(request, response);
			test(request);
			return;
		}else{
			
			WebUtils.sendError(response, 404, "页面不存在");
		}
	}
	
	
	public List<Object> getValue(HttpServletRequest request,Map<String,Class<?>> paramMap,HttpServletResponse response){
		List<Object> paramValue = new ArrayList<>();
		for(Entry<String, Class<?>> m:paramMap.entrySet()){
			String paramName = m.getKey();
			Class<?> paramType = m.getValue();
			if(paramType == String.class){
				paramValue.add(request.getParameter(paramName));
			}else if(paramType == int.class || paramType == Integer.class){
				String parameter = request.getParameter(paramName);
				paramValue.add(ClassCastUtil.StringToInt(parameter));
			}else if(paramType == long.class || paramType == Long.class){
				String parameter = request.getParameter(paramName);
				paramValue.add(ClassCastUtil.StringToLong(parameter));
			}else if(paramType == HttpServletRequest.class){
				paramValue.add(request);
			}else if(paramType == HttpServletResponse.class){
				paramValue.add(response);
			}
		}
		return paramValue;
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	private void test(HttpServletRequest request){
		Enumeration<String> attributeNames = request.getAttributeNames();
		System.out.println((attributeNames == null)+"::"+attributeNames.hasMoreElements());
		while(attributeNames.hasMoreElements()){
			String element = attributeNames.nextElement();
			System.out.println("element="+element);
		}
	}
}
