package com.qm.action;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;

import com.qm.bean.User;
import com.qm.framework.annotation.QAutworied;
import com.qm.framework.annotation.QController;
import com.qm.framework.annotation.QRequestMapping;
import com.qm.framework.annotation.QRequestParam;
import com.qm.framework.handlerMapping.Param;
import com.qm.service.UserService;

@QController
@QRequestMapping("/user")
public class TestAction {
	
	@QAutworied
	private UserService userService;
	
	@QRequestMapping("/test")
	public String test(@QRequestParam("name") String username,@QRequestParam("request") HttpServletRequest request
						,@QRequestParam("age") Integer age){
		User user = userService.getUser(username);
		user.setAge(age);
		request.setAttribute("user", user);
		return "test1";
	}
	
	public static void main(String[] args) throws NoSuchMethodException, SecurityException {
		Method method = TestAction.class.getDeclaredMethods()[1];
		Parameter[] parameters = method.getParameters();
		for(Parameter param : parameters){
			if(!param.isNamePresent()){
				System.out.println("null");
				continue;
			}
			System.out.println(param.getName());
		}
	}
	
}
