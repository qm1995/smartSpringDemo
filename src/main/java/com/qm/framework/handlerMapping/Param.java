package com.qm.framework.handlerMapping;

import java.util.Map;

public class Param {
	private Map<String, Object> paramMap;

	public Param(Map<String, Object> paramMap) {
		super();
		this.paramMap = paramMap;
	}

	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	
	
	public Object get(String key){
		return this.paramMap.get(key);
	}
	/*public Object toArray(){
		Map<String, Object> map = this.paramMap;
		for(Entry<String, Object> )
		Collections.
	}*/
}
