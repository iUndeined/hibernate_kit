# 您发现了我，让您快速便捷使用Hibernate CRUD的 DAO 小工具

------

### 1. 方法API

> 基础方法

| 方法             | 返回   |  参数  |  说明  |
| --------         | --------  | --------  | --------  |
| getSession()     |   org.hibernate.session   |    无  |    获取session   |
| save(Object)     |   ID 泛型指定类型的主键  |   POJO实体   |   保存记录   |
| update(Object)   |   boolean 成功与否   |  POJO实体  |   更新记录   |
| merge(Object)   |   boolean 成功与否   |  POJO实体  |   合并记录   |
| delete(Object)   |   boolean 成功与否   |  POJO实体  |   删除记录   |
| deleteById(ID)   |   boolean 成功与否   |  ID主键  |   根据ID删除记录   |

> HQL相关查询方法

| 方法             | 返回   |  参数  |  说明  |
| --------         | --------  | --------  | --------  |
| find(String, Object...) |   用户指定泛型E的List   |    hql, params  |    列表查询   |
| findFirst(String, Object...) |   用户指定泛型E  |   hql, params   |   首条查询   |
| findPage(String, int, int, Object...) | 用户指定泛型E的List |  hql, start, pageSize, params  |   分页查询   |

> SQL相关查询方法，在返回值转换上会比hql方法多一个参数class

| 方法             | 返回   |  参数  |  说明  |
| --------         | --------  | --------  | --------  |
| findSQL(String, Object...) |   用户指定泛型E的List   |    sql, params  |    列表查询，sql查询方式不指定class只能返回Map   |
| findSQL(Class, String, Object...) |   用户指定泛型E的List   |    pojoClass, sql, params  |    列表查询并转换为指定类型   |
| findFirstSQL(String, Object...) |   用户指定泛型E  |   sql, params   |   首条查询   |
| findFirstSQL(Class, String, Object...) | 用户指定泛型E |  objClass, sql, params  |   首条查询并转换为指定类型   |
| findPageSQL(String, int, int, Object...) | 用户指定泛型E的List |  sql, start, pageSize, params  |   分页查询   |
| findPageSQL(Class, String, int, int, Object...) | 用户指定泛型E的List |  pojoClass, sql, start, pageSize, params  |   分页查询并转换为指定类型   |
| executeSQL(String, Object...) | 受影响条数 |  sql, params  |   执行更新语句   |

### 2. 您需要一个Pojo实体

```java

@Entity  
@Table(name="user")
public class User {
	
	@Id  
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column
	private String nickname;
	@Column
	private String account;
	@Column
	private String password;
	@Column
	private String roles;
    
    // getter setter
    	
}

```

### 3. 您还需要一个DAO

> 单独使用方法，请继承 DefaultSessionDAO，且必须使用ProxyKit进行事务托管

```java
public class UserDAO extends DefaultSessionDAO<User, Integer> {
	
	public static final UserDAO me = new UserDAO();
	
}
```

```java
// 必须要托管然后通过service调用噢，不然无法正常运行
private UserService service = ProxyKit.transaction(new UserService());

```

> 集成 Spring 方法，请继承 SpringSessionDAO 并在 Spring 配置里向 dao 注入 session 

```java
public class UserDAO extends SpringSessionDAO<User, Integer> {
	
	public static final UserDAO me = new UserDAO();
	
}
```

### 4. 使用范例

> 基础方法

```java
@Test
public void save(){
    User user = new User();
    user.setNickname("测试");
    user.setAccount("test");
    Integer id = UserDAO.me.save(user);
    System.out.println("保存ID是 = " + id);
    assertNotNull(id);
}

@Test
public void update(){
	User user = UserDAO.me.getById(1);
	assertNotNull(user);
	String newPassword = Integer.toString(2);
	user.setPassword(newPassword);
	int count = UserDAO.me.update(user);
	assertEquals(count, 1);
	assertEquals(user.getPassword(), newPassword);
}

@Test
public void delete(){
	User user = UserDAO.me.getById(1);
	boolean result = UserDAO.me.delete(user);
	assertEquals(result, true);
}

@Test
public void deleteById(){
	boolean result = UserDAO.me.deleteById(1);
	assertEquals(result, true);
}

```

> HQL相关

``` java

@Test
public void findHql(){
	List<User> list = UserDAO.me.find("from User");
	assertNotNull(list);
}

@Test
public void findHqlPage(){
	List<User> page1 = UserDAO.me.findPage("from User", 0, 5);
	assertNotNull(page1);
	System.out.println(page1.get(0).getNickname());
}

@Test
public void findFirst(){
    // 返回类型任意指定
	String nickname = UserDAO.me.findFirst("select nickname from User");
	assertNotNull(nickname);
	System.out.println(nickname);
	
	User user = UserDAO.me.findFirst("from User where id = 1 ");
	assertNotNull(user);
}

```

> SQL相关

``` java

@Test
public void findSql(){
    // 返回pojo需指定class
	List<User> list = UserDAO.me.findSQL(User.class, "select * from user");
	assertNotNull(list);
	System.out.println(list.get(0).getNickname());
	
	// 默认则返回map
	List<Map<String, Object>> list2  = UserDAO.me.findSQL("select * from user");
	assertNotNull(list2);
	System.out.println(list2.get(0).get("nickname"));
}

@Test
public void findPageSql(){
    // 返回pojo需指定class, 默认则返回map
	List<User> page1 = UserDAO.me.findPageSQL(User.class, "select * from user", 0, 5);
	assertNotNull(page1);
	assertEquals(page1.size(), 5);
	System.out.println(page1.get(0).getNickname());
}

@Test
public void findFirstSql(){
    // 不指定class只可返回基础类型 string, int, long 之类的
	String nickname = UserDAO.me.findFirstSQL("select nickname from user");
	assertNotNull(nickname);
	System.out.println(nickname);
	
	// 指定类型可返回实体
	User user = UserDAO.me.findFirstSQL(User.class, "select * from user");
	assertNotNull(user);
	System.out.println(user.getNickname());
}

@Test
public void sqlExc(){
    // sql 执行更新语句
    int count = UserDAO.me.executeSQL("update user set password = ? where id = ? ", 1, "2");
    assertEquals(count, 1);
}

```

------

未完待续
感谢您花费时间阅读这份说明稿。

作者 cjd   
2016 年 11月 01日    