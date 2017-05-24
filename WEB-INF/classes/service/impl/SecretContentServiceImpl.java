package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import dao.*;
import entity.*;
import service.*;
import sun.swing.SwingUtilities2.Section;
@Service("secretContentService")
public class SecretContentServiceImpl implements SecretContentService {
	private static final Logger logger = Logger.getLogger(SecretContentServiceImpl.class);
	@Autowired
	@Qualifier("secretContentDao")
	private SecretContentDao secretContentDao;
	@Autowired
	@Qualifier("secretDialogDao")
	private SecretDialogDao secretDialogDao;
	@Autowired
	@Qualifier("userDao")
	private UserDao userDao;
	
	
	
	@Override
	public SecretContent get(Serializable id) {
		return secretContentDao.get(id);
	}
 
	@Override
	public List<SecretContent> getAll() {
		return secretContentDao.getAll();
	}

	@Override
	public Serializable save(SecretContent o) {
		return secretContentDao.save(o);
	}

	@Override
	public void delete(SecretContent o) {
		secretContentDao.delete(o);
	}

	@Override
	public void update(SecretContent o) {
		secretContentDao.update(o);
	}

	@Override
	public void saveOrUpdate(SecretContent o) {
		secretContentDao.saveOrUpdate(o);
	}

	@Override
	public List<SecretContent> getUserContents(String userid, int dialog_id) {
		List<SecretContent> result = secretContentDao.getUserContents(userid, dialog_id);
		// 遍历list，将当前用户设为已读
		for(SecretContent sc: result){
			System.out.println(sc);
			if(sc.getDialog_id().getUserA().getUser_id().equals(userid)){
				sc.setIf_Aread(true);
			}else{
				sc.setIf_Bread(true);
			}
			update(sc);
			
		}
		return result;
	}

	@Override
	public SecretContent publishSecret(String userid1, String userid2, String content)
			throws Exception {
		// 查找这两个用户的对话是否已经建立
		SecretDialog sd = secretDialogDao.findByUsers(userid1, userid2);
		// 当前时间
		Date now = new Date();
		
		// 如果存在，直接插入信息
		if(sd != null){
			// 如果用户userid1（即发表私信回复）为A
			if(sd.getUserA().getUser_id().equals(userid1)){
				SecretContent sc = new SecretContent(content, true, true, true, false, "A", now);
				sc.setDialog_id(sd);
				save(sc);
				return sc;
			}
			// 如果用户userid2为A
			else{
				SecretContent sc = new SecretContent(content, true, true, false, true, "B", now);
				sc.setDialog_id(sd);
				save(sc);
				return sc;
				
			}
		}
		//如果不存在，建立两人对话，再插入数据
		else{
			SecretDialog new_sd = new SecretDialog(userDao.get(userid1), userDao.get(userid2), now);
			secretDialogDao.save(new_sd);
			SecretContent sc = new SecretContent(content, true, true, true, false, "A", now);
			sc.setDialog_id(new_sd);
			save(sc);
			return sc;				
			
		}
	}

	@Override
	public void deleteUserContent(int content_id, String user_id)
			throws Exception {
		logger.info("您正在删除用户"+user_id+"的内容："+content_id);
		SecretContent sc = get(content_id);
		if(sc.getDialog_id().getUserA().getUser_id().equals(user_id)){
			logger.info("用户A");
			// 如果用户代号为A
			sc.setIf_Avisible(false);
		}else{
			logger.info("用户B");
			sc.setIf_Bvisible(false);
		}
		// 如果双方都不可见，则删除该content
		if(!sc.isIf_Avisible() && !sc.isIf_Bvisible()){
			logger.info("删除内容中。。。");
			// 解除父子关系
			SecretDialog sd = sc.getDialog_id();
			sd.getSecretContents().remove(sc);
			delete(sc);
			if(sd.getSecretContents().size() == 0){
				secretDialogDao.delete(sd);
			}
		}
	}

	@Override
	public void deleteUserDialog(int dialog_id, String user_id) throws Exception {
		SecretDialog sd = secretDialogDao.get(dialog_id);
		Set<SecretContent> scs = sd.getSecretContents();
		
		
		if(sd.getUserA().getUser_id().equals(user_id)){
			// 如果当前用户为A
			for(SecretContent sc: scs){
				sc.setIf_Avisible(false);
				if(!sc.isIf_Avisible() && !sc.isIf_Bvisible()){
					delete(sc);
				}
			}
		}else{
			for(SecretContent sc: scs){
				sc.setIf_Bvisible(false);
				if(!sc.isIf_Avisible() && !sc.isIf_Bvisible()){
					delete(sc);
				}
			}
		}
		
		// TODO 删除对话。。（用定时器）
		
	}

	@Override
	public List<SecretContent> getUnreadContent(String user_id, int dialog_id)
			throws Exception {
		List<SecretContent> result = secretContentDao.getUnreadContent(user_id, dialog_id);
		for(SecretContent sc: result){
			if(user_id.equals(sc.getDialog_id().getUserA().getUser_id())){
				sc.setIf_Aread(true);
			}else{
				sc.setIf_Bread(true);
			}
		}
		return result;
	}

}
