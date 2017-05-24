package dao;

import java.util.List;

import entity.SecretDialog;

public interface SecretDialogDao extends BaseDao<SecretDialog>{
	/**
	 * 获取指定用户的所有私信对话list
	 * @param userid 指定用户ID
	 * @return List<SecretDialog>
	 */
	List<SecretDialog> getUserDialogs(String userid);

	/**
	 * 根据两个用户查询对应的对话SecretDialog
	 * @param userid1 用户1
	 * @param userid2 用户2
	 * @return 如果存在，返回SecretDialog；如果不存在，返回null
	 */
	SecretDialog findByUsers(String userid1, String userid2);

}
 