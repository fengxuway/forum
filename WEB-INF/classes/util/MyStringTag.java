package util;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class MyStringTag extends SimpleTagSupport {
	private String value;
	private String regex;
	private String replacement = "";
	private int from;
	private int to;
	
	@Override
	public void doTag() throws JspException, IOException {
		System.out.println("正则"+regex);
		System.out.println("内容："+value);
		value = value.replaceAll(regex, replacement);
		if(to != 0){
			to = to > value.length() ? value.length() : to;
			value = value.substring(from, to);
		}
		if(value.trim().equals("")){
			value = "【图片】";
		}
		getJspContext().getOut().write(value);
	}
	
	
	
	
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getReplacement() {
		return replacement;
	}
	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}
	public int getFrom() {
		return from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	public int getTo() {
		return to;
	}
	public void setTo(int to) {
		this.to = to;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}
	
}
