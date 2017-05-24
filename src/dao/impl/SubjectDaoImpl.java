package dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pagination.*;

import dao.SubjectDao;
import entity.Subject;
@Repository("subjectDao")
public class SubjectDaoImpl extends HibernateBaseDao<Subject> implements SubjectDao {
	@Autowired
	@Qualifier("memberDao")
	private MemberDao memberDao;
	@Override
	public PageBean getDelSubject(int page, int identity) {
		PageBean result;
		if(identity >= 1000){
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_delete=true order by sub_time desc"
					, 6, page); 
		}else{
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_delete=true and sub.cate_id.cate_id=? order by sub_time desc"
					,(identity -100) , 6, page); 
		}
		for(Object o: result.getList()){
			Subject s= (Subject)o;
			System.out.println(s);
		}
		return result;
	}
	
	@Override
	public PageBean getPerfectSubject(int page, int identity) {
		PageBean result;
		if(identity >= 1000){
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_perfect=true order by sub_time desc"
					, 6, page); 
		}else{
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_perfect=true and sub.cate_id.cate_id=? order by sub_time desc"
					,(identity -100) , 6, page); 
		}
		for(Object o: result.getList()){
			Subject s= (Subject)o;
			System.out.println(s);
		}
		return result;
	}

	@Override
	public PageBean getTopSubject(int page, int identity) {
		PageBean result;
		if(identity >= 1000){
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_top=true order by sub_time desc"
					, 6, page); 
		}else{
			result = memberDao.findForPage(
					"from Subject as sub where sub.if_top=true and sub.cate_id.cate_id=? order by sub_time desc"
					,(identity -100) , 6, page); 
		}
		for(Object o: result.getList()){
			Subject s= (Subject)o;
			System.out.println(s);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subject> search(String kw) {
		kw = kw.replaceAll(" |;|'", "");
		Criteria cri = getSession().createCriteria(Subject.class);
		cri.add(Restrictions.like("sub_title", "%"+kw+"%"));
		cri.add(Restrictions.eq("if_delete", false));
		cri.setMaxResults(8);
		return cri.list();
	}

	@Override
	public PageBean searchForPage(String kw, int page) {
		kw = kw.replaceAll(" |;|'", "");
		return memberDao.findForPage("from Subject where if_delete=false and sub_title like '%"+kw+"%' order by last_reply_time desc", 10, page);
	}
}
 