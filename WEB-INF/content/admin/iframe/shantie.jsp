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
		totalPage : ${delSubjectBean.totalPage},
		currentPage : ${delSubjectBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="shantie">
	<header>删贴列表</header>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="delSubjectBean.list.size() == 0">
		<p class="noData">目前还没有被删除的主题</p>
	</s:if>
	<s:else>
	<table>
		<tr>
			<th style="">帖子标题</th>
			<th style="width: 150px;">作者</th>
			<th style="width: 100px;">所在分类</th>
			<th style="width: 160px;">发表时间</th>
			<th style="width: 80px;">点击恢复</th>
			<th style="width: 80px;">永久删除</th>
		</tr>
		<s:iterator value="delSubjectBean.list" var="subject">
			<tr>
				<td><a href="../../single?sub_id=${sub_id}" target="_blank" title="查看被删主题：${sub_title}">${fn:substring(sub_title,0,20)}</a></td>
				<td><a href="../../p?id=${author.userid_encode}" target="_blank" title="查看该作者主页">${author.user_id}</a></td>
				<td>${cate_id.cate_name}</td>
				<td><s:date name="sub_time" format="yyyy-MM-dd HH:mm"/></td>
				<td><a class="regain" data="${sub_id}" href="javascript:void(0);">恢复</a></td>
				<td><a class="delete" data="${sub_id}" href="javascript:void(0);">删除</a></td>
			</tr>
		</s:iterator>
	</table>

	<div class="pagin"></div>
	</s:else>
</article>

</body>
</html>
