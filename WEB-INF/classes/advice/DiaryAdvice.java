package advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.*;
import entity.*;
import util.*;
import java.util.*;

@Aspect
@Component("diaryAdvice")
public class DiaryAdvice {
	@Autowired
	private SubjectService subjectService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private DiaryService diaryService;
	@Autowired
	private InformationService informationService;
	@Autowired
	private UserService userService;
	@Autowired
	private ForbidListService forbidListService;
	
	/**
	 * 当更新主题的信息（比如删除，置顶, 加精等)
	 * @param jp
	 */
	@AfterReturning(pointcut="execution(* service.SubjectService.updateState(..))")
	public void updateStateAdvice(JoinPoint jp){
		Object[] args = jp.getArgs();
		int sub_id = (int)args[0];
		String flag = (String) args[1];
		String operUserid = (String) args[2];
		
		Subject sub = subjectService.get(sub_id);
		String operate = "";
		int type = 0;
		if(flag.equals("top")){
			type = 1;
			operate = "恭喜您，管理员将您的主题【"+sub.getSub_title()+"】置顶了";
		}else if(flag.equals("notop")){
			type = 2;
			operate = "由于置顶上限，管理员已将您的主题【"+sub.getSub_title()+"】取消置顶，感谢您对本论坛的支持！";
		}else if(flag.equals("perfect")){
			type = 3;
			operate = "恭喜您，您的主题被推荐为精品贴！";
		}else if(flag.equals("noperfect")){
			type = 4;
			operate = "由于精品上限，管理员已将您的主题【"+sub.getSub_title()+"】取消置顶，感谢您对本论坛的支持！";
		}else if(flag.equals("delete")){
			type = 5;
			operate = "十分抱歉，由于您发表的主题【"+sub.getSub_title()+"】可能有不合适的内容，管理员已经将其删除，详情请联系版主或管理员";
		}else {
			type = 6;
			operate = "您被删除的主题【"+sub.getSub_title()+"】被成功恢复";
		}
		Diary diary = new Diary(type, new Date(), operUserid, sub.getAuthor().getUser_id(), sub.getSub_title());
		diaryService.save(diary);
		// 通知主题作者
		Information info = new Information(type + 10, operate, false, 
				new Date(), sub.getAuthor(), sub, null);
		informationService.save(info);
	}
	
	/**
	 * 管理员将用户封禁的advice, 用于记录日志和通知用户
	 * @param jp
	 */
	@AfterReturning(pointcut="execution(* service.UserService.forbid(..))")
	public void forbidAdvice(JoinPoint jp){
		Object[] args = jp.getArgs();
		String userid = (String) args[0];
		int days = (int) args[1];
		String operUser = (String) args[2];
		Date now = new Date();
		
		Diary diary = new Diary(10, now, operUser, userid, "封禁"+days+"天");
		diaryService.save(diary);
		
		// 通知被封禁的人
		Information info = new Information(6, "您被管理员封禁了"+days+"天", false, now, userService.get(userid), null, null);
		info.setUser(userService.get(operUser));
		informationService.save(info);
	}
	
	/**
	 * 管理员主动解除用户的封禁的Advice，用于记录日志和通知用户
	 * @param jp
	 */
	@AfterReturning(returning="result",pointcut="execution(* service.ForbidListService.removeForbid(..))")
	public void removeForbidAdvice(JoinPoint jp, Object result){
		Object[] args = jp.getArgs();
		int forbid_id = (int) args[0];
		String operId = (String) args[1];
		Date now = new Date();
		User user = (User) result;
		Diary diary = new Diary(11, now, operId, user.getUser_id(), "解除封禁");
		diaryService.save(diary);
		
		Information info = new Information(7, "您已被解除封禁！", false, now, user, null, null);
		informationService.save(info);
	}

}
