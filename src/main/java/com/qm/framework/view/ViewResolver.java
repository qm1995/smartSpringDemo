package com.qm.framework.view;

import com.alibaba.fastjson.JSON;
import com.qm.framework.utils.StringUtils;
import com.qm.framework.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 视图解析器，
 * 为了处理方便，使用静态方法进行处理
 * @author qiumin
 * @create 2018/8/25 20:02
 * @desc
 **/
public class ViewResolver {


    public static void resolverView(HttpServletRequest request, HttpServletResponse response,ModelAndView view) throws ServletException, IOException {
        if(StringUtils.isNotEmpty(view.getView())){
            WebUtils.forward(request,response,view.getView());
        }else if(view.getData() != null){
            PrintWriter writer = null;
            try {
                response.setContentType("application/json;charset=UTF-8");
                String jsonString = JSON.toJSONString(view.getData());
                writer = response.getWriter();
                writer.write(jsonString);
            } finally {
                if(writer != null){
                    writer.close();
                }
            }

        }
    }

}
