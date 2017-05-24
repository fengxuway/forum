package entity;
import java.util.*;
import java.io.Serializable;
import org.apache.struts2.json.annotations.*;
public class Category implements Serializable  {
	private static final long serialVersionUID = 2381306469528047581L;

	private int cate_id;
	private String cate_name;
	private String cate_info;
	private int subject_num;
	private int reply_num;
	private Date last_time = null;
	
	private User cate_admin;
	private Set<Subject> subjects = new HashSet<Subject>();//关联主题表Subject双向一对多映射
	
	public Category(){}
	
	public Category(String cate_name, String cate_info){
		this.cate_name = cate_name;
		this.cate_info = cate_info;
		
	}
	@Override
	public String toString() {
		return "Category<"+cate_id+", "+cate_name+">";
	}
	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getCate_info() {
		return cate_info;
	}

	public void setCate_info(String cate_info) {
		this.cate_info = cate_info;
	}


	@JSON(serialize=false)
	public Set<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}

	public Date getLast_time() {
		return last_time;
	}

	public void setLast_time(Date last_time) {
		this.last_time = last_time;
	}

	public int getSubject_num() {
		return subject_num;
	}

	public void setSubject_num(int subject_num) {
		this.subject_num = subject_num;
	}

	public int getReply_num() {
		return reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public User getCate_admin() {
		return cate_admin;
	}

	public void setCate_admin(User cate_admin) {
		this.cate_admin = cate_admin;
	}
	
	
}
