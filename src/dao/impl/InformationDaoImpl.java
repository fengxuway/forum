package dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pagination.MemberDao;
import pagination.PageBean;
import service.impl.AttentionServiceImpl;

import dao.InformationDao;
import entity.Information;
import entity.User;
@Repository("informationDao")
public class InformationDaoImpl extends HibernateBaseDao<Information> implements	InformationDao {
	private static final Logger logger = Logger.getLogger(InformationDaoImpl.class);
	@Autowired
	@Qualifier("memberDao")
	private MemberDao memberDao;
	
	@Override
	public PageBean getReplyMe(User user, int pageNum) throws Exception{
		return memberDao.findForPage("from Information info where info.type=3 " +
				"and info.user_id = ? order by time desc",
					user, 5, pageNum);
	}

	@Override
	public PageBean getMentionMe(User user, int pageNum) {
		return memberDao.findForPage("from Information info where info.type=4 " +
				"and info.user_id = ? order by time desc",
					user, 5, pageNum);
	}
	
	@Override
	public PageBean getDynamic(List<User> users, int pageNum) {
		if(users == null || users.size()==0){
			PageBean p = new PageBean();
			p.setList(new ArrayList());
			return p;
		}
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
		
		String hql = "from Information info where user_id.user_id in ("+ sb.toString()+") and  ( type =2 or type=1 ) order by time desc";
		logger.info("获取用户动态："+hql);
		
		PageBean result = memberDao.findForPage(hql, 5, pageNum);
		
		List<Information> list = result.getList();
		for(Information tmp: list){
			System.out.println(tmp);
		}
		
		return result;
	}

	@Override
	public PageBean getNews(String user_id, int page) {
		return memberDao.findForPage("from Information info where info.user_id.user_id=? and type>=5 order by time desc", user_id, 5, page);
	}

}
 