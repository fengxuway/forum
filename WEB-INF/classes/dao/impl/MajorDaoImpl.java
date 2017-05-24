package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import dao.MajorDao;
import entity.Major;
@Repository("majorDao")
public class MajorDaoImpl extends HibernateBaseDao<Major> implements MajorDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Major> findByCollege(int college_id) {
		List<Major> result =  getHT().find("from Major  m where m.college.college_id=?", college_id);
		return result;
	}

}
 