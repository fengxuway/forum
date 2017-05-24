package dao;

import java.util.List;

import entity.Footmark;

public interface FootmarkDao extends BaseDao<Footmark>{
	
	/**
	 * 获取指定用户的足迹
	 * @param userid
	 * @return
	 */
	List<Footmark> getUserFootmark(String userid);

	/**
	 * 根据指定用户与主题查找Footmark
	 * @param userid 指定用户ID
	 * @param subid 指定主题ID
	 * @return Footmark对象
	 */
	Footmark findByUserAndSub(String userid, int subid);
	
}
 