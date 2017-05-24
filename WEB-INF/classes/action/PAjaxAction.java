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


@Controller("pAjaxAction")
@Scope("prototype")
public class PAjaxAction extends ActionSupport {
	private static final long serialVersionUID = -1005662055716154493L;

	private static final Logger logger = Logger.getLogger(PAjaxAction.class);
	
	//链接中的参数，指定显示内容（选项卡相关）
	private int r;
	//请求页码
	private int pageNum;
	//被查看的用户ID
	private String userid;
	//获取Ajax请求是否成功的状态
	private String msgAjax;
	
	//个人发表主题列表
	private PageBean pSubjectBean;
	//个人发表回复列表
	private PageBean pReplyBean;
	//session中用户关注的列表
	private List<User> myAtteList;
	//关注当前用户的列表（粉丝）
	private List<User> atteUserList;
	//当前用户关注的列表
	private List<User> userAtteList;
	
	@Autowired
	@Qualifier("subjectService")
	private SubjectService subjectService;
	@Autowired
	@Qualifier("replyService")
	private ReplyService replyService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("informationService")
	private InformationService informationService;
	@Autowired
	@Qualifier("attentionService")
	private AttentionService attentionService;
	
	//异步请求方法，用于获取个人主题列表、回复列表、回复我的等信息
	// @JSON(serialize=false)
	public String requestData() throws Exception{
		Thread.sleep(300);
		//获取当前session中的用户
		User user = userService.get(userid);
		//如果请求链接中没有指定参数r，设置r=11，显示我关注的动态
		if(r == 0) r = 11;
		switch(r){
			case 11: searchSelfSubject(user, pageNum); break;
			case 12: searchSelfReply(user,pageNum); break;
			case 13: searchAtteUser(userid); break;
			case 14: searchUserAtte(userid); break;
		}
		
		return SUCCESS;
		
	}
	
	
	
	
	/**
	 * 获取关注指定用户的用户列表（粉丝）
	 * @param userid 指定用户
	 */
	public void searchAtteUser(String userid) {
		atteUserList = attentionService.getAtteMe(userid);
		
		User sessionUser = (User) ActionContext.getContext().getSession().get("user");
		if(sessionUser != null){
			myAtteList = attentionService.getMyAtte(sessionUser.getUser_id());
		}
	}
	
	/**
	 * 获取指定用户关注的用户列表
	 * @param userid 指定用户
	 */
	public void searchUserAtte(String userid) {
		userAtteList = attentionService.getMyAtte(userid);
		
		User sessionUser = (User) ActionContext.getContext().getSession().get("user");
		if(sessionUser != null){
			myAtteList = attentionService.getMyAtte(sessionUser.getUser_id());
		}
	}

	/**
	 * 获取个人发表主题记录 
	 * @param user 指定用户
	 * @param pageNum 页码数
	 */
	public void searchSelfSubject(User user, int pageNum){
		try {
			
			pSubjectBean = userService.getSelfSubject(user, pageNum);
			logger.info("获取用户发表的主题成功！Ajax");
		} catch (Exception e) {
			logger.error("获取用户发表的主题失败！Caused By "+e.getMessage());
		}
	}
	
	/**
	 * 获取用户个人发表回复的记录
	 * @param user 指定用户
	 * @param pageNum 页码数
	 */
	public void searchSelfReply(User user, int pageNum){
		try{
			pReplyBean = userService.getSelfReply(user, pageNum);
			logger.info("获取用户发表的回复列表成功！");
		}catch(Exception e){
			logger.error("获取用户发表的回复列表失败！Caused By "+e.getMessage());
		}
	}
	
	

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public PageBean getpSubjectBean() {
		return pSubjectBean;
	}

	public void setpSubjectBean(PageBean pSubjectBean) {
		this.pSubjectBean = pSubjectBean;
	}

	public PageBean getpReplyBean() {
		return pReplyBean;
	}

	public void setpReplyBean(PageBean pReplyBean) {
		this.pReplyBean = pReplyBean;
	}

	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<User> getMyAtteList() {
		return myAtteList;
	}

	public void setMyAtteList(List<User> myAtteList) {
		this.myAtteList = myAtteList;
	}

	public List<User> getAtteUserList() {
		return atteUserList;
	}

	public void setAtteUserList(List<User> atteUserList) {
		this.atteUserList = atteUserList;
	}

	public List<User> getUserAtteList() {
		return userAtteList;
	}

	public void setUserAtteList(List<User> userAtteList) {
		this.userAtteList = userAtteList;
	}



	public String getMsgAjax() {
		return msgAjax;
	}



	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}





}
