package com.qm.framework.chain;

import java.util.ArrayList;
import java.util.List;

import com.qm.framework.controllerFilter.ControllerFilter;

/**
 * controller类拦截器执行链
 * @author qiumin
 *
 */
public class ProxyChain {
	private static final List<ControllerFilter> chains;
	private static int index;
	static{
		chains = new ArrayList<ControllerFilter>();
	}
	
	public Object doChain(){
		while(index != chains.size()){
			//chains.get(index).preHandler(request, response);
		}
		return null;
	}
}
