package service;

import java.util.List;

import entity.*;

public interface RankService extends BaseService<Rank> {
	/**
	 * 根据等级获取头衔对象rank
	 * @param grade 等级
	 * @return Rank 未找到返回null
	 */
	Rank findByGrade(int grade);
}
