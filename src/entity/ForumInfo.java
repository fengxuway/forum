package entity;

import java.io.Serializable;

public class ForumInfo  implements Serializable {
	private static final long serialVersionUID = 4919940080955978945L;
	
	private int forum_id;
	private String sign;

	@Override
	public String toString() {
		return "Forum<"+forum_id+">";
	}
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
	
}
