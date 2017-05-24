package dao;

import java.util.List;

import pagination.PageBean;
import entity.*;

public interface CategoryDao extends BaseDao<Category> {
	/**
	 * 根据分类名称获取分类对象
	 * @param cate_name 分类名称
	 * return Category 
	 */
	Category findByName(String cate_name);

	/**
	 * 根据指定页码获取所有板块分类
	 * @param page 页码
	 * @return PageBean分类bean
	 */
	PageBean getAllByPage(int page);
}
