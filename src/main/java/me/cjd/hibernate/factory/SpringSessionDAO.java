package me.cjd.hibernate.factory;

import org.hibernate.Session;
import me.cjd.hibernate.impl.QueryDAOImpl;

public class SpringSessionDAO<T, ID extends java.io.Serializable> extends QueryDAOImpl<T, ID> {
	
	private Session session;
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
}
