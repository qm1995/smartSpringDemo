package com.qm.framework.view;

/**
 * 在这里只简单的对controller层返回的是路径还是json数据做了处理
 * 只处理了forward和返回json数据两种情况，重定向暂不支持
 * 其中view和data两者之间一定有一个没有，一个有
 *    若view有值，则默认forward
 *    若data有值，则默认返回json数据
 * @author qiumin
 * @create 2018/8/25 19:54
 * @desc
 **/
public class ModelAndView {

    private String view;// 路径

    private Object data;// json数据

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
