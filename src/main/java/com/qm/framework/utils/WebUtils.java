package com.qm.framework.utils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WebUtils {

    private static final String SUFFIX = ".jsp";
	public static void sendRedirect(HttpServletResponse response,String path){
		try {
			response.sendRedirect(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			sendError(response, 404,"页面未找到");
		}
	}

	public static void forward(HttpServletRequest request,HttpServletResponse response,String path) throws ServletException, IOException {
	    request.getRequestDispatcher("/"+path+SUFFIX).forward(request,response);
    }
	public static void sendError(HttpServletResponse response,int errorCode,String message){
		try {
			response.sendError(errorCode,message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
