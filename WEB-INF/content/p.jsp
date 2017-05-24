<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/all.css">
<link rel="stylesheet" type="text/css" href="css/p.css">
<link rel="stylesheet" type="text/css" href="css/jquery.myPlugin.css">
<link rel="shortcut icon" type="image/x-ico" href="images/fav.ico">
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/p.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
});
</script>
<title>${puser.user_id }的主页</title>
</head>

<body>
	<!-- header部分 -->
	<header>
		<div id="logo">
			<a href="p?id=中北"><img src="images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title">中北校友论坛</div>
		<div id="links">
			<s:if test="#session.user != null">
				<a href="user/personal?r=21" style="color:#000;">${sessionScope.user.user_id }</a>&nbsp;&nbsp;<a href="quit">退出</a>
			</s:if>
			<s:else>
				<a href="register?req=21">登陆</a>
			</s:else>
		</div>
	</header>

	<!-- 个人信息部分 -->
	<article id="info">
		<div id="img">
			<a href="#"><img src="images/head_img/${puser.photo }" width="100"
				height="100" alt=""></a>
		</div>
		<div id="data">
			${puser.user_id }<br> <s:if test="user.sex">女</s:if><s:else>男</s:else><br>
			<div class="degree">
				<span class="degree_name">${puser.rank.rank_name }</span> 
				<span class="degree_id" style="background:url(images/s${puser.rank.img }.gif) no-repeat;">${puser.rank.grade }</span>
			</div>
			
			<!-- 隐藏域，用于存放当前用户的信息 -->
			<input type="hidden" name="current_user_id" value="${puser.user_id}"	/>
			<input type="hidden" name="session_user_id" value="${sessionScope.user.user_id}"/>
		</div>
		<div id="option">
			<a class="addMention" href="javascript:void(0);"><s:if test="ifAtte">已关注</s:if><s:else>+关注</s:else></a> 
			
			<a <s:if test="session.user == null"> onclick="$.myPlugin.msgAlert('您尚未登录！');" </s:if><s:else> href="user/secret?id=${puser.user_id}" </s:else>>私信他</a>
		</div>
	</article>

	<!-- 主体部分 -->
	<section>
		<!-- 第一个ul选项卡导航部分 -->
		<header>
			<ul>
				<li class="current">他在中北</li>
			</ul>
		</header>

		
		<!-- 他在中北部分 -->
		<div id="main0" class="main">
			<!-- 我在论坛中的动态 列表导航部分 -->
			<ul>
				<li class="main_current">主题帖</li>
				<li class="l"></li>
				<li>回复帖</li>
				<li class="l"></li>
				<li id="sss">粉丝</li>
				<li class="l"></li>
				<li>他的关注</li>
			</ul>

			<div class="line">
				<div class="active_line"></div>
				<div class="active_line"></div>
			</div>

			<!-- #relate0部分：发表过的主题 -->
			<div id="relate0" class="relate" >
				<input type="hidden" name="loadNum" value="1"/>
				<s:iterator value="pSubjectBean.list" id="subject">
					<article>
						<header>发表主题</header>
						<div class="theme_head">
							<a href="single?sub_id=${sub_id}">${sub_title }</a> 
							<a href="list?cate_id=${cate_id.cate_id }" class="theme_type">【${cate_id.cate_name }】</a>
						</div>
						<time><s:date name="sub_time" format="yyyy-MM-dd HH:mm"/></time>
					</article>
				</s:iterator>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="pSubjectBean.list.size == 0">
						该用户暂时没有发表主题
					</s:if>
					<s:elseif test="pSubjectBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
				<!-- <article>
					<header>发表主题</header>
					<div class="theme_head">
						<a href="#">我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。</a> <a href=""
							class="theme_type">【求助有你】</a>
					</div>
					<time>2012-12-23 18:30</time>
				</article>
				-->
			</div>

			<!-- #relate1 ： 我的回复列表 -->
			<div id="relate1" class="relate" style="display: none;">
				<input type="hidden" name="loadNum" value="0"/>
				
				
				<div class="more"><a href="javascript:void(0);">点击查看更多</a></div>
				
				<!--
				<article>
					<header>发表回复</header>
					<div class="theme_head">
						<a href="#">变成望妻石了。</a>
						<div class="theme_title">
							<a href="">我在希腊神殿等成一尊石像，而你，终抱着折断的翅膀奔来。</a> <a href=""
								class="theme_type">【求助有你】</a>
						</div>
						<time>2012-12-23 18:30</time>
					</div>

				</article>
				-->
			</div>

			<!-- #relate2： 我的粉丝 -->
			<div id="relate2" class="relate" style="display:none;">
				<input type="hidden" name="loadNum" value="0"/>
				<ul>
					<!-- <li><a href="#"><img src="images/head.png" alt=""></a>
						<div>
							<a href="" class="atte_name">独孤觅雪</a> <a href="" class="atte_add"></a>
						</div></li>
					 -->

				</ul>
				<div class="more"><a href="javascript:void(0);">点击查看更多</a></div>
			</div>

			<!-- #relate3：我的关注 -->
			<div id="relate3" class="relate" style="display: none;">
				<input type="hidden" name="loadNum" value="0"/>
				<ul>
					<!-- <li><a href="#"><img src="images/head.png" alt=""></a>
						<div>
							<a href="" class="atte_name">独孤觅雪</a> <a href=""
								class="atte_remove"></a>
						</div></li>
					-->
				</ul>
				<div class="more"><a href="javascript:void(0);">点击查看更多</a></div>
			</div>
		</div>

	</section>

	<aside>
		<p style="margin:20px 12px; text-align:center;">
			粉丝：<a style="font-size:24px;" href="javascript:void(0);">${atteMeCount }</a>&nbsp;&nbsp;&nbsp;&nbsp;
			关注：<a style="font-size:24px;" href="javascript:void(0);">${myAtteCount }</a>
		</p>
	</aside>

</body>
</html>
