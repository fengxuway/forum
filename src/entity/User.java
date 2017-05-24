package entity;

import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import org.apache.struts2.json.annotations.*;
public class User implements Serializable {
	private static final long serialVersionUID = -730416534275831367L;
	
	private String user_id;		//用户ID
	private String userid_encode;//用户ID的utf-8编码
	private String password;	//密码
	private String uuid;		//唯一标识
	private String email;		//email
	private boolean sex;		//性别
	private long active_time;	//最后激活时间
	/*-19--10	申请注册状态	-15
	-9--1 同意申请但未激活  -5
	0-9 激活成功，可以登录  5
	10-19 封禁状态 15*/ 
//	private int state = -15;	//
	private int identity = -15;//用户状态0-99普通用户， 101-999版主（减去100为版主管理的板块ID），1000表示超级管理员
	private String sign = "";		//个性签名
	private String photo = "default_head_img.jpg";//头像
	private boolean if_dynamic = true;	//是否允许查看动态
	private boolean if_attention = true;//是否允许关注
	private int exp = 1;			//经验
	// private int grade = 1;			//等级
	private Rank rank;
	// private boolean if_forbid = false;//是否被封禁
	
	private String name;//真实姓名
	private String student_id;//学号
	private College college;//所在院系
	private Major major;//所在专业
	
	
	public User(){}
	public User(String user_id, String email, String password){
		this.user_id = user_id;
		try {
			this.userid_encode = URLEncoder.encode(user_id, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.email = email;
		this.password = password;
	}
	
	public User(String userid, String email, String pswd, String name, boolean sex, String student_id){
		this.user_id = userid;
		try {
			this.userid_encode = URLEncoder.encode(user_id, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.email = email;
		this.password = pswd;
		this.name = name;
		this.sex = sex;
		this.student_id = student_id;
	}
	
	
	private Set<Subject> subjects = new HashSet<Subject>();//关联主题表subject（一对多）	
	private Set<Reply> replys = new HashSet<Reply>();//关联回复表reply（一对多）
	private Set<Information> informations = new HashSet<Information>();//关联消息表information（一对多）
	private Set<Footmark> footmarks = new HashSet<Footmark>();//关联足迹表footmark（一对多）
	private Set<SecretDialog> secretDialogA = new HashSet<SecretDialog>();//关联私信对话表secret_dialog userA列（一对多）
	private Set<SecretDialog> secretDialogB = new HashSet<SecretDialog>();//关联私信对话表secret_dialog userB列（一对多）
	private Set<Attention> atteMe = new HashSet<Attention>();//关注我的(粉丝)Attention（一对多）
	private Set<Attention> MyAtte = new HashSet<Attention>();//我的关注Attention（一对多）
	
	
	
	public String getUser_id() {
		return user_id;
	}
	@Override
	public String toString() {
		return "User<"+user_id+">";
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@JSON(serialize=false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@JSON(serialize=false)
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@JSON(serialize=false)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@JSON(serialize=false)
	public long getActive_time() {
		return active_time;
	}
	public void setActive_time(long active_time) {
		this.active_time = active_time;
	}
	
	/*public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}*/
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public boolean isIf_dynamic() {
		return if_dynamic;
	}
	public void setIf_dynamic(boolean if_dynamic) {
		this.if_dynamic = if_dynamic;
	}
	public boolean isIf_attention() {
		return if_attention;
	}
	public void setIf_attention(boolean if_attention) {
		this.if_attention = if_attention;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	/* public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	} */
	
	public Rank getRank() {
		return rank;
	}
	public void setRank(Rank rank) {
		this.rank = rank;
	}
	@JSON(serialize=false)
	public Set<Subject> getSubjects() {
		return subjects;
	}
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	@JSON(serialize=false)
	public Set<Reply> getReplys() {
		return replys;
	}
	public void setReplys(Set<Reply> replys) {
		this.replys = replys;
	}
	@JSON(serialize=false)
	public Set<Information> getInformations() {
		return informations;
	}
	public void setInformations(Set<Information> informations) {
		this.informations = informations;
	}
	@JSON(serialize=false)
	public Set<Footmark> getFootmarks() {
		return footmarks;
	}
	public void setFootmarks(Set<Footmark> footmarks) {
		this.footmarks = footmarks;
	}
	@JSON(serialize=false)
	public Set<SecretDialog> getSecretDialogA() {
		return secretDialogA;
	}
	public void setSecretDialogA(Set<SecretDialog> secretDialogA) {
		this.secretDialogA = secretDialogA;
	}
	@JSON(serialize=false)
	public Set<SecretDialog> getSecretDialogB() {
		return secretDialogB;
	}
	public void setSecretDialogB(Set<SecretDialog> secretDialogB) {
		this.secretDialogB = secretDialogB;
	}
	@JSON(serialize=false)
	public Set<Attention> getAtteMe() {
		return atteMe;
	}
	public void setAtteMe(Set<Attention> atteMe) {
		this.atteMe = atteMe;
	}
	@JSON(serialize=false)
	public Set<Attention> getMyAtte() {
		return MyAtte;
	}
	public void setMyAtte(Set<Attention> myAtte) {
		MyAtte = myAtte;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	@JSON(serialize=false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JSON(serialize=false)
	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}


	@JSON(serialize=false)
	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	@JSON(serialize=false)
	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int identity) {
		this.identity = identity;
	}
	public String getUserid_encode() {
		return userid_encode;
	}
	public void setUserid_encode(String userid_encode) {
		this.userid_encode = userid_encode;
	}
	
}
