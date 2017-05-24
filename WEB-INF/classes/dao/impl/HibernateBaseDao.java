package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import action.BackupAction;

import dao.BaseDao;
import java.lang.reflect.*;

@Repository("baseDao")
@Lazy(true)
public class HibernateBaseDao<T> extends HibernateDaoSupport implements BaseDao<T> {
	private static final Logger logger = Logger.getLogger(HibernateDaoSupport.class);
	//实体类的类型
	protected Class<T> entityClass;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HibernateBaseDao(){
		//通过反射机制获取泛型对应的实体类的类型
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType)genType).getActualTypeArguments();
		entityClass = (Class)params[0];
	} 
	
	public HibernateTemplate getHT(){
		return getHibernateTemplate();
	}
	
	@Autowired
	public void setSessionFactory0(SessionFactory sessionFactory){
		super.setSessionFactory(sessionFactory);
	}
	
	@Override
	public T get(Serializable id) {
		T tmp = getHT().get(entityClass, id);
		return tmp;
	}

	@Override
	public List<T> getAll() {
		return getHT().loadAll(entityClass);
	}

	@Override
	public Serializable save(Object o) {
		Serializable result;
		result = getHT().save(o);
		logger.info("保存"+entityClass+":"+o+" result:"+result);
		return result;
	}
	
	@Override
	public void saveOrUpdate(Object o) {
		getHT().saveOrUpdate(o);
	}

	@Override
	public void delete(Object o) {
		getHT().delete(o);
		logger.info("删除"+entityClass+":"+o);
	}
	

	@Override
	public void update(Object o) {
		getHT().update(o);
		logger.info("更新"+entityClass+":"+o);
	}
	
	@Override
	public List find(String hql, Object... values){
		logger.info("自定义查询HQL:"+hql);
		return getHT().find(hql, values);
	}
	@Override
	public List find(String hql){
		logger.info("自定义查询HQL:"+hql);
		return getHT().find(hql);
	}



}