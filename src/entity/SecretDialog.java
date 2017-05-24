package entity;
import java.io.Serializable;
import java.util.*;

import org.apache.struts2.json.annotations.JSON;

public class SecretDialog  implements Serializable {
	private static final long serialVersionUID = -6277747792585125436L;
	
	private int dialog_id;
	private User userA;
	private User userB;
	private Date dialog_time;
	private Set<SecretContent> secretContents = new HashSet<SecretContent>();//关联私信内容（一对多）
	
	public SecretDialog() {}
	
	public SecretDialog(User userA, User userB, Date dialog_time) {
		this.userA = userA;
		this.userB = userB;
		this.dialog_time = dialog_time;
	}
	@Override
	public String toString() {
		return "SecretDialog<"+dialog_id+">";
	}
	public int getDialog_id() {
		return dialog_id;
	} 
	public void setDialog_id(int dialog_id) {
		this.dialog_id = dialog_id;
	}
	public User getUserA() {
		return userA;
	}
	public void setUserA(User userA) {
		this.userA = userA;
	}
	public User getUserB() {
		return userB;
	}
	public void setUserB(User userB) {
		this.userB = userB;
	}
	public Set<SecretContent> getSecretContents() {
		return secretContents;
	}
	public void setSecretContents(Set<SecretContent> secretContents) {
		this.secretContents = secretContents;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getDialog_time() {
		return dialog_time;
	}
	public void setDialog_time(Date dialog_time) {
		this.dialog_time = dialog_time;
	}
	
	
}
