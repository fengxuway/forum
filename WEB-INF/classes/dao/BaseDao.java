package dao;

import java.io.Serializable;
import java.util.*;

public interface BaseDao<T> {
	T get(Serializable id);
	List<T> getAll();
	Serializable save(Object o);
	void delete(Object o);
	void update(Object o);
	void saveOrUpdate(Object o);
	/** 
	 * 根据HQL语句查询数据库, 返回List
	 * @param hql
	 * @return
	 */
	List find(String hql);
	
	/**
	 * 根据HQL语句及对象列表, 返回List
	 * @param hql
	 * @param values
	 * @return
	 */
	List find(String hql, Object... values);
}
