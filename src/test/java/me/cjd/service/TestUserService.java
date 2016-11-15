package me.cjd.service;

import me.cjd.hibernate.annotation.Readonly;
import me.cjd.hibernate.annotation.Transaction;
import me.cjd.hibernate.dao.UserDAO;
import me.cjd.pojo.TestUser;

@Transaction
public class TestUserService {
	
	@Readonly
	public String findFirst(){
		return UserDAO.me.findFirst("select nickname from TestUser");
	}
	
	@Readonly
	public String findFirstSQL(){
		return UserDAO.me.findFirstSQL("select nickname from user");
	}
	
	public boolean saveSometing(){
		TestUser user = new TestUser();
		user.setNickname("陈剑冬6");
		user.setAccount("chenjiandong6");
		user.setPassword(Integer.toString(1));
		return UserDAO.me.save(user) != null;
	}
	
	public boolean sqlExc(){
		return UserDAO.me.executeSQL("update user set password = ? ", "2") > 0;
	}
	
}
