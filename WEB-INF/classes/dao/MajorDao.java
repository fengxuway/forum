package dao;

import entity.Major;
import java.util.*;
public interface MajorDao extends BaseDao<Major> {
	public List<Major> findByCollege(int college_id);

}
 