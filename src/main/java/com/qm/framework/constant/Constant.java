package com.qm.framework.constant;

/**
 * 存储扫描路径的单例类
 * @author qiumin
 *
 */
public final class Constant {
	private String packagePath;
	private volatile static Constant constant; 
	private Constant(){}
	public static Constant getInstance(){
		if(constant == null){
			synchronized(Constant.class){
				if(constant == null){
					constant = new Constant();
				}
			}
		}
		return constant;
	}
	public String getPackagePath() {
		return packagePath;
	}

	public void setPackagePath(String packagePath) {
		this.packagePath = packagePath;
	}
	
	
}
