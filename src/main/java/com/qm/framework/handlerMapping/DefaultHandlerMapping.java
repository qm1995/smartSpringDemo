package com.qm.framework.handlerMapping;


import com.qm.framework.chain.HandlerChain;
import com.qm.framework.utils.WebUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认mapping处理起
 * @author: qiumin
 * @create: 2018-08-20 14:37
 **/
public class DefaultHandlerMapping implements HandlerMapping{

    // 将 url和责任链组装成一个map，
    private Map<QRequest,HandlerChain> handlerChainMap = new ConcurrentHashMap<>();

    @Override
    public HandlerChain getHandler(HttpServletRequest request){
        String method = request.getMethod();
        String requestURI = request.getRequestURI();
        String contextPath = request.getContextPath();
        String url = requestURI.substring(contextPath.length());// 结果为映射路径

        QRequest qRequest = new QRequest(url,method);

        HandlerChain handlerChain = handlerChainMap.get(qRequest);
        return handlerChain;

    }

    @Override
    public void put(QRequest request,HandlerChain handlerChain){
        handlerChainMap.put(request,handlerChain);
    }
}

