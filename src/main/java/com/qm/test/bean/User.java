package com.qm.test.bean;


public class User {
	
	private String username;
	
	private int age;
	
	private String sex;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Override
	public String toString() {
		return "User{" +
				"username='" + username + '\'' +
				", age=" + age +
				", sex='" + sex + '\'' +
				'}';
	}
}
