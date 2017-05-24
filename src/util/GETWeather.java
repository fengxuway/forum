package util;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class GETWeather {
	// method 要调用的webservie方法名，paramName请求参数名，paramValue请求参数值
	private static String makeSoapRequest(String method, String paramName,
			String paramValue) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" ");
		sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" ");
		sb.append("xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
		// 构造请求内容，这里需要将XXX替换为webservice的地址
		sb.append("<soap:Body><" + method
				+ " xmlns=\"http://WebXml.com.cn/\"><" + paramName + ">"
				+ paramValue + "</" + paramName + "></" + method
				+ "></soap:Body>");
		sb.append("</soap:Envelope>");
		return sb.toString();
	}

	private static InputStream getSoapInputStream(String method,
			String paramName, String paramValue) throws Exception {
		try {
			String soap = makeSoapRequest(method, paramName, paramValue);
			if (soap == null) {
				return null;
			}
			// 需要替换成webservice的完整地址
			URL url = new URL(
					"http://www.webxml.com.cn/WebServices/WeatherWebService.asmx");
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length",
					Integer.toString(soap.length()));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction",
					"http://WebXml.com.cn/getWeatherbyCityName");
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(soap);
			osw.flush();
			osw.close();
			InputStream is = conn.getInputStream();
			return is;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String getWeather(String city) {
		try {
			Document doc;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = getSoapInputStream("getWeatherbyCityName",
					"theCityName", city);
			doc = db.parse(is);
			NodeList nl = doc.getElementsByTagName("string");
			StringBuffer sb = new StringBuffer();
			for (int count = 0; count < nl.getLength(); count++) {
				Node n = nl.item(count);
				if (n==null ||n.getFirstChild()==null ||n.getFirstChild().equals("") ||n.getFirstChild().getNodeValue().equals("查询结果为空！")) {
					sb = new StringBuffer("#");
					break;
				}
				sb.append(n.getFirstChild().getNodeValue() + "#\n");
			}
			is.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		String[] res = getContent("太原");
		System.out.println("============");
		for(String s: res){
			System.out.println(s);
		}
	}
	public static String[] getContent(String city){
		try {
			String[] result = {"","","","",""};
			System.out.println("开始获取天气");
			String tmp = getWeather(city);
			System.out.println("-----------");
			if(tmp.length()<=10){
				System.out.println("获取失败，再次获取中。。。");
				tmp = getWeather(city);
				if(tmp.length()<10)throw new RuntimeException();
			}
			String[] str = tmp.split("#\n|\n");
			if(str.length<18){
				return null;
			}
			int i =0;
			for(String s:str){
				System.out.println(i+++":"+s);
			}
			Pattern p = Pattern.compile("今日天气实况：.*?风向/风力：(.*)；湿度：(.*)?；空气质量");
			Matcher m = p.matcher(str[10]);
			result[0] = str[5];
			result[1] = str[6].split(" ")[1];
			result[2] = str[8];
			if(m.find()){
				result[3] = m.group(1);
				result[4] = m.group(2);
			}
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}