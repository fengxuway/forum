package entity;

import java.io.Serializable;
import java.util.*;
import org.apache.struts2.json.annotations.*;
public class Subject  implements Serializable {
	private static final long serialVersionUID = 3148168080179124682L;
	
	private int sub_id;
	private String sub_title;//发贴标题
	private String sub_content;//发贴内容
	private Date sub_time;//发贴时间
	private int reply_num = 0;//回复数
	private Date last_reply_time;//最后回复时间
	private Reply last_reply;

	private boolean if_perfect = false;//是否精品
	private boolean if_delete = false;//是否已删除
	private boolean if_top = false;//是否置顶
	private boolean if_sign = false;//是否显示签名档

	
	private User author;	//关联用户表user（多对一）
	private Category cate_id;//关联分类表Category（多对一）
	private Set<Reply> replys;//关联回复表Reply（一对多）
	
	
	public Subject(){}
	public Subject(String sub_title, String sub_content, boolean if_sign, Date sub_time){
		this.sub_title = sub_title;
		this.sub_content = sub_content;
		this.if_sign = if_sign;
		this.sub_time = sub_time;
	}
	
	
	@Override
	public String toString() {
		return "Subject<"+sub_id+", "+sub_title+">";
	}
	public int getSub_id() {
		return sub_id;
	}
	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}
	public String getSub_title() {
		return sub_title;
	}
	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}
	public String getSub_content() {
		return sub_content;
	}
	public void setSub_content(String sub_content) {
		this.sub_content = sub_content;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getSub_time() {
		return sub_time;
	}
	public void setSub_time(Date sub_time) {
		this.sub_time = sub_time;
	}
	public int getReply_num() {
		return reply_num;
	}
	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}
	public boolean isIf_perfect() {
		return if_perfect;
	}
	public void setIf_perfect(boolean if_perfect) {
		this.if_perfect = if_perfect;
	}
	public boolean isIf_top() {
		return if_top;
	}
	public void setIf_top(boolean if_top) {
		this.if_top = if_top;
	}
	public boolean isIf_sign() {
		return if_sign;
	}
	public void setIf_sign(boolean if_sign) {
		this.if_sign = if_sign;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public Category getCate_id() {
		return cate_id;
	}
	public void setCate_id(Category cate_id) {
		this.cate_id = cate_id;
	}
	@JSON(serialize=false)
	public Set<Reply> getReplys() {
		return replys;
	}
	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}
	public boolean isIf_delete() {
		return if_delete;
	}
	public void setIf_delete(boolean if_delete) {
		this.if_delete = if_delete;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getLast_reply_time() {
		return last_reply_time;
	}
	public void setLast_reply_time(Date last_reply_time) {
		this.last_reply_time = last_reply_time;
	}
	
	public Reply getLast_reply() {
		return last_reply;
	}
	public void setLast_reply(Reply last_reply) {
		this.last_reply = last_reply;
	}
	
	
	
	
}
