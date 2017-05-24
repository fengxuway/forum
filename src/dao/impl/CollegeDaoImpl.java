package dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import dao.CollegeDao;
import entity.College;
@Repository("collegeDao")
public class CollegeDaoImpl extends HibernateBaseDao<College> implements CollegeDao {



}
 