package action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.*;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import pagination.*;
import service.*;
import util.*;
import entity.*;

import com.opensymphony.xwork2.*;


@Controller("personalUpdateAction")
@Scope("prototype")
public class PersonalUpdateAction extends ActionSupport {
	private static final long serialVersionUID = 8139043889864157216L;
	private static final Logger logger = Logger.getLogger(PersonalUpdateAction.class);
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	
	
	//当前用户
	private User user;
	//当前密码
	private String currentPswd;
	//新密码
	private String newPswd;
	//头像
	private File photo;
	//头像文件名
	private String photoFileName;
	//头像类型
	private String photoContentType;
	//签名
	private String sign;
	
	//头像上传路径
	private String savePath;
	
	//个人资料更改信息
	private String updateMsg;
	
	
	@SuppressWarnings("resource")
	public String execute(){
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("user");
		
			//如果用户上传了头像图片，上传头像，修改用户头像数据
			if(photoFileName != null){
				//头像使用UUID作为文件名
				String oldFileName = user.getPhoto();
				
				photoFileName = UUID.randomUUID().toString().replace("-", "") + photoFileName.substring(photoFileName.lastIndexOf("."),  photoFileName.length());
				
				try {
					FileOutputStream fos = new FileOutputStream(getSavePath() + "/" + getPhotoFileName());
					
					FileInputStream fis = new FileInputStream(getPhoto());
					byte[] buffer = new byte[1024];
					int len = 0;
					while((len = fis.read(buffer)) > 0){
						fos.write(buffer, 0 , len);
					}
					fos.close();
					fis.close();
					
					//删除旧的头像文件(默认的头像不被删除)
					File oldFile = new File(getSavePath() + "/" + oldFileName);
					System.out.println("正在删除"+getSavePath() + "/" + oldFileName+" 文件是否存在？"+oldFile.exists());
					if(!oldFileName.equals("default_head_img.jpg") && oldFile.exists()){
						System.out.println(oldFile.delete());
					}
					
					System.out.println("头像保存到："+getSavePath() + "/" + getPhotoFileName());
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				user.setPhoto(photoFileName);
			}
			
			//如果用户填写了签名档，写数据库
			if(sign != null){
				user.setSign(sign);
			}
			
			//如果用户填写修改密码表单，更改密码
			if(currentPswd != null && !currentPswd.equals("") && newPswd != null
					&& !newPswd.equals("") && !newPswd.equals(currentPswd)){
				if(MyMD5.md5(currentPswd).equals(user.getPassword())){
					user.setPassword(MyMD5.md5(newPswd));
				}
			}
			try{
				userService.update(user);
				updateMsg = URLEncoder.encode("恭喜，您的资料更新成功！","utf-8");
			}catch(Exception e){
				try {
					updateMsg = URLEncoder.encode("您的资料更新失败！请检查您的数据并重试。", "utf-8");
				} catch (UnsupportedEncodingException e1) {
					e1.printStackTrace();
				}
			}
			session.put("user", user);
			
			return SUCCESS;
			
		
	}

	public String getCurrentPswd() {
		return currentPswd;
	}


	public void setCurrentPswd(String currentPswd) {
		this.currentPswd = currentPswd;
	}


	public String getNewPswd() {
		return newPswd;
	}


	public void setNewPswd(String newPswd) {
		this.newPswd = newPswd;
	}


	public File getPhoto() {
		return photo;
	}


	public void setPhoto(File photo) {
		this.photo = photo;
	}


	public String getPhotoFileName() {
		return photoFileName;
	}


	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}


	public String getPhotoContentType() {
		return photoContentType;
	}


	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}


	public String getSign() {
		return sign;
	}


	public void setSign(String sign) {
		this.sign = sign;
	}


	public String getSavePath() {
		String path = ServletActionContext.getServletContext().getRealPath("/" + savePath);
		logger.info("头像保存路径："+ path);
		return path;
	}


	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}

	public String getUpdateMsg() {
		return updateMsg;
	}

	public void setUpdateMsg(String updateMsg) {
		this.updateMsg = updateMsg;
	}

}
