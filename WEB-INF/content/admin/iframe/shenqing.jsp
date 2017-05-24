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
		totalPage : ${shenqingBean.totalPage},
		currentPage : ${shenqingBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="shenqing">
	<header>申请注册的用户</header>
		<%-- <table>
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
		</table> --%>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="shenqingBean.list.size() == 0">
		<p class="noData">目前还没有申请注册的用户</p>
	</s:if>
	<s:else>
		<table>
			<tr>
				<th style="width: 150px;">用户ID</th>
				<th style="width: 100px;">真实姓名</th>
				<th style="width: 110px;">学号</th>
				<th style="width: 250px;">所在院系</th>
				<th style="width: 180px;">所在专业</th>
				<th>是否同意注册</th>
			</tr>
			
			<s:iterator value="shenqingBean.list" var="subject">
			<tr>
				<td>${user_id}</td>
				<td>${name}</td>
				<td>${student_id}</td>
				<td>${college.college_name}</td>
				<td>${major.major_name}</td>
				<td class="register_button">
					<input type="hidden" name="userid" value="${user_id}" />
					<button type="button">同意</button>&nbsp;
					<button type="button">否定</button></td>
			</tr>
			</s:iterator>
		</table>

		<div class="pagin"></div>
</s:else>
</article>
</body>
</html>
