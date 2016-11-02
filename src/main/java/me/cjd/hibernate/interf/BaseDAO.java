package me.cjd.hibernate.interf;

import org.hibernate.Session;

/**
 * 基础持久层接口
 * @author Me.cjd
 * @param <T> POJO类型
 * @param <ID> ID主键类型
 */
public interface BaseDAO<T, ID extends java.io.Serializable> {
	
	/**
	 * 获取session方法
	 * @return org.hibernate.Session
	 */
	Session getSession();
	
	/**
	 * 保存方法
	 * @param pojo 持久类
	 * @return {true: 成功, false: 失败}
	 */
	ID save(Object pojo);
	
	/**
	 * 更新方法
	 * @param pojo 持久类
	 * @return {true: 成功, false: 失败}
	 */
	boolean update(Object pojo);
	
	/**
	 * 合并方法
	 * @param pojo 持久类
	 * @return {true: 成功, false: 失败}
	 */
	boolean merge(Object pojo);
	
	/**
	 * 删除方法
	 * @param pojo 持久类
	 * @return {true: 成功, false: 失败}
	 */
	boolean delete(Object pojo);
	
	/**
	 * 根据ID删除唯一实体
	 * @param id 唯一主键
	 * @return {true: 成功, false: 失败}
	 */
	boolean deleteById(ID id);
	
	/**
	 * 根据ID获取唯一实体
	 * @param id 唯一主键
	 * @return 返回pojo持久类
	 */
	T getById(ID id);
	
}
