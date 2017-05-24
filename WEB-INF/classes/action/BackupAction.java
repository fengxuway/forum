package action;

import java.util.Date;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import service.*;
import util.MyDate;

import com.opensymphony.xwork2.ActionSupport;
import entity.*;

@Controller("backupAction")
@Scope("prototype")
public class BackupAction extends ActionSupport {
	private static final long serialVersionUID = 1263577663367026098L;
	private static final Logger logger = Logger.getLogger(BackupAction.class);

	@Autowired
	@Qualifier("categoryService")
	private CategoryService categoryService;
	@Autowired
	@Qualifier("collegeService")
	private CollegeService collegeService;
	@Autowired
	@Qualifier("majorService")
	private MajorService majorService;
	@Autowired
	@Qualifier("rankService")
	private RankService rankService;
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("secretDialogService")
	private SecretDialogService secretDialogService;
	@Autowired
	@Qualifier("secretContentService")
	private SecretContentService secretContentService;
	@Autowired
	private ForumInfoService forumInfoService;
	
	public String execute(){
		if(categoryService.getAll().size()<=0){
			categoryBackup();
		}
		if(collegeService.getAll().size()<=0){
			collegeBackup();
		}
		if(rankService.getAll().size()<=0){
			rankBackup();
		}
		if(userService.getAll().size()<=0){
			userBackup();
		}
		if(secretDialogService.getAll().size()<=0){
			dialogBackup();
		}
		if(secretContentService.getAll().size()<=0){
			contentBackup();
		}
		if(forumInfoService.getAll().size() <= 0){
			forumInfoBackup();
		}
		
		logger.info("数据已写入数据库");

		return SUCCESS;
	}
	
	private void forumInfoBackup() {
		ForumInfo fi = new ForumInfo();
		fi.setSign("青年人的灌水场所要有情调，有情调的灌水场所才有革新。");
		forumInfoService.save(fi);
	}

	private void contentBackup() {
		SecretContent sc = new SecretContent("我是中国人", true, true, false, false, "A", MyDate.get(2012, 3, 4));
		sc.setDialog_id(secretDialogService.get(1));
		SecretContent sc2 = new SecretContent("我也是中国人", true, true, false, false, "B", MyDate.get(2013, 4, 12));
		sc2.setDialog_id(secretDialogService.get(1));
		SecretContent sc3 = new SecretContent("我喜欢用火狐", true, true, false, false, "A", MyDate.get(2013, 3, 23));
		sc3.setDialog_id(secretDialogService.get(2));
		SecretContent sc4 = new SecretContent("我偏用IE，看你能把我怎么样", true, true, false, false, "B", MyDate.get(2013, 2, 1));
		sc4.setDialog_id(secretDialogService.get(2));
		SecretContent sc5 = new SecretContent("听说你用Chrome是吗", true, true, false, false, "A", MyDate.get(2012, 12, 20));
		sc5.setDialog_id(secretDialogService.get(3));
		
		secretContentService.save(sc);
		secretContentService.save(sc2);
		secretContentService.save(sc3);
		secretContentService.save(sc4);
		secretContentService.save(sc5);
		
	}

	private void dialogBackup() {
		SecretDialog sd = new SecretDialog(userService.get("fengxu"), userService.get("我的火狐"), new Date());
		SecretDialog sd2 = new SecretDialog(userService.get("我的火狐"), userService.get("IE大使"), MyDate.get(2013, 6, 2));
		SecretDialog sd3 = new SecretDialog(userService.get("我的火狐"), userService.get("我爱Chrome"), MyDate.get(2013, 5, 4));
		secretDialogService.save(sd);
		secretDialogService.save(sd2);
		secretDialogService.save(sd3);
		
	}

	private void userBackup(){
		User user = new User("fengxu", "fengxuway@qq.com", "202cb962ac59075b964b07152d234b70", "冯旭", false, "0906014119");
		user.setUuid(UUID.randomUUID().toString().replace("-", ""));
		user.setIdentity(103);
		user.setActive_time(System.currentTimeMillis());
		user.setRank(rankService.findByGrade(1));
		user.setCollege(collegeService.get(6));
		user.setMajor(majorService.get(57));
		userService.save(user);
		
		User user2 =new  User("我的火狐", "fengxuway@qq.com", "202cb962ac59075b964b07152d234b70", "冯旭", false, "0906014119");
		user2.setUuid(UUID.randomUUID().toString().replace("-", ""));
		user2.setMajor(majorService.get(33));
		user2.setCollege(collegeService.get(3));
		user2.setIdentity(5);
		user2.setRank(rankService.findByGrade(1));
		userService.save(user2);

		User user3 =new  User("IE大使", "fengxuway@qq.com", "202cb962ac59075b964b07152d234b70", "冯旭", false, "0906014119");
		user3.setUuid(UUID.randomUUID().toString().replace("-", ""));
		user3.setMajor(majorService.get(49));
		user3.setCollege(collegeService.get(5));
		user3.setIdentity(5);
		user3.setRank(rankService.findByGrade(1));
		userService.save(user3);
		
		
		User user4 =new  User("我爱Chrome", "fengxuway@qq.com", "202cb962ac59075b964b07152d234b70", "冯旭", false, "0906014119");
		user4.setUuid(UUID.randomUUID().toString().replace("-", ""));
		user4.setMajor(majorService.get(83));
		user4.setCollege(collegeService.get(21));
		user4.setIdentity(5);
		user4.setRank(rankService.findByGrade(1));
		userService.save(user4);
	}
	
	//Rank表数据
	public void rankBackup(){
		rankService.save(new Rank(1, 5, "托儿所", "1"));
		rankService.save(new Rank(2, 15, "幼儿园", "1"));
		rankService.save(new Rank(3, 30, "学前班", "1"));
		rankService.save(new Rank(4, 50, "一年级", "2"));
		rankService.save(new Rank(5, 80, "二年级", "2"));
		rankService.save(new Rank(6, 120, "三年级", "3"));
		rankService.save(new Rank(7, 180, "四年级", "3"));
		rankService.save(new Rank(8, 250, "五年级", "4"));
		rankService.save(new Rank(9, 320, "六年级", "4"));
		rankService.save(new Rank(10, 400, "初一年级", "5"));
		rankService.save(new Rank(11, 500, "初二年级", "5"));
		rankService.save(new Rank(12, 650, "初三年级", "6"));
		rankService.save(new Rank(13, 800, "高一年级", "6"));
		rankService.save(new Rank(14, 1000, "高二年级", "7"));
		rankService.save(new Rank(15, 1500, "高三年级", "7"));
		rankService.save(new Rank(16, 2000, "本科生", "8"));
		rankService.save(new Rank(17, 3000, "研究生", "8"));
		rankService.save(new Rank(18, 5000, "博士生", "8"));
	
	}
	
	
	/**
	 * 备份板块分类信息
	 */
	private void categoryBackup(){
		//备份板块
		categoryService.save(new Category("精品区", "收集各版块的精品贴"));
		categoryService.save(new Category("公告栏", "论坛动态，广而告之"));
		categoryService.save(new Category("中北之声", "最新校园广播，最热中北话题"));
		categoryService.save(new Category("灌水Style", "今天你水了吗？"));
		categoryService.save(new Category("求助有你", "你的举手之劳，是对我最大的鼓励"));
		categoryService.save(new Category("校友录", "睡在我上铺的兄弟的那些事"));
		categoryService.save(new Category("话说中北", "我们从太行山上走来"));
	}

	
	/**
	 * 备份学院及专业信息
	 */
	private void collegeBackup() {
		//院系专业设置备份  探测制导与控制技术
		College one = new College(1, "机电工程学院（一院）");
		Major aa = new Major("探测制导与控制技术", one);
		Major ab = new Major("弹药工程与爆炸技术", one);
		Major ac = new Major("地面武器机动工程", one);
		Major ad = new Major("武器系统与发射工程", one);
		Major ae = new Major("兵器科学与技术", one);
		Major af = new Major("火炮自动武器与弹药工程", one);
		Major ag = new Major("兵器发射理论与技术", one);
		Major ah = new Major("机动武器系统工程", one);
		Major ai = new Major("车辆工程", one);
		Major aj = new Major("热能与动力工程", one);
		Major ak = new Major("动力工程及机械学科", one);
		Major al = new Major("汽车检测与维修", one);
		Major am = new Major("飞行器制造工程", one);
		Major an = new Major("飞行器设计与工程", one);
		Major ao = new Major("航空宇航制造工程", one);
		Major ap = new Major("机械制造工艺研究所", one);
		
		
		collegeService.save(one);
		
		majorService.save(aa);
		majorService.save(ab);
		majorService.save(ac);
		majorService.save(ad);
		majorService.save(ae);
		majorService.save(af);
		majorService.save(ag);
		majorService.save(ah);
		majorService.save(ai);
		majorService.save(aj);
		majorService.save(ak);
		majorService.save(al);
		majorService.save(am);
		majorService.save(an);
		majorService.save(ao);
		majorService.save(ap);
		
		
		College two = new College(2, "机械工程与自动化学院（二院）");
		Major ba =  new Major("机械电子工程", two);
		Major bb =  new Major("机械设计制造及其自动化", two);
		Major bc =  new Major("包装工程", two);
		Major bd =  new Major("工业工程", two);
		Major be =  new Major("工业设计", two);
		Major bf =  new Major("过程装备与控制工程系", two);
		Major bg =  new Major("过程装备与控制工程", two);
		Major bh =  new Major("机械工程", two);
		Major bi =  new Major("机械设计及理论", two);
		Major bj =  new Major("控制科学与工程", two);
		Major bk =  new Major("机械设计及理论", two);
		Major bl =  new Major("机械工程及其自动化", two);
		Major bm =  new Major("模式识别与智能系统", two);
		Major bn =  new Major("工程力学", two);
		
		collegeService.save(two);
		majorService.save(ba);
		majorService.save(bb);
		majorService.save(bc);
		majorService.save(bd);
		majorService.save(be);
		majorService.save(bf);
		majorService.save(bg);
		majorService.save(bh);
		majorService.save(bi);
		majorService.save(bj);
		majorService.save(bk);
		majorService.save(bl);
		majorService.save(bm);
		majorService.save(bn);
		
		
		College three = new College(3, "材料科学与工程学院（三院）");
		Major ca = new Major("材料成型及控制工程", three);		
		Major cb = new Major("金属材料工程", three);		
		Major cc = new Major("无机非金属材料工程", three);		
		Major cd = new Major("高分子材料与工程", three);		
		Major ce = new Major("复合材料与工程", three);		
		
		collegeService.save(three);
		majorService.save(ca);
		majorService.save(cb);
		majorService.save(cc);
		majorService.save(cd);
		majorService.save(ce);
		
		College four = new College(4, "化工与环境学院（四院）");
		Major da = new Major("化学工程与工艺", four);
		Major db = new Major("制药工程", four);
		Major dc = new Major("环境工程", four);
		Major dd = new Major("安全工程", four);
		Major de = new Major("特种能源工程与烟火技术", four);
		Major df = new Major("生物工程", four);

		collegeService.save(four);
		majorService.save(da);
		majorService.save(db);
		majorService.save(dc);
		majorService.save(dd);
		majorService.save(de);
		majorService.save(df);
		
		College five = new College(5, "信息与通信工程学院（五院）");
		Major ea = new Major("电子信息科学与技术", five);
		Major eb = new Major("电子信息工程", five);
		Major ec = new Major("生物医学工程", five);
		Major ed = new Major("信息对抗技术", five);
		Major ee = new Major("信息管理与信息系统", five);
		Major ef = new Major("通信工程", five);
		Major eg = new Major("自动化", five);
		Major eh = new Major("电气工程及其自动化", five);
		Major ei = new Major("光信息科学与技术", five);
		Major ej = new Major("光电信息工程", five);
		Major ek = new Major("测控技术与仪器", five);
		
		collegeService.save(five);
		majorService.save(ea);
		majorService.save(eb);
		majorService.save(ec);
		majorService.save(ed);
		majorService.save(ee);
		majorService.save(ef);
		majorService.save(eg);
		majorService.save(eh);
		majorService.save(ei);
		majorService.save(ej);
		majorService.save(ek);
		
		College six = new College(6, "电子与计算机科学技术学院（六院）");
		Major fa = new Major("计算机科学与技术", six);
		Major fb = new Major("电子科学与技术", six);
		Major fc = new Major("微电子学", six);
		Major fd = new Major("网络工程", six);
		Major fe = new Major("教育技术学", six);
		Major ff = new Major("软件工程", six);
		
		collegeService.save(six);
		majorService.save(fa);
		majorService.save(fb);
		majorService.save(fc);
		majorService.save(fd);
		majorService.save(fe);
		majorService.save(ff);
		
		
		College seven = new College(7, "理学院（七院）");
		Major ga = new Major("信息与计算科学", seven);
		Major gb = new Major("数学与应用数学", seven);
		Major gc = new Major("物理学", seven);
		Major gd = new Major("应用化学", seven);
		Major ge = new Major("工程力学", seven);
		Major gf = new Major("土木工程", seven);

		collegeService.save(seven);
		majorService.save(ga);
		majorService.save(gb);
		majorService.save(gc);
		majorService.save(gd);
		majorService.save(ge);
		majorService.save(gf);
		
		College eight = new College(8, "人文社会科学学院（八院）");
		Major ha = new Major("广播电视新闻学", eight);
		Major hb = new Major("英语", eight);
		Major hc = new Major("政治学与行政学", eight);
		Major hd = new Major("法学", eight);
		Major he = new Major("汉语言文学", eight);
		
		collegeService.save(eight);
		majorService.save(ha);
		majorService.save(hb);
		majorService.save(hc);
		majorService.save(hd);
		majorService.save(he);
		
		College nine = new College(9, "经济与管理学院（九院）");
		Major ia = new Major("经济学", nine);
		Major ib = new Major("国际经济与贸易", nine);
		Major ic = new Major("工商管理", nine);
		Major id = new Major("市场营销", nine);
		Major ie = new Major("财务管理", nine);
		Major if_ = new Major("信息管理与信息系统", nine);
		
		collegeService.save(nine);
		majorService.save(ia);
		majorService.save(ib);
		majorService.save(ic);
		majorService.save(id);
		majorService.save(ie);
		majorService.save(if_);
		
		College ten = new College(10, "体育与艺术学院（十院）");
		Major ja = new Major("运动训练", ten);
		Major jb = new Major("社会体育", ten);
		Major jc = new Major("音乐学", ten);
		Major jd = new Major("艺术设计", ten);
		
		collegeService.save(ten);
		majorService.save(ja);
		majorService.save(jb);
		majorService.save(jc);
		majorService.save(jd);
		
		College ruanjian = new College(21, "软件学院（二十一院）");
		Major ra = new Major("软件工程", ruanjian);
		Major rb = new Major("网络工程", ruanjian);
		Major rc = new Major("信息管理与信息系统", ruanjian);
		Major rd = new Major("软件技术", ruanjian);
		Major re = new Major("计算机网络技术", ruanjian);
		Major rf = new Major("动漫设计与制作", ruanjian);
		Major rg = new Major("电脑艺术设计", ruanjian);
		Major rh = new Major("计算机信息管理", ruanjian);
		Major ri = new Major("电子商务", ruanjian);
		Major rj = new Major("物流管理", ruanjian);
		Major rk = new Major("通信技术", ruanjian);
		Major rl = new Major("数控技术", ruanjian);
		Major rm = new Major("模具设计与制造", ruanjian);
		Major rn = new Major("汽车检测与维修", ruanjian);
		
		collegeService.save(ruanjian);
		majorService.save(ra);
		majorService.save(rb);
		majorService.save(rc);
		majorService.save(rd);
		majorService.save(re);
		majorService.save(rf);
		majorService.save(rg);
		majorService.save(rh);
		majorService.save(ri);
		majorService.save(rj);
		majorService.save(rk);
		majorService.save(rl);
		majorService.save(rm);
		majorService.save(rn);
		
		
		collegeService.save(new College(24, "信息商务学院"));
		collegeService.save(new College(25, "后备军官教育学院"));
		collegeService.save(new College(26, "研究生院"));
		collegeService.save(new College(27, "成人教育学院"));

	}

}
