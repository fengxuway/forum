package util;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

public class URLEncoding {
	public static String toLocalString(HttpServletRequest request, String param){
		String referer = request.getHeader("Referer");
		String agent = request.getHeader("User-Agent");
		String id = request.getParameter(param);
		
		
		System.out.println("解码前："+id);
		System.out.println("User-Agent:"+agent);
		try {
			if(agent.indexOf("Ubuntu") >= 0 || agent.indexOf("Linux") >= 0){
				id = new String(id.getBytes("iso8859-1"), "utf-8");
			}else if(agent.indexOf("Windows") >= 0){
				//火狐
				if(agent.indexOf("Firefox") >= 0){
					if(referer == null){
						id = new String(id.getBytes("iso8859-1"), "gb2312");
					}else{
						id = new String(id.getBytes("iso8859-1"), "utf-8");
					}
				}
				//IE
				/*else if(agent.indexOf("MSIE") >= 0){
					
					id = new String(id.getBytes("iso8859-1"), "utf-8");				
				//Chrome	
				}else if(agent.indexOf("Chrome") >= 0){
					id = new String(id.getBytes("iso8859-1"), "utf-8");		
				
				//Opera
				}else if(agent.indexOf("Opera") >= 0){
					id = new String(id.getBytes("iso8859-1"), "utf-8");				
				}*/
				else{
					id = new String(id.getBytes("iso8859-1"), "utf-8");				
				}
			}
			
			System.out.println("解码后："+id);
			return id;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}	
	}

}
