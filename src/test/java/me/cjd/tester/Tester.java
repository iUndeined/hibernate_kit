package me.cjd.tester;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import me.cjd.pojo.User;
import me.cjd.service.UserService;
import me.cjd.kit.HibernateKit;
import me.cjd.kit.ProxyKit;
import me.cjd.hibernate.dao.UserDAO;
import junit.framework.TestCase;

@RunWith(BlockJUnit4ClassRunner.class)
public class Tester extends TestCase {
	
	// 使用事务代理
	UserService service = ProxyKit.transaction(new UserService());
	
	@Test
	@Ignore
	public void save(){
		User user = new User();
		user.setNickname("测试");
		user.setAccount("test");
		Integer id = UserDAO.me.save(user);
		System.out.println("保存ID是 = " + id);
	}
	
	@Test
	@Ignore
	public void update(){
		User user = UserDAO.me.getById(20);
		assertNotNull(user);
		String newPassword = Integer.toString(2);
		user.setPassword(newPassword);
		UserDAO.me.update(user);
		assertEquals(user.getPassword(), newPassword);
	}
	
	@Test
	@Ignore
	public void delete(){
		boolean result = UserDAO.me.deleteById(25);
		assertEquals(result, true);
	}
	
	@Test
	@Ignore
	public void getByIdMore(){
		User pojo1 = UserDAO.me.getById(24);
		User pojo2 = UserDAO.me.getById(23);
		User pojo3 = UserDAO.me.getById(22);
		assertNotNull(pojo1);
		assertNotNull(pojo2);
		assertNotNull(pojo3);
	}
	
	@Test
	@Ignore
	public void findHql(){
		List<User> list = UserDAO.me.find("from User");
		assertNotNull(list);
	}
	
	@Test
	@Ignore
	public void findSql(){
		List<User> list = UserDAO.me.findSQL(User.class, "select * from user");
		assertNotNull(list);
		System.out.println(list.get(0).getNickname());
		
		List<Map<String, Object>> list2  = UserDAO.me.findSQL("select * from user");
		assertNotNull(list2);
		System.out.println(list2.get(0).get("nickname"));
	}
	
	@Test
	@Ignore
	public void findHqlPage(){
		List<User> page1 = UserDAO.me.findPage("from User", 0, 5);
		assertNotNull(page1);
		System.out.println(page1.get(0).getNickname());
	}
	
	@Test
	@Ignore
	public void findFirst(){
		String nickname = UserDAO.me.findFirst("select nickname from User");
		assertNotNull(nickname);
		System.out.println(nickname);
	}
	
	@Test
	@Ignore
	public void findPageSql(){
		List<User> page1 = UserDAO.me.findPageSQL(User.class, "select * from user", 0, 5);
		assertNotNull(page1);
		assertEquals(page1.size(), 5);
		System.out.println(page1.get(0).getNickname());
	}
	
	@Test
	@Ignore
	public void findFirstSql(){
		String nickname = UserDAO.me.findFirstSQL("select nickname from user");
		assertNotNull(nickname);
		System.out.println(nickname);
		
		User user = UserDAO.me.findFirstSQL(User.class, "select * from user");
		assertNotNull(user);
		System.out.println(user.getNickname());
	}
	
	@Test
	@Ignore
	public void testProxy(){
		String nickname = this.service.findFirstSQL();
		assertNotNull(nickname);
	}
	
	@Test
	@Ignore
	public void testProxyTransactionSave(){
		assertEquals(this.service.saveSometing(), true);
	}
	
	@Test
	public void testExecuteSQL(){
		assertEquals(this.service.sqlExc(), true);
	}
	
	@After
	public void shutdown(){
		// 关闭hibernate
        HibernateKit.shutdown();
	}
}