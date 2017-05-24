<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Shantie</title>
<link rel="stylesheet" type="text/css" href="../../css/all.css">
<link rel="stylesheet" type="text/css" href="../../css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="../../css/moder_iframe.css">
<script type="text/javascript" src="../../js/jquery.js"></script>
<script type="text/javascript" src="../../js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="../../js/jquery.pageNum.js"></script>
<script type="text/javascript" src="../js/moder_iframe.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("div.pagin").pageNum({
		totalPage : ${huiyuanBean.totalPage},
		currentPage : ${huiyuanBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<input type="hidden" name="session_user" value="${sessionScope.user.user_id }"/>
<article id="huiyuan">
	<header>会员列表</header>
		<div class="search_user">
			<form action="huiyuan">
				<input type="text" name="kw" value="${kw }" id="input_userid" class="username">&nbsp;
				<input type="submit" value="搜索">
			</form>
		</div>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="huiyuanBean.list.size() == 0">
		<p class="noData">目前还没有已注册的用户</p>
	</s:if>
	<s:else>
		<table>
			<tr>
				<th style="width: 170px;">用户ID</th>
				<th style="width: 80px;">头像</th>
				<th style="width: 100px;">真实姓名</th>
				<th style="width: 120px;">学号</th>
				<th style="width: 200px;">所在专业</th>
				<th>封禁用户</th>
				<th>删除用户</th>
			</tr>
			
			<s:iterator value="huiyuanBean.list" var="subject">
			<tr>
				<td><a target="_blank" href="../../p?id=${user_id }">${user_id}</a></td>
				<td><img src="../../images/head_img/${photo }"/></td>
				<td>${name}</td>
				<td>${student_id}</td>
				<td>${major.major_name}</td>
				<td class="register_button">
					<p <s:if test="identity == 0">class="unforbid" </s:if><s:else> class="forbid"</s:else> data="${user_id}"></p>
				</td>
				<td><a href="javascript:void(0);" class="deleteUser" data="${user_id }">删除</a></td>
			</tr>
			</s:iterator>
		</table>

		<div class="pagin"></div>
</s:else>
</article>
</body>
</html>
