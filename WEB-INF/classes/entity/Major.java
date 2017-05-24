package entity;

import java.io.Serializable;

public class Major implements Serializable {
	private static final long serialVersionUID = -2495472305902248237L;
	private int major_id;
	private String major_name;
	private College college;
	
	public Major(){}
	public Major(String major_name){
		this.major_name = major_name;
	}
	public Major(String major_name, College college){
		this(major_name);
		this.college = college;
	}
	@Override
	public String toString() {
		return "Major<"+major_id+", "+major_name+">";
	}
	public int getMajor_id() {
		return major_id;
	}
	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}
	public String getMajor_name() {
		return major_name;
	}
	public void setMajor_name(String major_name) {
		this.major_name = major_name;
	}
	public College getCollege() {
		return college;
	}
	public void setCollege(College college) {
		this.college = college;
	}
	
	

}
