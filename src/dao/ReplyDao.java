package dao;

import java.util.List;

import entity.Reply;

public interface ReplyDao extends BaseDao<Reply> {

	
	/**
	 * 根据主题ID获取所有未删除的回复
	 * @param sub_id 主题ID
	 * @return List
	 */
	List<Reply> findBySubject(int sub_id);
	
	/**
	 * 根据主题ID获取所有未删除、且不是回复的回复贴
	 * @param sub_id 主题ID
	 * @return List
	 */
	List<Reply> findBySubjectNoReply(int sub_id);

}
 