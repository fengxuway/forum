<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fx" uri="http://www.fengxu.cn/fxtag" %>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="../css/all.css">
<link rel="stylesheet" type="text/css" href="../css/personal.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.myPlugin.css">
<link rel="shortcut icon" type="image/x-ico" href="../images/fav.ico">
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript">window.UEDITOR_HOME_URL="${pageContext.request.contextPath}/ueditor/"; </script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor_all.js"></script>
<script type="text/javascript" src="../js/jquery.myPlugin.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	//如果参数中有更新是否成功的信息，则显示。(referer为空除外)
	var updateMsg = "${param.updateMsg}";
	if(<%=request.getHeader("Referer") !=null%> && updateMsg && updateMsg != ""){
		$.myPlugin.msgAlert(decodeURI(updateMsg));
	}
	
	var r = parseInt(${param.r});
	if(!r){ r=11;}
	var main = parseInt(r/10) - 1;
	var sub = parseInt(r%10) - 1;
	
	$("section > header > ul > li:eq("+main+")").addClass("current").siblings("li").removeClass();
	$("section > div.main:eq("+main+")").show().siblings("div.main").hide();
	if( main == 1 || main == 2){
		var $current = $("section > div.main:eq("+main+")").children("ul").children("li:not(.l):eq("+sub+")");
		$current.addClass("main_current").siblings("li:not(.l)").removeClass();
		$current.parent("ul").next("div.line").children("div.active_line").css("left",$current.position().left+$current.width()/2-44);
		$("section > div.main:eq("+main+")").children("div.relate:eq("+sub+")").show().siblings("div.relate").hide();
	}
});
</script>
<script type="text/javascript" src="js/personal.js"></script>
<title>个人中心</title>
</head>

<body>
	<!-- header部分 -->
	<header>
		<div id="logo">
			<a href="../index"><img src="../images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title">中北校友论坛</div>
		<div id="links">
			<a href="personal?r=41" style="color:#000;">${sessionScope.user.user_id}</a>&nbsp;&nbsp;<a href="../quit">退出</a>
		</div>
	</header>

	<!-- 个人信息部分 -->
	<article id="info">
		<div id="img">
			<a href="#"><img src="../images/head_img/${sessionScope.user.photo}" width="100"
				height="100" alt=""></a>
		</div>
		<div id="data">
			${sessionScope.user.user_id}<br> 男<br>
			<div class="degree">
				<span class="degree_name">${sessionScope.user.rank.rank_name}</span> 
				<span class="degree_id" style="background:url(../images/s${sessionScope.user.rank.img}.gif) no-repeat;">${sessionScope.user.rank.grade}</span>
			</div>
			
			<!-- 存储当前用户的ID -->
			<input type="hidden" name="session_user_id" value="${sessionScope.user.user_id }"/>
		</div>
		<div id="option">
			<a href="javascript:void(0);" id="dd">个人资料设置</a>
			<a href="secret">我的私信</a>
		</div>
	</article>

	<!-- 主体部分 -->
	<section>
		<!-- 第一个ul选项卡导航部分 -->
		<header>
			<ul>
				<li class="current">关注动态</li>
				<li>我在中北</li>
				<li>我的消息</li>
				<li>个人资料</li>
			</ul>
		</header>

		<!-- 我的关注的动态 -->
		<div id="main0" class="main relate"	style="display: none; width: 688px;">
			<!-- 如果请求参数r=21，显示个人发表过的主题列表 -->
			<s:if test="r == 11">
				<!-- 用于记录是否已加载过 -->
				<input type="hidden" name="loadNum" value="1"/>
			<s:iterator value="atteDynamicBean.list" id="sub">
				<s:if test="type == 1">
				<!-- 如果动态为主题贴 -->
					<article>
						<header>
							<a href='../p?id=${sub_id.author.userid_encode}' target='_blank' class='reply_img'> 
		    					<img alt=''	src='../images/head_img/${sub_id.author.photo}'></a>
							<div class='reply_title'>
								<a href='../p?id=${sub_id.author.userid_encode}' target='_blank'>${sub_id.author.user_id}</a>&nbsp; 发表主题：
							</div>
							<div class='clear'></div>
						</header>
						<div class='theme_head'>
							<a href='../single?sub_id=${sub_id.sub_id}' target='_blank'>${sub_id.sub_title}</a> 
							<a href='../list?cate_id=${sub_id.cate_id.cate_id }' class='theme_type' target='_blank'>【${sub_id.cate_id.cate_name }】</a>
						</div>
						<time>${sub_id.sub_time}</time>
					</article>
				</s:if>
				<s:elseif test="type == 2">
				<!-- 如果动态为回复贴 -->
					<article>
						<header>
							<a href='../p?id=${reply_id.user_id.userid_encode}' class='reply_img' target='_blank'> 
		    					<img alt=''	src='../images/head_img/${reply_id.user_id.photo }'></a>
							<div class='reply_title'>
								<a href='../p?id=${reply_id.user_id.userid_encode }' target='_blank'>${reply_id.user_id.user_id }</a>&nbsp; 发表回复：
							</div>
							<div class='clear'></div>
						</header>
						<div class='theme_head'>
							<a href='../single?reply_id=${reply_id.reply_id}#${reply_id.reply_id}' target='_blank'>
								<fx:regsub replacement="" regex="<.*?>" value="${reply_id.reply_content }" to="40"></fx:regsub>
							</a>
							<div class='theme_title'>
								<span class='at_theme'>在主题：</span>
								<a href='../single?sub_id=${reply_id.sub_id.sub_id}' target='_blank'>${reply_id.sub_id.sub_title}</a>
								<a href='../list?cate_id=${reply_id.sub_id.cate_id.cate_id}' class='theme_type' target='_blank'>【${reply_id.sub_id.cate_id.cate_name }】</a>
							</div>
							<time>${reply_id.reply_time}</time>
						</div>
					</article>
				</s:elseif>
			</s:iterator>
			</s:if>
			<s:else>
				<input type="hidden" name="loadNum" value="0"/>
			</s:else>
			
			<div class="more"><a href="javascript:void(0);">
				<s:if test="r == 11 &&  atteDynamicBean.list.size == 0">
					您没有关注别人或您关注的用户还没有动态
				</s:if>
				<s:elseif test="r == 11 && atteDynamicBean.lastPage">
					已到最后一页
				</s:elseif>
				<s:else>
					点击查看更多
				</s:else>
			</a></div>
		</div>

		<!-- 我在中北部分 -->
		<div id="main1" class="main" style="display: none;">
			<!-- 我在论坛中的动态 列表导航部分 -->
			<ul>
				<li class="main_current">主题帖</li>
				<li class="l"></li>
				<li>回复帖</li>
				<li class="l"></li>
				<li>粉丝</li>
				<li class="l"></li>
				<li>我的关注</li>
			</ul>

			<div class="line">
				<div class="active_line"></div>
				<div class="active_line"></div>
			</div>

			<!-- #relate0部分：发表过的主题 -->
			<div id="relate0" class="relate" style="display: none;">
				<!-- 如果请求参数r=21，显示个人发表过的主题列表 -->
				<s:if test="r == 21 ">
					<!-- 用于记录是否已加载过 -->
					<input type="hidden" name="loadNum" value="1"/>
				<s:iterator value="selfSubjectBean.list" id="sub">
					<article>
						<header>发表主题</header>
						<div class="theme_head">
							<a href="../single?sub_id=${sub_id }" target="_blank" >${sub_title }</a> 
							<a href="../list?cate_id=${cate_id.cate_id }" class="theme_type" target="_blank" >【${cate_id.cate_name}】</a>
						</div>
						<time><s:date name="sub_time" format="yyyy-MM-dd HH:mm"/></time>
					</article>
				</s:iterator>
				</s:if>
				<s:else>
					<input type="hidden" name="loadNum" value="0"/>
				</s:else>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="selfSubjectBean.list.size == 0">
						您还没有发表主题
					</s:if>
					<s:elseif test="r == 21 && selfSubjectBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
			</div>

			<!-- #relate1 ： 我的回复列表 -->
			<div id="relate1" class="relate" style="display: none;">
				<s:if test=" r== 22">
					<input type="hidden" name="loadNum" value="1"/>
				<s:iterator value="selfReplyBean.list" id="rep">
					<article>
						<header>发表回复</header>
						<div class="theme_head">
							<a href="../single?reply_id=${reply_id }#${reply_id}" target="_blank" >
								<fx:regsub replacement="" regex="<.*?>" value="${reply_content }" to="40"></fx:regsub>
							</a>
							<div class="theme_title">
								<a href="../single?sub_id=${sub_id.sub_id}" target="_blank" >${sub_id.sub_title}</a> 
								<a href="../list?cate_id=${sub_id.cate_id.cate_id}"  target="_blank" class="theme_type">【${sub_id.cate_id.cate_name}】</a>
							</div>
							<time><s:date name="reply_time" format="yyyy-MM-dd HH:mm"/></time>
						</div>
					</article>
				</s:iterator>
				</s:if>
				<s:else>
					<input type="hidden" name="loadNum" value="0"/>
				</s:else>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="r == 22 && selfReplyBean.list.size == 0">
						您还没有发表回复
					</s:if>
					<s:elseif test="r == 22 && selfReplyBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
			</div>

			<!-- #relate2： 我的粉丝 -->
			<div id="relate2" class="relate">
				<input type="hidden" name="loadNum" value="0"/>
				
				<ul></ul>
				
				<div class="more"><a href="javascript:void(0);">点击查看更多</a></div>
				
			</div>

			<!-- #relate3：我的关注 -->
			<div id="relate3" class="relate" style="display: none;">
				<input type="hidden" name="loadNum" value="0"/>
				
				<ul></ul>
				
				<div class="more"><a href="javascript:void(0);">点击查看更多</a></div>
			</div>
		</div>

		<!-- 我的消息 -->
		<div id="main2" class="main">
			<!-- 我的消息 导航部分 -->
			<ul>
				<li class="main_current">回复我的</li>
				<li class="l"></li>
				<li>@我的</li>
				<li class="l"></li>
				<li>系统消息</li>
			</ul>

			<div class="line">
				<div class="active_line"></div>
				<div class="active_line"></div>
			</div>

			<!-- #relate4回复我的 -->
			<div id="relate4" class="relate">
				<s:if test = "r == 31">
					<input type="hidden" name="loadNum" value="1"/>	
				<s:iterator value="replyMeBean.list" id="replyMe">
				<article>
					<header>
						<a href="../p?id=${reply_id.user_id.userid_encode }" target="_blank" class="reply_img"> 
							<img alt="${reply_id.user_id.user_id }"	src="../images/head_img/${reply_id.user_id.photo }">
						</a>
						<div class="reply_title">
							<a href="../p?id=${reply_id.user_id.userid_encode }" target="_blank" >${reply_id.user_id.user_id }</a>&nbsp; 回复了您：
						</div>
					</header>
					<div class="theme_head">
						<a href="../single?reply_id=${reply_id.reply_id }#${reply_id.reply_id}" target="_blank" >
							<fx:regsub replacement="" regex="<.*?>" value="${reply_id.reply_content }" to="40"></fx:regsub>
						</a>
						<div class="theme_title">
							<span class="at_theme">在主题：</span><a href="../single?sub_id=${reply_id.sub_id.sub_id }" target="_blank" >${reply_id.sub_id.sub_title }</a>
							<a href="../list?cate_id=${reply_id.sub_id.cate_id.cate_id }"  target="_blank" class="theme_type">【${reply_id.sub_id.cate_id.cate_name }】</a>
						</div>
						<time><s:date name="time" format="yyyy-MM-dd HH:mm"/></time>
					</div>
					
				</article>
				</s:iterator>
				</s:if>
				<s:else>
					<input type="hidden" name="loadNum" value="0"/>
				</s:else>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="r == 31 && replyMeBean.list.size == 0">
						还没有人回复您
					</s:if>
					<s:elseif test="r == 31 && replyMeBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
			</div>

			<!-- #relate5@我的 -->
			<div id="relate5" class="relate" style="display: none;">
				<s:if test="r == 32">
					<input type="hidden" name="loadNum" value="1"/>
				<s:iterator value="mentionMeBean.list" id="mentionMe">
					<article>
						<header>
							<a href="../p?id=${user_id.userid_encode }" target="_blank"  class="reply_img"> 
								<img alt="" src="../images/head_img/${user_id.photo }">
							</a>
							<div class="reply_title">
								<a href="../p?id=${user_id.userid_encode }" target="_blank" >${reply_id.user_id.user_id }</a>&nbsp; @了您：
							</div>
						</header>
						<div class="theme_head">
							<a href="../single?reply_id=${reply_id.reply_id}#${reply_id.reply_id}" target="_blank" >
								<fx:regsub replacement="" regex="<.*?>" value="${reply_id.reply_content }" to="40"></fx:regsub>
							</a>
							<div class="theme_title">
								<span class="at_theme">在主题：</span><a href="../single?sub_id=${reply_id.sub_id.sub_id }" target="_blank" >${reply_id.sub_id.sub_title }</a>
								<a href="../list?cate_id=${reply_id.sub_id.cate_id.cate_id }"  target="_blank" class="theme_type">【${reply_id.sub_id.cate_id.cate_name }】</a>
							</div>
							<time>${time }</time>
						</div>
					</article>
					
				</s:iterator>
				</s:if>
				<s:else>
					<input type="hidden" name="loadNum" value="0"/>	
				</s:else>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="r == 32 && mentionMeBean.list.size == 0">
						还没有人@提到您
					</s:if>
					<s:elseif test="r == 32 && mentionMeBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
			</div>

			<!-- #relate6 系统消息 -->
			<div id="relate6" class="relate" style="display: none;">
				<s:if test="r == 33">
					<input type="hidden" name="loadNum" value="1"/>
				<s:iterator value="newsBean.list" id="news">
					<article>
						<div class="theme_head">${info_content }</div>
						<time><s:date name="time" format="yyyy-MM-dd HH:mm"/></time>
					</article>
				</s:iterator>
				</s:if>
				<s:else>
					<input type="hidden" name="loadNum" value="0"/>
				</s:else>
				
				<div class="more"><a href="javascript:void(0);">
					<s:if test="r == 33 && newsBean.list.size == 0">
						您还没有系统消息
					</s:if>
					<s:elseif test="r == 33 && newsBean.lastPage">
						已到最后一页
					</s:elseif>
					<s:else>
						点击查看更多
					</s:else>					
				</a></div>
			</div>

		</div>

		<!--  个人资料 -->
		<div id="main3" class="main">
			<!-- header：用户ID
        table：性别、头像、个性签名、是否允许被关注、是否接收私信 -->
			<header>我的个人资料</header>
			<div class="material">
				<form action="updateMaterial" enctype="multipart/form-data" method="post">
					<table>
						<tr>
							<td class="td_left" width=129>密码</td>
							<td>
								<button class="updatePswdBtn" type="button">修改密码</button>
								<div class="updatePswd">
									<label>当前密码：<input type="password" name="currentPswd" maxlength="16"></label><br>
									<label>请输入新密码：<input type="password" name="newPswd" maxlength="16"></label><br>
									<label>请再输入一次：<input type="password" name="newPsure" maxlength="16"></label><br>
									
								</div>
							</td>
						</tr>
						<tr>
							<td class="td_left">头像</td>
							<td>
								<figure>
									<img alt="" src="../images/head_img/${sessionScope.user.photo}">
									<figcaption>当前头像</figcaption>
								</figure>
								<div class="updatePhoto"><label>上传头像&nbsp;&nbsp;<input name="photo" type="file" placeholder="请点击上传头像"></label></div>
							</td>
						</tr>
						<tr>
							
							<td class="td_left">签名档</td>
							<td style="padding:10px; line-height:1;">
								<div id="sign_content" style="line-height:20px;" title="双击修改签名档" ondblclick="render()">${sessionScope.user.sign }</div>
								<script type="text/plain" name="sign" id="myEditor" >
									</script>
							</td>
						</tr>
						<tr>
							<td colspan=2>
								<button class="submitUpdate" type="submit">保存修改</button>
							</td>
						</tr>
					</table>
				</form>
			</div>
			
			<header style="margin-top:30px;">以下是您的隐私信息，不可修改，不会公开</header>
			<div class="material">
				<table>
					<tr>
						<td class="td_left" width=129>邮箱</td>
						<td class="td_right">${sessionScope.user.email}</td>
					</tr>
					<tr>
						<td class="td_left">姓名</td>
						<td class="td_right">${sessionScope.user.name}</td>
					</tr>
					<tr>
						<td class="td_left">学号</td>
						<td class="td_right">${sessionScope.user.student_id}</td>
					</tr>
					<tr>
						<td class="td_left">学院</td>
						<td class="td_right">${sessionScope.user.college.college_name}</td>
					</tr>
					<tr>
						<td class="td_left">专业</td>
						<td class="td_right">${sessionScope.user.major.major_name}</td>
					</tr>
				</table>
			</div>
			
		</div>
	</section>

	<aside>
		<p style="margin:20px 12px; text-align:center;">
			粉丝：<a style="font-size:24px;" href="javascript:void(0);">${atteMeCount }</a>&nbsp;&nbsp;&nbsp;&nbsp;
			关注：<a style="font-size:24px;" href="javascript:void(0);">${myAtteCount }</a>
		</p>
		
	</aside>

	
<script type="text/javascript">
	//渲染编辑器
	function render(){
		var ue = new UE.ui.Editor( {
		   toolbars:[[
				//'fullscreen',
				'source','|',
				'|',
				'bold',
				'italic',
				'underline','|',
				'insertimage',
				'emotion',
				/* 'attachment', */
				'scrawl',
				/* 'highlightcode', */'|',
				'justifyleft',
				'justifycenter',
				'justifyright',
				'justifyjustify','|',
				'preview'
			]],
			//最小高度
			//minFrameHeight:283,
			//initialFrameWidth:697,
			initialFrameWidth:536,
			initialFrameHeight:244,
			
			//不自动长高
			autoHeightEnabled:false,
			//初始文本
			initialContent:document.getElementById("sign_content").innerHTML,
			//聚焦时清除初始文本
			autoClearinitialContent:false,
			//表情本地化
			emotionLocalization:true,
			//是否保持toolbar的位置不动
			autoFloatEnabled:false,
			
			maximumWords:40,
			
			wordOverFlowMsg:'<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>'
		} );
		ue.render( 'myEditor' );

		ue.addListener( "selectionchange", function () {
			var state = ue.queryCommandState( "source" );
			var btndiv = document.getElementById( "btns" );
			if ( btndiv && state == -1 ) {
				disableBtn( "enable" );
			}
		} );
		$("#sign_content").slideUp(100);
		setTimeout(function(){$("#sign_content").remove();},300);
	} 
</script>
</body>
</html>
