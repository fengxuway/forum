package service.impl;

import static util.MyRegex.*;
import java.io.Serializable;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import action.ReplyAction;
import dao.*;
import entity.*;
import service.*;
import util.*;

@Service("rankService")
public class RankServiceImpl implements RankService {
	private static final Logger logger = Logger.getLogger(RankServiceImpl.class);
	@Autowired
	@Qualifier("rankDao")
	private RankDao rankDao;
	
	public Rank findByGrade(int grade){
		return rankDao.findByGrade(grade);
	}

	@Override
	public Serializable save(Rank rank) {
		return rankDao.save(rank);
	}

	@Override
	public void delete(Rank rank) {
		rankDao.delete(rank);
	}

	@Override
	public void update(Rank rank) {
		rankDao.update(rank);
	}

	@Override
	public Rank get(Serializable id) {
		return rankDao.get(id);
	}

	@Override
	public List<Rank> getAll() {
		return rankDao.getAll();
	}

	@Override
	public void saveOrUpdate(Rank o) {
		rankDao.saveOrUpdate(o);
	}



}
