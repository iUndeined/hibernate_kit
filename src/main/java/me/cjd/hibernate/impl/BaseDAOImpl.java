package me.cjd.hibernate.impl;

import org.apache.log4j.Logger;
import me.cjd.kit.ClassKit;
import me.cjd.hibernate.annotation.Readonly;
import me.cjd.hibernate.exception.HibernateTransactionException;
import me.cjd.hibernate.interf.BaseDAO;

public abstract class BaseDAOImpl<T, ID extends java.io.Serializable> implements BaseDAO<T, ID> {
	
	private static final Logger log = Logger.getLogger(BaseDAOImpl.class);
	
	@SuppressWarnings("unchecked")
	public ID save(Object pojo) {
		try {
			return (ID) getSession().save(pojo);
		} catch (Exception e) {
			log.error("Pojo 持久类实例保存失败", e);
			throw new HibernateTransactionException();
		}
	}
	
	public boolean update(Object pojo) {
		try {
			getSession().update(pojo);
			return true;
		} catch (Exception e) {
			log.error("Pojo 持久类实例修改失败", e);
			throw new HibernateTransactionException();
		}
	}

	public boolean merge(Object pojo) {
		try {
			getSession().merge(pojo);
			return true;
		} catch (Exception e) {
			log.error("Pojo 持久类实例合并失败", e);
			throw new HibernateTransactionException();
		}
	}

	public boolean delete(Object pojo) {
		try {
			getSession().delete(pojo);
			return true;
		} catch (Exception e) {
			log.error("Pojo 持久类实例删除失败", e);
			throw new HibernateTransactionException();
		}
	}

	public boolean deleteById(ID id) {
		T pojo = getById(id);
		if (pojo == null) {
			log.warn("deleteById(ID) 删除实例失败，id = " + id + " 不存在");
			return false;
		}
		return this.delete(pojo);
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public T getById(ID id) {
		try {
			return (T) getSession().get(ClassKit.getPatternClass(this.getClass(), 0), id);
		} catch (Exception e) {
			log.error("getById(ID) 发生错误", e);
			throw new HibernateTransactionException();
		}
	}
	
}
