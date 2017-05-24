package action;

import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.*;

import com.opensymphony.xwork2.*;
import entity.*;

@Controller("replyAction")
@Scope("prototype")
public class ReplyAction extends ActionSupport {
	private static final long serialVersionUID = -6090705650051568342L;
	private static final Logger logger = Logger.getLogger(ReplyAction.class);
	
	private String reply_content;
	private boolean if_sign;
	private int sub_id;
	private boolean if_reply;
	private int reply_superid;
	
	private int reply_id;
	private String msgAjax = "0";
	@Autowired
	@Qualifier("replyService")
	private ReplyService replyService;
	
	/**
	 * 发布回复
	 * 
	 * @return
	 */
	public String publishReply(){
		User user = (User) ActionContext.getContext().getSession().get("user");
		try {
			// Thread.sleep(300);
			//将回复内容、是否有签名档、作者、是否为回复的回复、被回复的回复ID
			//将生成的ID发送回客户端
			reply_id = replyService.publishReply(reply_content, if_sign, sub_id, user, if_reply, reply_superid).getReply_id();
			logger.info("回复发表成功!");
			msgAjax = "1";
		} catch (Exception e) {
			logger.error("回复发生异常 Cause by："+e.getMessage());
			msgAjax = "0";			
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 删除回复贴，将if_delete字段状态改为true
	 * 删除成功，返回msgAjax = "1"
	 */
	public String deleteReply(){
		User uu = (User) ActionContext.getContext().getSession().get("user");
		try{
			replyService.deleteReply(reply_id, uu.getUser_id());
			logger.info("回复贴" + reply_id + "删除成功！");
			msgAjax = "1";
		}catch(Exception e){
			logger.error("回复贴" + reply_id + "删除失败！"+e.getMessage());
			msgAjax = "0";
		}
 		return SUCCESS;
	}
	public boolean isIf_sign() {
		return if_sign;
	}
	public void setIf_sign(boolean if_sign) {
		this.if_sign = if_sign;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public ReplyService getReplyService() {
		return replyService;
	}
	public void setReplyService(ReplyService replyService) {
		this.replyService = replyService;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getSub_id() {
		return sub_id;
	}
	public void setSub_id(int sub_id) {
		this.sub_id = sub_id;
	}
	public boolean isIf_reply() {
		return if_reply;
	}
	public void setIf_reply(boolean if_reply) {
		this.if_reply = if_reply;
	}
	public int getReply_superid() {
		return reply_superid;
	}
	public void setReply_superid(int reply_superid) {
		this.reply_superid = reply_superid;
	}
	public String getMsgAjax() {
		return msgAjax;
	}
	public void setMsgAjax(String msgAjax) {
		this.msgAjax = msgAjax;
	}
	public void setReply_id(int reply_id){
		this.reply_id = reply_id;
	}
	public int getReply_id(){
		return reply_id;
	}


}
