package dao.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.*;
import entity.*;
@Repository("rankDao")
public class RankDaoImpl extends HibernateBaseDao<Rank> implements RankDao {
	
	public Rank findByGrade(int grade){
		List list = getHT().find("from Rank r where grade = ?", grade);
		return (list == null || list.size()<=0 )? null : (Rank)list.get(0);
	}

}
