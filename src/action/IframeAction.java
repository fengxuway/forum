package action;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import pagination.PageBean;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.util.*;

import service.*;
import util.URLEncoding;
import entity.*;
@Controller("iframeAction")
@Scope("prototype") 
public class IframeAction extends ActionSupport {
	private static final long serialVersionUID = 2842044029044737074L;
	private static final Logger logger = Logger.getLogger(IframeAction.class);
	//尚未同意注册的用户
	private List<User> userNoApply = new ArrayList<User>();
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("forbidListService")
	private ForbidListService forbidListService;
	@Autowired
	@Qualifier("subjectService")
	private SubjectService subjectService;
	@Autowired
	private DiaryService diaryService;
	@Autowired
	private CategoryService categoryService;
	
	
	// 存储被封禁的用户ForbidList
	private PageBean forbidListBean;
	// 存储已经被删除的主题
	private PageBean delSubjectBean;
	// 存储加精的主题
	private PageBean perfectSubjectBean;
	// 存储置顶的主题
	private PageBean topSubjectBean;
	// 存储删除主题的日志
	private PageBean diaryDeleteBean;
	// 存储精品的主题日志
	private PageBean diaryJiajingBean;
	// 存储置顶操作的日志
	private PageBean diaryZhidingBean;
	// 存储封禁日志
	private PageBean diaryFengjinBean;
	
	// 申请会员的bean分页列表
	private PageBean shenqingBean;
	// 会员管理
	private PageBean huiyuanBean;
	// 板块分类bean
	private PageBean bankuaiBean;
	// 当前页码
	private int page;
	// 搜索的关键词
	private String kw;
	
	
	

	public String fengjin(){
		User uu = (User) ActionContext.getContext().getSession().get("user");
		forbidListBean = forbidListService.getForbidList(page, uu);	
		return SUCCESS;
	}
	
	public String shantie(){
		User uu = (User) ActionContext.getContext().getSession().get("user");
		delSubjectBean = subjectService.getDelSubject(page, uu.getIdentity());
		
		return SUCCESS;
	}
	
	public String jiajing(){
		User uu = (User) ActionContext.getContext().getSession().get("user");
		perfectSubjectBean = subjectService.getPerfectSubject(page, uu.getIdentity());
		return SUCCESS;
	}
	
	public String zhiding(){
		User uu = (User) ActionContext.getContext().getSession().get("user");
		setTopSubjectBean(subjectService.getTopSubject(page, uu.getIdentity()));
		return SUCCESS;
	}
	
	public String diaryShantie(){
		diaryDeleteBean = diaryService.getDeleteSubject(page);
		return SUCCESS;
	}
	
	public String diaryJiajing(){
		diaryJiajingBean = diaryService.getJiajingSubject(page);
		return SUCCESS;
	}
	
	public String diaryZhiding(){
		diaryZhidingBean = diaryService.getZhidingSubject(page);
		return SUCCESS;
	}
	
	public String diaryFengjin(){
		diaryFengjinBean = diaryService.getFengjin(page);
		return SUCCESS;
	}
	
	/**
	 * 用于会员申请页面，查找所有未同意注册的用户
	 * @return
	 */
	public String shenqing(){
		shenqingBean = userService.findNoApply(page);
		return SUCCESS;
	}
	
	/**
	 * 会员管理，可查看用户信息
	 * @return
	 */
	public String huiyuan(){
		System.out.println("KW:"+kw);
		if(kw != null){
			kw = URLEncoding.toLocalString(ServletActionContext.getRequest(), "kw");
		}
		huiyuanBean = userService.findAllUsers(page, kw);
		return SUCCESS;
	}
	
	/**
	 * 版块管理
	 * @return
	 */
	public String bankuai(){
		bankuaiBean = categoryService.getAllByPage(page);
		return SUCCESS;
	}
	

	public PageBean getDiaryDeleteBean() {
		return diaryDeleteBean;
	}

	public void setDiaryDeleteBean(PageBean diaryDeleteBean) {
		this.diaryDeleteBean = diaryDeleteBean;
	}

	public List<User> getUserNoApply() {
		return userNoApply;
	}

	public void setUserNoApply(List<User> userNoApply) {
		this.userNoApply = userNoApply;
	}


	public PageBean getDelSubjectBean() {
		return delSubjectBean;
	}

	public void setDelSubjectBean(PageBean delSubjectBean) {
		this.delSubjectBean = delSubjectBean;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageBean getPerfectSubjectBean() {
		return perfectSubjectBean;
	}

	public void setPerfectSubjectBean(PageBean perfectSubjectBean) {
		this.perfectSubjectBean = perfectSubjectBean;
	}

	public PageBean getTopSubjectBean() {
		return topSubjectBean;
	}

	public void setTopSubjectBean(PageBean topSubjectBean) {
		this.topSubjectBean = topSubjectBean;
	}

	public PageBean getDiaryJiajingBean() {
		return diaryJiajingBean;
	}

	public void setDiaryJiajingBean(PageBean diaryJiajingBean) {
		this.diaryJiajingBean = diaryJiajingBean;
	}
	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}
	public PageBean getDiaryZhidingBean() {
		return diaryZhidingBean;
	}

	public void setDiaryZhidingBean(PageBean diaryZhidingBean) {
		this.diaryZhidingBean = diaryZhidingBean;
	}

	public PageBean getDiaryFengjinBean() {
		return diaryFengjinBean;
	}

	public void setDiaryFengjinBean(PageBean diaryFengjinBean) {
		this.diaryFengjinBean = diaryFengjinBean;
	}

	public PageBean getForbidListBean() {
		return forbidListBean;
	}

	public void setForbidListBean(PageBean forbidListBean) {
		this.forbidListBean = forbidListBean;
	}

	public PageBean getShenqingBean() {
		return shenqingBean;
	}

	public void setShenqingBean(PageBean shenqingBean) {
		this.shenqingBean = shenqingBean;
	}

	public PageBean getHuiyuanBean() {
		return huiyuanBean;
	}

	public void setHuiyuanBean(PageBean huiyuanBean) {
		this.huiyuanBean = huiyuanBean;
	}

	public PageBean getBankuaiBean() {
		return bankuaiBean;
	}

	public void setBankuaiBean(PageBean bankuaiBean) {
		this.bankuaiBean = bankuaiBean;
	}

}
