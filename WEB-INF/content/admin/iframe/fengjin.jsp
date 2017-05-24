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
		totalPage : ${forbidListBean.totalPage},
		currentPage : ${forbidListBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="fengjin">
	<header>封禁列表</header>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="forbidListBean.list.size() == 0">
		<p class="noData">目前还没有您封禁的用户</p>
	</s:if>
	<s:else>
	
		<!-- 贴子标题、作者、发表时间、操作人、加精时间、去除加精 -->
		<table>
			<tr>
				<th style="width: 170px;">被封禁的用户</th>
				<th style="width: 130px;">封禁天数</th>
				<th style="width: 200px;">封禁时间</th>
				<th style="width: 170px;">操作人</th>
				<th>从封禁移除</th>
			</tr>
			
			<s:iterator value="forbidListBean.list" var="subject">
			<tr>
				<td><a target="_blank" href="../../p?id=${user_id.userid_encode}">${user_id.user_id}</a></td>
				<td>${days }</td>
				<td><s:date name="operate_time" format="yyyy-MM-dd HH:mm"/></td>
				<td>${operate_id.user_id}</td>
				<td><a class="removeForbid" data="${forbid_id}" href="javascript:void(0);">移除封禁</a></td>
			</tr>
			</s:iterator>
		</table>

		<div class="pagin"></div>


</s:else>
</article>
</body>
</html>
