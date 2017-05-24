package service;

import java.util.List;

import entity.SecretContent;

public interface SecretContentService extends BaseService<SecretContent> {
	
	/**
	 * 根据指定的用户和对话ID获取与该用户相关的对话内容<br>
	 * 同时将与该对话相关的内容设置为已读
	 * @param userid 指定用户
	 * @param dialog_id SecretDialog的ID
	 * @return SecretContent的List列表
	 */
	List<SecretContent> getUserContents(String userid, int dialog_id);

	/**
	 * 发布私信内容
	 * @param userid1  用户1ID（该用户为发表私信的用户）
	 * @param userid2 用户2ID
	 * @param content 私信内容
	 * @return 返回保存的SecretContent对象
	 * @throws Exception 如果发布失败，抛出异常
	 */
	SecretContent publishSecret(String userid1, String userid2,
			String content) throws Exception;

	/**
	 * 将指定id的SecretContent内容中的指定用户的visible设置为false，以达到不可见的目的
	 * @param content_id 指定SecretContent的ID
	 * @param user_id 指定用户ID
	 * @throws 当更新异常时，抛出异常
	 */
	void deleteUserContent(int content_id, String user_id) throws Exception;

	/**
	 * 将指定id的SecretDialog内部的内容中的指定用户的visible设置为false，以达到不可见的目的
	 * @param dialog_id 指定SecretDialog的ID
	 * @param user_id 指定用户ID
	 * @throws Exception 
	 * @throws 当更新异常时，抛出异常
	 */
	void deleteUserDialog(int dialog_id, String user_id) throws Exception;

	/**
	 * 获取指定用户指定对话尚未阅读的内容
	 * @param user_id 指定用户
	 * @param dialog_id 指定对话框
	 * @return 用户未阅读的SecretContent的List
	 */
	List<SecretContent> getUnreadContent(String user_id, int dialog_id) throws Exception;

}
 