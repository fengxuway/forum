package service;

import java.util.List;
import pagination.*;
import entity.*;

public interface UserService extends BaseService<User> {
	
	User findByEmail(String email);
	
	User findByUuid(String uuid);
	
	/**
	 * 激活账户
	 * @param userid 用户ID
	 * @param uuid UUID字段
	 * @return 如果激活成功，返回user对象，否则返回null
	 */
	User userActive(String userid, String uuid) throws Exception;
	 
	/**
	 * 判断用户名是否符合要求，并查询是否可用
	 * @param userid
	 */
	boolean userValidate(String userid);
	/**
	 * 判断邮箱是否符合要求，并查询是否可用
	 * @param userid
	 * @return 可用返回true，不可用返回false
	 */
	boolean emailValidate(String userid);
	
	/**
	 * 验证表单信息是否符合正则表达式以及数据库的唯一性
	 * @param userid
	 * @param email
	 * @param password
	 * @return
	 */
	boolean infoValidate(String userid, String email, String password);
	
	/**
	 * 注册用户
	 * @param userid 用户名
	 * @param email 邮箱
	 * @param password 密码
	 */
	void register(String userid, String email, String password, String name, boolean sex, String student_id, int college_id, int major_id) 
			throws Exception;
	
	/**
	 * 同意注册更新模块
	 * @param user
	 * @return
	 */
	void updateAgree(User user) throws Exception;
	/**
	 * 同意注册更新的重载方法
	 * @param userid
	 * @return
	 */
	void updateAgree(String userid) throws Exception;
	
	/**
	 * 反对注册更新模块
	 * @param user
	 * @return
	 */
	boolean updateAgainst(User user) throws Exception;
	/**
	 * 反对注册更新的重载方法
	 * @param userid
	 * @return
	 */
	boolean updateAgainst(String userid) throws Exception;
	
	/**
	 * 登录用户
	 * @param userid 用户名或邮箱
	 * @param password 密码
	 * @return 登录成功返回user对象，否则返回null
	 */
	User login(String userid, String password);
	
	/**
	 * 计算经验值
	 * @param author 作者
	 * @param exp 要增加的经验值，负数表示减少经验值
	 */
	void updateExp(User author, int exp) throws Exception;
	
	
	/**
	 * 查找尚未被同意申请的用户
	 * @return
	 */
	List<User> findNoApply();
	
	/**
	 * 分页查找尚未被同意申请的用户
	 * @return
	 */
	PageBean findNoApply(int page);
	
	/**
	 * 获取指定用户发表的<b>主题</b>列表
	 * @param user 用户
	 * @param page 指定页码，以分批加载
	 * @return 主题封装类Pagebean
	 * @throws Exception
	 */
	PageBean getSelfSubject(User user, int page) throws Exception;
	
	/**
	 * 获取指定用户发表的<b>回复</b>列表
	 * @param user 用户
	 * @param page 指定页码，以分批加载
	 * @return 回复封装类PageBean
	 */
	PageBean getSelfReply(User user, int page) throws Exception;
	
	/**
	 * 添加关注操作
	 * @param user 操作用户
	 * @param atte_id 被关注的用户ID
	 */
	void addMention(User user, String atte_id) throws Exception;
	
	/**
	 * 取消关注操作
	 * @param user 操作用户
	 * @param atte_id 被关注的用户ID
	 */
	void removeMention(User user, String atte_id) throws Exception;

	/**
	 * 封禁用户
	 * @param userid 指定用户
	 * @param days 天数
	 * @param operUser 操作人ID
	 */
	void forbid(String userid, int days,String operUser) throws Exception;
	
	/**
	 * 根据指定的邮箱确定用户，并修改其密码为pswd
	 * @param email 指定邮箱
	 * @param pswd 密码
	 */
	void updatePswdByEmail(String email, String pswd);

	/**
	 * 获取所有用户或指定关键字的用户
	 * @param page 指定页码
	 * @param kw 关键字
	 * @return 用户list的分页Bean
	 */
	PageBean findAllUsers(int page, String kw);

	/**
	 * 根据identity身份标识获取用户列表
	 * @param identity 标识
	 * @return 用户列表List
	 */
	List<User> findByIdentity(int identity);

	/**
	 * 根据关键字搜索用户列表
	 * @param kw 关键字
	 * @return 用户的List
	 */
	List<User> search(String kw);

	/**
	 * 获取超级管理员，即identity==1000
	 * @return User对象
	 */
	User getSuperAdmin();
	
	/**
	 * 获取指定板块的版主
	 * @param cate_id 板块ID
	 * @return User对象
	 */
	User getModerAdmin(int cate_id);

	/**
	 * 获取用户总数
	 * @return int数据
	 */
	int getUserCount();
}
