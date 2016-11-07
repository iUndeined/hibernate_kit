package me.cjd.hibernate.dao;

import me.cjd.pojo.User;
import me.cjd.hibernate.factory.DefaultSessionDAO;

public class UserDAO extends DefaultSessionDAO<User, Integer> {
	
	public static final UserDAO me = new UserDAO();
	
}