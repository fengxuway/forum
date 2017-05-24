package service;

import pagination.PageBean;
import entity.Reply;
import entity.User;

public interface ReplyService extends BaseService<Reply> {
	/**
	 * 根据主题ID及页码查询回复贴
	 * @param sub_id 主题ID
	 * @param page 请求页码
	 * @return PageBean对象
	 */
	PageBean findBySubject(int sub_id, int page);
	
	/**
	 * 发表回复
	 * @param reply_content 回复内容
	 * @param if_sign 是否显示签名档
	 * @param sub_id 回复的主题ID
	 * @param user 发表主题的用户
	 * @param if_reply 是否为回复的回复
	 * @param reply_superid 回复的回复的ID
	 * @return int 发表的回复的ID
	 */
	Reply publishReply(String reply_content, boolean if_sign, int sub_id, User user, boolean if_reply, int reply_superid) throws Exception;
	
	/**
	 * 删除回复，即更改回复的if_delete字段为true
	 * @param reply_id 要删除的回复ID
	 * @param operUserid 仅为了AOP时写入日志中，与操作逻辑无关
	 */
	void deleteReply(int reply_id, String operUserid) throws Exception;
	
	/**
	 * 根据回复ID获取其主题的对象，以及回复所在的页码数
	 * @param reply_id 回复ID
	 * @return Object[] 其中Object[0]为主题对象Subject，Object[1]为回复所在页码数Integer
	 * @throws Exception
	 */
	Object[] getSubAndPage(int reply_id) throws Exception;
	
}
