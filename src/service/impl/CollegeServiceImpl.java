package service.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import dao.CollegeDao;

import org.apache.log4j.Logger;
import entity.College;
import service.CollegeService;
@Service("collegeService")
public class CollegeServiceImpl implements CollegeService {
	private static final Logger logger = Logger.getLogger(CollegeServiceImpl.class);
	@Autowired
	@Qualifier("collegeDao")
	private CollegeDao collegeDao;
	@Override
	public College get(Serializable id) {
		return collegeDao.get(id);
	}
 
	@Override
	public List<College> getAll() {
		return collegeDao.getAll();
	}

	@Override
	public Serializable save(College o) {
		return collegeDao.save(o);
	}

	@Override
	public void delete(College o) {
		collegeDao.delete(o);
	}

	@Override
	public void update(College o) {
		collegeDao.update(o);
	}

	@Override
	public void saveOrUpdate(College o) {
		collegeDao.saveOrUpdate(o);
	}

}
