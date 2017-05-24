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
		totalPage : ${topSubjectBean.totalPage},
		currentPage : ${topSubjectBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="zhiding">
	<header>置顶列表</header>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="topSubjectBean.list.size() == 0">
		<p class="noData">目前还没有被置顶的主题</p>
	</s:if>
	<s:else>
	
		<!-- 贴子标题、作者、发表时间、操作人、加精时间、去除加精 -->
		<table>
			<tr>
				<th style="width: 320px;">帖子标题</th>
				<th style="width: 150px;">作者</th>
				<th style="width: 100px;">所在分类</th>
				<th style="width: 145px;">发表时间</th>
				<th style="width: 90px;">回复数</th>
				<th>取消置顶</th>
			</tr>
			
			<s:iterator value="topSubjectBean.list" var="subject">
			<tr>
				<td><a target="_blank" href="../../single?sub_id=${sub_id}" title="${sub_title}">${sub_title}</a></td>
				<td><a target="_blank" href="../../p?id=${author.userid_encode}">${author.user_id}</a></td>
				<td>${cate_id.cate_name}</td>
				<td><s:date name="sub_time" format="yyyy-MM-dd HH:mm"/></td>
				<td>${reply_num}</td>
				<!-- <td>2012-5-20 20:00</td> -->
				<td><a class="removeTop" data="${sub_id}" href="javascript:void(0);">取消置顶</a></td>
			</tr>
			</s:iterator>
		</table>

		<div class="pagin"></div>
</s:else>
</article>
</body>
</html>
