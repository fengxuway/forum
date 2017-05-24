package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pagination.PageBean;
import service.*;
import util.MyDate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.*;

@Controller("singleAction")
@Scope("prototype")
public class SingleAction extends ActionSupport {
	private static final long serialVersionUID = 7310607266891319115L;
	private static final Logger logger = Logger.getLogger(SingleAction.class);
	private int sub_id;
	private int reply_id;
	//请求的页码
	private int page;
//	//当前主题的回复
	private PageBean replyPage;
	// 当前主题对象
	private Subject currentSubject;
	private List<Category> categoryList = new ArrayList<Category>();
 
	// 管理员或板块管理员
	private User admin;
	//用户身份标识
	private int user_degree;
	private Date today;
	private Date month;
	private Date year;
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
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
	private FootmarkService footmarkService;
	@Autowired
	private ForumInfoService forumInfoService;
	private String forumSign;
	@Autowired
	private WeatherService weatherService;
	private Weather weather;
	// 记录用户足迹
	private List<Footmark> footmark = new ArrayList<Footmark>();

	public String execute() {
		try{
			//如果根据reply_id获取页面，则先获取主题对象及当前回复所在页码
			if(sub_id == 0 && reply_id != 0){
				Object[] subAndPage = replyService.getSubAndPage(reply_id);
				currentSubject = (Subject) subAndPage[0];
				sub_id = currentSubject.getSub_id();
				page = page == 0 ? (int) subAndPage[1] : page;			
				logger.info("正在请求回复id");
			}else{
				currentSubject = subjectService.get(sub_id);
			}
			replyPage = replyService.findBySubject(sub_id, page);
			categoryList = categoryService.getAll();
			
			admin = userService.getModerAdmin(currentSubject.getCate_id().getCate_id());
			//初始化时间对象
			initTime();
			
			
			logger.info("进入Single页面 page="+replyPage.getCurrentPage()+" 当前主题："+currentSubject);
			//更新User对象
			Map<String, Object> session = ActionContext.getContext().getSession();
			User uu = (User) session.get("user");
			if(uu != null){
				session.put("user", userService.get(uu.getUser_id()));
				// 记录当前用户查看过的主题
				setFootmark(footmarkService.getUserFootmark(uu.getUser_id()));
				footmarkService.addFootmark(uu.getUser_id(), sub_id);
			}
			
			//计算用户身份（0未登陆，1普通用户，-1封禁用户，3主题作者，5版主，7管理员）
			countUser_degree();
			
			forumSign = forumInfoService.getForumSign();
			weather = weatherService.getWeather();
			
//				对于被删除的主题，只有super管理员以及该主题的版主方可查看
			if(currentSubject.isIf_delete()){
				logger.info("正在查看被删除的主题");
				if(uu!= null && (uu.getIdentity() >= 1000 || (uu.getIdentity() - 100) == currentSubject.getCate_id().getCate_id())){
					return SUCCESS;
				}else{
					return ERROR;
				}
			}
			return SUCCESS;
		}catch(Exception e){
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 计算时间对象，今天、本月第一天、本年第一天
	 */
	private void initTime(){
		//初始化时间对象, 用以判断时间范围, 以便正确显示时间
		MyDate myDate = new MyDate();
		int y = myDate.getYear();
		int m = myDate.getMonth();
		int d = myDate.getDate();
		today = MyDate.get(y, m, d);
		month = MyDate.get(y, m, 1);
		year = MyDate.get(y, 1, 1);
	}
	
	/**
	 * 计算用户身份
	 * （0未登陆，1普通用户，-1封禁用户，3主题作者，5版主，7管理员）
	 */
	private void countUser_degree(){
		User u = (User) ActionContext.getContext().getSession().get("user");
		if( u == null){
			user_degree = 0;
		}else{
			int identmp = u.getIdentity();
			if(identmp >= 1000){
				user_degree = 7;
			}else if(identmp - 100 == currentSubject.getCate_id().getCate_id()){
				user_degree = 5;
			}else if(u.getUser_id().equals(currentSubject.getAuthor().getUser_id())){
				user_degree = 3;
			}else if(identmp <= 0){
				user_degree = -1;
			}else{
				user_degree = 1; 
			}
		}
	}

	public int getSub_id() {
		return sub_id;
	}

	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}

	public PageBean getReplyPage() {
		return replyPage;
	}

	public void setReplyPage(PageBean replyPage) {
		this.replyPage = replyPage;
	}

	public Subject getCurrentSubject() {
		return currentSubject;
	}

	public void setCurrentSubject(Subject currentSubject) {
		this.currentSubject = currentSubject;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public Date getMonth() {
		return month;
	}

	public void setMonth(Date month) {
		this.month = month;
	}

	public Date getYear() {
		return year;
	}

	public void setYear(Date year) {
		this.year = year;
	}

	public int getUser_degree() {
		return user_degree;
	}

	public void setUser_degree(int user_degree) {
		this.user_degree = user_degree;
	}

	public int getReply_id() {
		return reply_id;
	}

	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}

	public List<Footmark> getFootmark() {
		return footmark;
	}

	public void setFootmark(List<Footmark> footmark) {
		this.footmark = footmark;
	}

	public String getForumSign() {
		return forumSign;
	}

	public void setForumSign(String forumSign) {
		this.forumSign = forumSign;
	}

	public Weather getWeather() {
		return weather;
	}

	public void setWeather(Weather weather) {
		this.weather = weather;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}


}
