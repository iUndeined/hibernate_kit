package me.cjd.hibernate.factory;

import org.hibernate.Session;
import me.cjd.kit.HibernateKit;
import me.cjd.hibernate.impl.QueryDAOImpl;

public class DefaultSessionDAO<T, ID extends java.io.Serializable> extends QueryDAOImpl<T, ID> {
	
	public Session getSession(){
		return HibernateKit.getSession();
	}
	
}
