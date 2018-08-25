package com.qm.framework.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: qiumin
 * @create: 2018-08-20 11:25
 **/
public interface HandlerInterceptor{

    /**
     * @Author qiumin
     * @Description 拦截器前处理的逻辑
     * @Date 2018/8/20 11:27
     * @Param [request, response,handler]
     * @return boolean
     **/
    boolean preHandler(HttpServletRequest request, HttpServletResponse response,Object handler);

    /**
     * @Author qiumin
     * @Description 拦截器后处理的逻辑
     * @Date 2018/8/20 11:27
     * @Param [request, response]
     * @return void
     **/
    void afterHandler(HttpServletRequest request,HttpServletResponse response);

    /**
     * @Author qiumin
     * @Description 拦截器顺序数
     * @Date 2018/8/25 11:28
     * @return int
     **/
    int getOrder();
}
