package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.SecretDialogDao;
import entity.SecretDialog;
@Repository("secretDialogDao")
public class SecretDialogDaoImpl extends HibernateBaseDao<SecretDialog> implements SecretDialogDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<SecretDialog> getUserDialogs(String userid) {
		String hql = "from SecretDialog sd "+
			"where "+
				"(select COUNT(sc) from SecretContent sc  "+ 
					"where sc.dialog_id = sd.dialog_id and "+
					 "((sd.userA.user_id=? and sc.if_Avisible=true) " +
					 "or (sd.userB.user_id=? and sc.if_Bvisible=true))) "+
				 "> 0 order by sd.dialog_time";
		List<SecretDialog> result = getHT().find(hql, new Object[]{ userid, userid});
		
		for(SecretDialog sd : result){
			System.out.println(sd);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SecretDialog findByUsers(String userid1, String userid2) {
		String hql = "from SecretDialog sd where (sd.userA.user_id=? and sd.userB.user_id=?) " +
				" or (sd.userA.user_id=? and sd.userB.user_id=?)";
		List<SecretDialog> result =  getHT().find(hql, new Object[]{userid1, userid2, userid2, userid1});
		if(result == null || result.size()== 0){
			return null;
		}else{
			return result.get(0);
		}
	}

}
