package service;

import entity.Major;
import java.util.*;
public interface MajorService extends BaseService<Major> {
	/**
	 * 根据学院ID获得专业list
	 * @param college_id
	 * @return
	 */
	public List<Major> findByCollege(int college_id);
	
	/**
	 * 对findByCollege的改进方法，直接返回Action需要的专业list的映射Map<major_id, Major_name>
	 * @param college_id
	 * @return
	 */
	public Map<String, String> selectMajorByCollege(int college_id);

}
 