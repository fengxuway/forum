package service;

import java.util.List;

import pagination.PageBean;
import entity.Category;
import entity.Subject;
import entity.User;

public interface SubjectService extends BaseService<Subject> {
	/**
	 * 发表主题
	 * @param sub_title 主题标题
	 * @param sub_content 主题内容
	 * @param cate_id 类别ID
	 * @param if_sign 是否含有签名档 
	 * @param author 作者
	 * @return 发布成功返回true
	 * @throws Exception 
	 */
	Subject publishSubject (String sub_title, String sub_content, int cate_id, boolean if_sign, User author) 
			throws Exception;
	/** 
	 * 根据类别ID及页码获取指定页面的主题
	 * @param cate_id 类别ID
	 * @param page 页码
	 * @return PageBean 包含主题List及其它信息的javaBean
	 */
	PageBean findByCategory(int cate_id, int page);
	
	
	/**
	 * 更改主题状态（包括是否置顶、是否加精、是否删除等）
	 * @param sub_id 主题ID
	 * @param flag 标识("top","notop","perfect","noperfect","delete","nodelete")
	 * @param operUserid 仅为了AOP时写入日志中，与操作逻辑无关
	 * @throws Exception 更新失败时抛出异常
	 */
	void updateState(int sub_id, String flag, String operUserid) throws Exception;
	
	/**
	 * 更改主题类别
	 * @param sub_id 要更改的主题ID
	 * @param cate_id 指定的类别ID
	 * @param operUserid 仅为了AOP时写入日志中，与操作逻辑无关
	 */
	void moveToCategory(int sub_id, int cate_id, String operUserid) throws Exception;
	
	/**
	 * 为分类增加或减少主题数量
	 * @param cate 分类对象
	 * @param count 增加或减少的数量（负数为减少）
	 */
	void updateSub_num(Category cate, int count);
	
	/**
	 * 获取当前用户管理的已被删除的主题list
	 * @param page 当前页码
	 * @param identity 标记当前session中的用户身份（user的identity)
	 * @return PageBean分布封装对象
	 */
	PageBean getDelSubject(int page, int identity);
	
	/**
	 * 获取当前用户管理的被加精的主题list
	 * @param page 当前页码
	 * @param identity 用户标识
	 * @return 加精的主题分页Bean
	 */
	PageBean getPerfectSubject(int page, int identity);
	
	/**
	 * 获取当前用户管理的被置顶的主题List
	 * @param page 当前页码
	 * @param identity 用户标识
	 * @return 置顶的主题分页Bean
	 */
	PageBean getTopSubject(int page, int identity);
	
	/**
	 * 根据关键字搜索主题列表
	 * @param kw 关键字
	 * @return 主题的List
	 */
	List<Subject> search(String kw);
	
	/**
	 * 根据关键字搜索主题列表（分页）
	 * @param kw 关键字
	 * @param page 页码
	 * @return 主题的PageBean
	 */
	PageBean searchForPage(String kw, int page);

	
}
