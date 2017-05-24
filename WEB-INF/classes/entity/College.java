package entity;

import java.io.Serializable;
import java.util.*;
import org.apache.struts2.json.annotations.*;

public class College implements Serializable {
	private static final long serialVersionUID = -6910489004231420800L;
	private int college_id;
	private String college_name;
	private Set<Major> majors = new HashSet<Major>();

	public College(){}
	public College(int college_id, String college_name){
		this.college_id = college_id;
		this.college_name = college_name;
	}
	@Override
	public String toString() {
		return "College<"+college_id+">";
	}
	public int getCollege_id() {
		return college_id;
	}
	public void setCollege_id(int college_id) {
		this.college_id = college_id;
	}
	public String getCollege_name() {
		return college_name;
	}
	public void setCollege_name(String college_name) {
		this.college_name = college_name;
	}
	@JSON(serialize=false)
	public Set<Major> getMajors() {
		return majors;
	}
	public void setMajors(Set<Major> majors) {
		this.majors = majors;
	}
	
	
	

}
