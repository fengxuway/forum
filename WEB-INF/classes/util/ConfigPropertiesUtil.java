package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigPropertiesUtil {
	private static Properties p = null;
	private static String filepath = "";
	public final static String ROOTPATH;
	static {
		String url = ConfigPropertiesUtil.class.getResource("/").getPath();
		url = url.replaceAll("%20", "");
		String os_name = System.getProperty("os.name");
		if(os_name.startsWith("Windows")){
			filepath = url.substring(1, url.indexOf("WEB-INF")) + "WEB-INF/classes/config.properties";
		}else{
			filepath = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/classes/config.properties";
		}
//		filepath = url.substring(1, url.indexOf("WEB-INF")) + "WEB-INF/classes/config.properties";
		System.out.println(filepath);
		ROOTPATH = url.substring(1, url.indexOf("WEB-INF") - 1);
		init();
	}
	
	public static void init(){
		try{
			p = new Properties();
			FileInputStream fis = new FileInputStream(filepath);
			p.load(fis);
			System.out.println("ConfigProperties reload success!");
			fis.close();
		}catch(IOException e){
			p = null;
		}
	}
	
	public static String get(String key){
		if(p == null){
			return "";
		}
		try{
			String value = p.getProperty(key);
			if(value == null){
				value = "";
			}
			System.out.println("Key: " + key + " | value: " + value);
			return value;
		}catch(Exception e){
			return "";
		}
	}
}
