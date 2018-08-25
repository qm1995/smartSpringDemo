package com.qm.framework.interceptor;


import com.qm.framework.annotation.QInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: qiumin
 * @create: 2018-08-20 11:28
 **/
public class DefaultHandlerInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandler(HttpServletRequest request, HttpServletResponse response,Object handler) {
        System.out.println("默认拦截器-------拦截器前的操作");
        return true;
    }

    @Override
    public void afterHandler(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("默认拦截器-------拦截器后的操作");
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
