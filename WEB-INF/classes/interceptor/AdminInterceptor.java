package interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import service.impl.ReplyServiceImpl;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

import entity.User;

public class AdminInterceptor implements Interceptor {
	private static final long serialVersionUID = -529326061695570721L;
	private static final Logger logger = Logger.getLogger(AdminInterceptor.class);
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map session = ActionContext.getContext().getSession();
		User user = (User) session.get("user");
		if(user != null){
			String action = invocation.getAction().getClass().getSimpleName();
			if(action.equals("ActionSupport")){
				// 访问的是admin
				// 判断用户权限，return对应的值
				if(user.getIdentity()<1000 && user.getIdentity() > 100){
					return "moder";
				}else if(user .getIdentity() >= 1000 ){
					return "super";
				}else {
					return "error";
				}
			}else if(action.equals("AdminAction")){
				if(user.getIdentity() >= 1000){
					return invocation.invoke();
				}else{
					return "error";
				}
			}else if(action.equals("ModerAction")){
				if(user.getIdentity()>100 && user.getIdentity() < 1000){
					return invocation.invoke();
				}else{
					return "error";
				}
			}else{
				return invocation.invoke();
			}
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
