package com.qm.test.service.impl;

import com.qm.framework.annotation.QAutworied;
import com.qm.framework.annotation.QService;
import com.qm.test.bean.User;
import com.qm.test.dao.UserDAO;
import com.qm.test.service.UserService;


@QService
public class UserServiceImpl implements UserService {
	
	@QAutworied
	private UserDAO userDAO;
	
	@Override
	public User getUser(String username) {
		// TODO Auto-generated method stub
		return userDAO.getUser(username);
	}
	
}
