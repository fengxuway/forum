<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<% 
	String basepath = request.getContextPath(); 
	String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ basepath; 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>快速注册</title>
<link rel="stylesheet" type="text/css" href="css/all.css" media="all">
<link rel="stylesheet" type="text/css" href="css/prettySelect.css">
<link rel="stylesheet" type="text/css" href="css/register.css" media="all">
<link rel="stylesheet" type="text/css" href="css/jquery.myPlugin.css" media="all" />
<link rel="stylesheet" type="text/css" href="css/password_strength.css" />
<link rel="shortcut icon" type="image/x-ico" href="images/fav.ico">
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.prettySelect.js"></script>
<script type="text/javascript" src="js/jquery.passwordStrength.js"></script>
<script type="text/javascript" src="js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/register.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//根据请求的页面内容显示及隐藏网页部分div
	var req = parseInt('<s:property value="req" />');
	if(req != null && req >= 10){
		var reqF = parseInt(req / 10);
		var reqS = req % 10;
		if(reqF == 1){
			$("#pswd_back").hide();
			if(reqS == 1){
				$("#main").show().siblings("div").hide();
			}else{
				$("#main"+reqS).show().siblings("div").hide();
			}			
		}else if(reqF == 2){
			$("#main").hide();
			$("#pswd_back").hide();
			$("#main2").hide();
			$("#main3").hide();
			$("#main_login").show();
			$("#log_button").addClass("current").siblings("div.current").removeClass("current");
		}else if(reqF == 3){
			//找回密码
			$("#main").hide();
			$("#main2").hide();
			$("#main3").hide();
			$("#main_login").hide();
			if(reqS == 1){
				$("#pswd_back").show();
			}else if (reqS == 2){
				$("#pswd_reset").show().siblings("div").hide();
			}
			$("#bac_button").addClass("current").siblings("div.current").removeClass("current");
		}
	}	
});
</script>
</head>

<body>

	<header>
		<div id="logo">
			<a href="index"><img src="images/logo2.png" alt="中北校友论坛"></a>&nbsp;
			中北校友论坛
		</div>
		<a id="return_home" href="<%=path %>/">&lt;&lt;返回论坛首页</a>
	</header>
	<article>
		<aside>
			<div id="reg_button" class="current">快速注册</div>
			<div id="log_button">立即登录</div>
			<div id="bac_button">找回密码</div>
		</aside>
		<div id="main" class="main" style="display: block;">
			<header></header>
			<form action="" style="position: relative;">
				<table>
					<tr>
						<td class="label"><label for="email">邮箱：</label></td>
						<td><input id="email" type="text" name="email"
							placeholder="请输入邮箱"></td>
						<td class="tip"><span></span><small>请输入你的常用邮箱，以便找回密码</small></td>
					</tr>
					<tr>
						<td class="label"><label for="userid">用户ID：</label></td>
						<td><input id="userid" type="text" name="userid"
							placeholder="输入您的用户名"></td>
						<td class="tip"><span></span><small>使用中文、英文或数字作为您的ID</small></td>
					</tr>
					<tr>
						<td class="label"><label for="pswd">密码：</label></td>
						<td><input id="pswd" type="password" name="pswd"
							placeholder="请输入密码"></td>
						<td class="tip"><span></span><small>密码强度</small>
							<div id="passwordStrengthDiv" class="is0"></div></td>
					</tr>
					<tr>
						<td class="label"><label for="psure">确认密码：</label></td>
						<td><input id="psure" type="password" name="psure"
							placeholder="请再输入一遍密码"></td>
						<td class="tip"><span></span><small>密码输入正确</small></td>
					</tr>
				</table>
				<button id="next" type="button"></button>
			</form>
		</div>

		<div id="main2" class="main" style="display: none;">
			<header></header>
			<div
				style="height: 30px; margin: 10px 0 0 180px; color: #888; font-size: 14px;">注：该表单的信息（除性别外）不会公布</div>
			<form action="">
				<table style="margin-top: 0;">
					<tr>
						<td class="label"><label for="name">真实姓名：</label></td>
						<td><input id="name" type="text" name="name"></td>
						<td class="tip"><span class="tishi"></span><small>请输入你的真实姓名</small></td>
					</tr>
					<tr>
						<td class="label">性别：</td>
						<td class="sexForm"><label><input id="sex"
								type="radio" name="sex" value="male" checked="checked">&nbsp;男</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<label><input id="sex" type="radio" name="sex"
								value="female">&nbsp;女</label></td>
						<td class="tip"><span class="zhengque"></span><small>&nbsp;</small></td>
					</tr>
					<tr>
						<td class="label"><label for="student_id">学号：</label></td>
						<td><input id="student_id" type="text" name="student_id"></td>
						<td class="tip"><span class="tishi"></span><small>&nbsp;</small></td>
					</tr>
					<tr>
						<td class="label"><label for="major">所在专业：</label></td>
						<td class="selectForm"><select name="college"
							id="collegeSelect">
								<option value="">请选择院系</option>
								<s:iterator value="collegeList" id="college">
									<option value="<s:property value="college_id"/>">
										<s:property value="college_name" />
									</option>
								</s:iterator>
						</select>
							<div id="beforeMajorSelect" style="display: none; height: 5px;"></div>

						</td>
						<td class="tip"><span class="zhengque"></span><small>&nbsp;</small>
						</td>
					</tr>
				</table>

				<div id="register_submit_content">
					<div id="register_shade">
						<div id="register_shade_left"></div>
						<div id="register_shade_right"></div>
					</div>
					<button id="previous" type="button"></button>
					<button id="register_shenqing" type="button"></button>
				</div>
				<p id="register_msg"></p>
			</form>

		</div>

		<div id="main3" style="display: none;">
			<header></header>
			<div class="helloImg"></div>
			<div class="msg">
				请耐心等待审核，审核通过后会通过邮箱通知您：<br>
				<span id="main3_email">&nbsp;</span>
			</div>
			<div class="msg2">请在收到邮件的一个礼拜内登录邮箱，按照邮箱中的提示完成激活</div>
		</div>
		<div id="main4" style="display: none;">
			<header></header>
			<div class="helloImg"></div>
			<div class="msg">
				恭喜您注册成功：
				<s:property value="#session.user.user_id" />
			</div>
			<p id="mylinks">
				<a href="index">点击进入论坛首页</a> <br> <a href="user/personal">我的个人中心</a>
			</p>
		</div>
		<div id="main5" style="display: none;">
			<header></header>
			<div class="yihanImg"></div>
			<div class="msg">很遗憾，您注册失败！</div>
			<p id="mylinks">
				<a href="index">点击进入论坛首页</a>
			</p>
		</div>


		<div id="main_login" style="display: none;">
			<form action="#" id="login_form">
				<table>
					<tr>
						<td><label for="username">用户名：</label></td>
						<td><input id="username" type="text" name="username"
							placeholder="输入用户名或邮箱"></td>
					</tr>
					<tr>
						<td><label for="password">密码：</label></td>
						<td><input id="password" type="password" name="password"
							placeholder="输入您的密码"></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<div id="login_submit_content">
								<div id="login_shade"></div>
								<button type="button"></button>
							</div>
						</td>
					</tr>
					<tr style="font-size: 14px; text-align: center;">
						<td>&nbsp;</td>
						<td id="login_msg"></td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 找回密码 -->
		<div id="pswd_back" style="display: none;">
			<form action="register_validateCW">
				<table>
					<tr>
						<td><label for="pswd_email">邮箱：</label></td>
						<td style="width:250px;"><input id="pswd_email" type="text" name="email"
							placeholder="输入您的激活邮箱" value="${email }"></td>
						<td><button type="button" id="getCheckWord" style="margin:0;"></button></td>
						
					</tr>
					<tr>
						<td><label for="pswd_cw">验证码：</label></td>
						<td><input type="text" id="pswd_cw" name="checkWord"
							placeholder="请输入6位验证码"></td>
						<td style="font-size:12px;">&nbsp;</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<button type="button" id="back_next" style="margin: 20px 0 0 2px;"></button>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr style="font-size: 14px; text-align: center;">
						<td>&nbsp;</td>
						<td id="back_msg"></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 找回密码第二步，填写新密码 -->
		<!-- 仅当req==32时方显示 -->
		<s:if test="req == 32">
		<div id="pswd_reset" style="display: none;" class="main">
			<form action="register_updatePswd" method="post">
				<table>
					<tr>
						<td><label for="pswd_new">设置新密码：</label></td>
						<td style=""><input id="pswd_new" type="password" name="pswd_new"
							placeholder="请设置您的新密码"></td>
						<td class="tip"><span></span><small></small></td>
						
					</tr>
					<tr>
						<td><label for="pswd_new_sure">确认新密码：</label></td>
						<td><input type="password" id="pswd_new_sure" name="pswd_new_sure"
							placeholder="请再次输入您的新密码"></td>
						<td class="tip"><span></span><small></small></td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td>
							<button type="button" id="pswd_save" style="margin: 20px 0 0 2px;"></button>
						</td>
						<td>&nbsp;</td>
					</tr>
					<tr style="font-size: 14px; text-align: center;">
						<td>&nbsp;</td>
						<td id="back_msg"></td>
						<td>&nbsp;</td>
					</tr>
				</table>
			</form>
		</div>
		</s:if>
		<s:elseif test="req == 33">
		<div id="alter_pswd_tip">
			<p style="margin-top:120px; text-align:center; font-size:24px;">密码修改成功！</p>
		</div>
		</s:elseif>
	</article>

	<footer id="footer"> &copy;2013 Fengxu at North University of
		China </footer>


</body>
</html>
