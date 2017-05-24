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

import service.CategoryService;
import service.FootmarkService;
import service.ForumInfoService;
import service.SecretContentService;
import service.UserService;
import service.WeatherService;
import util.MyDate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.Category;
import entity.Footmark;
import entity.User;
import entity.Weather;

@Controller("indexAction")
@Scope("prototype")
public class IndexAction extends ActionSupport {
	private static final long serialVersionUID = -903752277625921821L;
	private static final Logger logger = Logger.getLogger(IndexAction.class);

	private int flag = 0;
	private List<Category> categoryList;
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("secretContentService")
	private SecretContentService secretContentService;
	@Autowired
	private FootmarkService footmarkService;
	@Autowired
	private ForumInfoService forumInfoService;
	@Autowired
	private WeatherService weatherService;
	private Weather weather;
	private String forumSign;
	
	private Date today;
	private Date month;
	private Date year;
	
	// 管理员
	private User admin;
	// 会员人数
	private int userCount;
	// 论坛信息
	
	// 当前用户的足迹
	private List<Footmark> footmark = new ArrayList<Footmark>();

	public String execute() {
		/*if (new CookieUtil().getCookie(ServletActionContext.getRequest(), userService)) {  
            return SUCCESS;  
        } */
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");

		flag = user != null ? user.getIdentity() : 0;
		
		// 获取超级管理员
		admin = userService.getSuperAdmin();
		// 获取用户总数
		userCount = userService.getUserCount();

		//更新User对象
		if(user != null){
			session.put("user", userService.get(user.getUser_id()));
			setFootmark(footmarkService.getUserFootmark(user.getUser_id()));
		}
		// 获取所有类别
		categoryList = categoryService.getAll();
		
		//初始化时间对象, 用以判断时间范围, 以便正确显示时间
		initTime();
		
		forumSign = forumInfoService.getForumSign();
		
		weather = weatherService.getWeather();
		
		logger.info("进入首页");
		
		return SUCCESS;

	}
	
	/**
	 * 退出的Action
	 * @return
	 */
	public String quit(){
		ActionContext.getContext().getSession().clear();
		return SUCCESS;
	}
	
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

	public Weather getWeather(){
		return weather;
	}
	
	public void setFlag(int flag) {
		this.flag = flag;
	}

	@SuppressWarnings("rawtypes")
	public List getCategoryList() {
		return categoryList;
	}
	public CategoryService getCategoryService() {
		return categoryService;
	}

	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	public User getAdmin() {
		return admin;
	}

	public void setAdmin(User admin) {
		this.admin = admin;
	}

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getFlag() {
		return flag;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
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

}
