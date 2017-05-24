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
@Service("informationService")
public class InformationServiceImpl implements InformationService {
	private static final Logger logger = Logger.getLogger(InformationServiceImpl.class);
	@Autowired
	@Qualifier("informationDao")
	private InformationDao informationDao;
	
	@Override
	public PageBean getReplyMe(User user,int  pageNum) throws Exception{
		return informationDao.getReplyMe(user, pageNum);
	}
	
	@Override
	public PageBean getMentionMe(User user, int pageNum) {
		return informationDao.getMentionMe(user, pageNum);
	}
	
	@Override
	public Information get(Serializable id) {
		return informationDao.get(id);
	}
 
	@Override
	public List<Information> getAll() {
		return informationDao.getAll();
	}

	@Override
	public Serializable save(Information o) {
		return informationDao.save(o);
	}

	@Override
	public void delete(Information o) {
		informationDao.delete(o);
	}

	@Override
	public void update(Information o) {
		informationDao.update(o);
	}

	@Override
	public void saveOrUpdate(Information o) {
		informationDao.saveOrUpdate(o);
	}

	@Override
	public PageBean getNews(String user_id, int page) {
		return informationDao.getNews(user_id, page);
	}



}
