package util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpSession;  
  
import org.apache.commons.lang.StringUtils;  

import service.UserService;
import entity.User;  
  
/** 
 * cookie的增加、删除、查询 
 */  
public class CookieUtil{  
    public static final String USER_COOKIE = "user";  
  
    // 添加一个cookie  
    public Cookie addCookie(User user) {  
        Cookie cookie = new Cookie(USER_COOKIE, user.getUserid_encode() + ","  
                + user.getPassword());  
        System.out.println("添加cookie");  
        cookie.setMaxAge(60 * 60 * 24 * 14);// cookie保存两周  
        return cookie;  
    }  
  
    // 得到cookie  
    public boolean getCookie(HttpServletRequest request, UserService userService) {  
        Cookie[] cookies = request.getCookies();  
        System.out.println("cookies: " + cookies);  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                System.out.println("cookie "+cookie.getName()+":"+cookie.getValue());
                if (USER_COOKIE.equals(cookie.getName())) {  
                    String value = cookie.getValue();  
                    if (StringUtils.isNotBlank(value)) {  
                        String[] split = value.split(",");  
                        String username = split[0];  
                        String password = split[1];  
                        try {
							username = URLDecoder.decode(username, "utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
                        
                        User user = userService.login(username, password);
                        if (user != null) {  
                            HttpSession session = request.getSession();  
                            session.setAttribute("user", user);// 添加用户到session中  
                            return true;  
                        }  
                    }  
                }  
            }  
        }  
        return false;  
    }  
  
    // 删除cookie  
    public Cookie delCookie(HttpServletRequest request) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (USER_COOKIE.equals(cookie.getName())) {  
                    cookie.setValue("");  
                    cookie.setMaxAge(0);  
                    return cookie;  
                }  
            }  
        }  
        return null;  
    }  
}  
