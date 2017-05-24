package dao;
import java.util.*;

import entity.*;

public interface RankDao extends BaseDao<Rank> {
	/**
	 * 根据等级获取头衔对象rank
	 * @param grade 等级
	 * @return Rank 未找到返回null
	 */
	Rank findByGrade(int grade);
	
}
