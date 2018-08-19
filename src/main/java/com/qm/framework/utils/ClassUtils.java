package com.qm.framework.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 工具类
 * @author qiumin
 *
 */
public class ClassUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);
	/**
	 * 得到加载器
	 * @return 当前线程的类加载器
	 */
	public static ClassLoader getClassLoader(){
		return Thread.currentThread().getContextClassLoader();
	}
	
	
	/**
	 * 根据全路径名得到Class对象
	 * @param packageName
	 * @param initialize
	 * @return
	 */
	public static Class<?> loadClass(String packageName,boolean initialize){
		Class<?> cls = null;
		try {
			cls = Class.forName(packageName, initialize, getClassLoader());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.toString());
		}
		return cls;
	}
	
	/**
	 * 获取packageName路径下的所有类的class对象
	 * @param packageName
	 * @return class对象集合
	 */
	public static Set<Class<?>> getClasses(String packageName){
		Set<Class<?>> classes = new HashSet<>();
		try {
			Enumeration<URL> urls = getClassLoader().getResources(packageName.replaceAll("\\.", "/"));
			while(urls.hasMoreElements()){
				URL url = urls.nextElement();
				String protocol = url.getProtocol();
				if("file".equals(protocol)){
					String path = url.getPath().replaceAll("%20", " ");//防止路径有空字符串，如" "
					addClasses(classes, packageName, path);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return classes;
	}
	
	/**
	 * 添加类
	 * @param classes
	 * @param packageName 包名
	 * @param path  路径
	 */
	public static void addClasses(Set<Class<?>> classes,String packageName,String path){
		File[] list = new File(path).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				// TODO Auto-generated method stub
				//只留下后缀为class的文件或者目录
				return (file.isFile()&&file.getName().endsWith("class")) || file.isDirectory();
			}
			
		});
		for(File f:list){
			String fileName = f.getName();
			if(f.isFile()){
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if(StringUtils.isNotEmpty(className)){
					className = packageName + "." + className; 
				}
				doAddClass(classes, className);
			}else{
				String subPath = fileName;
				if(StringUtils.isNotEmpty(subPath)){
					subPath = path + "/" + subPath;
				}
				
				String subPackageName = fileName;
				if(StringUtils.isNotEmpty(subPackageName)){
					subPackageName = packageName + "." + subPackageName;
				}
				addClasses(classes, subPackageName, subPath);
			}
		}
	}
	public static void doAddClass(Set<Class<?>> classes,String packageName){
		Class<?> loadClass = loadClass(packageName, false);
		classes.add(loadClass);
		return;
	}
	
	/**
	 * 判断child有没有实现parent
	 * @param parent
	 * @param child
	 * @return
	 */
	public static boolean isSubClass(Class<?> parent,Class<?> child){
		Class<?>[] interfaces = child.getInterfaces();
		if(interfaces == null || interfaces.length < 1){
			return false;
		}
		for(Class<?> c:interfaces){
			if(c == parent){
				return true;
			}
		}
		return false;
	}
	public static void main(String[] args) {
		Set<Class<?>> classes = getClasses("com.qm");
		for(Class<?> c:classes){
			System.out.println(c.getName());
		}
		System.out.println(classes.size());
	}
}
