package com.qm.test.action;

import com.qm.framework.annotation.*;
import com.qm.test.bean.User;
import com.qm.test.service.UserService;

import javax.servlet.http.HttpServletRequest;

@QController
@QRequestMapping("/user")
public class TestAction {
	
	@QAutworied
	private UserService userService;
	
	@QRequestMapping("/test")
    @QResponseBody
	public User test(@QRequestParam("name") String username,@QRequestParam("request") HttpServletRequest request
						,@QRequestParam("age") Integer age){
		User user = userService.getUser(username);
		user.setAge(age);
		request.setAttribute("user", user);
		return user;
	}
}
