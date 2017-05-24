package entity;
import java.io.Serializable;
import java.util.*;

public class Footmark  implements Serializable {
	private static final long serialVersionUID = 6136032240992652600L;

	private int foot_id;
	private Date foot_time;//查看时间
	
	private User user_id;//关联用户user（多对一）
	private Subject sub_id;//关联主题（多对一）
	
	public Footmark() {	}
	public Footmark(Date foot_time, User user_id, Subject sub_id) {
		super();
		this.foot_time = foot_time;
		this.user_id = user_id;
		this.sub_id = sub_id;
	}
	@Override
	public String toString() {
		return "Footmark<"+foot_id+", "+user_id.getUser_id()+">";
	}

	public int getFoot_id() {
		return foot_id;
	}
	public void setFoot_id(int foot_id) {
		this.foot_id = foot_id;
	}
	public Date getFoot_time() {
		return foot_time;
	}

	public void setFoot_time(Date foot_time) {
		this.foot_time = foot_time;
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
	
	
}
