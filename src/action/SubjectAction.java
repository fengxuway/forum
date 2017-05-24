package action;

import org.apache.log4j.Logger;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.SubjectService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import entity.*;

@Controller("subjectAction")
@Scope("prototype")
public class SubjectAction extends ActionSupport {
	private static final long serialVersionUID = -6090705650051568342L;
	private static final Logger logger = Logger.getLogger(SubjectAction.class);
	
	private String sub_title;
	private String sub_content;
	private boolean if_sign;
	private User user;
	private int cate_id;
	private String msg;
	private int sub_id;
	
	//标识请求类型，用于操作主题的置顶、加精、删除等，值为(top,noTop,perfect,noPerfect,delete, noDelete)
	private String operate_flag;

	@Autowired
	@Qualifier("subjectService")
	private SubjectService subjectService;

	public String publishSubject() {
		// 发布主题，成功则用msg = "1"
		msg = "0";
		user = (User) ActionContext.getContext().getSession().get("user");
		try {
			// Thread.sleep(300);
			subjectService.publishSubject(sub_title, sub_content, cate_id,
					if_sign, user);
			msg = "1";
			logger.info("发布主题成功");
			
		} catch (Exception e) {
			logger.error("发布主题异常："+e.getMessage());
			msg = "0";
		}

		return SUCCESS;
	}
	
	/**
	 * 更新主题状态（置顶、加精、删除）
	 * 用operate_flag标识操作，参数为(top,notop, perfect, noperfect, delete, nodelete)
	 */
	public String updateState(){
		user = (User) ActionContext.getContext().getSession().get("user");
		try{
			subjectService.updateState(sub_id, operate_flag, user.getUser_id());
			logger.info(operate_flag+" 操作成功！");
			msg = "1";
		}catch(Exception e){
			logger.error(operate_flag+" 操作失败！"+e.getMessage());
			msg = "0";
			e.printStackTrace();
		}	
		return SUCCESS;
	}
	
	/**
	 * 将主题移动到指定的类别中
	 */
	public String moveToCategory(){
		user = (User) ActionContext.getContext().getSession().get("user");
		try{
			subjectService.moveToCategory(sub_id, cate_id, user.getUser_id());
			msg = "1";
		}catch(Exception e){
			msg = "0";
		}
		return SUCCESS;
	}

	public String getSub_title() {
		return sub_title;
	}

	public void setSub_title(String sub_title) {
		this.sub_title = sub_title;
	}

	public String getSub_content() {
		return sub_content;
	}

	public void setSub_content(String sub_content) {
		this.sub_content = sub_content;
	}

	public int getCate_id() {
		return cate_id;
	}

	public void setCate_id(int cate_id) {
		this.cate_id = cate_id;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isIf_sign() {
		return if_sign;
	}

	public void setIf_sign(boolean if_sign) {
		this.if_sign = if_sign;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void setSub_id(int sub_id){
		this.sub_id = sub_id;
	}
	
	public int getSub_id(int sub_id){
		return sub_id;
	}
	public String getOperate_flag(){
		return operate_flag;
	}
	
	public void setOperate_flag(String oper){
		operate_flag = oper;
	}
}
