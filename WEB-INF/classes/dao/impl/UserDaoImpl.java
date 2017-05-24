package dao.impl;

import java.io.Serializable;
import java.util.*;

import org.apache.struts2.json.annotations.*;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import pagination.*;
import dao.*;
import entity.*;
@Repository("userDao")
public class UserDaoImpl extends HibernateBaseDao<User> implements UserDao {
	
	@Autowired
	@Qualifier("memberDao")
	private MemberDao memberDao;
	
	@Override
	public User get(Serializable id) {
		List list = getHT().find("from User as u where u.user_id = ?", id);
		return list.size() > 0 ? (User)list.get(0) : null;
	}
 
	@Override
	public User findByEmail(String email){
		List list = getHT().find("from User as u where u.email = ?", email);
		return list.size() > 0 ? (User)list.get(0) : null;
	}

	@Override
	public User findByUuid(String uuid) {
		List list = getHT().find("from User as u where u.uuid = ?", uuid);
		return list.size() > 0 ? (User)list.get(0) : null;
	}

	@Override
	public void userActive(User user) {
		user.setIdentity(5);
		//设置激活时间为当前时间
		user.setActive_time(System.currentTimeMillis());
		update(user);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findNoApply() {
		List result = getHT().find("from User as u where u.identity>-20 and u.identity<-9 order by u.active_time desc");
		return result;
	}

	@Override
	public PageBean getUsersSubject(List<User> users, int pageNum) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < users.size(); i++){
			String userId = users.get(i).getUser_id();
			if(i == 0){
				sb.append("'");
				sb.append(userId);
				sb.append("'");
			}else{
				sb.append(",'");
				sb.append(userId);
				sb.append("'");
			}
		}
		String hql = "from Subject as sub where sub.author.user_id in ("+sb.toString()+") order by sub_time desc";
		System.out.println(hql);
		PageBean result = memberDao.findForPage(hql , 5, pageNum);
		System.out.println("查询到 "+result.getList().size()+" 条数据");
		return result;
	}
	
	@Override
	public PageBean getUsersReply(List<User> users, int pageNum){
		StringBuilder sb = new StringBuilder();
		for(int i = 0;i < users.size(); i++){
			String userId = users.get(i).getUser_id();
			if(i == 0){
				sb.append("'");
				sb.append(userId);
				sb.append("'");
			}else{
				sb.append(",'");
				sb.append(userId);
				sb.append("'");
			}
		}
		String hql = "from Reply as r where r.user_id.user_id in ("+sb.toString()+") order by reply_time desc";
		System.out.println(hql+" 请求页码"+pageNum);
		return  memberDao.findForPage(hql , 5, pageNum);
	}

	@Override
	public PageBean findNoApply(int page) {
		return memberDao.findForPage("from User as u where u.identity>-20 and u.identity<-9 order by u.active_time desc", 6, page);
	}

	@Override
	public PageBean findAllUsers(int page, String kw) {
		String hql = "from User as u where u.identity >= 0 order by user_id asc";
		// 如果用户输入搜索关键字
		if(kw != null && !kw.equals("")){
			kw = kw.replaceAll(" |;|'", "");
			hql = "from User as u where u.identity >= 0 and user_id like '%"+kw+"%' order by user_id asc";
		}
		return memberDao.findForPage(hql, 4, page);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByIdentity(int identity) {
		return getHT().find("from User u where u.identity = ?",identity);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> search(String kw) {
		Criteria cri = getSession().createCriteria(User.class);
		cri.add(Restrictions.like("user_id", "%"+kw+"%"));
		cri.setMaxResults(8);
		return cri.list();		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		return getHT().find("from User where identity >=0");
	}

}
