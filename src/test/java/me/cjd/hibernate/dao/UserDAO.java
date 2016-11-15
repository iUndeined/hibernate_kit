package me.cjd.hibernate.dao;

import me.cjd.pojo.TestUser;
import me.cjd.hibernate.factory.DefaultSessionDAO;

public class UserDAO extends DefaultSessionDAO<TestUser, Integer> {
	
	public static final UserDAO me = new UserDAO();
	
}