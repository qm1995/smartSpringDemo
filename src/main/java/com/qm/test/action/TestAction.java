package com.qm.test.action;

import com.qm.framework.annotation.QAutworied;
import com.qm.framework.annotation.QController;
import com.qm.framework.annotation.QRequestMapping;
import com.qm.framework.annotation.QRequestParam;
import com.qm.test.bean.User;
import com.qm.test.service.UserService;

import javax.servlet.http.HttpServletRequest;

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
}
