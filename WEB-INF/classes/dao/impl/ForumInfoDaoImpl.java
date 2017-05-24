package dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import dao.ForumInfoDao;
import entity.ForumInfo;
@Repository("forumInfoDao")
public class ForumInfoDaoImpl extends HibernateBaseDao<ForumInfo> implements ForumInfoDao {

}
 