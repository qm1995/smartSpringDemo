package com.qm.test.service.impl;

import com.qm.framework.annotation.QAutworied;
import com.qm.framework.annotation.QService;
import com.qm.test.bean.User;
import com.qm.test.dao.UserDao;
import com.qm.test.service.UserService;


@QService
public class UserServiceImpl implements UserService {
	
	@QAutworied
	private UserDao userDao;
	
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userDao.getUser(username);
	}
	
}
