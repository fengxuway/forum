package service;

import pagination.PageBean;
import entity.Diary;

public interface DiaryService extends BaseService<Diary> {

	/**
	 * 获取主题删除的日志
	 * @param page 页码
	 * @return 日志的分页Bean
	 */
	PageBean getDeleteSubject(int page);

	/**
	 * 获取被加精的主题
	 * @param page 页码
	 * @return 加精的主题日志列表Bean
	 */
	PageBean getJiajingSubject(int page);

	/**
	 * 获取置顶操作的日志list
	 * @param page 页码
	 * @return 置顶操作的分页bean
	 */
	PageBean getZhidingSubject(int page);

	/**
	 * 获取封禁日志
	 * @param page 指定页码
	 * @return 封禁日志的分页Bean
	 */
	PageBean getFengjin(int page);

}
 