package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.apache.log4j.Logger;

import pagination.MemberDao;
import pagination.PageBean;
import dao.CategoryDao;
import entity.*;

@Repository("categoryDao")
public class CategoryDaoImpl extends HibernateBaseDao<Category> implements CategoryDao {
	private static final Logger logger = Logger.getLogger(CategoryDaoImpl.class);
	
	@Autowired
	private MemberDao memberDao;
	
	public Category findByName(String cate_name){
		List list = getHT().find("from Category as cate where cate_name=?", cate_name);
		if(list == null || list.size()==0){
			logger.info("没有找到cate_name = " + cate_name + "的版块");
			return null;
		}else{
			logger.info("成功找到cate_name = " + cate_name + "的版块");
			return (Category) list.get(0);
		}
	}

	@Override
	public PageBean getAllByPage(int page) {
		return memberDao.findForPage("from Category cate order by cate_id asc", 5, page);
	}
	
}
 