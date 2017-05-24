package action;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import service.UserService;

import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import entity.*;
@Controller("moderAction")
@Scope("prototype") 
public class ModerAction extends ActionSupport {
	private static final long serialVersionUID = -7319531862561656923L;
	private static final Logger logger = Logger.getLogger(ModerAction.class);
	
}
