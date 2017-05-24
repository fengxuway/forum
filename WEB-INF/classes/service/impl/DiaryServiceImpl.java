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
@Service("diaryService")
public class DiaryServiceImpl implements DiaryService {
	private static final Logger logger = Logger.getLogger(DiaryServiceImpl.class);
	@Autowired
	@Qualifier("diaryDao")
	private DiaryDao diaryDao;
	
	
	@Override
	public Diary get(Serializable id) {
		return diaryDao.get(id);
	} 

	@Override
	public List<Diary> getAll() {
		return diaryDao.getAll();
	}

	@Override
	public Serializable save(Diary o) {
		return diaryDao.save(o);
	}

	@Override
	public void delete(Diary o) {
		diaryDao.delete(o);
	}

	@Override
	public void update(Diary o) {
		diaryDao.update(o);
	}

	@Override
	public void saveOrUpdate(Diary o) {
		diaryDao.saveOrUpdate(o);
	}

	@Override
	public PageBean getDeleteSubject(int page) {
		return diaryDao.getDeleteSubject(page);
	}

	@Override
	public PageBean getJiajingSubject(int page) {
		return diaryDao.getJiajingSubject(page);
	}

	@Override
	public PageBean getZhidingSubject(int page) {
		return diaryDao.getZhidingSubject(page);
	}

	@Override
	public PageBean getFengjin(int page) {
		return diaryDao.getFengjin(page);
	}

}
