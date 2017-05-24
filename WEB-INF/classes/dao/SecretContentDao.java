package dao;

import java.util.List;

import entity.SecretContent;

public interface SecretContentDao extends BaseDao<SecretContent> {
	/**
	 * 根据指定的用户和对话ID获取与该用户相关的对话内容
	 * @param userid 指定用户
	 * @param dialog_id SecretDialog的ID
	 * @return SecretContent的List列表
	 */
	List<SecretContent> getUserContents(String userid, int dialog_id);

	/**
	 * 获取指定用户指定对话中尚未阅读的内容list
	 * @param user_id 指定用户ID
	 * @param dialog_id 指定对话框ID
	 * @return SecretContent的List
	 */
	List<SecretContent> getUnreadContent(String user_id, int dialog_id);

}
 