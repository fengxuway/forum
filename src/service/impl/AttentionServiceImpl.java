package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import pagination.PageBean;

import dao.*;
import entity.*;
import service.*;

@Service("attentionService")
public class AttentionServiceImpl implements AttentionService {
	private static final Logger logger = Logger.getLogger(AttentionServiceImpl.class);
	@Autowired
	@Qualifier("attentionDao")
	private AttentionDao attentionDao;
	
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	@Autowired
	@Qualifier("informationDao")
	private InformationDao informationDao;
	
	
	
	@Override
	public Attention get(Serializable id) {
		return attentionDao.get(id);
	} 

	@Override
	public List<Attention> getAll() {
		return attentionDao.getAll();
	}

	@Override
	public Serializable save(Attention o) {
		return attentionDao.save(o);
	}

	@Override
	public void delete(Attention o) {
		attentionDao.delete(o);
	}

	@Override
	public void update(Attention o) {
		attentionDao.update(o);
	}

	@Override
	public void saveOrUpdate(Attention o) {
		attentionDao.saveOrUpdate(o);
	}

	@Override
	public boolean isAtte(String userA, String userB) {
		return attentionDao.isAtte(userA, userB);
	}

	@Override
	public List<User> getMyAtte(String userid) {
		List<Attention> list = attentionDao.getMyAtte(userid);
		List<User> result = new ArrayList<User>();
		for(Attention tmp: list){
			result.add(tmp.getAtte_userid());
		}
		return result;
	}

	@Override
	public List<User> getAtteMe(String userid) {
		List<Attention> list = attentionDao.getAtteMe(userid);
		List<User> result = new ArrayList<User>();
		for(Attention tmp: list){
			result.add(tmp.getAtte_byuserid());
		}
		return result;
	}

	@Override
	public PageBean getAtteDynamic(User user, int pageNum2) {
		user = userDao.get(user.getUser_id());
		List<User> users =getMyAtte(user.getUser_id());
		
		return informationDao.getDynamic(users, pageNum2);
		
		
	}

}
