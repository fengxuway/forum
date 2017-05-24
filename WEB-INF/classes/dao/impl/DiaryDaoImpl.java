package dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import pagination.MemberDao;
import pagination.PageBean;

import dao.DiaryDao;
import entity.Diary;
@Repository("diaryDao")
public class DiaryDaoImpl extends HibernateBaseDao<Diary> implements DiaryDao {

	@Autowired
	private MemberDao memberDao;
	
	@Override
	public PageBean getDeleteSubject(int page) {
		PageBean result = memberDao.findForPage("from Diary where type=5 or type=6 order by time desc", 6, page);
		return result;
	}

	@Override
	public PageBean getJiajingSubject(int page) {
		return memberDao.findForPage("from Diary where type=3 or type=4 order by time desc", 6, page);
	}

	@Override
	public PageBean getZhidingSubject(int page) {
		return memberDao.findForPage("from Diary where type=1 or type=2 order by time desc", 6, page);
	}

	@Override
	public PageBean getFengjin(int page) {
		return memberDao.findForPage("from Diary where type=10 or type=11 order by time desc", 6, page);
		
	}

}
