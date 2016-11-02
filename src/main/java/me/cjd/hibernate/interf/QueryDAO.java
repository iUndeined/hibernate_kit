package me.cjd.hibernate.interf;

import java.util.List;

public interface QueryDAO<T, ID extends java.io.Serializable> extends BaseDAO<T, ID> {
	
	// HQL 查询方法
	
	/**
	 * HQL 列表查询
	 * @param hql 语句
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> find(String hql, Object... params);
	
	/**
	 * HQL 查询首条数据方法
	 * @param hql 语句
	 * @param params 参数们
	 * @return 随用户预期
	 */
	<E> E findFirst(String hql, Object... params);
	
	/**
	 * HQL 分页查询
	 * @param hql 语句
	 * @param start 开始页
	 * @param pageSize 最大条数
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> findPage(String hql, int start, int pageSize, Object... params);
	
	// SQL查询方法
	
	/**
	 * SQL 列表查询
	 * @param sql 语句
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> findSQL(String sql, Object... params);
	
	/**
	 * SQL 列表查询
	 * @param clazz 预期返回的列表类型，置 null 则默认转换为 map
	 * @param sql 语句
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> findSQL(Class<E> clazz, String sql, Object... params);
	
	/**
	 * SQL 查询首条数据方法
	 * @param sql 语句
	 * @param params 参数们
	 * @return 随用户预期
	 */
	<E> E findFirstSQL(String sql, Object... params);
	
	/**
	 * SQL 查询首条数据方法
	 * @param clazz 预期返回的列表类型，置 null 则不做转换处理
	 * @param sql 语句
	 * @param params 参数们
	 * @return 随用户预期
	 */
	<E> E findFirstSQL(Class<E> clazz, String sql, Object... params);
	
	/**
	 * SQL 分页查询
	 * @param sql 语句
	 * @param start 开始页
	 * @param pageSize 最大条数
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> findPageSQL(String sql, int start, int pageSize, Object... params);
	
	/**
	 * SQL 分页查询
	 * @param clazz 预期返回的列表类型，置 null 则默认转换为 map
	 * @param sql 语句
	 * @param start 开始页
	 * @param pageSize 最大条数
	 * @param params 参数们
	 * @return 列表
	 */
	<E> List<E> findPageSQL(Class<E> clazz, String sql, int start, int pageSize, Object... params);

	/**
	 * 执行sql语句
	 * @param sql 语句
	 * @param params 参数们
	 * @return 受影响记录条数
	 */
	int executeSQL(String sql, Object... params);
}
