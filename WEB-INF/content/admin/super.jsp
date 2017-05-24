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
<link rel="stylesheet" type="text/css" href="../css/super.css">
<link rel="shortcut icon" type="image/x-ico" href="../images/fav.ico">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/super.js"></script>
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
	<header id="header">
		<div id="logo">
			<a href="../index" target="_blank"><img src="../images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title">中北校友论坛坛后台管理</div>
		<div id="links">
			<a href="../user/personal?r=21" target="_blank" style="color:#333;">${sessionScope.user.user_id }</a>&nbsp;&nbsp;<a href="../quit">退出</a>
		</div>
	</header>

	<nav>
		<ul>
			<li class="current"><a href="#0">版块管理</a></li>
			<li><a href="#1">申请会员</a></li>
			<li><a href="#2">会员管理</a></li>
			<li><a href="#3">封禁名单</a></li>
			<li><a href="#4">系统日志</a></li>
		</ul>
	</nav>

	<!-- 吧务候选 -->
	<article id="main0">
		<iframe src="iframe/bankuai" scrolling="no" frameborder="0" style="height:440px;"></iframe>
	</article>

	<!-- 申请会员 -->
	<article id="main1">
		<iframe src="iframe/shenqing" scrolling="no" frameborder="0"></iframe>
	</article>
	<!-- 会员列表，以及封禁、解封操作 -->
	<article id="main2">
		<iframe src="iframe/huiyuan" scrolling="no" frameborder="0"></iframe>
	</article>

	<!-- 封禁名单 -->
	<article id="main3">
		<iframe src="iframe/fengjin" scrolling="no" frameborder="0"></iframe>
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
