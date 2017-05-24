package dao;

import java.util.List;
import java.util.Set;

import pagination.PageBean;

import entity.*;

public interface AttentionDao extends BaseDao<Attention> {
	
	/**
	 * 根据关注人的对象及被关注人的对象，查询是否存在已关注的对象
	 * @param byuser 关注人的对象
	 * @param user 被关注的对象
	 * @return Attention or null
	 */
	Attention findByUserAndUser(User byuser, User user);

	/**
	 * 判断用户B是否被用户A关注着
	 * @param userA 关注人
	 * @param userB 被关注人
	 * @return boolean
	 */
	boolean isAtte(String userA, String userB);
	
	/**
	 * 获取指定用户关注的用户
	 * @param user 指定用户
	 */
	List<Attention> getMyAtte(String userid);
	
	/**
	 * 获取关注指定用户的用户（粉丝）
	 * @param user 指定用户
	 */
	List<Attention> getAtteMe(String userid);
	
	
} 
 