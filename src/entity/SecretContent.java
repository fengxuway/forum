package entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

public class SecretContent implements Serializable  {
	private static final long serialVersionUID = 850479345071901960L;
	
	private int secret_id;
	private String content;//私信内容
	private boolean if_Avisible;
	private boolean if_Bvisible;
	private boolean if_Aread;
	private boolean if_Bread;
	private String say;
	private Date content_time;
	private SecretDialog dialog_id;//关联私信对话表SecretDialog（多对一）
	
	public SecretContent() {}

	/*public SecretContent(String content, boolean if_Avisible,
			boolean if_Bvisible, boolean if_Aread, boolean if_Bread, String say) {
		this.content = content;
		this.if_Avisible = if_Avisible;
		this.if_Bvisible = if_Bvisible;
		this.if_Aread = if_Aread;
		this.if_Bread = if_Bread;
		this.say = say;
	}*/

	public SecretContent(String content, boolean if_Avisible,
			boolean if_Bvisible, boolean if_Aread, boolean if_Bread,
			String say, Date content_time) {
		super();
		this.content = content;
		this.if_Avisible = if_Avisible;
		this.if_Bvisible = if_Bvisible;
		this.if_Aread = if_Aread;
		this.if_Bread = if_Bread;
		this.say = say;
		this.content_time = content_time;
	}

	@Override
	public String toString() {
		return "SecretContent<"+secret_id+">";
	} 

	public int getSecret_id() {
		return secret_id;
	}

	public void setSecret_id(int secret_id) {
		this.secret_id = secret_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isIf_Avisible() {
		return if_Avisible;
	}

	public void setIf_Avisible(boolean if_Avisible) {
		this.if_Avisible = if_Avisible;
	}

	public boolean isIf_Bvisible() {
		return if_Bvisible;
	}

	public void setIf_Bvisible(boolean if_Bvisible) {
		this.if_Bvisible = if_Bvisible;
	}

	public SecretDialog getDialog_id() {
		return dialog_id;
	}

	public void setDialog_id(SecretDialog dialog_id) {
		this.dialog_id = dialog_id;
	}


	public String getSay() {
		return say;
	}

	public void setSay(String say) {
		this.say = say;
	}

	public boolean isIf_Aread() {
		return if_Aread;
	}

	public void setIf_Aread(boolean if_Aread) {
		this.if_Aread = if_Aread;
	}

	public boolean isIf_Bread() {
		return if_Bread;
	}

	public void setIf_Bread(boolean if_Bread) {
		this.if_Bread = if_Bread;
	}
	@JSON(format="yyyy-MM-dd HH:mm")
	public Date getContent_time() {
		return content_time;
	}

	public void setContent_time(Date content_time) {
		this.content_time = content_time;
	}
	
	
	
}
