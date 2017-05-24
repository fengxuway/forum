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


@Controller("personalAction")
@Scope("prototype")
public class PersonalAction extends ActionSupport {
	private static final long serialVersionUID = -3026222933505458914L;
	private static final Logger logger = Logger.getLogger(PersonalAction.class);
	
	//链接中的参数，指定显示内容（选项卡相关）
	private int r;
	
	//我的关注用户的动态
	private PageBean atteDynamicBean;
	//个人发表主题列表
	private PageBean selfSubjectBean;
	//个人发表回复列表
	private PageBean selfReplyBean;
	//回复个人的列表
	private PageBean replyMeBean;
	//提到个人的列表
	private PageBean mentionMeBean;
	// 系统消息
	private PageBean newsBean;
	// 我关注的人数
	private int myAtteCount;
	// 我的粉丝数
	private int atteMeCount;
	
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
	
	@SuppressWarnings("unchecked")
	public String execute(){
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if( user != null){
			session.put("user", userService.get(user.getUser_id()));
		}
		setMyAtteCount(attentionService.getMyAtte(user.getUser_id()).size());
		setAtteMeCount(attentionService.getAtteMe(user.getUser_id()).size());
		
		//如果请求链接中没有指定参数r，设置r=11，显示我关注的动态
		if(r == 0) r = 21;
		else if(r < 19) r = 11;
		switch(r){
			case 11: searchAtteDynamic(user, 1); break;
			case 21: searchSelfSubject(user, 1); break;
			case 22: searchSelfReply(user,1); break;
			case 31: searchReplyMe(user, 1); break;
			case 32: searchMentionMe(user, 1); break;
			case 33: searchNews(user,1); break;
		}
		
		return SUCCESS;
		
	}
	
	private void searchNews(User user, int page) {
		newsBean = informationService.getNews(user.getUser_id(), page);
	}

	/**
	 * 退出的Action
	 * @return
	 */
	public String quit(){
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
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
	 * 获取个人发表主题记录
	 */
	public void searchSelfSubject(User user, int pageNum){
		try {
			selfSubjectBean = userService.getSelfSubject(user, pageNum);
			logger.info("获取用户发表的主题成功！");
		} catch (Exception e) {
			logger.error("获取用户发表的主题失败！Caused By "+e.getMessage());
		}
	}
	
	/**
	 * 获取个人发表回复的记录
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
			mentionMeBean = informationService.getMentionMe(user, pageNum);
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
	
	public PageBean getSelfReplyBean(){
		return selfReplyBean;
	}
	public void setSelfReplyBean(PageBean p){
		selfReplyBean = p;
	}
	public PageBean getSelfSubjectBean() {
		return selfSubjectBean;
	}

	public void setSelfSubjectBean(PageBean selfSubjectBean) {
		this.selfSubjectBean = selfSubjectBean;
	}
	
	public void setReplyMeBean(PageBean pageBean){
		replyMeBean = pageBean;
	}
	public PageBean getReplyMeBean(){
		return replyMeBean;
	}

	public PageBean getMentionMeBean() {
		return mentionMeBean;
	}

	public void setMentionMeBean(PageBean mentionMeBean) {
		this.mentionMeBean = mentionMeBean;
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
