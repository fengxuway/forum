package entity;

import java.io.Serializable;
import java.util.*;
import org.apache.struts2.json.annotations.*;
public class Reply implements Serializable  {
	private static final long serialVersionUID = 7234688358402641631L;
	
	private int reply_id;
	private Date reply_time;//回复时间
	private String reply_content;//回复内容
	private boolean if_reply ;//是否为回帖的回复
	private Reply reply_superid;//回帖的ID
	private String anchor;//锚点
	private boolean if_delete = false;//是否已删除
	private boolean if_sign;//是否使用签名档

	private Subject sub_id;//关联主题（多对一）
	private User user_id;//关联用户（多对一）
	private Set<Reply> replys = new LinkedHashSet<Reply>();
	
	public Reply(){}
	
	public Reply(String reply_content, boolean if_sign,User author, Date reply_time, boolean if_reply, Reply reply_superid ) {
		super();
		this.reply_time = reply_time;
		this.reply_content = reply_content;
		this.if_reply = if_reply;
		this.reply_superid = reply_superid;
		this.if_sign = if_sign;
		this.user_id = author;
	}
	public Set<Reply> getReplys() {
		return replys;
	}

	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}

	@Override
	public String toString() {
		return "Reply<"+reply_id+">";
	}
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getReply_time() {
		return reply_time;
	}
	public void setReply_time(Date reply_time) {
		this.reply_time = reply_time;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public boolean isIf_reply() {
		return if_reply;
	}
	public void setIf_reply(boolean if_reply) {
		this.if_reply = if_reply;
	}
	
	public Reply getReply_superid() {
		return reply_superid;
	}
	public void setReply_superid(Reply reply_superid) {
		this.reply_superid = reply_superid;
	}
	public String getAnchor() {
		return anchor;
	}
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}
	public boolean isIf_sign() {
		return if_sign;
	}
	public void setIf_sign(boolean if_sign) {
		this.if_sign = if_sign;
	}
	public Subject getSub_id() {
		return sub_id;
	}
	public void setSub_id(Subject sub_id) {
		this.sub_id = sub_id;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public boolean isIf_delete() {
		return if_delete;
	}
	public void setIf_delete(boolean if_delete) {
		this.if_delete = if_delete;
	}
	
	
}
