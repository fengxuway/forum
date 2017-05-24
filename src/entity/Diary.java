package entity;
import java.io.Serializable;
import java.util.*;
public class Diary implements Serializable  {
	private static final long serialVersionUID = -4700142413367790467L;

	private int diary_id;
	/**
	 * 1 top
	 * 2 notop
	 * 3 perfect
	 * 4 noperfect
	 * 5 delete
	 * 6 nodelete
	 * 7 moveTo
	 * 
	 * 10 封禁用户
	 * 11管理员主动解封用户 
	 */
	private int type;//日志类型
	private Date time;
	private int operate;//int型标识操作类型
	private String operate_id;//操作人关联user表（多对一）
	private String author_id; //作者或被封禁用户（多对一  唯一性unique）
	private String title;//日志与贴子有关的是帖子标题，与封禁有关的是封禁原因
 	
	public Diary() {
	}
	/**
	 * @param type 日志类型
	 * @param time 当前时间
	 * @param operate_id 操作人ID
	 * @param author_id 被操作ID或作者
	 * @param title 内容、备注
	 */
	public Diary(int type, Date time, String operate_id,
			String author_id, String title) {
		super();
		this.type = type;
		this.time = time;
		this.operate_id = operate_id;
		this.author_id = author_id;
		this.title = title;
	}
	@Override
	public String toString() {
		return "Diary<"+diary_id+">";
	}
	public int getDiary_id() {
		return diary_id;
	}
	public void setDiary_id(int diary_id) {
		this.diary_id = diary_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getOperate() {
		return operate;
	}
	public void setOperate(int operate) {
		this.operate = operate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getOperate_id() {
		return operate_id;
	}
	public void setOperate_id(String operate_id) {
		this.operate_id = operate_id;
	}
	public String getAuthor_id() {
		return author_id;
	}
	public void setAuthor_id(String author_id) {
		this.author_id = author_id;
	}
	
	
}
