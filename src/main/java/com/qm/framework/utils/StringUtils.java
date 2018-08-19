package com.qm.framework.utils;

public class StringUtils {
	
	public static boolean isEmpty(String str){
		return str == null || str.equals("");
	}
	
	public static boolean isNotEmpty(String str){
		return !isEmpty(str);
	}
}
