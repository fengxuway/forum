package service.impl;

import java.io.Serializable;
import java.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import pagination.PageBean;

import dao.*;
import entity.*;
import service.*;
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	private static final Logger logger = Logger.getLogger(CategoryServiceImpl.class);
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ForbidListService forbidListService;
	
	
	@Override
	public Category get(Serializable id) {
		return categoryDao.get(id);
	}

	@Override
	public List<Category> getAll() {
		return categoryDao.getAll();
	}

	@Override
	public Serializable save(Category o) {
		return categoryDao.save(o);
	}

	@Override
	public void delete(Category o) {
		categoryDao.delete(o);
	}

	@Override
	public void update(Category o) {
		categoryDao.update(o);
	}

	@Override
	public void saveOrUpdate(Category o) {
		categoryDao.saveOrUpdate(o);
	}

	@Override
	public PageBean getAllByPage(int page) {
		return categoryDao.getAllByPage(page);
	}

	@Override
	public String updateCate(int cate_id, String cate_name, String cate_info,
			String cate_admin) throws Exception {
		Category cate = get(cate_id);
		if(cate != null){
			// 获取曾经是版主的管理员，取消其管理身份
			List<User> list = userDao.findByIdentity(100+cate_id);
			for(User tmp: list){
				tmp.setIdentity(5);
			}
			User admin = userDao.get(cate_admin);
			// 如果用户不为空，判断是否不已经是管理员
			if(admin != null){
				int identity = admin.getIdentity();
				if(identity > 100 && identity != (100+cate_id)){
					return "用户【"+cate_admin+"】已经是其他版块的管理员！";
				}else{
					// 如果用户尚被封禁，取消封禁
					if(identity <=0){
						forbidListService.delete(forbidListService.findByUser(cate_admin));
					}
					admin.setIdentity(100+cate_id);
				}
			}else if(!cate_admin.trim().equals("")){
				return "用户【"+cate_admin+"】不存在！";
			}
			cate.setCate_admin(admin);
			cate.setCate_name(cate_name);
			cate.setCate_info(cate_info);
			
			return "1";
		}else{
			return "网络异常，请刷新后重试！";
		}
	}

	@Override
	public String addCate(String cate_name, String cate_info, String cate_admin) {
		User admin = userDao.get(cate_admin);
		// 如果用户不为空，判断是否不已经是管理员
		if(admin != null){
			int identity = admin.getIdentity();
			if(identity > 100){
				return "用户【"+cate_admin+"】已经是其他版块的管理员！";
			}else{
				// 如果用户尚被封禁，取消封禁
				if(identity <=0){
					forbidListService.delete(forbidListService.findByUser(cate_admin));
				}
			}
		}else if(!cate_admin.trim().equals("")){
			return "用户【"+cate_admin+"】不存在！";
		}
		
		Category cate = new Category(cate_name, cate_info);
		cate.setCate_admin(admin);
		int cate_id = (Integer) save(cate);
		if(admin!=null){
			admin.setIdentity(100+cate_id);
		}
		return "1";
	}

}
