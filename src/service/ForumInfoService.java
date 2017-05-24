package service;

import entity.ForumInfo;

public interface ForumInfoService extends BaseService<ForumInfo> {
	/**
	 * 获取论坛的sign
	 * @return sign信息
	 */
	String getForumSign();
}
 