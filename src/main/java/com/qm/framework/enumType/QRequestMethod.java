package com.qm.framework.enumType;


/*
 *
 * @author: qiumin
 * @create: 2018-08-20 10:24
 **/
public enum QRequestMethod {
    GET("get"),POST("post"),ALL("all");

    private String method;

    private QRequestMethod(String method){
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
