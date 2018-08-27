package com.qm.framework.chain;


import com.alibaba.fastjson.JSON;
import com.qm.framework.annotation.QResponseBody;
import com.qm.framework.handlerMapping.HandlerMethod;
import com.qm.framework.interceptor.HandlerInterceptor;
import com.qm.framework.utils.ClassCastUtil;
import com.qm.framework.utils.ReflectUtil;
import com.qm.framework.view.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: qiumin
 * @create: 2018-08-20 11:36
 **/
public class HandlerChain {

    private List<HandlerInterceptor> interceptors;

    private Object handler;

    private HandlerMethod handlerMethod;

    public HandlerChain(List<HandlerInterceptor> interceptors, Object handler,HandlerMethod handlerMethod) {
        this.interceptors = interceptors;
        this.handler = handler;
        this.handlerMethod = handlerMethod;

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

    public HandlerMethod getHandlerMethod() {
        return handlerMethod;
    }

    public void setHandlerMethod(HandlerMethod handlerMethod) {
        this.handlerMethod = handlerMethod;
    }

    public boolean applyPreHandler(HttpServletRequest request, HttpServletResponse response){
        if(interceptors != null && interceptors.size() > 0){
            for(HandlerInterceptor interceptor : interceptors){
                if(!interceptor.preHandler(request,response,handler)){
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

    public ModelAndView invokeMethod(HttpServletRequest request,HttpServletResponse response){
        List<Object> value = getValue(request, handlerMethod.getParam(), response);
        Method method = handlerMethod.getMethod();
        Object result = ReflectUtil.invokeMethod(handler, method, value);
        ModelAndView view = new ModelAndView();
        if(method.getAnnotation(QResponseBody.class) != null ||
                handler.getClass().getAnnotation(QResponseBody.class) != null){
            view.setData(result);
        }else{
            view.setView(result.toString());
        }
        return view;

    }

    public List<Object> getValue(HttpServletRequest request, Map<String,Class<?>> paramMap, HttpServletResponse response){
        List<Object> paramValue = new ArrayList<>();
        for(Map.Entry<String, Class<?>> m:paramMap.entrySet()){
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
}
