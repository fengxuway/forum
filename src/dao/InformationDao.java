package dao;

import java.util.List;

import pagination.PageBean;

import entity.Information;
import entity.User;

public interface InformationDao extends BaseDao<Information>{
	/**
	 * 获取回复指定用户的列表
	 * @param user 指定用户
	 * @param pageNum 请求页码
	 * @return PageBean
	 */
	PageBean getReplyMe(User user, int pageNum) throws Exception;

	/**
	 * 获取提到指定用户的列表
	 * @param user 指定用户
	 * @param pageNum 请求页码
	 * @return PageBean
	 */
	PageBean getMentionMe(User user, int pageNum);
	
	/**
	 * 获取指定用户列表的动态
	 * @param users 指定用户列表
	 * @param pageNum 页码
	 * @return PageBean
	 */
	PageBean getDynamic(List<User> users, int pageNum);

	/**
	 * 获取指定用户的系统消息
	 * @param user_id 用户
	 * @param page 页码
	 * @return 系统消息的分页bean
	 */
	PageBean getNews(String user_id, int page);

}
 