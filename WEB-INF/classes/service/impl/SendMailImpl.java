package service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import service.SendMail;
import util.ConfigPropertiesUtil;
import entity.User;
@Service("sendMail")
public class SendMailImpl implements SendMail {
	private static final Logger logger = Logger.getLogger(SendMailImpl.class);
	private static String HOST_URL = ConfigPropertiesUtil.get("HOST_URL");
	private String host = "smtp.qq.com";// smtp服务器
	private String user = "fengxuway";// 用户名--就是邮箱地址@之前的部分
	private String pwd = "ucVJ06!(";// 密码--邮箱密码

	public void send(String str_from, String str_to, String str_title,
			String str_content) {
 
		// str_content="<a href='www.163.com'>html元素</a>"; //for testing send
		// html mail!
		try {
			// 建立邮件会话
			Properties props = new Properties(); // 用来在一个文件中存储键-值对的，其中键和值是用等号分隔的，
			// 存储发送邮件服务器的信息
			props.put("mail.smtp.host", host);
			// 同时通过验证
			props.put("mail.smtp.auth", "true");
			// 根据属性新建一个邮件会话
			Session s = Session.getInstance(props);
			// s.setDebug(true); //有他会打印一些调试信息。
			// 由邮件会话新建一个消息对象
			MimeMessage message = new MimeMessage(s);
			// 设置邮件
			InternetAddress from = new InternetAddress(str_from); // pukeyouxintest2@163.com
			message.setFrom(from); // 设置发件人的地址
			// //设置收件人,并设置其接收类型为TO
			InternetAddress to = new InternetAddress(str_to); // pukeyouxintest3@163.com
			message.setRecipient(Message.RecipientType.TO, to);
			// 设置标题
			str_title = MimeUtility.encodeText(str_title, "UTF-8", "B");
			logger.info("title" + str_title);
			message.setSubject(str_title); // java学习
			// 设置信件内容
			// message.setText(str_content); //发送文本邮件 //你好吗？
			message.setContent(str_content, "text/html;charset=utf-8"); // 发送HTML邮件
																		// //<b>你好</b><br><p>大家好</p>
			// 设置发信时间
			message.setSentDate(new Date());
			// 存储邮件信息
			message.saveChanges();
			// 发送邮件
			Transport transport = s.getTransport("smtp");
			// 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
			transport.connect(host, user, pwd);
			// 发送邮件,其中第二个参数是所有已设好的收件人地址
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			System.out.print("邮件发送错误，请检查网络！");
			System.err.println(e.getMessage());
			
			//TODO 此句会导致事务回滚
			throw new RuntimeException("邮件发送错误，请检查网络！");
		}
	}
	
	@Override
	public void sendActivateMail(User user){
		String user_id_encode = null;
		try {
			user_id_encode = URLEncoder.encode(user.getUser_id(), "utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("SendMail.sendActivateMail()-Encode编码失败");
			user_id_encode = user.getUser_id();
		}
		logger.info("激活链接："+HOST_URL + "/register_userActive?uuid="
				+ user.getUuid() +"&userid="+user_id_encode);
		send(
			"fengxuway@qq.com",
			user.getEmail(),
			"中北校友论坛账户激活",
			"<h2 style='font-size:28px'>您好，"+ user.getUser_id() +"</h2>"
			+ "<h3 style='font-size:24px'>欢迎进入<a href='"+HOST_URL+"'>中北校友论坛</a></h3>"
			+ "<p>请在收到邮件的一个礼拜内点击以下链接（或将链接复制到地址栏）以激活您的账户</p>"
			+ "<a href='"+HOST_URL+"/register_userActive?uuid="
			+ user.getUuid() +"&userid="+user_id_encode+"'>" 
			+ "点击链接激活账户：&nbsp;"+HOST_URL+"/register_userActive?uuid="
			+ user.getUuid()  +"&userid="+user.getUser_id()+"</a><br>"
			+ "如果不是您本人操作，请直接忽略该邮件。"
		);
	}
	@Override
	public void sendCheckWordMail(String email, String cw){
		logger.info("验证码："+cw);
		send(
				"fengxuway@qq.com",
				email,
				"中北校友论坛找回密码",
				
				"<h3 style='font-size:24px'>您好，您正在找回您的密码，此次验证码是："+cw+"</h3>"
				+ "<p>验证码有效期是5分钟</p><p>如果不是您本人操作，请直接忽略该邮件</p>"
				);
	}
	
	public static void main(String[] args) {
		User u = new User();
		u.setUser_id("独孤觅雪");
		u.setEmail("fengxuway@qq.com");
		u.setUuid("lkdsjfldskfjldks");
		SendMailImpl s = new SendMailImpl();
		s.sendActivateMail(u);
		//s.send("fengxu@qq.com", "fengxu@qq.com", "dsklfj", "dkls");
	}

}
