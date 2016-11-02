package me.cjd.hibernate.factory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import me.cjd.hibernate.impl.QueryDAOImpl;

public class SpringSessionDAO<T, ID extends java.io.Serializable> extends QueryDAOImpl<T, ID> {
	
	private Session session;
	private SessionFactory sessionFactory;
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
}
