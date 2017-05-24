package action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import entity.*;
@Controller("adminAction")
@Scope("prototype")
public class AdminAction extends ActionSupport {
	private static final long serialVersionUID = 640391935928728716L;
	private static final Logger logger = Logger.getLogger(AdminAction.class);
	//尚未同意注册的用户
	private List<User> userNoApply = new ArrayList<User>();
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	public String execute(){
		/*userNoApply = userService.findNoApply();
		for(User u : userNoApply){
			System.out.println(u.getCollege().getCollege_name());
		}
		logger.info("进入管理首页");*/
		return SUCCESS;
	}

	public List<User> getUserNoApply() {
		return userNoApply;
	}

	public void setUserNoApply(List<User> userNoApply) {
		this.userNoApply = userNoApply;
	}

}
