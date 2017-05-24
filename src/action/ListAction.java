package action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Controller;
import pagination.*;
import service.*;
import util.*;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import entity.Category;
import entity.Footmark;
import entity.User;
import entity.Weather;

@Controller("listAction")
@Scope("prototype")
public class ListAction extends ActionSupport {
	private static final long serialVersionUID = -903752277625921821L;
	private static final Logger logger = Logger.getLogger(ListAction.class);
	
	
	private int cate_id;
	private int page;
	private PageBean pageBean;
	private List<Category> categoryList;
	private Category category;
	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	@Autowired
	@Qualifier("subjectService")
	private SubjectService subjectService;
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
	
	// 搜索关键字
	private String kw;
	// 搜索标识
	private boolean searchFlag;
	// 管理员或板块管理员
	private User admin;
	// 会员人数
	private int userCount;
	//用户身份标识
	private int user_degree;
	private Date today;
	private Date month;
	private Date year;

	// 记录用户足迹
	private List<Footmark> footmark = new ArrayList<Footmark>();
	public String execute() {
		if(cate_id == 0 && kw != null && !kw.equals("")){
			// 查询用户输入的关键字有关的主题
			pageBean = subjectService.searchForPage(kw, page);
			// 设置状态为搜索
			searchFlag = true;
			// 获取超级管理员
			admin = userService.getSuperAdmin();
			// 获取用户总数
			userCount = userService.getUserCount();
		}else{
			searchFlag = false;
			pageBean = subjectService.findByCategory(cate_id, page);
			//根据类别ID获取类别对象
			category = categoryService.get(cate_id);
			admin = userService.getModerAdmin(cate_id);
		}
		// 获取所有类别
		categoryList = categoryService.getAll();
		
		
		//更新User对象
		Map<String, Object> session = ActionContext.getContext().getSession();
		User uu = (User) session.get("user");
		if(uu != null){
			session.put("user", userService.get(uu.getUser_id()));
			setFootmark(footmarkService.getUserFootmark(uu.getUser_id()));
		}
		
		//初始化时间对象, 用以判断时间范围, 以便正确显示时间
		initTime();
		//计算用户标识
		countUser_degree();
		
		forumSign = forumInfoService.getForumSign();
		
		weather = weatherService.getWeather();
		
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
			}else if(!searchFlag && (identmp - 100 == category.getCate_id())){
				user_degree = 5;
			}else if(identmp <= 0){
				user_degree = -1;
			}else{
				user_degree = 1; 
			}
		}
	}

	public List getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List categoryList) {
		this.categoryList = categoryList;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public Category getCategory() {
		return category;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
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

	public int getCate_id() {
		return cate_id;
	}


	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setUser_degree(int u){
		user_degree = u;
	}
	public int getUser_degree(){
		return user_degree;
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

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public boolean isSearchFlag() {
		return searchFlag;
	}

	public void setSearchFlag(boolean searchFlag) {
		this.searchFlag = searchFlag;
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

}
