package me.cjd.service;

import me.cjd.hibernate.annotation.Readonly;
import me.cjd.hibernate.annotation.Transaction;
import me.cjd.hibernate.dao.UserDAO;
import me.cjd.pojo.User;

@Transaction
public class UserService {
	
	@Readonly
	public String findFirstSQL(){
		String nickname = UserDAO.me.findFirstSQL("select nickname from user");
		System.out.println(nickname);
		return nickname;
	}
	
	public boolean saveSometing(){
		User user = new User();
		user.setNickname("陈剑冬6");
		user.setAccount("chenjiandong6");
		user.setPassword(Integer.toString(1));
		return UserDAO.me.save(user) != null;
	}
	
	public boolean sqlExc(){
		return UserDAO.me.executeSQL("update user set password = ? ", "2") > 0;
	}
	
}
