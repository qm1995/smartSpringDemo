package com.qm.framework.handlerMapping;

import com.qm.framework.chain.HandlerChain;

import javax.servlet.http.HttpServletRequest;

/*
 *
 * @author: qiumin
 * @create: 2018-08-20 14:45
 **/
public interface HandlerMapping {

    HandlerChain getHandler(HttpServletRequest request);

}
