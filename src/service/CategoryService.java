package service;

import pagination.PageBean;
import entity.Category;

public interface CategoryService extends BaseService<Category> {

	/**
	 * 根据指定页码获取所有板块分类
	 * @param page 页码
	 * @return PageBean分类bean
	 */
	PageBean getAllByPage(int page);

	/**
	 * 根据指定的分类ID更新数据
	 * @param cate_id 指定ID
	 * @param cate_name 更新后的name
	 * @param cate_info 更新后的info
	 * @param cate_admin 更新后的版主
	 * @return 通知信息
	 */
	String updateCate(int cate_id, String cate_name, String cate_info,
			String cate_admin) throws Exception;

	/**
	 * 添加分类版块
	 * @param cate_name 版块名称
	 * @param cate_info 版块简介
	 * @param cate_admin 版主ID
	 * @return 通知是否添加成功
	 */
	String addCate(String cate_name, String cate_info, String cate_admin) throws Exception;
	
}
 