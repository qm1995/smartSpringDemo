package com.qm.test.dao.impl;

import com.qm.framework.annotation.QService;
import com.qm.test.bean.User;
import com.qm.test.dao.UserDao;


@QService
public class UserDaoImpl implements UserDao {

	@Override
	public User getUser(String username) {
		User u = new User();
		u.setUsername(username);
		u.setAge(14);
		u.setSex("男");
		return u;
	}

}
