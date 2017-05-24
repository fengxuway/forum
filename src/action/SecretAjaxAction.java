package action;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.apache.struts2.json.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import pagination.*;
import service.*;
import util.*;
import entity.*;

import com.opensymphony.xwork2.*;


@Controller("secretAjaxAction")
@Scope("prototype")
public class SecretAjaxAction extends ActionSupport {
	private static final long serialVersionUID = 2316874196329019019L;
	private static final Logger logger = Logger.getLogger(SecretAjaxAction.class);
	
	@Autowired
	@Qualifier("secretDialogService")
	private SecretDialogService secretDialogService;
	@Autowired
	@Qualifier("secretContentService")
	private SecretContentService secretContentService;
	
	//对话ID
	private int dialog_id;
	//请求标志
	private String method;
	//对话的另一方user_id
	private String anotherUser_id;
	//回复内容
	private String content;
	//请求是否成功？
	private String msgAjax;
	//secretContent实例，用于传递数据
	private SecretContent secretContent;
	//content_id，内容ID
	private int content_id;
	//存储私信对话
	private List<SecretDialog> dialogs = new ArrayList<SecretDialog>();
	
	
	//某一个对话的所有当前用户可见的对话内容
	private List<SecretContent> contents = new ArrayList<SecretContent>();
	
	public String execute(){
		if(method.equals("searchContent")){
			//请求当前session中用户的所有有关的私信对话
			searchContent();
			
		}else if(method.equals("publish")){
			//发布回复请求
			publishSecret();
		}else if(method.equals("deleteContent")){
			//移除与当前session中的用户相关的对应内容（设置visible=false)
			deleteContent();
		}else if(method.equals("deleteDialog")){
			//移除与当前session中的用户相关的对话（设置其内容visible=false)
			deleteDialog();
		}else if(method.equals("searchDialog")){
			searchDialog();
		}else if(method.equals("searchUnreadContent")){
			// 获取当前用户当前对话中尚未阅读的内容
			searchUnreadContent();
		}
		return SUCCESS;
	}
	
	private void searchUnreadContent() {
		//session中的用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		try {
			logger.info("正在获取用户"+user+"对话ID为"+dialog_id);
			contents = secretContentService.getUnreadContent(user.getUser_id(), dialog_id);
			msgAjax= "1";
		} catch (Exception e) {
			msgAjax= "0";
			e.printStackTrace();
		}
		
	}

	private void searchDialog() {
		//session中的用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		//获取用户相关的私信对话
		dialogs = secretDialogService.getUserDialogs(user.getUser_id());
		// 遍历移除与当前用户无关（不可见）的content
		for(SecretDialog tmp: dialogs){
			for(Iterator<SecretContent> it = tmp.getSecretContents().iterator(); it.hasNext(); ){
				SecretContent o = it.next();
				if(!o.isIf_Avisible() && user.getUser_id().equals(o.getDialog_id().getUserA().getUser_id()) ||
						!o.isIf_Bvisible() && user.getUser_id().equals(o.getDialog_id().getUserB().getUser_id())){
					it.remove();
				}
			}
		}
		
	}

	/**
	 * 移除与当前session中的用户相关的对话（设置其内容visible=false)
	 */
	private void deleteDialog() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		try {
			secretContentService.deleteUserDialog(dialog_id, user.getUser_id());
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	/**
	 * 移除与当前session中的用户有关的指定Content_id的内容
	 */
	private void deleteContent() {
		User user = (User) ActionContext.getContext().getSession().get("user");
		try {
			secretContentService.deleteUserContent(content_id, user.getUser_id());
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	/**
	 * 发布secretContent
	 */
	private void publishSecret() {
		String currentUser_id = ((User)ActionContext.getContext().getSession().get("user")).getUser_id();
		
		try {
			secretContent = secretContentService.publishSecret(currentUser_id, anotherUser_id, content);
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}
	}

	/**
	 * 获取当前session用户指定ID的dialog的所有对话内容
	 */
	public void searchContent(){
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//获取session中的用户
		User user = (User) ActionContext.getContext().getSession().get("user");
		
		contents = secretContentService.getUserContents(user.getUser_id(), dialog_id);
		
	}

	public List<SecretContent> getContents() {
		return contents;
	}

	public void setContents(List<SecretContent> contents) {
		this.contents = contents;
	}

	public int getDialog_id() {
		return dialog_id;
	}

	public void setDialog_id(int dialog_id) {
		this.dialog_id = dialog_id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public String getMsgAjax() {
		return msgAjax;
	}

	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}

	public SecretContent getSecretContent() {
		return secretContent;
	}

	public void setSecretContent(SecretContent secretContent) {
		this.secretContent = secretContent;
	}

	public int getContent_id() {
		return content_id;
	}

	public void setContent_id(int content_id) {
		this.content_id = content_id;
	}

	public List<SecretDialog> getDialogs() {
		return dialogs;
	}

	public void setDialogs(List<SecretDialog> dialogs) {
		this.dialogs = dialogs;
	}
}
