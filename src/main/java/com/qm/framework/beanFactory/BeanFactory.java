package com.qm.framework.beanFactory;

public interface BeanFactory {
	
	<T> T getBean(Class<T> clazz);
}
