package service;

import java.util.List;
import java.util.Set;

import pagination.PageBean;

import entity.Attention;
import entity.User;

public interface AttentionService extends BaseService<Attention> {
	
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
	List<User> getMyAtte(String userid);
	
	/**
	 * 获取关注指定用户的用户（粉丝）
	 * @param user 指定用户
	 */
	List<User> getAtteMe(String userid);

	/**
	 * 获取指定用户的关注的用户的动态
	 * @param user 指定用户
	 * @param pageNum2 指定页码
	 * @return PageBean
	 */
	PageBean getAtteDynamic(User user, int pageNum2);

}
 