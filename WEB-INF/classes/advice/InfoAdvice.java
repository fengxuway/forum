package advice;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import entity.*;
import service.*;

/**
 * 切面类，记录用户Information的事件（如发表主题、发表回复、回复我的等消息）
 * @author fengxu
 *
 */
@Aspect
@Component("infoAdvice")
public class InfoAdvice {
	private static final Logger logger = Logger.getLogger(InfoAdvice.class);
	@Autowired
	@Qualifier("informationService")
	private InformationService informationService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("replyService")
	private ReplyService replyService;
	@Autowired
	private SubjectService subjectService;
	
	/**
	 * 用户发表主题后，将用户的操作记录到Information中
	 * @param jp
	 * @param result
	 */
	@AfterReturning(returning="result",pointcut="execution(* service.SubjectService.publishSubject(..))")
	public void publishSubjectAdvice(JoinPoint jp, Object result){
		//参数列表中获取user对象
		User user = (User) jp.getArgs()[4];
		//获取返回值中的主题对象
		Subject subject = (Subject)result;
		
		//一个已读的发表主题的消息
		Information info = new Information(1, "发表主题", true, new Date(), user, subject, null);
		
		informationService.save(info);
		
		logger.info("发表主题消息已送至Information表");
		
	}
	
	/**
	 * 回复贴正确发表后执行的方法，通知被回复者Information
	 * @param jp
	 * @param result
	 */
	@AfterReturning(returning="result",pointcut="execution(* service.ReplyService.publishReply(..))")
	public void publishReplyAdvice(JoinPoint jp, Object result){
		Object[] args = jp.getArgs();
		//当前回复作者
		User author = (User) args[3];
		Reply reply = (Reply) result;
		//主题作者
		User subAuthor = reply.getSub_id().getAuthor();
		Date now = new Date();
		String reply_content = (String)args[0];
		
		//为作者添加回复记录
		informationService.save(new Information(2, "发表回复", true, now, author, null, reply));
		
		//为作者回复的主题的作者添加回复记录
		if(!subAuthor.getUser_id().equals(author.getUser_id())){
			informationService.save(new Information(3, "回复了您", false, now, subAuthor, null, reply));
			logger.info("通知主题作者");
		}
		
		//如果该回复贴为回复的回复，通知被回复的用户
		if((boolean) args[4]){
			Reply superReply = replyService.get((int)args[5]);
			User superReplyAuthor = superReply.getUser_id();
			//当父级回复作者不是当前回复本人，且不是主题作者本人，通知该用户 
			if(superReply != null && !superReplyAuthor.getUser_id().equals(subAuthor.getUser_id()) && !superReplyAuthor.getUser_id().equals(author.getUser_id())){
				informationService.save(new Information(3, "回复了您", false, now, superReplyAuthor, null, reply));
				logger.info("通知被回复的回复作者");
			}
		
			//如果字符串开始以“回复  ...：”开始，获取用户名，并通知该用户
			Pattern pattern1 = Pattern.compile("^回复\\s+([\\w\u2E80-\u9FFF-]{3,14})?：");
			Matcher matcher1 = pattern1.matcher(reply_content);
			if(matcher1.find()){
				User user = userService.get(matcher1.group(1));
				String userId = user.getUser_id();
				//如果“回复。。。”之后的用户为当前回复的主题作者或回复的super回复,或本人，则不再次通知
				if(user != null && !userId.equals(author.getUser_id()) && !userId.equals(superReplyAuthor.getUser_id()) && !userId.equals(subAuthor.getUser_id())){
					informationService.save(new Information(3, "回复了您", false, now, user, null, reply));
					logger.info("回复字符串中有回复。。");
				}
			}
		}
		
		//获取回复中@提到的人，通知该用户
		Pattern pattern2 = Pattern.compile("@([\\w\u2E80-\u9FFF-]{3,14})");
		Matcher matcher2 = pattern2.matcher(reply_content);
		while(matcher2.find()){
			User user = userService.get(matcher2.group(1));
			if(user != null){
				informationService.save(new Information(4, "提到了您", false, now, user, null, reply));
			}
		}
		
	}
	
	/**
	 * 添加关注之后的切面方法，通知被关注的人
	 * @param jp
	 */
	@AfterReturning(pointcut="execution(* service.UserService.addMention(..)) ")
	public void addMentionAdvice(JoinPoint jp){
		Object[] args = jp.getArgs();
		//执行操作的用户对象
		User byuser = (User) args[0];
		//被关注的用户ID
		String atte_id = (String) args[1];
		//获取被关注的用户对象
		User user = userService.get(atte_id);
		if(user == null){
			return;
		}
		
		Information info = new Information(5, "用户【"+byuser.getUser_id()+"】关注了您", false, new Date(), user, null, null);
		//设置关注的人
		info.setUser(byuser);
		informationService.save(info);
	}
	
	
	/**
	 * 用户发表主题前，将发表内容中带有flv格式的链接转换为可以在html中播放的结点
	 * @param jp AOP对象
	 * @throws Throwable 
	 */
	@Around("execution(* service.SubjectService.publishSubject(..))")
	public Object publishSubjectBeforeAdvice(ProceedingJoinPoint jp) throws Throwable{
		//参数列表中获取user对象
		Object[] args = jp.getArgs();
		
		String content = (String) args[1];
		
//		System.out.println(content);
		
		String content2 = new String(content);
		
		// 匹配<a href="/forum.......123123123.flv">文件名</a>类似的字符串
		String regex = "<a\\s+href=\"(.*?/ueditor/jsp/upload/\\d+/\\d+\\.flv)\">.*?\\.flv</a>";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while(m.find()){
			System.out.println("------------");
			String a = m.group();
			String fileName = m.group(1);
			// TODO 这里可以设置下载链接的权限，将a链接指向某个action
			content2 = content2.replaceFirst(regex, a.substring(0, 2) 
					+ " title=\"点击下载\" " + a.substring(2) + getFlvStr(fileName));
		}
//		System.out.println(content2);
		args[1] = content2;
		
		// 执行方法
		return jp.proceed(args);
		
	}
	
	/**
	 * publishSubjectBeforeAdvice方法的私有方法，用于获取在线视频的节点
	 * @param fileName 文件路径及文件名
	 * @return 返回可以在html中播放视频的结点
	 */
	private static String getFlvStr(String fileName){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		String result = "</p><p id=\"" +uuid+ "\" style=\"width:400px; height:280px; margin:0px; border:solid 5px #50031a;color:#ffffff;\" ></p>"+
				"<script type=\"text/javascript\">"+
					"var s" +uuid+ " = new SWFObject(\"images/FlvPlayer201002.swf\",\"playlist\",\"400\",\"280\",\"7\");"+
					"s" +uuid+ ".addParam(\"allowfullscreen\",\"true\");"+
					"s" +uuid+ ".addVariable(\"autostart\",\"false\");"+
					"s" +uuid+ ".addVariable(\"image\",\"images/flash5.jpg\");"+
					"s" +uuid+ ".addVariable(\"file\",\"" +fileName+ "\");"+
					"s" +uuid+ ".addVariable(\"width\",\"400\");"+
					"s" +uuid+ ".addVariable(\"height\",\"280\");"+
					"s" +uuid+ ".write(\"" +uuid+ "\");"+
				"</script>";
		
		return result;
	}
	
	/**
	 * 监听更新板块分类方法，以便通知被提升为版主的人及被退出版主的人
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	/*String updateCate(int cate_id, String cate_name, String cate_info,
			String cate_admin) throws Exception;*/
	@Around("execution(* service.CategoryService.updateCate(..))")
	public Object updateCateAdvice(ProceedingJoinPoint jp) throws Throwable{
		//参数列表中获取user对象
		Object[] args = jp.getArgs();
		
		int cate_id = (int) args[0];
		String cate_name = (String) args[1];
		String cate_info = (String) args[2];
		String cate_admin = (String) args[3];  
		
		List<User> oldList = userService.findByIdentity(100 + cate_id);
		
		String result = (String) jp.proceed(args);
		
		if(result.equals("1")){
			// 当原先并无版主且后来也没有指定版主，或 当前版主与后来指定版主一致时，不进行任何info的提示
			if(oldList.size() > 0 && ((User) oldList.get(0)).getUser_id().equals(cate_admin) 
					|| oldList.size() == 0 && cate_admin.equals("")){
				
			}else{
				Date now = new Date();
				if(oldList.size() > 0){
					// 如果已有原版主
					User old = oldList.get(0);
					// 通知原版主，您工作期间贡献很大，不过为了新人，让您暂时歇息
					Information info = new Information(8, "在您做版主期间，管理的板块井然有序，为本论坛创造不少佳绩。不过为了鼓励新人，只好请您暂时休息一下。", false, now, old, null, null);
					informationService.save(info);					
				}
				
				if(!cate_admin.equals("")){
					User newUser = userService.get(cate_admin);
					// 如果指定新任版主，通知版主恭喜恭喜，能力越大，责任越大
					if(newUser != null){
						Information info = new Information(9, "恭喜您，由于您表现出色，被管理员提升为【" + cate_name + "】板块的版主，希望您再接再厉，管理好该板块。能力越大，责任越大！", false, now, newUser, null, null);
						informationService.save(info);
					}
				}
			}
				
		}
		return result;
	}	
	
	//public void moveToCategory(int sub_id, int cate_id, String operUserid)
	@Around("execution(* service.SubjectService.moveToCategory(..))")
	public void moveToCateAdvice(ProceedingJoinPoint jp) throws Throwable{
		Object[] args = jp.getArgs();
		int sub_id = (int) args[0];
		int cate_id = (int) args[1];
		String operUserid = (String) args[2];
		
		jp.proceed(args);
		
		Subject sub = subjectService.get(sub_id);
		User user = sub.getAuthor();
		Information info = new Information(17, "由于您的主题【"+sub.getSub_title()+"】的内容不符合当前的板块，" +
				"管理员将其移到【"+sub.getCate_id().getCate_name()+"】板块中。",
				false, new Date(), user, sub, null);
		informationService.save(info);
	}
	//public void deleteReply(int reply_id, String operUserid) 
	@Around("execution(* service.ReplyService.deleteReply(..))")
	public void deleteReplyAdvice(ProceedingJoinPoint jp) throws Throwable{
		Object[] args = jp.getArgs();
		int reply_id = (int) args[0];
		String operUserid = (String) args[1];
		Reply reply = replyService.get(reply_id);
		User author = reply.getUser_id();
		Subject subject = reply.getSub_id();
		String sub_title = subject.getSub_title();
		jp.proceed(args);
		
		Information info = new Information(18, "由于您在主题【"+sub_title+"】中的回复可能有不合适的内容，管理员已经将其删除，详情请联系版主或管理员", false, new Date(), author, null, null);
		informationService.save(info);
	}
}
