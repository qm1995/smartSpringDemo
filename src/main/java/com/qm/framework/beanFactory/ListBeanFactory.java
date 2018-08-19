package com.qm.framework.beanFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.qm.framework.annotation.QController;
import com.qm.framework.annotation.QService;
import com.qm.framework.constant.Constant;
import com.qm.framework.utils.ClassUtils;
import com.qm.framework.utils.ReflectUtil;

public abstract class ListBeanFactory implements BeanFactory {
	
	protected static final Map<Class<?>, Object> BEANMAP_SET = new ConcurrentHashMap<>();
	private static final Set<Class<?>> CLASSES;
	static{
		CLASSES = ClassUtils.getClasses(Constant.getInstance().getPackagePath());
		fillBeanMap();
	}
	public static Set<Class<?>> getServiceClass(){
		Set<Class<?>> serviceClasses = new HashSet<Class<?>>();
		for(Class<?> c:CLASSES){
			if(c.isAnnotationPresent(QService.class)){
				serviceClasses.add(c);
			}
		}
		return serviceClasses;
	}
	
	public static Set<Class<?>> getControllerClass(){
		Set<Class<?>> controllerClasses = new HashSet<>();
		for(Class<?> c:CLASSES){
			if(c.isAnnotationPresent(QController.class)){
				controllerClasses.add(c);
			}
		}
		return controllerClasses;
	}
	private static Set<Class<?>> getClassSet(){
		Set<Class<?>> allClasses = new HashSet<>();
		allClasses.addAll(getControllerClass());
		allClasses.addAll(getServiceClass());
		return allClasses;
	}
	private static void fillBeanMap(){
		Set<Class<?>> allClasses = getClassSet();
		for(Class<?> c: allClasses){
			if(!BEANMAP_SET.containsKey(c)){
				Object instances = ReflectUtil.getInstances(c);
				BEANMAP_SET.put(c, instances);
			}
		}
	}
	
	public static Map<Class<?>, Object> getBeanmapSet() {
		return BEANMAP_SET;
	}

	public static Set<Class<?>> getSubClass(Class<?> parent){
		Set<Class<?>> subClassSet = new HashSet<>();
		for(Class<?> c:CLASSES){
			if(parent != c && ClassUtils.isSubClass(parent, c)){
				subClassSet.add(c);
			}
		}
		return subClassSet;
	}

	public static Object getObject(Class<?> clazz){
		return BEANMAP_SET.get(clazz);
	}
	
	
}
