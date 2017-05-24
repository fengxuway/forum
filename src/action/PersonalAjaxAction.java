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


@Controller("personalAjaxAction")
@Scope("prototype")
public class PersonalAjaxAction extends ActionSupport {
	private static final long serialVersionUID = -3026222933505458914L;
	private static final Logger logger = Logger.getLogger(PersonalAjaxAction.class);
	
	//链接中的参数，指定显示内容（选项卡相关）
	private int r;
	//请求页码
	private int pageNum;
	
	//我的关注用户的动态
	private PageBean atteDynamicBean;
	//个人发表主题列表
	private PageBean selfSubjectBean;
	//个人发表回复列表
	private PageBean selfReplyBean;
	//回复个人的列表
	private PageBean replyMeBean;
	//@提到个人的列表
	private PageBean mentionMeBean;
	//我的粉丝
	private List<User> atteMeList;
	//我的关注
	private List<User> myAtteList;
	// 系统消息
	private PageBean newsBean;
	
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
		User user = (User) ActionContext.getContext().getSession().get("user");
		//如果请求链接中没有指定参数r，设置r=11，显示我关注的动态
		if(r == 0) r = 21;
		else if(r < 20) r = 11;
		switch(r){
			case 11: searchAtteDynamic(user, pageNum); break;
			case 21: searchSelfSubject(user, pageNum); break;
			case 22: searchSelfReply(user,pageNum); break;
			case 23: searchAtteMe(user.getUser_id()); break;
			case 24: searchMyAtte(user.getUser_id()); break;
			case 31: searchReplyMe(user, pageNum); break;
			case 32: searchMentionMe(user, pageNum); break;
			case 33: searchNews(user, pageNum); break;
		}
		
		return SUCCESS;
		
	}
	
	
	private void searchNews(User user, int page) {
		setNewsBean(informationService.getNews(user.getUser_id(), page));
	}

	/**
	 * 获取指定用户的关注用户的动态
	 * @param user 指定用户
	 * @param pageNum2 指定页码
	 */
	private void searchAtteDynamic(User user, int pageNum2) {
		atteDynamicBean = attentionService.getAtteDynamic(user, pageNum2);
	}



	/**
	 * 获取我关注的用户列表
	 * @param userid 我的ID
	 */
	private void searchMyAtte(String userid) {
		myAtteList = attentionService.getMyAtte(userid);
	}
	
	/**
	 * 获取我的粉丝用户的列表（获取我的关注以便判断该用户是否已关注）
	 * @param userid 我的ID
	 */
	private void searchAtteMe(String userid) {
		atteMeList = attentionService.getAtteMe(userid);
		myAtteList = attentionService.getMyAtte(userid);
	}

	/**
	 * 获取个人发表主题记录 
	 * @param user 指定用户
	 * @param pageNum 页码数
	 */
	public void searchSelfSubject(User user, int pageNum){
		try {
			
			selfSubjectBean = userService.getSelfSubject(user, pageNum);
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
			selfReplyBean = userService.getSelfReply(user, pageNum);
			logger.error("获取用户发表的回复列表成功！");
		}catch(Exception e){
			logger.error("获取用户发表的回复列表失败！Caused By "+e.getMessage());
		}
	}
	
	/**
	 * 获取回复指定用户的回复贴通知
	 * @param user
	 * @param pageNum
	 */
	public void searchReplyMe(User user, int pageNum){
		try{
			replyMeBean = informationService.getReplyMe(user, pageNum);
			logger.info("获取回复用户的列表成功！");
		}catch(Exception e){
			logger.error("获取回复用户的列表失败！Caused By "+e.getMessage());
		}
	}
	
	/**
	 * 获取@提到指定用户的信息
	 * @param user
	 * @param i
	 */
	private void searchMentionMe(User user, int pageNum) {
		try{
			setMentionMeBean(informationService.getMentionMe(user, pageNum));
		}catch(Exception e){
			logger.error("获取提到用户的列表失败！Caused By "+e.getMessage());
		}
	}


	public int getR() {
		return r;
	}

	public void setR(int r) {
		this.r = r;
	}

	public PageBean getSelfSubjectBean() {
		return selfSubjectBean;
	}

	public void setSelfSubjectBean(PageBean selfSubjectBean) {
		this.selfSubjectBean = selfSubjectBean;
	}

	public PageBean getSelfReplyBean() {
		return selfReplyBean;
	}

	public void setSelfReplyBean(PageBean selfReplyBean) {
		this.selfReplyBean = selfReplyBean;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public PageBean getReplyMeBean() {
		return replyMeBean;
	}

	public void setReplyMeBean(PageBean replyMeBean) {
		this.replyMeBean = replyMeBean;
	}

	public PageBean getMentionMeBean() {
		return mentionMeBean;
	}

	public void setMentionMeBean(PageBean mentionMeBean) {
		this.mentionMeBean = mentionMeBean;
	}

	public List<User> getAtteMeList() {
		return atteMeList;
	}

	public void setAtteMeList(List<User> atteMeList) {
		this.atteMeList = atteMeList;
	}

	public List<User> getMyAtteList() {
		return myAtteList;
	}

	public void setMyAtteList(List<User> myAtteList) {
		this.myAtteList = myAtteList;
	}


	public PageBean getAtteDynamicBean() {
		return atteDynamicBean;
	}


	public void setAtteDynamicBean(PageBean atteDynamicBean) {
		this.atteDynamicBean = atteDynamicBean;
	}


	public PageBean getNewsBean() {
		return newsBean;
	}


	public void setNewsBean(PageBean newsBean) {
		this.newsBean = newsBean;
	}
	
	

}
