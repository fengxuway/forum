package util;

import static util.MyRegex.regex;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyRegex {
	/**
	 * 根据指定正则表达式和字符串验证是否匹配成功
	 * @param regex 正则表达式
	 * @param matcher 字符串
	 * @return 匹配成功返回true
	 */
	public static boolean regex(String regex, String matcher){
//		return Pattern.compile(regex).matcher(matcher);		
		Matcher m = Pattern.compile(regex).matcher(matcher);
		if(m.find() && m.group().equals(matcher)){
	 		return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 验证字符串是否为email
	 * @param email 待验证的邮箱
	 * @return 匹配成功返回true
	 */
	public static boolean regexEmail(String email){
		return regex("[\\w.-]+@\\w+\\..{2,6}", email);
	}
	/**
	 * 验证字符串是否符合userid
	 * @param userid 待验证的userid
	 * @return 匹配成功返回true
	 */
	public static boolean regexUserid(String userid){
		Pattern p = Pattern.compile("[\u2E80-\u9FFF]");
		Matcher m = p.matcher(userid);
//		s = m.replaceAll("12");
//		s.replaceAll("[\u2E80-\u9FFF]", "22");
		while(m.find()){
			userid = userid.replaceAll(m.group(), "11");
		}
		return regex("[\\w_-]{3,14}", userid);
	}
	

}
