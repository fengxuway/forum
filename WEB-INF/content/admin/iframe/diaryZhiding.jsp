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
		totalPage : ${diaryZhidingBean.totalPage},
		currentPage : ${diaryZhidingBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="d_shantie">
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="diaryZhidingBean.list.size() == 0">
		<p class="noData">置顶日志为空！</p>
	</s:if>
	<s:else>
	
		<!-- 贴子标题、作者、发表时间、操作人、加精时间、去除加精 -->
		<table>
			<tr>
				<th style="width: 150px;">时间</th>
				<th style="width: 160px;">操作人</th>
				<th style="width: 110px;">操作</th>
				<th style="width: 160px;">作者</th>
				<th>标题</th>
			</tr>
			<s:iterator value="diaryZhidingBean.list" var="subject">
			<tr>
				<td><s:date name="time" format="yyyy-MM-dd HH:mm"/></td>
				<td>${operate_id }</td>
				<td><s:if test="type == 1">置顶</s:if><s:else>取消置顶</s:else></td>
				<td>${author_id }</td>
				<td>${title }</td>
			</tr>
			</s:iterator>
		</table>

		<div class="pagin"></div>
</s:else>
</article>
</body>
</html>
