package com.qm.framework.handlerMapping;


import com.qm.framework.annotation.QRequestMapping;
import com.qm.framework.annotation.QRequestParam;
import com.qm.framework.beanFactory.ListBeanFactory;
import com.qm.framework.chain.HandlerChain;
import com.qm.framework.enumType.QRequestMethod;
import com.qm.framework.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 默认mapping处理起
 * @author: qiumin
 * @create: 2018-08-20 14:37
 **/
public class DefaultHandlerMapping implements HandlerMapping{

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHandlerMapping.class);

    // 将 url和责任链组装成一个map，
    private Map<QRequest,HandlerMethod> REGISTER_MAP = new ConcurrentHashMap<>();

    public DefaultHandlerMapping() {
        init();
    }

    public void init(){
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
                    LOGGER.info("路径{}被添加", controllerMapping+url);
                    Parameter[] parameters = m.getParameters();
                    Map<String,Class<?>> map = new LinkedHashMap<>();
                    for(Parameter parameter:parameters){
                        String parameterName = parameter.getName();
                        Class<?> parameterType = parameter.getType();
                        QRequestParam param = parameter.getAnnotation(QRequestParam.class);
                        if(param != null){
                            parameterName = param.value();
                        }
                        if(StringUtils.isEmpty(parameterName)){
                            throw new IllegalArgumentException("请使用jdk1.8版本且配上{-parameters}参数或者用QRequestParam注解标上参数名");
                        }
                        map.put(parameterName,parameterType);
                    }
                    HandlerMethod handlerMethod = new HandlerMethod(m,ListBeanFactory.getObject(c),map);
                    registerHandler(qr, handlerMethod);
                }
            }
        }
    }

    @Override
    public HandlerChain getHandler(HttpServletRequest request){
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestURI.substring(contextPath.length());// 结果为映射路径

        QRequest qRequest = new QRequest(url,method);
        HandlerMethod handlerMethod = getHandlerMethod(qRequest,request);

        if(handlerMethod == null){
            return null;
        }
        HandlerChain handlerChain = new HandlerChain(ListBeanFactory.getInterceptorList(), handlerMethod.getHandler(), handlerMethod);
        return handlerChain;

    }

    private HandlerMethod getHandlerMethod(QRequest qRequest,HttpServletRequest request){
        HandlerMethod handlerMethod = this.REGISTER_MAP.get(qRequest);
        if(handlerMethod == null){
            String requestURI = request.getRequestURI();
            String contextPath = request.getContextPath();
            String currentRequestPath = requestURI.substring(contextPath.length());// 结果为映射路径
            String method = request.getMethod().toLowerCase();
            for (Map.Entry<QRequest,HandlerMethod> map : this.REGISTER_MAP.entrySet()){
                QRequest key = map.getKey();
                HandlerMethod value = map.getValue();
                Matcher matcher = Pattern.compile(key.getUrl()).matcher(currentRequestPath);
                if(matcher.matches() && (QRequestMethod.ALL.getMethod().equals(key.getMethod()) || method.equalsIgnoreCase(key.getMethod()))){
                    handlerMethod = value;
                    break;
                }
            }
        }
        return handlerMethod;
    }

    private void registerHandler(QRequest request,HandlerMethod handlerMethod){
        if(this.REGISTER_MAP.containsKey(request)){
            throw new IllegalArgumentException(request.getUrl()+"重复了");
        }
        this.REGISTER_MAP.put(request, handlerMethod);

    }
}

