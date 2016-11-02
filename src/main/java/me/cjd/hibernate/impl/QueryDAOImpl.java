package me.cjd.hibernate.impl;

import java.util.List;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;

import me.cjd.hibernate.annotation.Readonly;
import me.cjd.hibernate.interf.QueryDAO;

public abstract class QueryDAOImpl<T, ID extends java.io.Serializable> extends BaseDAOImpl<T, ID> implements QueryDAO<T, ID> {
	
	/**
	 * 将参数动态加入查询的方法
	 * @param query
	 * @param params
	 */
	protected Query addParas(Query query, Object... params){
		if (params == null || params.length == 0) {
			return query;
		}
		for (int i = 0; i < params.length; i ++) {
			query.setParameter(i, params[i]);
		}
		return query;
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public <E> List<E> find(String hql, Object... params) {
		return this.addParas(getSession().createQuery(hql), params).list();
	}
	
	@Readonly
	public <E> E findFirst(String hql, Object... params) {
		List<E> list = this.findPage(hql, 0, 1, params);
		return list == null || list.isEmpty() ? null : list.get(0);
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public <E> List<E> findPage(String hql, int start, int pageSize, Object... params) {
		return this.addParas(getSession().createQuery(hql), params)
			.setFirstResult(start)
			.setMaxResults(pageSize).list();
	}
	
	@Readonly
	public <E> List<E> findSQL(String sql, Object... params) {
		return this.findSQL(null, sql, params);
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public <E> List<E> findSQL(Class<E> clazz, String sql, Object... params) {
		return this.addParas(getSession().createSQLQuery(sql), params)
			.setResultTransformer(clazz == null ? Transformers.ALIAS_TO_ENTITY_MAP : Transformers.aliasToBean(clazz))
			.list();
	}
	
	@Readonly
	public <E> E findFirstSQL(String sql, Object... params) {
		return this.findFirstSQL(null, sql, params);
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public <E> E findFirstSQL(Class<E> clazz, String sql, Object... params) {
		Query query = this.addParas(getSession().createSQLQuery(sql), params)
			.setFirstResult(0)
			.setMaxResults(1);
		if (clazz != null) {
			query.setResultTransformer(
				"java.util.map".equalsIgnoreCase(clazz.getName()) ? 
				Transformers.ALIAS_TO_ENTITY_MAP : 
				Transformers.aliasToBean(clazz)
			);
		}
		List<E> list = query.list();
		return list == null || list.isEmpty() ? null : list.get(0);
	}
	
	@Readonly
	public <E> List<E> findPageSQL(String sql, int start, int pageSize, Object... params) {
		return this.findPageSQL(null, sql, start, pageSize, params);
	}
	
	@Readonly
	@SuppressWarnings("unchecked")
	public <E> List<E> findPageSQL(Class<E> clazz, String sql, int start, int pageSize, Object... params) {
		return this.addParas(getSession().createSQLQuery(sql), params)
			.setFirstResult(start)
			.setMaxResults(pageSize)
			.setResultTransformer(clazz == null ? Transformers.ALIAS_TO_ENTITY_MAP : Transformers.aliasToBean(clazz))
			.list();
	}
	
	public int executeSQL(String sql, Object... params) {
		return addParas(getSession().createSQLQuery(sql), params).executeUpdate();
	}
	
}
