package com.qm.service.impl;

import com.qm.bean.User;
import com.qm.dao.UserDao;
import com.qm.framework.annotation.QAutworied;
import com.qm.framework.annotation.QService;
import com.qm.service.UserService;


@QService
public class UserServiceImpl implements UserService{
	
	@QAutworied
	private UserDao user;
	
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return user.getUser(username);
	}
	
}
