package dao.impl;

import java.util.List;
import java.util.Set;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pagination.PageBean;

import dao.AttentionDao;
import entity.Attention;
import entity.User;
@Repository("attentionDao")
public class AttentionDaoImpl extends HibernateBaseDao<Attention> implements AttentionDao{


	@Override
	@SuppressWarnings("unchecked")
	public Attention findByUserAndUser(User byuser, User user) {
		List<Attention> list = getHT().find("from Attention atte where atte.atte_byuserid = ? and atte.atte_userid=?", new Object[]{byuser, user});
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public boolean isAtte(String userA, String userB) {
		List<Attention> list = getHT().find("from Attention atte where atte.atte_byuserid.user_id = ? and atte.atte_userid.user_id = ?", new  Object[]{userA, userB});
		if(list.size() >= 1){
			return true;
		}
		return false;
	}

	@Override
	public List<Attention> getMyAtte(String userid) {
		return getHT().find("from Attention atte where atte.atte_byuserid.user_id=?", userid);
	}

	@Override
	public List<Attention> getAtteMe(String userid) {
		return getHT().find("from Attention atte where atte.atte_userid.user_id=?", userid);
	}

	

} 
 