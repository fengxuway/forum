<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>校友论坛后台管理</title>
<link rel="stylesheet" type="text/css" href="../css/all.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="../css/admin.css">
<link rel="shortcut icon" type="image/x-ico" href="../images/fav.ico">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/admin.js"></script>
</head>

<body>

	<!--  
版主候选
会员管理
封禁名单
删贴日志
加精日志
置顶日志

 -->

	<header id="header">
		<div id="logo">
			<a href="../index.html"><img src="../images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title" onClick="$.myPlugin.msgAlert('服务器发生异常，请稍候重试！','error');">中北校友论坛坛后台管理</div>
		<div id="links">
			独孤觅雪&nbsp;&nbsp;<a href="#">退出</a>
		</div>
	</header>

	<nav>
		<ul>
			<li class="current">版主候选</li>
			<li>申请会员</li>
			<li>会员管理</li>
			<li>封禁名单</li>
			<li>删贴列表</li>
			<li>加精列表</li>
			<li>置顶列表</li>
			<li>系统日志</li>
		</ul>
	</nav>

	<!-- 吧务候选 -->
	<article id="main0">
		<!-- 申请人ID、等级、发贴数、时间、是否同意申请 -->
		<header>申请人列表</header>
		<table>
			<tr>
				<th style="width: 60px;">勾选</th>
				<th style="width: 200px;">申请人ID</th>
				<th style="width: 60px;">等级</th>
				<th style="width: 180px;">精品数/发贴数</th>
				<th style="width: 220px;">申请时间</th>
				<th>是否同意</th>
			</tr>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td><a href="#">我的亲娘在哪里</a></td>
				<td>3</td>
				<td>2/10</td>
				<td>2012-3-23 12:00</td>
				<td><button>同意</button>&nbsp;
					<button>否定</button></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td><a href="#">我的亲娘在哪里</a></td>
				<td>3</td>
				<td>2/10</td>
				<td>2012-3-23 12:00</td>
				<td><button>同意</button>&nbsp;
					<button>否定</button></td>
			</tr>
		</table>
		<div class="select_all_div">
			<label><input type="checkbox" name="" id="select_all">全选</label>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="">【选中否定】</a>
		</div>
		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>

		<header>论坛当前坛主</header>
		<p class="explain">
			<a id="quit_admin">【退出坛主身份】</a>
		</p>
		<div id="admin_list">
			<ul>
				<li><a href=""><img src="../images/head.png" alt=""><br>独孤觅雪</a></li>
				<li><a href=""><img src="../images/head.png" alt=""><br>独孤觅雪</a></li>
				<li><a href=""><img src="../images/head.png" alt=""><br>独孤觅雪</a></li>
			</ul>
		</div>

	</article>

	<!-- 申请会员 -->
	<article id="main1">
		<header>申请注册的用户</header>
		<table>
			<tr>
				<th style="width: 150px;">用户ID</th>
				<th style="width: 100px;">真实姓名</th>
				<th style="width: 110px;">学号</th>
				<th style="width: 250px;">所在院系</th>
				<th style="width: 180px;">所在专业</th>
				<th>是否同意注册</th>
			</tr>
			<s:if test="userNoApply.size>0">
				<s:iterator value="userNoApply" id="user">
					<tr>
						<td><s:property value="user_id" /></td>
						<td><s:property value="name" /></td>
						<td><s:property value="student_id" /></td>
						<td><s:property value="college.college_name" /></td>
						<td><s:property value="major.major_name" /></td>
						<td class="register_button"><input type="hidden"
							name="userid" value="<s:property value="user_id"/>" />
							<button type="button">同意</button>&nbsp;
							<button type="button">否定</button></td>
					</tr>
				</s:iterator>
			</s:if>
			<s:else>
				<tr>
					<td colspan="6">目前暂无新注册的用户</td>
				</tr>
			</s:else>


		</table>
	</article>
	<!-- 会员列表，以及封禁、解封操作 -->
	<article id="main2">
		<header>会员列表</header>
		<div class="search_user">
			<form action="">
				<input type="text" name="" id="input_userid" class="username">&nbsp;
				<input type="submit" value="搜索">
			</form>
		</div>
		<!-- 选择按钮、头像、ID、等级、精品数/主题数、注册时间、封禁 -->
		<table>
			<tr>
				<th style="width: 60px;">勾选</th>
				<th style="width: 120px;">头像</th>
				<th style="width: 180px;">用户ID</th>
				<th style="width: 60px;">等级</th>
				<th style="width: 150px;">精品/主题数</th>
				<th style="width: 210px;">注册时间</th>
				<th>封禁</th>
			</tr>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪</a></td>
				<td><span class="degree_id">1</span></td>
				<td>2/23</td>
				<td>2012-5-20 20:00</td>
				<td><p class="forbid"></p></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪</a></td>
				<td><span class="degree_id">1</span></td>
				<td>2/23</td>
				<td>2012-5-20 20:00</td>
				<td><p class="forbid"></p></td>
			</tr>
			<tr>
				<td><input type="checkbox" name="" id=""></td>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪</a></td>
				<td><span class="degree_id">1</span></td>
				<td>2/23</td>
				<td>2012-5-20 20:00</td>
				<td><p class="unforbid"></p></td>
			</tr>
		</table>
		<div class="select_all_div">
			<label><input type="checkbox" name="" id="select_all">全选</label>&nbsp;&nbsp;&nbsp;&nbsp;
			<a href="">【选中封禁】</a>&nbsp;&nbsp;&nbsp;<a href="">【选中解封】</a>
		</div>
		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>
	</article>

	<!-- 封禁名单 -->
	<article id="main3">
		<header>封禁名单</header>
		<div class="search_user">
			<form action="">
				<input type="text" name="" value="请输入封禁的ID" id="input_forbid_userid"
					class="username">&nbsp; <input type="submit" value="搜索">
			</form>
		</div>
		<!-- 、头像、ID、被禁天数、理由、操作人、操作时间、解封 -->
		<table>
			<tr>
				<th style="width: 100px;">头像</th>
				<th style="width: 160px;">ID</th>
				<th style="width: 70px;">被封天数</th>
				<th style="width: 120px;">封禁理由</th>
				<th style="width: 160px;">操作人</th>
				<th style="width: 160px;">操作时间</th>
				<th>解封</th>
			</tr>
			<tr>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>1</td>
				<td style="max-width: 200px">恶意放水恶意放水恶意放水恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><p class="unforbid"></p></td>
			</tr>
			<tr>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪</a></td>
				<td>1</td>
				<td>恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><p class="unforbid"></p></td>
			</tr>
			<tr>
				<td><a href="" class="user_head_img"><img
						src="../images/head.png" alt=""></a></td>
				<td><a href="">独孤觅雪</a></td>
				<td>1</td>
				<td>恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><p class="unforbid"></p></td>
			</tr>
		</table>

		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>
	</article>

	<!-- 删贴列表 -->
	<article id="main4">
		<header>删贴列表</header>
		<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
		<table>
			<tr>
				<th style="width: 220px;">帖子标题</th>
				<th style="width: 150px;">作者</th>
				<th style="width: 100px;">删除理由</th>
				<th style="width: 150px;">操作人</th>
				<th style="width: 170px;">操作时间</th>
				<th>恢复</th>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td style="max-width: 200px">恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">点击恢复</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td style="max-width: 200px">恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">点击恢复</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td style="max-width: 200px">恶意放水</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">点击恢复</a></td>
			</tr>
		</table>

		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>
	</article>

	<!-- 加精列表 -->
	<article id="main5">
		<header>加精列表</header>
		<!-- 贴子标题、作者、发表时间、操作人、加精时间、去除加精 -->
		<table>
			<tr>
				<th style="width: 220px;">帖子标题</th>
				<th style="width: 150px;">作者</th>
				<th style="width: 145px;">发表时间</th>
				<th style="width: 150px;">操作人</th>
				<th style="width: 145px">操作时间</th>
				<th>去除加精</th>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除加精</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除加精</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除加精</a></td>
			</tr>
		</table>

		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>
	</article>

	<!-- 置顶列表 -->
	<article id="main6">
		<header>置顶列表</header>
		<!-- 贴子标题、作者、发表时间、操作人、置顶时间、去除置顶 -->
		<table>
			<tr>
				<th style="width: 220px;">帖子标题</th>
				<th style="width: 150px;">作者</th>
				<th style="width: 145px;">发表时间</th>
				<th style="width: 150px;">操作人</th>
				<th style="width: 145px;">操作时间</th>
				<th>去除置顶</th>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除置顶</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除置顶</a></td>
			</tr>
			<tr>
				<td><a href="" title="我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。。">我在希腊神殿等成一尊...</a></td>
				<td><a href="">独孤觅雪独孤觅雪</a></td>
				<td>2012-5-20 20:00</td>
				<td>独孤觅雪</td>
				<td>2012-5-20 20:00</td>
				<td><a href="">去除置顶</a></td>
			</tr>
		</table>

		<div class="pagin">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="#">上一页</a></li>
				<li><a href="#">1</a></li>
				<li><a href="#">2</a></li>
				<li><a href="#" class="a_current" onClick="return false;">3</a></li>
				<li><a href="#">4</a></li>
				<li><a href="#">5</a></li>
				<li><a href="#">下一页</a></li>
				<li><a href="#">末页</a></li>
			</ul>
		</div>
	</article>

	<!-- 系统日志 -->
	<article id="main7">
		<!-- 删贴、封禁、加精、置顶 -->
		<div id="main7_nav">
			<ul>
				<li class="main7_current">删贴日志</li>
				<li>封禁日志</li>
				<li>加精日志</li>
				<li>置顶日志</li>
			</ul>
		</div>

		<!-- 删贴日志 -->
		<div id="main7_content0" class="main7_content">
			<!-- 时间、操作人、操作、作者、标题 -->
			<table>
				<tr>
					<th style="width: 150px;">时间</th>
					<th style="width: 160px;">操作人</th>
					<th style="width: 110px;">操作</th>
					<th style="width: 160px;">作者</th>
					<th>标题</th>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>删除</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>删除</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>删除</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
			</table>
		</div>
		<!-- 封禁日志 -->
		<div id="main7_content1" class="main7_content">
			<!-- 时间、操作人、操作、作者、封禁原因 -->
			<table>
				<tr>
					<th style="width: 150px;">时间</th>
					<th style="width: 160px;">操作人</th>
					<th style="width: 110px;">操作</th>
					<th style="width: 160px;">作者</th>
					<th>封禁原因</th>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>封禁3天</td>
					<td>婉君</td>
					<td>恶意灌水</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>封禁3天</td>
					<td>婉君</td>
					<td>恶意灌水</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>封禁3天</td>
					<td>婉君</td>
					<td>恶意灌水</td>
				</tr>
			</table>
		</div>
		<!-- 加精日志 -->
		<div id="main7_content2" class="main7_content">
			<!-- 时间、操作人、操作、作者、标题 -->
			<table>
				<tr>
					<th style="width: 150px;">时间</th>
					<th style="width: 160px;">操作人</th>
					<th style="width: 110px;">操作</th>
					<th style="width: 160px;">作者</th>
					<th>标题</th>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>加精</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>取消加精</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>取消加精</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
			</table>
		</div>
		<!-- 置顶日志 -->
		<div id="main7_content3" class="main7_content">
			<!-- 时间、操作人、操作、作者、标题 -->
			<table>
				<tr>
					<th style="width: 150px;">时间</th>
					<th style="width: 160px;">操作人</th>
					<th style="width: 110px;">操作</th>
					<th style="width: 160px;">作者</th>
					<th>标题</th>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>置顶</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>取消置顶</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
				<tr>
					<td>2012-3-12 12:00</td>
					<td>独孤觅雪</td>
					<td>取消置顶</td>
					<td>婉君</td>
					<td>我在希腊神殿等成一尊石像</td>
				</tr>
			</table>
		</div>

	</article>


</body>
</html>
