package action;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import pagination.*;
import service.*;
import util.*;
import entity.*;

import com.opensymphony.xwork2.*;


@Controller("secretAction")
@Scope("prototype")
public class SecretAction extends ActionSupport {
	private static final long serialVersionUID = 7774284861317459992L;
	private static final Logger logger = Logger.getLogger(SecretAction.class);
	
	@Autowired
	@Qualifier("secretDialogService")
	private SecretDialogService secretDialogService;
	@Autowired
	@Qualifier("secretContentService")
	private SecretContentService secretContentService;
	
	//存储私信对话
	private List<SecretDialog> dialogs = new ArrayList<SecretDialog>();
	private String anotherUser_id;
	private String content;
	private SecretContent secretContent;
	// 当在他人主页中点击“私信他”时，传过来的值
	private String id;
	private int r;
	
	public String execute(){
		//session中的用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		//获取用户相关的私信对话
		dialogs = secretDialogService.getUserDialogs(user.getUser_id());
		
		if(id != null && !id.equals("")){
			id = URLEncoding.toLocalString(ServletActionContext.getRequest(), "id");
			// 如果点击了“私信他”，
			r = 20;
		}else{
			r = 10;
		}
		
		return SUCCESS;
	}
	
	public String publishNewSecret(){
		String currentUser_id = ((User)ActionContext.getContext().getSession().get("user")).getUser_id();
		logger.info("正在发表私信："+anotherUser_id+" 内容："+content);
		try {
			if(!currentUser_id.equals(anotherUser_id)){
				secretContent = secretContentService.publishSecret(currentUser_id, anotherUser_id, content);
			}
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}

	public List<SecretDialog> getDialogs() {
		return dialogs;
	}

	public String getAnotherUser_id() {
		return anotherUser_id;
	}

	public void setAnotherUser_id(String anotherUser_id) {
		this.anotherUser_id = anotherUser_id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public SecretContent getSecretContent() {
		return secretContent;
	}

	public void setSecretContent(SecretContent secretContent) {
		this.secretContent = secretContent;
	}

	public void setDialogs(List<SecretDialog> dialogs) {
		this.dialogs = dialogs;
	}
	public void setId(String id){
		this.id = id;
	}
	public String getId(){
		return id;
	}
	public void setR(int r){
		this.r = r;
	}
	public int getR(){
		return r;
	}

}
