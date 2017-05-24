package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.SecretContentDao;
import entity.SecretContent;
@Repository("secretContentDao")
public class SecretContentDaoImpl extends HibernateBaseDao<SecretContent> implements SecretContentDao {

	@Override
	@SuppressWarnings("unchecked")
	public List<SecretContent> getUserContents(String userid, int dialog_id) {
		List<SecretContent> result = getHT().find(
				"from SecretContent sc where sc.dialog_id.dialog_id=? " +
				"and (( sc.dialog_id.userA.user_id = ? and sc.if_Avisible = true ) " +
				"or (sc.dialog_id.userB.user_id = ? and sc.if_Bvisible = true)) " +
				"order by sc.content_time asc",
				new Object[]{dialog_id, userid, userid}
				);
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SecretContent> getUnreadContent(String user_id, int dialog_id) {
		List<SecretContent> result = getHT().find(
				"from SecretContent sc where sc.dialog_id.dialog_id=? " +
				"and ((sc.dialog_id.userA.user_id=? and sc.if_Aread=false and sc.if_Avisible=true) " +
				"or (sc.dialog_id.userB.user_id=? and sc.if_Bread=false and if_Bvisible=true))" +
				" order by sc.content_time asc", 
				new Object[]{dialog_id, user_id, user_id});
		System.out.println("=============");
		for(SecretContent sc:result){
			System.out.println(sc);
		}
		return result;
	}

}
 