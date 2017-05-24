package service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pagination.MemberDao;
import pagination.PageBean;
import service.*;
import dao.*;
import entity.*;
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
	private static final Logger logger = Logger.getLogger(ReplyServiceImpl.class);
	//该HQL用于查询非回复的回帖, 没有被删除, 并且为指定主题的回复, 以时间正序排列
	private final String replySQL = "from Reply r where if_reply=false " +
			"and if_delete=false and sub_id = ? order by reply_time asc";
	private final int pageSize = 5;
	@Autowired
	@Qualifier("replyDao")
	private ReplyDao replyDao;
	@Autowired
	@Qualifier("subjectDao")
	private SubjectDao subjectDao;
	 
	@Autowired
	@Qualifier("memberDao")
	private MemberDao memberDao;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	@Override
	public PageBean findBySubject(int sub_id, int page){
		Subject sub = subjectDao.get(sub_id);
		if(sub != null){
			return  memberDao.findForPage(replySQL, sub, pageSize, page);
		}
		return null;
	}
	
	
	
	@Override
	public Reply get(Serializable id) {
		return replyDao.get(id);
	}

	@Override
	public List<Reply> getAll() {
		return replyDao.getAll();
	}

	@Override
	public Serializable save(Reply o) {
		return replyDao.save(o);
	}

	@Override
	public void delete(Reply o) {
		replyDao.delete(o);
	}

	@Override
	public void update(Reply o) {
		replyDao.update(o);
	}

	@Override
	public void saveOrUpdate(Reply o) {
		replyDao.saveOrUpdate(o);
	}



	@Override
	public Reply publishReply(String reply_content, boolean if_sign, int sub_id, User user, boolean if_reply, int reply_superid) throws Exception {
		Date now = new Date();
		Subject subject = subjectDao.get(sub_id);
		Reply superReply = null;
		if(reply_superid >= 1){
			superReply = replyDao.get(reply_superid);
		}
		Reply reply = new Reply(reply_content, if_sign, user, now, if_reply, superReply);
		reply.setSub_id(subject);

		replyDao.save(reply);
		
		//主题回复数 + 1, 最后回复时间改为now
		subject.setReply_num(subject.getReply_num() + 1);
		subject.setLast_reply_time(now);
		subject.setLast_reply(reply);
		//分类数 +1
		Category category = subject.getCate_id();
		category.setReply_num(category.getReply_num() + 1);
		
		//用户经验值+1
		userService.updateExp(reply.getUser_id(), +1);
		
		logger.info("发表回复"+reply+"成功！");
		return reply;
	}
	
	@Override
	public void deleteReply(int reply_id, String operUserid) throws Exception{
		Reply reply = get(reply_id);
		reply.setIf_delete(true);
		delete(reply);
		Subject subject = reply.getSub_id();
		Category category = subject.getCate_id();
		
		//删除回复， 主题回复数 -1, 分类数 -1
		if(subject.getReply_num() > 0){
			subject.setReply_num(subject.getReply_num() - 1);
		}
		if(category.getReply_num() > 0){
			category.setReply_num(category.getReply_num() - 1);
		}
		
		//用户经验值-1
		userService.updateExp(reply.getUser_id(), -1);
		logger.info("删除回复"+reply+"成功！");
	}



	@Override
	public Object[] getSubAndPage(int reply_id) throws Exception {
		Reply reply = replyDao.get(reply_id);
		Subject subject = reply.getSub_id();
		int pageNum = 0;
		List<Reply> replys = reply.isIf_reply()? replyDao.findBySubjectNoReply(reply.getReply_superid().getReply_id()) : replyDao.findBySubjectNoReply(subject.getSub_id());
		int totalRow = replys.size();
		
		//TODO 有一个页面尺寸
		int pageSize = 5;
		int currentRow = 0;
		for(int i = 0;i<totalRow; i++){
			Reply tmp = replys.get(i);
			if(tmp.getReply_id() == reply_id){
				currentRow = i + 1;
				break;
			}
		}
		if(currentRow % pageSize == 0){
			pageNum = currentRow / pageSize;
		}else{
			pageNum = currentRow / pageSize + 1;
		}
		System.out.println("当前回复行数："+currentRow + " 总行数："+ totalRow + " 页码："+pageNum);
		return new Object[]{subject, pageNum};
	}
	
}
