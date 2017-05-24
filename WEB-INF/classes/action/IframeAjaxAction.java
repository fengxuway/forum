package action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pagination.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import service.*;
import entity.*;
@Controller("iframeAjaxAction")
@Scope("prototype") 
public class IframeAjaxAction extends ActionSupport {
	private static final long serialVersionUID = -8896215205554061900L;
	private static final Logger logger = Logger.getLogger(IframeAjaxAction.class);
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("forbidListService")
	private ForbidListService forbidListService;
	@Autowired
	@Qualifier("subjectService")
	private SubjectService subjectService;
	
	// 请求标识
	private String method;
	// 主题ID
	private int sub_id;
	// 封禁ID
	private int forbid_id;
	private String user_id;
	
	
	
	// 是否成功的标志
	private String msgAjax;
	
	
	
	public String execute(){
		if(method.equals("deleteSubject")){
			deleteSubject();
		}else if(method.equals("removeForbid")){
			removeForbid();
		}else if(method.equals("removeUserForbid")){
			removeUserForbid();
		}else if(method.equals("deleteUser")){
			deleteUser();
		}
		
		return SUCCESS;
	}

	private void deleteUser() {
		User u = userService.get(user_id);
		if(u != null){
			try {
				userService.delete(u);
				msgAjax = "1";
			} catch (Exception e) {
				msgAjax = "0";
				e.printStackTrace();
			}
		}
	}

	private void removeUserForbid() {
		User uu = (User) ActionContext.getContext().getSession().get("user");
		try {
			forbidListService.removeForbid(forbidListService.findByUser(user_id).getForbid_id(), uu.getUser_id());
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}
		
	}

	private void removeForbid() {
		User uu = (User) ActionContext.getContext().getSession().get("user");
		try {
			forbidListService.removeForbid(forbid_id, uu.getUser_id());
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}
	}

	private void deleteSubject() {
		try {
			subjectService.delete(subjectService.get(sub_id));
			logger.info("删除成功！");
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}		
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getSub_id() {
		return sub_id;
	}

	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}

	public String getMsgAjax() {
		return msgAjax;
	}

	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}

	public int getForbid_id() {
		return forbid_id;
	}

	public void setForbid_id(int forbid_id) {
		this.forbid_id = forbid_id;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	

}
