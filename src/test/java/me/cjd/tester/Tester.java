package me.cjd.tester;

import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import me.cjd.pojo.TestUser;
import me.cjd.service.TestUserService;
import me.cjd.kit.HibernateKit;
import me.cjd.kit.ProxyKit;
import me.cjd.hibernate.dao.UserDAO;
import junit.framework.TestCase;

@RunWith(BlockJUnit4ClassRunner.class)
public class Tester extends TestCase {
	
	// 使用事务代理
	private TestUserService service = ProxyKit.transaction(new TestUserService());
	
	@Test
	@Ignore
	public void save(){
		TestUser user = new TestUser();
		user.setNickname("测试");
		user.setAccount("test");
		Integer id = UserDAO.me.save(user);
		System.out.println("保存ID是 = " + id);
	}
	
	@Test
	@Ignore
	public void update(){
		TestUser user = UserDAO.me.getById(20);
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
		TestUser pojo1 = UserDAO.me.getById(24);
		TestUser pojo2 = UserDAO.me.getById(23);
		TestUser pojo3 = UserDAO.me.getById(22);
		assertNotNull(pojo1);
		assertNotNull(pojo2);
		assertNotNull(pojo3);
	}
	
	@Test
	@Ignore
	public void findHql(){
		List<TestUser> list = UserDAO.me.find("from TestUser");
		assertNotNull(list);
	}
	
	@Test
	@Ignore
	public void findSql(){
		List<TestUser> list = UserDAO.me.findSQL(TestUser.class, "select * from user");
		assertNotNull(list);
		System.out.println(list.get(0).getNickname());
		
		List<Map<String, Object>> list2  = UserDAO.me.findSQL("select * from user");
		assertNotNull(list2);
		System.out.println(list2.get(0).get("nickname"));
	}
	
	@Test
	@Ignore
	public void findHqlPage(){
		List<TestUser> page1 = UserDAO.me.findPage("from TestUser", 0, 5);
		assertNotNull(page1);
		System.out.println(page1.get(0).getNickname());
	}
	
	@Test
	@Ignore
	public void findFirst(){
		String nickname = UserDAO.me.findFirst("select nickname from TestUser");
		assertNotNull(nickname);
		System.out.println(nickname);
	}
	
	@Test
	@Ignore
	public void findPageSql(){
		List<TestUser> page1 = UserDAO.me.findPageSQL(TestUser.class, "select * from user", 0, 5);
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
		
		TestUser user = UserDAO.me.findFirstSQL(TestUser.class, "select * from user");
		assertNotNull(user);
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testProxy(){
		String nickname = this.service.findFirstSQL();
		System.out.println("SQL Result: " + nickname);
		assertNotNull(nickname);
		
		String nickname2 = this.service.findFirst();
		System.out.println("HQL Result: " + nickname2);
		assertNotNull(nickname2);
	}
	
	@Test
	@Ignore
	public void testProxyTransactionSave(){
		assertEquals(this.service.saveSometing(), true);
	}
	
	@Test
	@Ignore
	public void testExecuteSQL(){
		assertEquals(this.service.sqlExc(), true);
	}
	
	@After
	public void shutdown(){
		// 关闭hibernate
        HibernateKit.shutdown();
	}
}