package service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import dao.*;

import entity.College;
import entity.Major;
import service.MajorService;
@Service("majorService")
public class MajorServiceImpl implements MajorService {
	private static final Logger logger = Logger.getLogger(MajorServiceImpl.class);
	@Autowired
	@Qualifier("majorDao")
	private MajorDao majorDao;
	@Override
	public Major get(Serializable id) {
		return majorDao.get(id);
	}

	@Override
	public List<Major> getAll() {
		return majorDao.getAll();
	} 

	@Override
	public Serializable save(Major o) {
		return majorDao.save(o);
	}

	@Override
	public void delete(Major o) {
		majorDao.delete(o);
	}

	@Override
	public void update(Major o) {
		majorDao.update(o);
	}

	@Override
	public void saveOrUpdate(Major o) {
		majorDao.saveOrUpdate(o);
	}

	@Override
	public List<Major> findByCollege(int college_id) {
		return majorDao.findByCollege(college_id);
	}

	@Override
	public Map<String, String> selectMajorByCollege(int college_id) {
		Map<String, String> majorMap = new HashMap<String, String>();
		List<Major> majorList = findByCollege(college_id);
		if (college_id > 0) {
			for (Iterator<Major> it = majorList.iterator(); it.hasNext();) {
				Major major = it.next();
				majorMap.put(Integer.toString(major.getMajor_id()),
						major.getMajor_name());
			}
		}
		
		return majorMap;
	}

}
