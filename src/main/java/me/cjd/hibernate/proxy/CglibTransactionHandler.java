package me.cjd.hibernate.proxy;

import java.lang.reflect.Method;
import org.apache.log4j.Logger;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import net.sf.cglib.proxy.MethodProxy;
import me.cjd.hibernate.annotation.Readonly;
import me.cjd.hibernate.annotation.RollbackFor;
import me.cjd.hibernate.annotation.Transaction;
import me.cjd.hibernate.exception.ValueExceptionNotFound;
import me.cjd.kit.HibernateKit;

public class CglibTransactionHandler extends CglibHandler {
	
	private static final Logger log = Logger.getLogger(CglibTransactionHandler.class);
	
	private Class<?> targetClass;
	
	public CglibTransactionHandler(Class<?> targetClass) {
		super();
		this.targetClass = targetClass;
	}
	
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		if (this.targetClass == null) {
			log.debug("targetClass 未设置，无法进行事务托管处理");
			return proxy.invokeSuper(obj, args);
		}
		
		Transaction atx = this.targetClass.getDeclaredAnnotation(Transaction.class);
		
		if (atx == null) {
			log.debug("objName 未设置 Transaction 注解，不做事务托管处理");
			return proxy.invokeSuper(obj, args);
		}
		
		Session session;
		org.hibernate.Transaction tx = null;
		Object proxyObj = null;
		
		try {
			Readonly only = method.getDeclaredAnnotation(Readonly.class);
			session = HibernateKit.getSession();
			// 处理只读业务
			session.setFlushMode(only == null ? FlushMode.AUTO : FlushMode.MANUAL);
			tx = session.beginTransaction();
			proxyObj = proxy.invokeSuper(obj, args);
			// 事务必须提交
			tx.commit();
		} catch (Exception e) {
			RollbackFor rollFor = method.getDeclaredAnnotation(RollbackFor.class);
			if (rollFor == null) {
				log.error("事务托管：用户方法发生错误，事务将进行回滚", e);
				if (tx != null) {
					tx.rollback();
				}
			} else {
				if (rollFor.value() == null) {
					log.error("事务托管：用户标识了注解 RollbackFor 但没有给value赋值，无法执行回滚，用户方法错误如下", e);
					throw new ValueExceptionNotFound();
				}
				if (rollFor.value().equals(e.getClass())) {
					log.error("事务托管：用户方法发生错误，错误类型与用户限定标识RollbackFor匹配，事务将进行回滚", e);
					if (tx != null) {
						tx.rollback();
					}
				} else {
					log.error("事务托管：用户方法发生错误，错误类型与用户限定标识RollbackFor不匹配，事务不会回滚", e);
				}
			}
			throw e;
		}
		return proxyObj;
	}
	
}
