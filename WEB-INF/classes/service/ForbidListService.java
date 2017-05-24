package service;

import pagination.PageBean;
import entity.ForbidList;
import entity.User;

public interface ForbidListService extends BaseService<ForbidList> {

	/**
	 * 根据指定用户查看指定的封禁信息(管理员可查看所有, 版主可查看自己封禁的用户List)
	 * @param page 页码
	 * @param uu 当前查看的用户
	 * @return PageBean
	 */
	PageBean getForbidList(int page, User uu);
	
	/**
	 * 根据userid获取forbid
	 * @param userid 用户id
	 * @return Forbid对象
	 */
	ForbidList findByUser(String userid);

	/**
	 * 移除封禁
	 * @param forbid_id 封禁表的ID
	 * @param user_id 操作人ID
	 */
	User removeForbid(int forbid_id, String user_id) throws Exception;

	/**
	 * 删除封禁时间已过的用户列表，并将用户的Identity = 5 （ 在myTimer中调用）
	 */
	void removeTimeoutForbid();
}
 