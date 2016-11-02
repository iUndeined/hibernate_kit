package me.cjd.kit;

import me.cjd.hibernate.proxy.CglibHandler;
import me.cjd.hibernate.proxy.CglibTransactionHandler;
import net.sf.cglib.proxy.Enhancer;

public class ProxyKit {
	
	@SuppressWarnings("unchecked")
	public static final <E> E duang(E proxyTarget, CglibHandler handler){
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(proxyTarget.getClass());
		enhancer.setCallback(handler);
		return (E) enhancer.create();
	}
	
	/**
	 * 事务代理方法
	 * @param proxyTarget
	 * @return
	 */
	public static final <E> E transaction(E proxyTarget){
		return duang(proxyTarget, new CglibTransactionHandler(proxyTarget.getClass()));
	}
	
}
