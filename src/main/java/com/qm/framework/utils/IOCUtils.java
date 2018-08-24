package com.qm.framework.utils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.qm.framework.annotation.QAutworied;
import com.qm.framework.beanFactory.ListBeanFactory;

/**
 * 实现依赖注入的工具类
 * @author qiumin
 *
 */
public class IOCUtils {
	
	/**
	 * @Author qiumin
	 * @Description 实现依赖注入的方法
	 * @Date 2018/8/20 10:17
	 * @Param []
	 * @return void
	 **/
	public static void dependInjectOject(){
		Map<Class<?>, Object> beanMapSet = ListBeanFactory.getBeanMap();
		if(beanMapSet == null || beanMapSet.size() == 0 ){
			return;
		}
		for(Entry<Class<?>, Object> entry:beanMapSet.entrySet()){
			Class<?> key = entry.getKey();
			Object bean = entry.getValue();
			//获取key的所有字段
			Field[] fields = key.getDeclaredFields();
			if(fields == null || fields.length < 1){
				continue;
			}
			for(Field f:fields){
				Class<?> type = f.getType();//得到字段类型
				if(f.isAnnotationPresent(QAutworied.class)){
					if(!type.isInterface()){//字段不是接口，直接注入
						Object fieldValue = beanMapSet.get(type);
						if(fieldValue == null){
							throw new RuntimeException("not found ["+type.getName()+"] class");
						}
						ReflectUtil.setField(bean, f, fieldValue);
					}else{//是接口，获取其实现类集合
						Set<Class<?>> subClass = ListBeanFactory.getSubClass(type);
						//判断该类型是否有子类或多于一个子类
						if(subClass.size() > 1 || subClass.size() == 0){
							throw new RuntimeException("autowired is failed");
						}else{
							Object value = null;
							for(Class<?> c:subClass){
								value = beanMapSet.get(c);
							}
							//若为null，则表示beanFactory未初始化成功
							if(value == null){
								throw new RuntimeException("not found ["+type.getName()+"] implement class");
							}
							ReflectUtil.setField(bean, f, value);
						}
					}
				}
				
			}
		}
	}
}
