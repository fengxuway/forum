package service;

import java.util.List;

import entity.Footmark;

public interface FootmarkService extends BaseService<Footmark> {

	/**
	 * 获取指定用户的足迹
	 * @param userid
	 * @return
	 */
	List<Footmark> getUserFootmark(String userid);
	
	/**
	 * 添加指定用户的足迹(如果已经看过, 则更新时间)
	 * @param userid 指定用户ID
	 * @param sub_id 指定主题ID
	 */
	void addFootmark(String userid, int sub_id);
}
 