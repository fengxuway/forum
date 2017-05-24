package action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.*;
import util.URLEncoding;
import com.opensymphony.xwork2.ActionSupport;

import entity.Category;
import entity.ForumInfo;
import entity.User;

@Controller("adminAjaxAction")
@Scope("prototype")
public class AdminAjaxAction extends ActionSupport {
	private static final long serialVersionUID = -4477153386596207388L;
	private static final Logger logger = Logger.getLogger(AdminAjaxAction.class);
	private String msgAjax = "0";
	private String user_id;
	
	private int cate_id;
	private String cate_name;
	private String cate_info;
	private String forum_info;
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private ForumInfoService forumInfoService;
	/**
	 * 同一注册请求
	 * @return
	 */
	public String agreeRegister(){
		//如果更新成功, 返回msgAjax="1"
		try {
			userService.updateAgree(user_id);
			msgAjax = "1";
			logger.info("已同意注册-->"+user_id);
		} catch (Exception e) {
			logger.info("操作失败! Caused by "+e.getMessage());
			msgAjax = "0";
		}
		return SUCCESS;
	}
	
	/**
	 * 反对注册请求
	 * @return
	 */
	public String againstRegister(){
		try {
			userService.delete(userService.get(user_id));
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}
		logger.info("用户已被拒绝注册："+user_id);
		
		return SUCCESS;
	}
	
	/**
	 * 更改分类信息
	 */
	public String updateCate(){
		try {
			msgAjax = categoryService.updateCate(cate_id, cate_name, cate_info, user_id);
		} catch (Exception e) {
			msgAjax = "网络异常，请刷新后重试！";
			e.printStackTrace();
		}
		return SUCCESS;
		
	}
	public String deleteCate(){
		try {
			categoryService.delete(categoryService.get(cate_id));
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "0";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String addCate(){
		try {
			msgAjax = categoryService.addCate(cate_name,cate_info, user_id);
		} catch (Exception e) {
			msgAjax = "网络异常，请稍后重试！";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String updateForum(){
		try {
			ForumInfo fi = forumInfoService.get(1);
			if(fi != null){
				fi.setSign(forum_info);
				forumInfoService.update(fi);
			}
			msgAjax = "1";
		} catch (Exception e) {
			msgAjax = "网络异常，请稍后重试！";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public String getMsgAjax() {
		return msgAjax;
	}
	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public String getCate_name() {
		return cate_name;
	}

	public void setCate_name(String cate_name) {
		this.cate_name = cate_name;
	}

	public String getCate_info() {
		return cate_info;
	}

	public void setCate_info(String cate_info) {
		this.cate_info = cate_info;
	}

	public String getForum_info() {
		return forum_info;
	}

	public void setForum_info(String forum_info) {
		this.forum_info = forum_info;
	}


}
