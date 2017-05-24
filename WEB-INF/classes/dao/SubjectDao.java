package dao;

import java.util.List;

import pagination.PageBean;

import entity.Subject;

public interface SubjectDao extends BaseDao<Subject> {

	/**
	 * 获取被删除的主题list
	 * @param page 当前页码
	 * @param identity 标记当前session中的用户身份（user的identity)
	 * @return PageBean分页封装对象
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
  