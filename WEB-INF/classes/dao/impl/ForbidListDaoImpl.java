package dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pagination.MemberDao;
import pagination.PageBean;

import dao.ForbidListDao;
import entity.ForbidList;
import entity.User;
@Repository("forbidListDao")
public class ForbidListDaoImpl extends HibernateBaseDao<ForbidList> implements ForbidListDao {
	@Autowired
	private MemberDao memberDao;
	@SuppressWarnings("unchecked")
	@Override
	public ForbidList findByUser(String userid) {
		List<ForbidList> result = getHT().find("from ForbidList fb where user_id.user_id=?", userid);
		if(result.size()>0){
			return result.get(0);
		}else{
			return null;
		}
	}

	@Override
	public PageBean getForbidList(int page) {
		PageBean result = memberDao.findForPage("from ForbidList fb order by operate_time desc", 6, page);
		return result;
	}

	@Override
	public PageBean getOperUserForbidList(int page, User uu) {
		PageBean result = memberDao.findForPage("from ForbidList fb where operate_id=? order by operate_time desc", uu, 6, page);
		return result;
	}

	@Override
	public void removeTimeoutForbid() {
		long time = System.currentTimeMillis();
		
		/*String hql1 = "update ForbidList as fl set fl.user_id.identity = 5 where fl.unforbid_time <="+time;
		Query query1 = getSession().createQuery(hql1);
		query1.executeUpdate();*/
		
		String hql2 = "delete from ForbidList fb where unforbid_time <= "+time;
		Query query=getSession().createQuery(hql2); 
		query.executeUpdate();

	}

}
 