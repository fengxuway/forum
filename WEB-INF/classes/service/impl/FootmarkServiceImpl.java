package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import dao.*;
import entity.*;
import service.*;
@Service("footmarkService")
public class FootmarkServiceImpl implements FootmarkService {
	private static final Logger logger = Logger.getLogger(FootmarkServiceImpl.class);
	@Autowired
	@Qualifier("footmarkDao")
	private FootmarkDao footmarkDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private SubjectDao subjectDao;

	@Override
	public Footmark get(Serializable id) {
		return footmarkDao.get(id);
	}
 
	@Override
	public List<Footmark> getAll() {
		return footmarkDao.getAll();
	}

	@Override
	public Serializable save(Footmark o) {
		return footmarkDao.save(o);
	}

	@Override
	public void delete(Footmark o) {
		footmarkDao.delete(o);
	}

	@Override
	public void update(Footmark o) {
		footmarkDao.update(o);
	}

	@Override
	public void saveOrUpdate(Footmark o) {
		footmarkDao.saveOrUpdate(o);
	}

	@Override
	public List<Footmark> getUserFootmark(String userid) {
		return footmarkDao.getUserFootmark(userid);
	}

	@Override
	public void addFootmark(String userid, int subid) {
		Footmark fm = footmarkDao.findByUserAndSub(userid, subid);
		Date now = new Date();
		if(fm != null){
			fm.setFoot_time(now);
		}else{
			save(new Footmark(now, userDao.get(userid), subjectDao.get(subid)));
		}
	}

}
