package dao;

import java.util.List;

import pagination.PageBean;

import entity.ForbidList;
import entity.User;

public interface ForbidListDao extends BaseDao<ForbidList> {

	/**
	 * 根据被封禁的用户ID获取Forbid对象
	 * @param userid 指定用户ID
	 * @return Forbid对象,如果不存在数据,返回null
	 */
	ForbidList findByUser(String userid);

	/**
	 * 获取所有被封禁用户列表(super管理员访问)
	 * @param page 页码
	 * @return
	 */
	PageBean getForbidList(int page);

	/**
	 * 获取当前版主封禁的用户List
	 * @param page 页码
	 * @param uu 版主
	 * @return PageBean
	 */
	PageBean getOperUserForbidList(int page, User uu);

	/**
	 * 删除超时的封禁列表
	 */
	void removeTimeoutForbid();


}
 