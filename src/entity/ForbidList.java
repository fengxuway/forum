package entity;
import java.io.Serializable;
import java.util.*;
public class ForbidList implements Serializable  {
	private static final long serialVersionUID = 8292442537521209749L;
	
	private Integer forbid_id;
	private User user_id;//被封用户ID
	private int days;//被封禁天数
	private String reason;//封禁原因
	private User operate_id;//操作人
	private Date operate_time;//操作时间
	private long unforbid_time;//解封时间 

	public ForbidList() {}
	/**
	 * 创建封禁对象
	 * @param user_id 被封禁用户
	 * @param days 天数
	 * @param operate_id 操作人
	 * @param operate_time 操作时间
	 * @param unforbid_time 解封禁时间
	 */
	public ForbidList(User user_id, int days, User operate_id,
			Date operate_time, long unforbid_time) {
		super();
		this.user_id = user_id;
		this.days = days;
		this.operate_id = operate_id;
		this.operate_time = operate_time;
		this.unforbid_time = unforbid_time;
	}
	@Override
	public String toString() {
		return "ForbidList<"+forbid_id+", "+user_id.getUser_id()+">";
	}
	public Integer getForbid_id() {
		return forbid_id;
	}
	public void setForbid_id(Integer forbid_id) {
		this.forbid_id = forbid_id;
	}
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public User getOperate_id() {
		return operate_id;
	}
	public void setOperate_id(User operate_id) {
		this.operate_id = operate_id;
	}
	public Date getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(Date operate_time) {
		this.operate_time = operate_time;
	}
	public long getUnforbid_time() {
		return unforbid_time;
	}
	public void setUnforbid_time(long unforbid_time) {
		this.unforbid_time = unforbid_time;
	}
	
	
	
}
