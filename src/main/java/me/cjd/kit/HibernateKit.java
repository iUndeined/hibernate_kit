package me.cjd.kit;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateKit {
	
	private static final Logger log = Logger.getLogger(HibernateKit.class);
	
    private static final SessionFactory sessionFactory = buildSessionFactory();
    
    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
        	return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
        catch (Exception ex) {
            // Make sure you log the exception, as it might be swallowed
            log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static final SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
    public static final Session getSession(){
    	return sessionFactory.getCurrentSession();
    }
    
    public static final void shutdown(){
    	sessionFactory.close();
    }

}
