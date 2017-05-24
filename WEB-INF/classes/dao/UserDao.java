package dao;
import java.util.*;

import pagination.*;
import entity.*;

public interface UserDao extends BaseDao<User> {
	
	/**
	 * 根据用户email获取user对象
	 * @param email
	 * @return
	 */
	User findByEmail(String email);
	 
	/**
	 * 根据uuid获取用户对象
	 * @param uuid
	 * @return
	 */
	User findByUuid(String uuid);
	
	/**
	 * 激活账户
	 * @param user
	 */
	void userActive(User user);
	
	/**
	 * 查询所有没有被允许注册的用户
	 * @return
	 */
	List<User> findNoApply();
	
	/**
	 * 获取指定用户List发表的<b>主题</b>列表
	 * @param user 用户
	 * @param page 指定页码，以分批加载
	 * @return 主题封装类PageBean
	 * @throws Exception
	 */
	PageBean getUsersSubject(List<User> users, int pageNum);
	
	/**
	 * 获取指定用户List发表的<b>回复</b>列表
	 * @param user 用户
	 * @param page 指定页码，以分批加载
	 * @return 回复封装类PageBean
	 * @throws Exception
	 */
	PageBean getUsersReply(List<User> users, int pageNum);

	/**
	 * 分页获取尚未被同意注册的用户List
	 * @param page 页码
	 * @return PageBean分页
	 */
	PageBean findNoApply(int page);

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
	 * 获取已注册所有用户List
	 * @return 用户List
	 */
	List<User> getAllUsers();
	
	
}
