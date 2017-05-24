package entity;

import java.io.Serializable;

public class Rank implements Serializable{
	private static final long serialVersionUID = 7806063812722051143L;
	private int rank_id;
	private int grade;
	private int exp;
	private String rank_name;
	private String img;
	
	public Rank(){}
	public Rank(int grade, int exp, String rank_name, String img){
		this.grade = grade;
		this.exp = exp;
		this.rank_name = rank_name;
		this.img = img;
	}
	
	public String toString(){
		return "Rank<等级"+grade+">";
	}
		
	public void setRank_id(int r){
		rank_id = r;
	}
	
	public int getRank_id(){
		return rank_id;
	}
	
	public void setGrade(int g){
		grade = g;
	}
	
	public int getGrade(){
		return grade;
	}
	
	public void setExp(int e){
		exp = e;
	}
	
	public int getExp(){
		return exp;
	}
	
	public void setRank_name(String name){
		rank_name = name;
	}
	
	public String getRank_name(){
		return rank_name;
	}
	
	public void setImg(String i){
		img = i;
	}
	public String getImg(){
		return img; 
	}

}