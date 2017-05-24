package service.impl;

import java.io.Serializable;
import java.util.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import org.apache.log4j.Logger;
import dao.*;
import entity.*;
import service.*;
@Service("secretDialogService")
public class SecretDialogServiceImpl implements SecretDialogService {
	private static final Logger logger = Logger.getLogger(SecretDialogServiceImpl.class);
	@Autowired
	@Qualifier("secretDialogDao")
	private SecretDialogDao secretDialogDao;
	
	
	
	
	@Override
	public SecretDialog get(Serializable id) {
		return secretDialogDao.get(id);
	}
 
	@Override
	public List<SecretDialog> getAll() {
		return secretDialogDao.getAll();
	}

	@Override
	public Serializable save(SecretDialog o) {
		return secretDialogDao.save(o);
	}

	@Override
	public void delete(SecretDialog o) {
		secretDialogDao.delete(o);
	}

	@Override
	public void update(SecretDialog o) {
		secretDialogDao.update(o);
	}

	@Override
	public void saveOrUpdate(SecretDialog o) {
		secretDialogDao.saveOrUpdate(o);
	}

	@Override
	public List<SecretDialog> getUserDialogs(String userid) {
		return secretDialogDao.getUserDialogs(userid);
	}

}
