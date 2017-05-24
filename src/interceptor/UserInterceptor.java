package interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import service.impl.ReplyServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import entity.User;

public class UserInterceptor implements Interceptor {
	private static final long serialVersionUID = 1563414320234643398L;
	private static final Logger logger = Logger.getLogger(UserInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if(user!=null){
			logger.info("user.user_id: "+user.getUser_id());
			return invocation.invoke();
		}else{
			logger.info("您还没有登录！");
			return "error";
		}
	}


	@Override
	public void destroy() {
	}
	
	@Override
	public void init() {
	}
}
