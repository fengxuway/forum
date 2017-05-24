<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Fengjin</title>
<link rel="stylesheet" type="text/css" href="../../css/all.css">
<link rel="stylesheet" type="text/css" href="../../css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="../../css/moder_iframe.css">
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/jquery.myPlugin.js"></script>
</head>

<body>

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

</body>
</html>
