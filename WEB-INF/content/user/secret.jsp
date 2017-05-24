<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>我的私信</title>
<link rel="stylesheet" type="text/css" href="../css/all.css">
<link rel="stylesheet" type="text/css" href="../css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="../css/secret.css">
<link rel="shortcut icon" type="image/x-ico" href="../images/fav.ico">
<script type="text/javascript">window.UEDITOR_HOME_URL="${pageContext.request.contextPath}/ueditor/"; </script>
<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript" src="../ueditor/editor_config.js"></script>
<script type="text/javascript" src="../ueditor/editor_all.js"></script>
<script type="text/javascript" src="../js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/secret.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	var r = parseInt(${r});
	if(r >= 20){
		$("#xiexin").click();
	}
});
</script>
</head>

<body>
	<!-- header部分 -->
	<header>
		<div id="logo">
			<a href="../index.html"><img src="../images/logo2.png" alt="论坛首页"></a>
		</div>
		<div id="title">中北校友论坛</div>
		<div id="links">
			<a href="personal?r=21" style="color:#000;" title="我的个人中心">${sessionScope.user.user_id }</a>&nbsp;&nbsp;<a href="#">退出</a>
			<input type="hidden" name="session_user_id" value="${sessionScope.user.user_id }"/>
		</div>
	</header>

	<!-- 主体部分 -->
	<div id="body">
		<!-- 侧边收信/发信按钮 -->
		<aside>
			<ul>
				<li id="shouxin" class="shou current" <s:if test="dialogs.size() == 0">data="0"</s:if><s:else>data="1"</s:else>>
					<div class="icon"></div> <span>收信</span>
				</li>
				<li id="xiexin" class="xie">
					<div class="icon"></div> <span>写信</span>
				</li>
			</ul>
		</aside>
		
		<div id="no_talk"  <s:if test="dialogs.size() != 0">style="display:none;"</s:if>>
			<img src="../images/notalk_icon.png">
			<p>您还没收到私信哦</p>
			<button type="button">写私信</button>
		</div>
		
		<!-- 收信内容，与#shou对应 -->
		<div id="for_shou"  <s:if test="dialogs.size() == 0">style="display:none;"</s:if>>
			<!-- 对话列表 -->
			<div id="list">
				<!-- 批量操作部分，一个隐藏的全选按钮 -->
				<!-- <header>
					<div id="select_all">
						<input type="checkbox" name="" id="">
					</div>
					<div id="delete_all">批量删除</div>
					<div id="read_all">标记已读</div>
				</header> -->

				<!-- 与每个用户的私信对话列表 -->
				<ul style="margin-top:39px;">
				
				<s:iterator value="dialogs" var="dialog">
				
					<li data="${dialog_id }">
						<!-- 选中按钮 -->
						<div class="select">
							<!-- <input type="checkbox" name="" id=""> -->
						</div> <!-- 用户头像及名字、链接 -->
						<div class="user_link">
							<s:if test="userA.user_id.equals(#session.user.user_id)">
								<a href="../p?id=${userB.userid_encode }" target="_blank"><img src="../images/head_img/${userB.photo }" alt=""></a><span>${userB.user_id }</span>
							</s:if>
							<s:else>
								<a href="../p?id=${userA.userid_encode }" target="_blank"><img src="../images/head_img/${userA.photo }" alt=""></a><span>${userA.user_id }</span>
							</s:else>
						</div> <!-- 关闭按钮 -->
						<div class="main_close"></div>
					</li>
				</s:iterator>
					<!-- <li class="main_current">
						<div class="select">
							<input type="checkbox" name="" id="">
						</div>
						<div class="user_link">
							<a href="#"><img src="../images/head.png" alt=""></a>独孤觅雪
						</div>
						<div class="main_close"></div>
					</li>
					<li>
						<div class="select">
							<input type="checkbox" name="" id="">
						</div>
						<div class="user_link">
							<a href="#"><img src="../images/head.png" alt=""></a>独孤觅雪
						</div>
						<div class="main_close"></div>
					</li> -->
				</ul>

			</div>

			<!-- 聊天记录及聊天输入框 -->
			<div id="main">
				<div id="loading">
					<img src="../images/nycli1.gif">
					<p>加载中。。。</p>
				</div>
			
				<!-- section：聊天对话 -->
				<section id="content">
					<header>
						<%-- 我与<strong>独孤觅雪</strong>的私信--%>
					</header> 
					<!-- div.content部分：包含对话内容 -->
					<div class="content">
						<%--
						<!-- 每个article对应一个用户的一段话，.the_left表示别人的话，.the_right表示我说的话 -->
						<!-- 注：每个article下面有一个.clear以清除float -->

						<!-- user_head：用户头像
		                    left_icon：三角形小图标
		                    left_dialog：对话框
		                    close：关闭按钮
		                    left_content：说话内容 -->
						<article class="the_left">
							<div class="user_head">
								<img src="../images/head.png" alt="">
							</div>
							<div class="left_icon"></div>
							<div class="left_dialog">
								<div class="close"></div>
								<div class="clear"></div>
								<div class="left_content">
									我是中国人，我爱中国！我是中国人，我爱中国！我是中国人，我爱中国！我是中国人，我爱中国！我是中国人，我爱中国！我是中国人，我爱中国！我是中国人，我爱中国！
								</div>
								<time>2012-3-12 12:00</time>
							</div>
						</article>

						<div class="clear"></div>

						<!-- 注：the_right中time标签在关闭按钮.close的后边 -->
						 <article class="the_right">
							<div class="right_dialog">
								<div class="right_content">我也是中国人，我也爱中国@！</div>
								<div class="close"></div>
								<div class="clear"></div>
								<time>2012-3-12 12:00</time>
							</div>
							<div class="right_icon"></div>
						</article>

						<div class="clear"></div>

						<article class="the_left">
							<div class="user_head">
								<img src="../images/head.png" alt="">
							</div>
							<div class="left_icon"></div>
							<div class="left_dialog">
								<div class="left_close close"></div>
								<div class="clear"></div>
								<div class="left_content">
									我
								</div>
								<time>2012-3-12 12:00</time>
							</div>
						</article>

						<div class="clear"></div> --%>
					</div>
				</section>

				<!-- <div class="border"></div> -->

				<div id="input">
					<!-- <textarea id="reply_editor" name="" id=""></textarea> -->
					<script type="text/plain" id="reply_editor"></script>
					<button id="input_submit">
						点<br>击<br>回<br>复
					</button>
				</div>
			</div>
		</div>

		<!-- 写信内容，与#xie对应 -->
		<div id="for_xie" style="display:none;">
			<article>
				<form id="publishSecretForm" action="publishNewSecret" method="post" style="overflow:hidden;">
				<table>
					<tr>
						<td><label for="xie_name">收信人：</label></td>
						<td><input type="text" name="anotherUser_id" value="${id}" id="xie_name"></td>
					</tr>
					<tr>
						<td style="vertical-align: top;"><label for="xie_content">内容：</label></td>
						<td>
							<script type="text/plain" id="xie_content"></script>
							<input id="input_content" name="content" type="hidden" >
						</td>
					</tr>
					<tr>
						<td>&nbsp;</td>
						<td><button id="xie_submit" type="button">发送</button></td>
					</tr>
				</table>
				</form>
			</article>
			<div id="xie_side">
				<ol style="font-size:15px; margin-top:20px; padding-right:10px; color:#333; ">
					<li>请在收信人处填写收信人的ID。</li>
					<li>私信对话绝对保密，私信内容请遵守地方相关法律法规，不传播带有色情、暴力、辱骂等内容。</li>
					<li>由个人违犯规定而造成的任何后果，由个人承担。</li>
					<li>私信的最终解释权归本站所有。</li>
				</ol>
			</div>
		</div>
	</div>

<script type="text/javascript">
	var ue = new UE.ui.Editor( {
		toolbars:[[
			'emotion'
		]],
		//最小高度
		minFrameHeight:112,
		//initialFrameWidth:697,
		initialFrameWidth:556,
		initialFrameHeight:44,
		
		//不自动长高
		autoHeightEnabled:false,
		//初始文本
		initialContent:'',
		//聚焦时清除初始文本
		autoClearinitialContent:false,
		//表情本地化
		emotionLocalization:true,
		//是否保持toolbar的位置不动
		autoFloatEnabled:false,
		
		maximumWords:40,
		//是否进行字数统计
		elementPathEnabled: false,
    
		wordOverFlowMsg:'<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>'
	});
	ue.render( 'reply_editor' );
	
	ue.addListener( "selectionchange", function () {
		var state = ue.queryCommandState( "source" );
		var btndiv = document.getElementById( "btns" );
		if ( btndiv && state == -1 ) {
		    disableBtn( "enable" );
		}
	});
	
	var ue2 = new UE.ui.Editor( {
		toolbars:[[
			'emotion'
		]],
		//最小高度
		minFrameHeight:83,
		//initialFrameWidth:697,
		initialFrameWidth:470,
		initialFrameHeight:210,
		
		//不自动长高
		autoHeightEnabled:false,
		//初始文本
		initialContent:'',
		//聚焦时清除初始文本
		autoClearinitialContent:false,
		//表情本地化
		emotionLocalization:true,
		//是否保持toolbar的位置不动
		autoFloatEnabled:false,
		
		maximumWords:40,
		//是否进行字数统计
		elementPathEnabled: false,
		wordOverFlowMsg:'<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>'
	});
	ue2.render( 'xie_content' );
	
	ue2.addListener( "selectionchange", function () {
		var state = ue.queryCommandState( "source" );
		var btndiv = document.getElementById( "btns" );
		if ( btndiv && state == -1 ) {
			disableBtn( "enable" );
		}
	});
	
	
	function getUE1Content(){
    	return ue.getContent();
    }
    function getUE1ContentTxt(){
    	return ue.getContentTxt();
    }
	function getUE2Content(){
    	return ue2.getContent();
    }
    function getUE2ContentTxt(){
    	return ue2.getContentTxt();
    }
    function reset() {
        if(ue){
            ue.setContent("");
            ue.reset();
        }
    }


</script>
</body>
</html>
