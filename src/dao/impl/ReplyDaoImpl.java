package dao.impl;

import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.ReplyDao;
import dao.SubjectDao;
import entity.Reply;
@Repository("replyDao")
public class ReplyDaoImpl extends HibernateBaseDao<Reply> implements ReplyDao {
	private static final Logger logger = Logger.getLogger(ReplyDaoImpl.class);
	@Autowired
	@Qualifier("subjectDao")
	private SubjectDao subjectDao;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Reply> findBySubject(int sub_id) {
		List<Reply> list =  getHT().find("from Reply r where sub_id.sub_id = ?  and if_delete =false order by reply_time asc", sub_id);
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Reply> findBySubjectNoReply(int sub_id){
		return getHT().find("from Reply r where sub_id.sub_id = ?  and if_delete =false and if_reply=false order by reply_time asc", sub_id);
	}


}
