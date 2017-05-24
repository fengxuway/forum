package action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class T {
	public static void main(String[] args) throws UnsupportedEncodingException {
		
		String s= "<p style='color:#ccc'>安全了非的<img src='sdkfjdslf'/>搜房为欧普诶阿婆返回破爱你i哦哦</p>";
		String reg = "<[^i].*?>";
		s=s.replaceAll(reg, "");
		System.out.println(s);
		
		/*String s = "硒鼓国@fengxu冯旭 @kdsfl; @-dfkls_ @qqqq.com";
//		Pattern pattern = Pattern.compile("^回复\\s+([\\w\u2E80-\u9FFF-]{3,16})?：");
		Pattern pattern = Pattern.compile("@([\\w\u2E80-\u9FFF-]{3,16})[\\s;,]+");
		Matcher m = pattern.matcher(s);
		while(m.find()){
			System.out.println("------------");
			System.out.println(m.group(1));
		}
		*/
		
		
		
		/*String content = "<p style=\"line-height:16px;\">"+
			"<img src=\"/forum/ueditor/dialogs/attachment/fileTypeImages/icon_mv.gif\" />" +
			"<a href=\"/forum/ueditor/jsp/upload/20130511/38651368285123681.flv\">1.flv</a>"+
			"</p><p><br /></p>";
		String content2 = new String(content);
		
		String regex = "<a\\s+href=\"(/forum/ueditor/jsp/upload/\\d+/\\d+\\.flv)\">.*?\\.flv</a>";
		
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(content);
		while(m.find()){
			System.out.println("------------");
			String a = m.group();
			String fileName = m.group(1);
			content2 = content2.replaceFirst(a, a+getFlvStr(fileName));
			m.replaceFirst(fileName);
		}
		System.out.println(content2);
		System.out.println("=======\n"+content);*/
		
	}
	
	private static String getFlvStr(String fileName){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		
		String result = "<div id=\"" +uuid+ "\" style=\"width:400px; height:280px; margin:0px; border:solid 5px #50031a;color:#ffffff;\" ></div>"+
				"<script type=\"text/javascript\">"+
					"var s" +uuid+ " = new SWFObject(\"images/FlvPlayer201002.swf\",\"playlist\",\"400\",\"280\",\"7\");"+
					"s" +uuid+ ".addParam(\"allowfullscreen\",\"true\");"+
					"s" +uuid+ ".addVariable(\"autostart\",\"false\");"+
					"s" +uuid+ ".addVariable(\"image\",\"image/flash5.jpg\");"+
					"s" +uuid+ ".addVariable(\"file\",\"" +fileName+ "\");"+
					"s" +uuid+ ".addVariable(\"width\",\"400\");"+
					"s" +uuid+ ".addVariable(\"height\",\"280\");"+
					"s" +uuid+ ".write(\"" +uuid+ "\");"+
				"</script>";
		
		return result;
	}

}
