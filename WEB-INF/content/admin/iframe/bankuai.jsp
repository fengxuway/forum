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
<script type="text/javascript" src="../js/bankuai.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("div.pagin").pageNum({
		totalPage : ${bankuaiBean.totalPage},
		currentPage : ${bankuaiBean.currentPage},
		currentClass : "a_current",
		otherClass : "",
		ifFirst:false,
		ifLast:false
	});
});
</script>
</head>

<body>
<article id="bankuai">
	<div id="main4_nav">
		<ul>
			<li class="main4_current">板块管理</li>
			<li>论坛信息</li>
		</ul>
	</div>
	<div id="main4_content0" class="main4_content">
	<header style="height: 30px; margin-left:20px; margin-bottom:10px; line-height: 30px; font-family: '宋体'; font-size: 12px;">板块管理中，可点击单元格修改数据，点击保存按钮提交</header>
	<!-- 贴子标题、作者、发表时间、操作人、删除理由、删除时间、恢复 -->
	<s:if test="bankuaiBean.list.size() == 0">
		<p class="noData">目前还没有申请注册的用户</p>
	</s:if>
	<s:else>
		<table id="table" style="width:860px;">
			<tr>
				<th style="width: 75px;">板块ID</th>
				<th style="width: 130px;">名称</th>
				<th style="width: 310px;">简介</th>
				<th style="width: 160px;">版主</th>
				<th>保存</th>
				<th style="width: 60px;">删除</th>
			</tr>
			
			<s:iterator value="bankuaiBean.list" var="subject">
			<s:if test="cate_name!='精品区'">
				<tr>
					<td data="${cate_id}">${cate_id}</td>
					<td class="text" data="${cate_name }">${cate_name}</td>
					<td class="text" data="${cate_info }">${cate_info}</td>
					<%-- <td>${subject_num}</td> --%>
					<td class="text" data="${cate_admin.user_id}">${cate_admin.user_id}</td>
					<td class="save_button">
						<button type="button">保存</button>&nbsp;
						<button type="button">取消</button></td>
					<td><a href="javascript:void(0);" class="deleteCate" >删除</a></td>
				</tr>
			</s:if>
			<s:else>
				<tr>
					<td data="${cate_id}">${cate_id}</td>
					<td data="${cate_name }">${cate_name}</td>
					<td data="${cate_info }">${cate_info}</td>
					<%-- <td>${subject_num}</td> --%>
					<td data="${cate_admin.user_id}">${cate_admin.user_id}</td>
					<td class="save_button">
						精品区不可修改
					<td>-</td>
				</tr>
			</s:else>
			</s:iterator>
			
			
			<tr id="addTr" style="display:none;">
				<td></td>
				<td class='text'></td>
				<td class='text'></td>
				<td class='text'></td>
				<td class='save_button'><button id="addNewCate" type='button' style="width:87px;">保存</button></td>
				<td><a class='deleteNewCate' href='javascript:void(0);'>删除</a></td>
			</tr>
		</table>
		
		<div class="pagin" style="width:430px;"></div>
		<div style="height: 26px; margin: 20px 0 10px 680px; width: 152px;"><a class="addCate" href="javascript:void(0);">添加版块</a></div>
		
</s:else>
	</div>
	<div id="main4_content1" class="main4_content" style="display:none;">
	
		<header style=" float: left; margin:0 0 10px 20px; padding: 0 40px; border: 1px solid #FFC973; background: #FEFFDB; color: #333;height: 30px; line-height: 30px; font-family: '宋体'; font-size: 12px;">下面是论坛信息简介，请点击修改</header>
		<div id="forum_info" style="float:left;background: none repeat scroll 0 0 #D1E4F4; clear: both; font-family:'微软雅黑'; font-size: 20px; line-height: 50px; margin: 20px; padding: 0 20px; width: 660px;"
		><input type="text" style="background:none;border:none;font-size:20px; font-family:'微软雅黑'; width:650px; height:30px;" maxlength="32" value="青年人的灌水场所要有情调，有情调的灌水场所才有革新。"/></div>
		<button id="forum_info_save" type="button">保存</button>
	</div>
</article>
</body>
</html>
