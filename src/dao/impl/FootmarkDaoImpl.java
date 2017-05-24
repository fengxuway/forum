package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.FootmarkDao;
import entity.Footmark;
@Repository("footmarkDao")
public class FootmarkDaoImpl extends HibernateBaseDao<Footmark> implements FootmarkDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<Footmark> getUserFootmark(String userid) {
		return getHT().find("from Footmark f where f.user_id.user_id=? order by f.foot_time desc limit 10",userid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Footmark findByUserAndSub(String userid, int subid) {
		List<Footmark> result = getHT().find("from Footmark f where f.user_id.user_id=? and f.sub_id.sub_id=?",
				new Object[]{userid, subid});
		if(result.size()>0){
			return result.get(0);
		}else{
			return null;
		}
	} 
}
