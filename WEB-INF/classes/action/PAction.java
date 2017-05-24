package action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pagination.PageBean;
import service.*;
import util.MyDate;
import util.URLEncoding;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.*;

@Controller("pAction")
@Scope("prototype")
public class PAction extends ActionSupport {
	private static final long serialVersionUID = 8695663007569239335L;
	private static final Logger logger = Logger.getLogger(PAction.class);
	
	private String id;
	private User puser;
	
	private boolean ifAtte;
	// 他关注的人数
	private int myAtteCount;
	// 他的粉丝数
	private int atteMeCount;
	//他发表的主题表
	private PageBean pSubjectsBean;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Autowired
	@Qualifier("attentionService")
	private AttentionService attentionService;
	
	public String execute() {
		id = URLEncoding.toLocalString(ServletActionContext.getRequest(), "id");
		logger.info("正在查看用户"+id+"的个人空间");

		User user = (User)ActionContext.getContext().getSession().get("user");
		
		//如果用户访问的是自己的主页，转到个人中心
		if(user != null && user.getUser_id().equals(id)){
			return "self";
		}
		
		puser = userService.get(id);
		
		setMyAtteCount(attentionService.getMyAtte(id).size());
		setAtteMeCount(attentionService.getAtteMe(id).size());
		
		if(user != null){
			ifAtte = attentionService.isAtte(user.getUser_id(),id);
		}else{
			ifAtte = false;
		}
		
		//获取该用户发表过的主题
		try {
			pSubjectsBean = userService.getSelfSubject(puser, 1);
			logger.info("获取他的发表过的主题成功");
		} catch (Exception e) {
			logger.error("获取他的发表过的主题失败，Caused by: "+e.getMessage());
		}
		 
		return SUCCESS;
	}


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public User getPuser() {
		return puser;
	}
	public void setPuser(User puser) {
		this.puser = puser;
	}
	public PageBean getPSubjectBean() {
		return pSubjectsBean;
	}
	public void setPSubjectBean(PageBean pSubjectBean) {
		this.pSubjectsBean = pSubjectBean;
	}
	public boolean isIfAtte() {
		return ifAtte;
	}
	public void setIfAtte(boolean ifAtte) {
		this.ifAtte = ifAtte;
	}
	public int getMyAtteCount() {
		return myAtteCount;
	}
	public void setMyAtteCount(int myAtteCount) {
		this.myAtteCount = myAtteCount;
	}
	public int getAtteMeCount() {
		return atteMeCount;
	}
	public void setAtteMeCount(int atteMeCount) {
		this.atteMeCount = atteMeCount;
	}
}
