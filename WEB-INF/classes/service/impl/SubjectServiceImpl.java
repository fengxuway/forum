package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import pagination.MemberDao;
import pagination.PageBean;

import dao.*;
import entity.*;
import service.*;

@Service("subjectService")
public class SubjectServiceImpl implements SubjectService {
	private static final Logger logger = Logger.getLogger(SubjectServiceImpl.class);
	// 通过类别ID获取主题(顺序: 是否置顶倒序, 回复时间倒序, 发表时间倒序)
	private final String listSQL = "from Subject as s  where s.cate_id.cate_id = ? "
			+ "and s.if_delete=false order by s.if_top desc, s.last_reply_time desc, s.sub_time desc";
	// 通过是否精品获取主题(顺序: 发表时间倒序)
	private final String listSQL2 = "from Subject as s  where s.if_perfect = true "
			+ "and s.if_delete=false order by s.sub_time desc";
	// 每页显示数量 
	private final int pageSize = 10;

	@Autowired
	@Qualifier("subjectDao")
	private SubjectDao subjectDao;
	@Autowired
	@Qualifier("categoryDao")
	private CategoryDao categoryDao;
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("rankDao")
	private RankDao rankDao;
	@Autowired
	@Qualifier("replyDao")
	private ReplyDao replyDao;
	@Autowired
	@Qualifier("informationDao")
	private InformationDao informationDao;
	/*
	 * @Autowired
	 * 
	 * @Qualifier("memberService") private MemberService memberService;
	 */
	@Autowired
	@Qualifier("memberDao")
	private MemberDao memberDao;

	public PageBean findByCategory(int cate_id, int requestPage) {
		Category cate = categoryDao.get(cate_id);
		if (cate == null) {
			System.out.println("cate_id:" + cate);
			cate = (Category) categoryDao.find(
					"from Category c order by c.cate_id desc").get(0);
		}
		if ("精品区".equals(cate.getCate_name())) {
			System.out.println("精品区");
			return memberDao.findForPage(listSQL2, pageSize, requestPage);

		} else {
			return memberDao.findForPage(listSQL, cate_id, pageSize,
					requestPage);
		}
	}

	@Override
	public Subject get(Serializable id) {
		return subjectDao.get(id);
	}

	@Override
	public List<Subject> getAll() {
		return subjectDao.getAll();
	}

	@Override
	public Serializable save(Subject o) {
		return subjectDao.save(o);
	}

	@Override
	public void delete(Subject o) {
		subjectDao.delete(o);
	}

	@Override
	public void update(Subject o) {
		subjectDao.update(o);
	}

	@Override
	public void saveOrUpdate(Subject o) {
		subjectDao.saveOrUpdate(o);
	}
	
	@Override
	public Subject publishSubject(String sub_title, String sub_content,
			int cate_id, boolean if_sign, User author)  throws Exception{
		if (author != null && author.getIdentity() > 0) {
			Date now = new Date();
			Subject subject = new Subject(sub_title, sub_content, if_sign, now);
			// 当主题创建时, 设置最后回复时间为当前时间, 以便按时间排序
			subject.setLast_reply_time(now);
			
			// 建立用户与主题的关系
			subject.setAuthor(author);
			Category category = categoryDao.get(cate_id);
			subject.setCate_id(category);
			save(subject);

			// 保存主题，最后发表时间更改
			category.setLast_time(now);
			//类别表主题数+1
			updateSub_num(category, +1);
			
			//作者经验 +1
			userService.updateExp(author, +1);
			
			logger.info("主题"+ subject +"发表成功！");
			
			return subject;
			
		} else {
			throw new RuntimeException();
		}
	}
	
	//("top","notop","perfect","noperfect","delete","nodelete")
	@Override
	public void updateState(int sub_id, String flag, String operUserid) throws Exception{
		Subject subject = get(sub_id);
		if(flag.equals("top")){
			//置顶
			subject.setIf_top(true);
			
			userService.updateExp(subject.getAuthor(), +1);
			
			logger.info(subject+" 置顶成功！");
		}else if(flag.equals("notop")){
			//取消置顶
			subject.setIf_top(false);
			logger.info(subject+" 取消置顶成功！");
		
		}else if(flag.equals("perfect")){
			//加精
			subject.setIf_perfect(true);
			//精品区主题数 + 1
			updateSub_num(categoryDao.findByName("精品区"), +1);
			
			//设置加精，作者经验 +2
			userService.updateExp(subject.getAuthor(), +2);
			
			logger.info(subject + " 加精成功！");
		}else if(flag.equals("noperfect")){
			subject.setIf_perfect(false);
			//精品区主题数 - 1
			updateSub_num(categoryDao.findByName("精品区"), -1);
			logger.info(subject + " 取消加精成功！");
		
		}else if(flag.equals("delete")){
			//删除主题，该主题去精，对应的版块主题数 -1，如果为精品，精品区 -1
			subject.setIf_delete(true);
			updateSub_num(subject.getCate_id(), -1);
			if(subject.isIf_perfect()){
				subject.setIf_perfect(false);
				updateSub_num(categoryDao.findByName("精品区"), -1);
			}
			
			subject.getCate_id().setReply_num(subject.getCate_id().getReply_num() - subject.getReply_num());
			
			//用户经验值减少 1+回复数
			userService.updateExp(subject.getAuthor(), -1*(subject.getReply_num() + 1));
			
			logger.info(subject + " 删除成功！");
		
		}else if(flag.equals("nodelete")  && subject.isIf_delete()){
			//恢复删除的主题
			subject.setIf_delete(false);
			//分类主题数 +1
			updateSub_num(subject.getCate_id(), +1);
			
			//用户经验值增加 1+回复数
			userService.updateExp(subject.getAuthor(), (subject.getReply_num() + 1));
			
			logger.info(subject + " 恢复成功！");
				
		
		}
	
	}
	
	
	@Override
	public void moveToCategory(int sub_id, int cate_id, String operUserid) throws Exception{
		Subject sub = get(sub_id);
		//获取原本的主题分类，并将其主题数 -1
		Category oldCate = sub.getCate_id();
		if(oldCate.getSubject_num() > 0){
			oldCate.setSubject_num(oldCate.getSubject_num() - 1);
		}
		
		//获取当前主题回复数（不算被删除的），将分类回复数 - reply_num
		int reply_num = sub.getReply_num();
		
		if(oldCate.getReply_num() > reply_num){
			oldCate.setReply_num(oldCate.getReply_num() - reply_num);
		}else{
			oldCate.setReply_num(0);
		}
		
		//获取新分类对象，主题数 +1， 回复数 + reply_num
		Category newCate = categoryDao.get(cate_id);
		updateSub_num(newCate, +1);
		newCate.setSubject_num(newCate.getSubject_num() + 1);
		newCate.setReply_num(newCate.getReply_num() + reply_num);
		
		//设置主题与分类的关联
		sub.setCate_id(newCate);
		logger.info("主题"+sub+"已更新为"+newCate+"版块");
	}
	
	
	
	/**
	 * 为分类增加或减少主题数量
	 * @param cate 分类对象
	 * @param count 增加或减少的数量（负数为减少）
	 */
	public void updateSub_num(Category cate, int count){
		count += cate.getSubject_num();
		count = count>=0? count: 0;
		cate.setSubject_num(count);
		logger.info("版块"+cate+"主题数改为"+count);
	}

	@Override
	public PageBean getDelSubject(int page, int identity) {
		return subjectDao.getDelSubject(page, identity);
	}


	@Override
	public PageBean getPerfectSubject(int page, int identity) {
		return subjectDao.getPerfectSubject(page, identity);
	}

	@Override
	public PageBean getTopSubject(int page, int identity) {
		return subjectDao.getTopSubject(page, identity);
	}

	@Override
	public List<Subject> search(String kw) {
		return subjectDao.search(kw);
	}

	@Override
	public PageBean searchForPage(String kw, int page) {
		return subjectDao.searchForPage(kw, page);
	}

}
