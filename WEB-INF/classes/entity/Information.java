package entity;

import java.io.Serializable;
import java.util.*;

import org.apache.struts2.json.annotations.JSON;

public class Information implements Serializable  {
	private static final long serialVersionUID = 1027349685205776515L;
	
	private int info_id;
	/**
	 * 1个人发表主题，
	 * 2个人发表回复，
	 * 3回复个人的回复，
	 * 4@提到个人的回复，
	 * 以下都是系统通知
	 * 5被关注通知
	 * 6被封禁通知
	 * 7被解除封禁通知
	 * 8提升为版主
	 * 9 取消版主
	 * 11 主题被top
	 * 12 notop
	 * 13 perfect
	 * 14 noperfect
	 * 15 delete 
	 * 16 nodelete
	 * 17 moveto
	 * 18 删除回复
	 */
	private int type;//消息类型(1个人发表主题，2个人发表回复，3回复个人的回复，4＠提到个人的回复，5被关注通知（系统）
	private String info_content;//消息内容
	private boolean if_read;//是否已读
	private Date time;//消息发布时间

	private User user_id;//关联User表（多对一）
	private Subject sub_id;//关联主题表（多对一）
	private Reply reply_id;//关联回复表reply（多对一）
	private User user;//关联User表（多对一）
	
	/**
	 * 
	 * @param type 消息分类
	 * @param info_content 消息内容
	 * @param if_read 是否已读
	 * @param time 当前时间
	 * @param user_id 被通知的用户
	 * @param sub_id 与消息有关的主题
	 * @param reply_id 与消息有关的回复帖
	 */
	public Information(int type, String info_content, boolean if_read,
			Date time, User user_id, Subject sub_id, Reply reply_id) {
		this.type = type;
		this.info_content = info_content;
		this.if_read = if_read;
		this.time = time;
		this.user_id = user_id;
		this.sub_id = sub_id;
		this.reply_id = reply_id;
	}
	public Information() {}
	@Override
	public String toString() {
		return "Information<"+info_id+">";
	}
	public int getInfo_id() {
		return info_id;
	}
	public void setInfo_id(int info_id) {
		this.info_id = info_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getInfo_content() {
		return info_content;
	}
	public void setInfo_content(String info_content) {
		this.info_content = info_content;
	}
	public boolean isIf_read() {
		return if_read;
	}
	public void setIf_read(boolean if_read) {
		this.if_read = if_read;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public Subject getSub_id() {
		return sub_id;
	}
	public void setSub_id(Subject sub_id) {
		this.sub_id = sub_id;
	}
	public Reply getReply_id() {
		return reply_id;
	}
	public void setReply_id(Reply reply_id) {
		this.reply_id = reply_id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
