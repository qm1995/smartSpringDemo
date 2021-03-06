package com.qm.framework.beanFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import com.qm.framework.annotation.QController;
import com.qm.framework.annotation.QService;
import com.qm.framework.constant.Constant;
import com.qm.framework.interceptor.HandlerInterceptor;
import com.qm.framework.utils.ClassUtils;
import com.qm.framework.utils.ReflectUtil;

public abstract class ListBeanFactory implements BeanFactory {
    // 存储所有的类的map
    protected static final Map<Class<?>, Object> BEAN_MAP = new ConcurrentHashMap<>();

    // 存储 拦截器
    protected static final Map<Class<?>, HandlerInterceptor> INTERCEPTOR_BEAN_MAP = new ConcurrentHashMap<>();

    private static final Set<Class<?>> CLASSES;

    static {
        CLASSES = ClassUtils.getClasses(Constant.getInstance().getPackagePath());
        fillBeanMap();
        fillInterceptorBeanMap();
    }

    public static Set<Class<?>> getServiceClass() {
        Set<Class<?>> serviceClasses = new HashSet<Class<?>>();
        for (Class<?> c : CLASSES) {
            if (c.isAnnotationPresent(QService.class)) {
                serviceClasses.add(c);
            }
        }
        return serviceClasses;
    }

    public static Set<Class<?>> getControllerClass() {
        Set<Class<?>> controllerClasses = new HashSet<>();
        for (Class<?> c : CLASSES) {
            if (c.isAnnotationPresent(QController.class)) {
                controllerClasses.add(c);
            }
        }
        return controllerClasses;
    }

    private static Set<Class<?>> getClassSet() {
        Set<Class<?>> allClasses = new HashSet<>();
        allClasses.addAll(getControllerClass());
        allClasses.addAll(getServiceClass());
        return allClasses;
    }

    private static Set<Class<?>> getInterceptorSet() {
        Set<Class<?>> interceptorClasses = new HashSet<>();
        for (Class<?> c : CLASSES) {
            if (ClassUtils.isSubClass(HandlerInterceptor.class, c)) {
                interceptorClasses.add(c);
            }
        }
        return interceptorClasses;
    }

    private static void fillBeanMap() {
        Set<Class<?>> allClasses = getClassSet();
        for (Class<?> c : allClasses) {
            if (!BEAN_MAP.containsKey(c)) {
                Object instances = ReflectUtil.getInstances(c);
                BEAN_MAP.put(c, instances);
            }
        }
    }

    private static void fillInterceptorBeanMap() {
        Set<Class<?>> interceptorClassSet = getInterceptorSet();
        for (Class<?> c : interceptorClassSet) {
            if (!INTERCEPTOR_BEAN_MAP.containsKey(c)) {
                Object instances = ReflectUtil.getInstances(c);
                INTERCEPTOR_BEAN_MAP.put(c, (HandlerInterceptor) instances);
            }
        }
    }

    public static Map<Class<?>, Object> getBeanMap() {
        return BEAN_MAP;
    }

    public static Map<Class<?>, HandlerInterceptor> getInterceptorBeanMap() {
        return INTERCEPTOR_BEAN_MAP;
    }

    /**
     * 获取所有拦截器，
     * 在这个demo中，默认给所有的请求都配置了拦截器
     * @return
     */
    public static List<HandlerInterceptor> getInterceptorList() {
        List<HandlerInterceptor> interceptorList = new ArrayList<>();
        for (Map.Entry<Class<?>, HandlerInterceptor> map : INTERCEPTOR_BEAN_MAP.entrySet()) {
            interceptorList.add(map.getValue());
        }
        // 根据 拦截器中的getOrder()方法进行排序
        Collections.sort(interceptorList, new Comparator<HandlerInterceptor>() {
            @Override
            public int compare(HandlerInterceptor o1, HandlerInterceptor o2) {
                return o1.getOrder() - o2.getOrder();
            }
        });
        return interceptorList;
    }

    public static Set<Class<?>> getSubClass(Class<?> parent) {
        Set<Class<?>> subClassSet = new HashSet<>();
        for (Class<?> c : CLASSES) {
            if (parent != c && ClassUtils.isSubClass(parent, c)) {
                subClassSet.add(c);
            }
        }
        return subClassSet;
    }

    public static Object getObject(Class<?> clazz) {
        return BEAN_MAP.get(clazz);
    }


}
