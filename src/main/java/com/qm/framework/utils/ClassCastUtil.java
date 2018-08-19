package com.qm.framework.utils;


/**
 * 类型转换工具类
 * @author qiumin
 *
 */
public class ClassCastUtil {
	
	/**
	 * 将value字符串转为
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int StringToInt(String value,Integer defaultValue){
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}
		int parseValue = -1;
		try {
			parseValue = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parseValue = defaultValue;
		}
		return parseValue;
	}
	
	
	public static int StringToInt(String value){
		return StringToInt(value, 0);
	}
	
	public static long StringToLong(String value,long defaultValue){
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}
		long parseValue = -1l;
		try {
			parseValue = Long.parseLong(value);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			parseValue = defaultValue;
		}
		return parseValue;
	}
	
	public static long StringToLong(String value){
		return StringToLong(value,0);
	}
}
