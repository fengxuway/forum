package service;

import java.util.List;

import entity.SecretDialog;

public interface SecretDialogService extends BaseService<SecretDialog> {
	
	/**
	 * 获取指定用户的所有私信对话list
	 * @param userid 指定用户ID
	 * @return List<SecretDialog>
	 */
	List<SecretDialog> getUserDialogs(String userid);

}
