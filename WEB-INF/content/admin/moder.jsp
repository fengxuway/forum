<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>版主管理页面</title>
<link rel="stylesheet" type="text/css" href="../css/all.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="../css/moder.css">
<link rel="shortcut icon" type="image/x-ico" href="../images/fav.ico">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/moder.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//当加载页面完毕后，根据url中的#指定的li点击
	var href = location.href;
	var li_href;
	if(href.indexOf("#") > 0 ){
		li_href= href.substring(href.indexOf("#")+1, href.length);
		if(li_href.length > 0){
			$("nav > ul > li:eq(" + li_href + ")").click();
		}else{
			$("nav > ul > li:first").click();
		}
	}else{
		$("nav > ul > li:first").click();
	}
});
</script>
</head>

<body>

<!--  
封禁名单
删贴日志
加精日志
置顶日志

 -->

	<header id="header">
		<div id="logo">
			<a href="../index"><img src="../images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title" onClick="$.myPlugin.msgAlert('服务器发生异常，请稍候重试！','error');">中北校友论坛坛后台管理</div>
		<div id="links">
			${sessionScope.user.user_id }&nbsp;&nbsp;<a href="../quit">退出</a>
		</div>
	</header>

	<nav>
		<ul>
			<li><a href="#0">封禁名单</a></li>
			<li><a href="#1">删贴列表</a></li>
			<li><a href="#2">加精列表</a></li>
			<li><a href="#3">置顶列表</a></li>
			<li><a href="#4">系统日志</a></li>
		</ul>
	</nav>

	<!-- 封禁名单 -->
	<article id="main0">
		<iframe src="iframe/fengjin" scrolling="no" frameborder="0"></iframe>
		
	</article>

	<!-- 删贴列表 -->
	<article id="main1">
		<iframe src="iframe/shantie" scrolling="no" frameborder="0"></iframe>
	</article>

	<!-- 加精列表 -->
	<article id="main2">
		<iframe src="iframe/jiajing" scrolling="no" frameborder="0"></iframe>
	</article>

	<!-- 置顶列表 -->
	<article id="main3">
		<iframe src="iframe/zhiding" scrolling="no" frameborder="0"></iframe>

		
	</article>

	<!-- 系统日志 -->
	<article id="main4">
		<!-- 删贴、封禁、加精、置顶 -->
		<div id="main4_nav">
			<ul>
				<li class="main4_current">删贴日志</li>
				<li>封禁日志</li>
				<li>加精日志</li>
				<li>置顶日志</li>
			</ul>
		</div>

		<!-- 删贴日志 -->
		<div id="main4_content0" class="main4_content">
			<!-- 时间、操作人、操作、作者、标题 -->
			<iframe src="iframe/diaryShantie" scrolling="no" frameborder="0"></iframe>
		</div>
		<!-- 封禁日志 -->
		<div id="main4_content1" class="main4_content" style="display:none;">
			<iframe src="iframe/diaryFengjin" scrolling="no" frameborder="0"></iframe>
		</div>
		<!-- 加精日志 -->
		<div id="main4_content2" class="main4_content" style="display:none;">
			<!-- 时间、操作人、操作、作者、标题 -->
			<iframe src="iframe/diaryJiajing" scrolling="no" frameborder="0"></iframe>
		</div>
		<!-- 置顶日志 -->
		<div id="main4_content3" class="main4_content" style="display:none;">
			<!-- 时间、操作人、操作、作者、标题 -->
			<iframe src="iframe/diaryZhiding" scrolling="no" frameborder="0"></iframe>
		</div>

	</article>


</body>
</html>
