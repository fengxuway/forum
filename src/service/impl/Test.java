package service.impl;

import entity.*;
import static org.junit.Assert.*;

import java.util.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import entity.User;

import service.AttentionService;
import service.SecretContentService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:app-common.xml")
public class Test {
	@Resource
	private AttentionService attentionService;
	@Resource
	private SecretContentService secretContentService;

	@Before
	public void setUp() throws Exception {
	}

	@org.junit.Test
	public void testGetMyAtte() {
		/*List<User> set = attentionService.getMyAtte("fengxu");*/
//		System.out.println("我的关注："+Arrays.toString(set.toArray()));
	}

	@org.junit.Test
	public void testGetAtteMe() throws Exception {
		/*List<User> set = attentionService.getAtteMe("fengxu");
		for(User tmp: set){
			System.out.println(tmp.getExp());
			System.out.println(tmp.getRank().getRank_name());
			System.out.println(tmp.getName());
			System.out.println(tmp.isSex());
		}*/
		List<SecretContent> list = secretContentService.getUnreadContent("我的火狐", 3);
		for(SecretContent sc: list){
			System.out.println(sc);
		}
	}

}
