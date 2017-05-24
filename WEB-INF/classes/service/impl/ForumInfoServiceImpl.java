package service.impl;

import java.io.Serializable;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import dao.*;
import entity.*;
import service.*;
@Service("forumInfoService")
public class ForumInfoServiceImpl implements ForumInfoService {
	private static final Logger logger = Logger.getLogger(ForumInfoServiceImpl.class);
	@Autowired
	@Qualifier("forumInfoDao")
	private ForumInfoDao forumInfoDao;
	@Override
	public ForumInfo get(Serializable id) {
		return forumInfoDao.get(id);
	}

	@Override
	public List<ForumInfo> getAll() {
		return forumInfoDao.getAll();
	}
 
	@Override
	public Serializable save(ForumInfo o) {
		return forumInfoDao.save(o);
	}

	@Override
	public void delete(ForumInfo o) {
		forumInfoDao.delete(o);
	}

	@Override
	public void update(ForumInfo o) {
		forumInfoDao.update(o);
	}

	@Override
	public void saveOrUpdate(ForumInfo o) {
		forumInfoDao.saveOrUpdate(o);
	}

	@Override
	public String getForumSign() {
		ForumInfo fi = get(1);
		if(fi != null){
			return fi.getSign();
		}else{
			return "青年人的灌水场所要有情调，有情调的灌水场所才有革新。";
		}
	}

}
