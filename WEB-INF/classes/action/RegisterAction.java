package action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.*;
import service.impl.UserServiceImpl;
import util.URLEncoding;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import entity.*;

@Controller("registerAction")
@Scope("prototype")
public class RegisterAction extends ActionSupport {
	private static final long serialVersionUID = -133428857028698167L;
	private static final Logger logger = Logger.getLogger(RegisterAction.class);
	private String req;
	private String uuid;
	private String userid;
	private String email;
	private String msgAjax;
	private String pswd_new;
	//验证码，用于找回密码
	private String checkWord;
	private List<College> collegeList = new ArrayList<College>();
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("collegeService")
	private CollegeService collegeService;
	@Autowired
	private SendMail sendMail;
	 
	public String execute(){
		
		//获取所有院系
		collegeList = collegeService.getAll();
		
		return SUCCESS;
	}
	
	public String userActive(){
		userid = URLEncoding.toLocalString(ServletActionContext.getRequest(), "userid");
		//通过uuid获取用户名及最后激活时间，时间未过，更改if_active字段为true
		logger.info("用户激活中.... userId:"+userid+"\tuuid:"+uuid);
		User u; 
		try {
			u = userService.userActive(userid, uuid);
			Map<String, Object> session = ActionContext.getContext().getSession();
			session.put("user", u);			
			//1_4表示显示register.jsp页面中的"立即注册"的#main4
			req = "14";
			logger.info("用户激活成功！");
		} catch (UnsupportedEncodingException e) {
			//页面转到激活失败提示框
			req = "15"; 
			logger.error("解码失败");
		} catch (Exception e) {
			req = "15";
			logger.error("用户激活失败! Caused by "+e.getMessage());
			e.printStackTrace();   
		}
	
		
		return execute();
	}
	
	/**
	 * 向用户提供的邮箱发送6位验证码，并将验证码放置在session中，以便验证
	 */
	public String checkWord(){
		int cwInt = (int) (Math.random()*1000000);
		while(cwInt < 100000){
			cwInt = (int) (Math.random()*1000000);
		}
		String cw = String.valueOf(cwInt);
		try {
			System.out.println("验证码："+cw);
			User user = userService.findByEmail(email);
			if(user != null){
				msgAjax = "邮箱发送成功，请在5分钟内查收邮箱并填写验证码";
				ActionContext.getContext().getSession().put("checkWord", cw);
				sendMail.sendCheckWordMail(email, cw);
			}else{
				msgAjax = "该邮箱不存在，请检查后重试！";
			}
		} catch (Exception e) {
			msgAjax = "网络发生异常，请稍后重试！";
			logger.error(msgAjax+e.getMessage());
		}
		return SUCCESS;
	}
	
	/**
	 * 检验验证码是否正确，如果正确，将提供的邮箱将session，然后转到修改密码界面
	 * @return
	 */
	public String validateCW(){
		if(checkWord.equals((String)ActionContext.getContext().getSession().get("checkWord"))){
			// 将email加入session，以便修改密码提交后的确认用户
			ActionContext.getContext().getSession().put("email", email);
			return SUCCESS;
		}else{
			return ERROR;
		}
	}
	
	/**
	 * 检验验证码是否正确，用于ajax请求，以便及时提示用户信息
	 * @return
	 */
	public String validateCWAjax(){
		if(checkWord.equals((String)ActionContext.getContext().getSession().get("checkWord"))){
			msgAjax = "1";
		}else{
			msgAjax = "0";
		}
		return SUCCESS;
	}
	
	/**
	 * 获取验证码一段时间后ajax调用，用于删除校验码
	 * @return
	 */
	public String deleteCW(){
		ActionContext.getContext().getSession().remove("checkWord");
		return SUCCESS;
	}
	
	/**
	 * 修改用户密码
	 * @return
	 */
	public String updatePswd(){
		userService.updatePswdByEmail((String)ActionContext.getContext().getSession().get("email"), pswd_new);
		return SUCCESS;
	}
	
	public String getReq() {
		return req;
	}

	public void setReq(String req) {
		this.req = req;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<College> getCollegeList() {
		return collegeList;
	}

	public void setCollegeList(List<College> collegeList) {
		this.collegeList = collegeList;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMsgAjax() {
		return msgAjax;
	}

	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}

	public String getCheckWord() {
		return checkWord;
	}

	public void setCheckWord(String checkWord) {
		this.checkWord = checkWord;
	}

	public String getPswd_new() {
		return pswd_new;
	}

	public void setPswd_new(String pswd_new) {
		this.pswd_new = pswd_new;
	}


}
