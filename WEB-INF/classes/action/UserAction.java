package action;

import java.util.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;

import service.MajorService;
import service.SubjectService;
import service.UserService;
import util.CookieUtil;

import com.opensymphony.xwork2.*;

import entity.College;
import entity.Major;
import entity.Subject;
import entity.User;

@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {
	private static final long serialVersionUID = -7984316934997700643L;
	private static final Logger logger = Logger.getLogger(UserAction.class);

	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("majorService")
	private MajorService majorService;
	@Autowired
	private SubjectService subjectService;
	
	//请求标识
	private String method;

	private String email;
	private String userid;
	private String pswd;
	private String student_id;
	private String name;
	private boolean sex;
	private int college_id;// 学院ID
	private int major_id;// 专业ID
	// 用户被封禁的天数
	private int days;
	// 搜索字符串
	private String kw;
	// 搜索出的用户列表
	private List<User> searchUserList;
	// 搜索出的主题列表
	private List<Subject> searchSubjectList;
	

	// Ajax传递信息
	private String msgAjax;
	private User user;
	private Map<String, String> majorMap = new TreeMap<String, String>();
	
	public String method(){
		user = (User) ActionContext.getContext().getSession().get("user");
		if(method !=null && !method.equals("")){
			if(method.equals("addMention")){
				addMention(user, userid);
			}else if(method.equals("removeMention")){
				removeMention(user, userid);
			}else if(method.equals("forbid")){
				forbid();
			}else if(method.equals("searchUser")){
				searchUser();
			}else if(method.equals("searchSubject")){
				searchSubject();
			}
			
		}
		
		
		return SUCCESS;
		
	}
	public void searchSubject(){
		searchSubjectList = subjectService.search(kw);
	}
	private void searchUser() {
		System.out.println("Action开始搜索");
		setSearchUserList(userService.search(kw));
	}

	/**
	 * 封禁用户
	 */
	private void forbid() {
		User uu = (User) ActionContext.getContext().getSession().get("user");
		try {
			// 判断用户权限
			if(uu == null || uu.getIdentity() < 100){
				msgAjax = "对不起，您没有封禁用户的权限！";
				return;
			}
			userService.forbid(userid, days, uu.getUser_id());
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = e.getMessage();
			e.printStackTrace();
		}
	}

	/**
	 * 取消关注操作
	 * @param user2 取消关注操作的用户对象
	 * @param userid2 被关注的用户ID
	 */
	private void removeMention(User user2, String userid2) {
		try {
			userService.removeMention(user2, userid2);
			logger.info("取消关注成功");
			msgAjax = "1";
		} catch (Exception e) {
			logger.error("取消关注失败！");
			msgAjax = "0";
			e.printStackTrace();
		}	
	}

	/**
	 * 添加关注操作
	 * @param user2  添加关注操作的用户对象
	 * @param userid2 被关注的用户ID
	 */
	private void addMention(User user2, String userid2) {
		try {
			userService.addMention(user2, userid2);
			logger.info("添加关注成功");
			msgAjax = "1";
		} catch (Exception e) {
			logger.error("添加关注失败！");
			msgAjax = "0";
			e.printStackTrace();
		}
	}

	/**
	 * 验证用户名是否已经存在
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String userExist() throws InterruptedException {
		// 校验用户名是否符合要求
		// 用户被占用传回1，可以使用传回0
		Thread.sleep(1000);
		if (userService.userValidate(userid)) {
			msgAjax = "0";
			logger.info("用户"+userid+"可以注册");
		} else {
			msgAjax = "1";
			logger.info("用户"+userid+"已存在");
		}
		return SUCCESS;
	}

	/**
	 * 查询已知院系的专业请求
	 * 
	 * @return
	 */
	public String selectMajor() {
		majorMap = majorService.selectMajorByCollege(college_id);
		
		return SUCCESS;
	}

	/**
	 * 处理注册请求
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String register() throws InterruptedException{
		//注册成功返回1，否则返回0
		Thread.sleep(1000);
		msgAjax = "0";
		try{
			userService.register(userid,email, pswd, name, sex,student_id, college_id, major_id);
			msgAjax = "1";
			logger.info("注册成功！userid="+userid+" 请耐心等待审核");
		}catch(Exception e){
			msgAjax = "0";
			logger.error("注册失败！Cause by "+e.getMessage());
		}
		return SUCCESS;
	}

	/**
	 * 处理登录请求
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String login() throws InterruptedException {
		// 登录成功返回1，否则返回0
		// 登录成功与否的标志
		Thread.sleep(1000);
		Map<String, Object> session = ActionContext.getContext().getSession();
		logger.info("用户userid: "+userid+"正在登陆！");
		User user = userService.login(userid, pswd);
		if (user != null) {
			
			try{
			/*Cookie cookie = new CookieUtil().addCookie(user);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.addCookie(cookie);
			*/
			}catch(Exception e){
				e.printStackTrace();
			}
			msgAjax = "1";
			session.put("user", user);
			logger.info("登录成功！"+user);
		} else {
			msgAjax = "0";
			logger.info("登录失败！"+user);
		}

		return SUCCESS;
	}

	/**
	 * 验证邮箱是否被占用
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String emailExist() throws InterruptedException {
		// 邮箱被占用传回1，可以使用传回0
		Thread.sleep(1000);
		if (userService.emailValidate(email)) {
			msgAjax = "0";
		} else {
			msgAjax = "1";
		}
		return SUCCESS;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPswd() {
		return pswd;
	}

	public void setPswd(String pswd) {
		this.pswd = pswd;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	public List<Subject> getSearchSubjectList() {
		return searchSubjectList;
	}
	public void setSearchSubjectList(List<Subject> searchSubjectList) {
		this.searchSubjectList = searchSubjectList;
	}

	public String getMsgAjax() {
		return msgAjax;
	}

	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}

	public int getCollege_id() {
		return college_id;
	}

	public void setCollege_id(int college_id) {
		this.college_id = college_id;
	}

	@JSON(name = "majorAjax")
	public Map<String, String> getMajorMap() {
		return majorMap;
	}

	public void setMajorMap(Map<String, String> majorMap) {
		this.majorMap = majorMap;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public boolean isSex() {
		return sex;
	}

	public void setSex(boolean sex) {
		this.sex = sex;
	}

	public int getMajor_id() {
		return major_id;
	}

	public void setMajor_id(int major_id) {
		this.major_id = major_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public List<User> getSearchUserList() {
		return searchUserList;
	}

	public void setSearchUserList(List<User> searchUserList) {
		this.searchUserList = searchUserList;
	}

}
