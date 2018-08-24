package com.qm.framework.chain;


import com.qm.framework.handlerMapping.HandlerAdaptor;
import com.qm.framework.interceptor.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: qiumin
 * @create: 2018-08-20 11:36
 **/
public class HandlerChain {

    private List<HandlerInterceptor> interceptors;

    private Object handler;

    private List<HandlerAdaptor> handleAdaptors;

    public HandlerChain(List<HandlerInterceptor> interceptors, Object handler,List<HandlerAdaptor> handleAdaptors) {
        this.interceptors = interceptors;
        this.handler = handler;
        this.handleAdaptors = handleAdaptors;

    }

    public List<HandlerInterceptor> getInterceptors() {
        return interceptors;
    }

    public void setInterceptors(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    public Object getHandler() {
        return handler;
    }

    public void setHandler(Object handler) {
        this.handler = handler;
    }

    public boolean applyPreHandler(HttpServletRequest request, HttpServletResponse response){
        if(interceptors != null && interceptors.size() > 0){
            for(HandlerInterceptor interceptor : interceptors){
                if(!interceptor.preHandler(request,response)){
                    return false;
                }
            }
        }
        return true;
    }

    public void applyAfterHandler(HttpServletRequest request,HttpServletResponse response){
        if(interceptors != null && interceptors.size() > 0){
            for(HandlerInterceptor interceptor : interceptors){
                interceptor.afterHandler(request,response);
            }
        }
    }
}
