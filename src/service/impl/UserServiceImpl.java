package service.impl;

import static util.MyRegex.*;
import java.io.Serializable;
import java.util.*;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pagination.*;
import action.ReplyAction;
import dao.*;
import entity.*;
import service.*;
import util.*;

@Service("userService")
public class UserServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	@Autowired
	@Qualifier("sendMail")
	private SendMail sendMail;
	@Autowired
	@Qualifier("collegeDao")
	private CollegeDao collegeDao;
	@Autowired
	@Qualifier("majorDao")
	private MajorDao majorDao;
	@Autowired
	@Qualifier("rankDao")
	private RankDao rankDao;
	@Autowired
	@Qualifier("attentionDao")
	private AttentionDao attentionDao;
	@Autowired
	private ForbidListDao forbidListDao;
	
	@Override
	public User findByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public boolean userValidate(String userid) {
		// userid符合正则表达式的才可以进行查询数据库
		// 用户可以使用传回true
		if (regexUserid(userid) && get(userid) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean emailValidate(String email) {
		// email符合正则表达式的才可以进行查询数据库
		// 邮箱被占用返回false
		if (regexEmail(email) && findByEmail(email) == null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean infoValidate(String userid, String email, String password) {
		if (userValidate(userid) && emailValidate(email)
				&& password.length() >= 3 && password.length() <= 16) {
			return true;
		}
		return false;
	}

	@Override
	public void register(String userid, String email, String pswd,
			String name, boolean sex, String student_id, int college_id,
			int major_id) {
		if (infoValidate(userid, email, pswd)
				&& regex("[\\u2E80-\\u9FFF]{1,5}", name)
				&& regex("\\d{8,12}", student_id) && college_id > 0
				&& college_id < 30) {
			User user = new User(userid, email, pswd, name, sex, student_id);
			user.setActive_time(System.currentTimeMillis());
			user.setCollege(collegeDao.get(college_id));
			user.setRank(rankDao.findByGrade(1));
			if (major_id != 0) {
				user.setMajor(majorDao.get(major_id));
			}
			user.setPassword(MyMD5.md5(pswd));
			save(user);
			
		}else{
			throw new RuntimeException("注册失败！数据校验失败");
		}
	}

	@Override
	public User login(String userid, String pswd) {
		// try {
		if (regexEmail(userid)) {
			// 如果输入为邮箱
			User u = findByEmail(userid);
			if (u != null && u.getIdentity() >= 0
					&& u.getPassword().equals(MyMD5.md5(pswd))) {
				// 验证成功
				return u;
			}
		} else if (regexUserid(userid)) {
			// 如果输入的是userid
			User u = get(userid);
			if (u != null && u.getIdentity() >= 0
					&& u.getPassword().equals(MyMD5.md5(pswd))) {
				return u;
			}
		}
		return null;
	}

	@Override 
	public User userActive(String userid, String uuid) throws Exception {
		if (uuid != null && uuid.length() >= 32 && userid != null) {
			// 根据userid获取用户
			User user = get(userid);
  
			if (user == null) { 
				logger.error("UserService.userActive(): 用户激活失败! Caused by 用户未找到!");
				throw new RuntimeException("UserService.userActive(): 用户激活失败! Caused by 用户未找到!");
			}

			// 如果用户已经激活，不进行操作，直接返回对象
			if (user.getIdentity() >= 0) { 
				logger.info("用户已激活");
				return user;
			}

			else if (user.getUuid().equals(uuid)
					&& user.getActive_time() >= System.currentTimeMillis()) {
				userDao.userActive(user);
				logger.info("用户激活成功");
				return user;
			}
			
		}

		throw new RuntimeException("UserService.userActive(): 用户激活失败! Caused by 数据处理异常!");

	}
	
	public void updateExp(User author, int exp) throws Exception{
		author = userDao.get(author.getUser_id());
		//作者经验 +1, 如果经验值 >= 当前rank表的exp，等级 +1
		exp += author.getExp();
		if(exp >= 0)	author.setExp(exp);
		
		int grade = author.getRank().getGrade();
		//如果经验值 >= 当前用户的rank的经验值，提升一个等级
		//如果经验值 < 当前比用户小的等级的经验值，降低一个等级
		if(exp >= author.getRank().getExp()){
			author.setRank(rankDao.findByGrade(grade + 1));
		}else if(grade > 1 && exp < rankDao.findByGrade(grade - 1).getExp()){
			author.setRank(rankDao.findByGrade(grade - 1));			
		}
		
		
		logger.info("用户经验值："+exp+" 等级："+author.getRank());
	}

	@Override
	public void updateAgree(User user) throws Exception {
		/**
		 * 更改User对象 1.将if_apply字段改为true 2.设置UUID 3.设置最后激活时间 4.持久化User对象 5.发送激活邮件
		 */
		user.setIdentity(-5);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		user.setUuid(uuid);
		user.setActive_time(System.currentTimeMillis() + 86400000 * 7);
		update(user);
		
		logger.info("管理员已同意注册");

		// 发送激活邮件
		sendMail.sendActivateMail(user);
		
		logger.info("邮件发送成功！");
	}

	@Override
	public void updateAgree(String userid) throws Exception {
		updateAgree(get(userid));
	}

	@Override
	public boolean updateAgainst(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateAgainst(String userid) {
		return updateAgainst(get(userid));
	}
	
	

	@Override
	public List<User> findNoApply() {
		return userDao.findNoApply();
	}

	@Override
	public Serializable save(User user) {
		return userDao.save(user);
	}
	
	@Override
	public void delete(User user) {
		userDao.delete(user);
	}
	
	@Override
	public void update(User user) {
		userDao.update(user);
	}
	
	@Override
	public User get(Serializable id) {
		return userDao.get(id);
	}
	
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}
	
	@Override
	public void saveOrUpdate(User o) {
		userDao.saveOrUpdate(o);
	}
	
	@Override
	public User findByUuid(String uuid) {
		return userDao.findByUuid(uuid);
	}

	@Override
	public PageBean getSelfSubject(User user, int page) throws Exception {
		List<User> users = new ArrayList<User>();
		users.add(user);
		return userDao.getUsersSubject(users, page);
	}
	
	@Override
	public PageBean getSelfReply(User user, int page) throws Exception{
		List<User> users = new ArrayList<User>();
		users.add(user);
		return userDao.getUsersReply(users, page);
	}

	@Override
	public void addMention(User user, String atte_id) {
		logger.info("数据为空，正在添加。。。"+ user.getUser_id()+" "+atte_id);
		User atte_user = userDao.get(atte_id);
		//用户不能关注自己
		if( ! user.getUser_id().equals(atte_id)){
			//如果用户尚未添加关注
			logger.info("正在添加关注..");
			if(attentionDao.findByUserAndUser(user, atte_user) == null){
				Attention atte = new Attention(user, atte_user);
				attentionDao.save(atte);
			}else{
				logger.info("已添加，不需操作");
			}
		}
	}

	@Override
	public void removeMention(User user, String atte_id) {
		logger.info("已经关注，数据不为空！"+user.getUser_id() + " "+atte_id);
		User atte_user = userDao.get(atte_id);
		//用户不能关注自己
		if( ! user.getUser_id().equals(atte_id)){
			//如果用户已添加关注，删除关注
			Attention atte = attentionDao.findByUserAndUser(user, atte_user);
			logger.info("正在取消关注");
			if(atte != null){
				attentionDao.delete(atte);
			}else{
				logger.info("数据为空");
			}
		}
		
	}

	@Override
	public void forbid(String userid, int days,String operUser) {
		ForbidList fb = forbidListDao.findByUser(userid);
		Date now = new Date();
		// 如果用户尚未被封禁,创建一个ForbidList对象, 并更改用户if_forbid为true
		if(fb == null){
			User user = get(userid);
			// 如果用户为版主或管理员, 则不允许封禁
			if(user.getIdentity()> 100){
				throw new RuntimeException("该用户为管理员身份，不可以被封禁！");
			}
			ForbidList f = new ForbidList(user, days, get(operUser), now, now.getTime() + days * 1000 * 3600 *24);
			forbidListDao.save(f);
			user.setIdentity(0);
		}else{
		// 如果用户已经被封禁, 则更新封禁时间
			fb.setDays(days);
			fb.setOperate_id(get(operUser));
			fb.setOperate_time(now);
			fb.setUnforbid_time(now.getTime() + days * 1000 * 3600 * 24);
		}
	}

	@Override
	public void updatePswdByEmail(String email, String pswd) {
		User user = findByEmail(email);
		if(user != null){
			user.setPassword(MyMD5.md5(pswd));
		}else{
			throw new RuntimeException();
		}
	}

	@Override
	public PageBean findNoApply(int page) {
		return userDao.findNoApply(page);
	}

	@Override
	public PageBean findAllUsers(int page, String kw) {
		return userDao.findAllUsers(page,kw);
	}

	@Override
	public List<User> findByIdentity(int identity) {
		return userDao.findByIdentity(identity);
	}

	@Override
	public List<User> search(String kw) {
		System.out.println("Service开事实上");
		return userDao.search(kw);
	}

	@Override
	public User getSuperAdmin() {
		List<User> list = findByIdentity(1000);
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public User getModerAdmin(int cate_id) {
		List<User> list = findByIdentity(100+ cate_id);
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
		
	}

	@Override
	public int getUserCount() {
		return userDao.getAllUsers().size();
	}

}
