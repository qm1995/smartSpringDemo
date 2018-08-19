package com.qm.dao.impl;

import com.qm.bean.User;
import com.qm.dao.UserDao;
import com.qm.framework.annotation.QService;


@QService
public class UserDaoImpl implements UserDao{

	@Override
	public User getUser(String username) {
		User u = new User();
		u.setUsername(username);
		u.setAge(14);
		u.setSex("男");
		return u;
	}

}
