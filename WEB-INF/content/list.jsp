<%@page import="pagination.PageBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
	String basepath = request.getContextPath(); 
	String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ basepath; 
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/all.css">
<link rel="stylesheet" type="text/css" href="css/prettySelect.css">
<link rel="stylesheet" href="js/jquery-ui-1.10.0.custom/css/ui-lightness/jquery-ui-1.10.0.custom.css">
<link rel="stylesheet" type="text/css" href="css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="shortcut icon" type="image/x-ico" href="images/fav.ico">
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">window.UEDITOR_HOME_URL="${pageContext.request.contextPath}/ueditor/"; </script>
<script src="js/jquery-ui-1.10.0.custom/js/jquery-ui-1.10.0.custom.js"></script>
<script type="text/javascript" src="js/jquery.prettySelect.js"></script>
<script type="text/javascript" src="js/jquery.simpleMove.js"></script>
<script type="text/javascript" src="js/jquery.myWindow.js"></script>
<script type="text/javascript" src="js/jquery.scrollto.js"></script>
<script type="text/javascript" src="ueditor/editor_config.js"></script>
<script type="text/javascript" src="ueditor/editor_all.js"></script>
<script type="text/javascript" src="js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/jquery.pageNum.js"></script>
<script type="text/javascript" src="js/list.js"></script>
<script type="text/javascript">
function href(s){
	window.location.href=s;
}
</script>
<script type="text/javascript">
$(document).ready(function(){
	$("aside").height($("#section_in_list").height());
	$("section > footer").pageNum({
		totalPage : ${pageBean.totalPage},
		currentPage : ${pageBean.currentPage},
		currentClass : "currentPage",
		otherClass : ""
	});
});
</script>

<title>中北校友<s:if test="searchFlag">&nbsp;搜索：${kw }</s:if><s:else>${category.cate_name }</s:else></title>
</head>

<body>
	<!-- header部分 -->
	<header>
		<div id="logo">
			<a href="<%=path%>/"><img src="images/logo.png" alt="论坛首页"
				title="中北校友论坛欢迎您"></a>
		</div>
		<div id="title">
			<h1>中北校友论坛</h1>
			<div id="weather">
				<img src="images/weather/b_${weather.image }">
				<div class="weather_content">
					<div style="float:left; width:75px;">
						<span>中北大学</span><br>
						<span>${weather.degree }</span><br>
						<span>${weather.climate }</span>
					</div>
					<div style="margin-left:75px;">
						<span>&nbsp;</span><br>
						<span>风力：${weather.wind }</span><br>
						<span>湿度：${weather.humidity }</span>
					</div>
				</div>
			</div>
			<p>${forumSign }</p>
		</div>
	</header>
	<!-- header end -->

	<!-- 导航nav -->
	<nav id="main_nav">
		<ul style="width:936px;">
			<li id="home"><a href="index"><span></span>论坛首页</a></li>
			<li class="l"></li>
			<li><a href="javascript:void(0);">&nbsp;板&nbsp;&nbsp;块&nbsp;</a>
				<div>
					<!-- 遍历分类categoryList，生成链接 -->
					<s:iterator value="categoryList" id="category">
						<a href="list?cate_id=<s:property value='cate_id'/>"><s:property
								value="cate_name" /></a>
					</s:iterator>
				</div></li>
			<li class="l"></li>
			<li><a
				<s:if test="user_degree == 0"> href="javascript:void(0);" onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>
				<s:else>href="user/personal?r=21" target="_blank"</s:else>>我的中北</a></li>
			<li class="l"></li>
			<li><a href="javascript:void(0);"
				<s:if test="user_degree == 0">onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>>我的足迹</a>
				<s:if test="footmark.size > 0">
					<div style="width: 200px;">
						<s:iterator value="footmark" var="fm">
							<a href="single?sub_id=${sub_id.sub_id }">${fn:substring(sub_id.sub_title,0,12)}</a>
						</s:iterator>
					</div>
				</s:if></li>
			<li class="l"></li>
			<li><a 
				<s:if test="#session.user == null"> href="javascript:void(0);" onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>
				<s:else>href="user/personal?r=31" target="_blank"</s:else>>&nbsp;消&nbsp;&nbsp;息&nbsp;</a>
				</li>
			<li class="l"></li>
			<li
				<s:if test="user_degree == 0">onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>
				<s:elseif test="user_degree < 0">onClick="$.myPlugin.msgAlert('您被封禁，暂时无法发表','warn')"</s:elseif>
				<s:else> id="publish" </s:else>><a href="javascript:void(0);">发表主题</a></li>
				<s:if test="#session.user != null">
				<li style="float:right"><a href="quit">退出</a></li>
				</s:if>
				<s:if test="#session.user.identity > 100">
				<li class="l" style="float:right"></li>
				<li style="float:right"><a href="admin/admin" target="_blank">管理</a></li>
				</s:if>
		</ul>
		<div id="refresh" onClick="window.location.reload();"></div>
		<div class="l"></div>
	</nav>
	<!-- 主nav end -->
	<nav id="second_nav">
		<div id="link_nav">
			<a href="<%=path%>/">首页</a>&nbsp;-&gt; <s:if test="searchFlag">搜索：${kw }</s:if><s:else>${category.cate_name}</s:else>
		</div>
		<div id="search">
			<form action="list" method="get">
				<select id="search_select" name="search_type">
					<option value="1">搜索主题</option>
					<option value="0">搜索会员</option>
				</select>
				<input id="search_text" value="${kw}" type="text" name="kw" maxlength="20">
				
				<input id="search_submit" type="button">
			</form>
		</div>
	</nav>
	<!-- 副nav end -->

	<!-- 主体部分 -->
	<section id="section_in_list">
		<!-- 每个article代表一个主题 -->
		
		<s:iterator value="pageBean.list" id="subject" status="st">
			<article <s:if test="#st.even"> style="background: #f0f0f0;"</s:if>>
				<div class="sub_title">
					<span>${reply_num }</span><a href="single?sub_id=${sub_id }" title="主题：${sub_title}">
						<!-- 标题过长的，截断字符串 -->
						<s:if test="sub_title.length() >= 25"><s:property value="sub_title.substring(0, 24)"/>...</s:if>
						<s:else>${sub_title}</s:else>
					</a>
					<s:if test="if_perfect">
						<mark class="perfect_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</mark>
					</s:if>
					<s:if test="if_top">
						<mark class="top_icon">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</mark>
					</s:if>
					<s:if test="searchFlag"><a style="float:right; margin-right:5px;" href="list?cate_id=${cate_id.cate_id }">【${cate_id.cate_name }】</a>&nbsp;&nbsp;</s:if>
				</div>
				<div class="sub_info">
					<a href="p?id=${author.userid_encode }" target="_blank" class="sub_author">${author.user_id }</a><br>
					<s:if test="last_reply != null">
						<a href="p?id=${last_reply.user_id.userid_encode}" target="_blank" class="sub_reply">${last_reply.user_id.user_id }</a>
					</s:if>
					<s:else>
						<a href="p?id=${author.userid_encode }" target="_blank" class="sub_reply">${author.user_id }</a>
					</s:else>
					<time title="最后回复：<s:date name="last_reply_time" format="yyyy-MM-dd HH:mm:ss"/>">
						<s:if test="last_reply_time.time >= today.time">
							<s:date name="last_reply_time" format="HH:mm"/>
						</s:if>
						<s:elseif test="last_reply_time.time > year.time">
							<s:date name="last_reply_time" format="MM-dd"/>
						</s:elseif>
						<s:else>
							<s:date name="last_reply_time" format="yyyy-MM"/>
						</s:else>
					</time>
				</div>
			</article>
		</s:iterator>
	

		<!-- 页码 -->
		<footer></footer>
	</section>
	<!-- section end -->

	<!-- 边栏 -->
	<aside>
		<div style="position:relative;top:-12px;"><div id="search_list">
			<ul>
				<li><a href="#">123</a></li>
				<li><a href="#">123</a></li>
				<li><a href="#">123</a></li>
				<li><a href="#">123</a></li>
				<li><a href="#">123</a></li>
			</ul>
		</div></div>
		<!-- 在用户未登录时显示的部分，登录和注册按钮 -->
		<s:if test="user_degree == 0">
			<article id="login_register">
				<h3>欢迎来到中北校友论坛！</h3>
				<span id="login_button">登录</span> <span id="register_button"
					onClick="javascript:href('register')">注册</span>
			</article>
		</s:if>
		<!-- 个人资料，包含等级、经验等 -->
		<s:else>
		<!-- 个人资料，包含等级、经验等 -->
	    <article id="own_info">
	    	<h3>我的本吧头衔</h3>
	        <div id="own_photo">
	        	<img src="images/head_img/${sessionScope.user.photo }" alt="">
	        </div>
	        <div id="own_name">
	        	<span>${sessionScope.user.user_id}</span><br>
	            <div class="degree">
	            	<span class="degree_name">${sessionScope.user.rank.rank_name }</span>
	                <span class="degree_id"  style="background:url(images/s${sessionScope.user.rank.img}.gif) no-repeat;">${sessionScope.user.rank.grade}</span>
	            </div>
	        </div>
	        <div id="exper">经验：       	
	        	<div id="super_bar">
	            	<div id="exper_id">${sessionScope.user.exp }/${sessionScope.user.rank.exp}</div>
	                <div id="sub_bar" style="width:${sessionScope.user.exp * 135.0 / sessionScope.user.rank.exp }px"></div>
	            </div>
	        </div>
	    </article>
		</s:else>
		<!-- 论坛信息 -->
		<article id="forum_info">
			<s:if test="searchFlag">
				<p>论坛信息</p>
				&nbsp;&nbsp;&nbsp;管理员：<a href="p?id=${admin.userid_encode }" target="_blank">${admin.user_id }</a><br>
				&nbsp;&nbsp;&nbsp;中北学子：共&nbsp;${userCount }&nbsp;位
			</s:if>
			<s:else>
				<p>板块信息</p>
				&nbsp;&nbsp;&nbsp;版主：
				<s:if test="admin != null">
					<a href="p?id=${admin.userid_encode}" target="_blank">${admin.user_id}</a><br>
				</s:if>
				<s:else>
					暂无<br>
				</s:else>
				&nbsp;&nbsp;&nbsp;主题数：${category.subject_num }
			</s:else>
		</article>
		<!-- 友情链接 -->
		<article id="friend_link">
			<p>友情链接</p>
			<a href="http://http://www.nuc.edu.cn/" target="_blank">中北大学</a><br>
	        <a href="http://202.207.182.100/new/index.action" target="_blank">中北大学图书馆</a><br>
	        <a href="http://www1.nuc.edu.cn/jwc/" target="_blank">中北大学教务处</a>
		</article>
	</aside>
	<!-- aside end -->

	<div class="clear"></div>

	<footer id="footer"> &copy;2013 Fengxu at North University of
		China </footer>
	<!-- footer end -->

	<!-- 浮动div部分，包括到顶部底部按钮、登录窗口、发帖窗口等。

	<!-- 到顶部及到底部 -->
	<div id="top_bottom">
	<script type="text/javascript">
    function goto(id){
        $(id).ScrollTo(1000);	
    }
    </script>
		<a id="totop" onClick="javascript:goto('header:first'); return false;"></a>
		<a id="tobottom" onClick="javascript:goto('#footer'); return false;"></a>
	</div>

	<!-- 登录窗口 -->
	<s:if test="user_degree == 0">
		<!-- 登录窗口 -->
		<div id="login">
			<header>
				<span>登&nbsp;&nbsp;录</span><span class="close"></span>
			</header>
			<article>
				<form action="#">
					<label>用户名：<input type="text" name="userid"
						placeholder="请输入用户名或邮箱" maxlength="14"></label> <label>密码：<input
						type="password" name="pswd" maxlength="16" placeholder="请输入密码"></label>
				</form>
				<p id="tip">&nbsp;</p>
				<p>
					<button>登录</button>
					<a href="register">立即注册</a>&nbsp;&nbsp;<a href="register?req=31">忘记密码？</a>
				</p>
			</article>
		</div>
	</s:if>
	<s:else>
	<!-- 发表新主题窗口 -->
	<div id="new_theme">
		<header>
			<span>发表主题</span><span class="close"></span>
		</header>
		<article>
			<div class="moves1"></div>
			<form action="">
				<label for="new_type">请选择板块：</label> <select id="new_type"
					name="cate_id">
					<option value="">请选择板块</option>
					<!-- 遍历主题类别, 如果名字为"精品区"则不显示, 
					如果名字为"公告栏", 只有管理员和版主可以选择 -->
					<s:iterator value="categoryList" id="category">
						<s:if test="cate_name != '精品区'">
							<s:if test="cate_name =='公告栏'">
								<s:if test="#session.user.identity >= 100">
									<option value="${cate_id }">${cate_name }</option>
								</s:if>
							</s:if>
							<s:else>
								<option value="${cate_id }">${cate_name }</option>
							</s:else>
						</s:if>
					</s:iterator>
				</select> 
				<span id="select_msg" style="color: #e30f0f; margin-left: 20px;">&nbsp;</span><br>
				<label for="new_title">标题：</label> 
				<input id="new_title" name="sub_title" type="text" maxlength="30" placeholder="请输入标题（不能为空）"><br> 
				<label	for="new_content">内容：</label>
				<!-- UEditor文本输入框 -->
				<script type="text/plain" id="new_content" name="sub_content"
					style="width:570px; height: 300px;"></script>
				<div class="clear"></div>
				<label style="float: left; margin-left: 55px; margin-top: 10px;"><input type="checkbox"
					name="if_sign" value="yes"
					<s:if test="#session.user.sign==''">disabled title="您还没有签名档，进入“我的中北”添加您的签名档"</s:if>
					>&nbsp;使用签名档</label>
				<button id="new_theme_submit" type="button">发表</button>
				
				<div class="moves3"></div>
			</form>
			<div class="moves2"></div>
		</article>
	</div>
	</s:else>

	<script type="text/javascript">
	//判断是否已登陆, 如果未登陆, 不初始化UEditor
	var login_flag = <s:property value="user_degree > 0"/>;
	if(login_flag){
	    var ue = new UE.ui.Editor( {
	           toolbars:[[
					'fullscreen',
					'source','|',
					'fontsize','|',
					'bold',
					'italic',
					'underline','|',
					'link',
		            'insertimage',
		            'emotion',
		            'attachment',
		            'scrawl',
		            'highlightcode','|',
		            'justifyleft',
		            'justifycenter',
		            'justifyright',
		            'justifyjustify','|',
		            'preview'
				]],
	            //最小高度
	            initialFrameWidth:568,
	            initialFrameHeight:244,
	            'fontsize':[10, 11, 12, 14, 16, 18, 20, 24],
	            //不自动长高
	            autoHeightEnabled:false,
	            //初始文本
	            initialContent:'',
	            //聚焦时清除初始文本
	            autoClearinitialContent:true,
	            //表情本地化
	            emotionLocalization:true,
	          	//是否保持toolbar的位置不动
	            autoFloatEnabled:false,
	            //最大字数限制
	            maximumWords:2000,
	            //超出字条数的显示提示信息
	            wordOverFlowMsg:'<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>'
	    } );
	    ue.render( 'new_content' );
	
	    ue.addListener( "selectionchange", function () {
	        var state = ue.queryCommandState( "source" );
	        var btndiv = document.getElementById( "btns" );
	        if ( btndiv && state == -1 ) {
	            disableBtn( "enable" );
	        }
	    } );
	}
    
    function getContent(){
    	return ue.getContent();
    }
    function setFocus() {
        ue.focus();
    }
    function getContentTxt(){
    	return ue.getContentTxt();
    }
</script>

</body>
</html>
