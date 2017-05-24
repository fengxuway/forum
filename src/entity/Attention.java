package entity;

import java.io.Serializable;

public class Attention  implements Serializable {
	private static final long serialVersionUID = 7487921912878360948L;

	private int atte_id;
	private User atte_byuserid;//关联关注人
	private User atte_userid;//关联被关注人
	
	public Attention(){}
	public Attention(User byuserid, User userid){
		this.atte_byuserid = byuserid;
		this.atte_userid = userid;
	}
	
	@Override
	public String toString() {
		return "Attention<"+atte_id+">";
	}
	public int getAtte_id() {
		return atte_id;
	}
	public void setAtte_id(int atte_id) {
		this.atte_id = atte_id;
	}
	public User getAtte_byuserid() {
		return atte_byuserid;
	}
	public void setAtte_byuserid(User atte_byuserid) {
		this.atte_byuserid = atte_byuserid;
	}
	public User getAtte_userid() {
		return atte_userid;
	}
	public void setAtte_userid(User atte_userid) {
		this.atte_userid = atte_userid;
	}
	
	

}
