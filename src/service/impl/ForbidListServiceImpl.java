package service.impl;

import java.io.Serializable;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import dao.*;
import entity.*;
import service.*;
import org.apache.log4j.Logger;

import pagination.PageBean;

@Service("forbidListService")
public class ForbidListServiceImpl implements ForbidListService {
	private static final Logger logger = Logger.getLogger(ForbidListServiceImpl.class);
	@Autowired
	@Qualifier("forbidListDao")
	private ForbidListDao forbidListDao;
	
	@Override
	public ForbidList get(Serializable id) {
		return forbidListDao.get(id);
	} 

	@Override
	public List<ForbidList> getAll() {
		return forbidListDao.getAll();
	}

	@Override
	public Serializable save(ForbidList o) {
		return forbidListDao.save(o);
	}

	@Override
	public void delete(ForbidList o) {
		forbidListDao.delete(o);
	}

	@Override
	public void update(ForbidList o) {
		forbidListDao.update(o);
	}

	@Override
	public void saveOrUpdate(ForbidList o) {
		forbidListDao.saveOrUpdate(o);
	}

	@Override
	public PageBean getForbidList(int page, User uu) {
		if(uu.getIdentity() >= 1000){
			// 如果是管理员
			return forbidListDao.getForbidList(page);
		}else{
			return forbidListDao.getOperUserForbidList(page, uu);
		}
	}

	@Override
	public User removeForbid(int forbid_id, String user_id) {
		ForbidList fl = get(forbid_id);
		System.out.println("==============n "+forbid_id);
		if(fl == null) throw new RuntimeException();
		User user = fl.getUser_id();
		user.setIdentity(5);
		delete(fl);
		
		return user;
	}
	
	@Override
	public void removeTimeoutForbid(){
		List<ForbidList> list = forbidListDao.getAll();
		long time = System.currentTimeMillis();
		for(ForbidList fl :list){
			if(fl.getUnforbid_time() >= time){
				User user = fl.getUser_id();
				user.setIdentity(5);
			}
		}
		
		forbidListDao.removeTimeoutForbid();
	}

	@Override
	public ForbidList findByUser(String userid) {
		return forbidListDao.findByUser(userid);
	}
}
