package service;

import entity.User;

public interface SendMail {
	/**
	 * 发送激活邮件
	 * @param user 对象包含邮箱、uuid等信息
	 */
	public void sendActivateMail(User user) throws Exception;

	/**
	 * 发送获取忘记密码时的6位验证码
	 * @param email 被发送的邮箱
	 * @param cw 验证码
	 */
	void sendCheckWordMail(String email, String cw);
}
 