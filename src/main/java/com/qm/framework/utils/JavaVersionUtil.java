package com.qm.framework.utils;

public class JavaVersionUtil {

    private static final String JDK19 = "JDK1.9";

    private static final String JDK18 = "JDK1.8";

    private static final String JDK17 = "JDK1.7";

    private static final String JDK16 = "JDK1.6";

    public static String getJavaVersion(){
        String jdkVersion = System.getProperty("java.version");
        if(jdkVersion.contains("1.9")){
            return JDK19;
        }else if(jdkVersion.contains("1.8")){
            return JDK18;
        }else if(jdkVersion.contains("1.7")){
            return JDK17;
        }
        return JDK16;
    }

    public static boolean isJDK18Version(){
        return JDK18.endsWith(getJavaVersion());
    }
}
