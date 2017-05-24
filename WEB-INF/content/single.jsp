<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.*" %>
<%-- <%@ page  errorPage="error.jsp" %> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% 
	String basepath = request.getContextPath(); 
	String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+ basepath; 
%>
<!-- replyNum用于在迭代回复列表时显示楼号 -->
<s:set scope="page" value="(replyPage.currentPage-1) * 5 + 2" id="replyNum" ></s:set>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="css/all.css">
<link rel="stylesheet" type="text/css" href="css/prettySelect.css">
<link rel="stylesheet" type="text/css" href="css/jquery.myPlugin.css">
<link rel="stylesheet" type="text/css" href="ueditor/third-party/SyntaxHighlighter/shCoreDefault.css"/>
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/single.css">
<link rel="shortcut icon" type="image/x-ico" href="images/fav.ico" >
<!--[if IE]>
<script type="text/javascript" src="js/html5.js"></script>
<![endif]-->
<script type="text/javascript">window.UEDITOR_HOME_URL="${pageContext.request.contextPath}/ueditor/"; </script>
<script type="text/javascript" src="js/swfobject.js"></script>

<title>中北校友&nbsp;${currentSubject.sub_title }</title>
</head>

<body>
<header>
	<div id="logo">
    	<a href="index"><img src="images/logo.png" alt="论坛首页" title="论坛首页"></a>
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
    <!-- 隐藏域，存储当前页面的主题ID -->
    <input type="hidden" name="currentSubject_id" value="<s:property value="currentSubject.sub_id"/>">
	<input type="hidden" name="currentCate_id" value="${currentSubject.cate_id.cate_id}"/>
</header>
<!-- header end -->
<!-- 导航nav -->
<nav id="main_nav">
	<ul style="width:936px;">
		<li id="home"><a href="<%=path%>/"><span></span>论坛首页</a></li>
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
			<s:elseif test="#session.user.identity == 0">onClick="$.myPlugin.msgAlert('您被封禁，暂时无法回复','warn')"</s:elseif>
			<s:else> id="publishReply" </s:else>><a href="javascript:void(0);">发表回复</a></li>
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
    	<a href="<%=path%>/">首页</a>&nbsp;-&gt;
        <a href="list?cate_id=${currentSubject.cate_id.cate_id }">${currentSubject.cate_id.cate_name }</a>&nbsp;-&gt;
		<!-- 标题长度超过15，将其截断 -->
		<s:if test="currentSubject.sub_title.length() >= 15"><s:property value="currentSubject.sub_title.substring(0, 14)"/>&nbsp;...</s:if>
		<s:else>${currentSubject.sub_title}</s:else>
    </div>
    <div id="search">
    	<form action="list" method="get">
			<select id="search_select" name="search_type">
				<option value="1">搜索主题</option>
				<option value="0">搜索会员</option>
			</select>
			<input id="search_text" type="text" name="kw" maxlength="20">
			<input id="search_submit" type="button">
		</form>
    </div>
</nav>
<!-- 副nav end -->
<section id="section_in_single" class="section_in_single">
	<header>
    	<h4>${currentSubject.sub_title }</h4>
        <span 
        <s:if test="user_degree == 0">onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>
			<s:elseif test="#session.user.identity == 0">onClick="$.myPlugin.msgAlert('您被封禁，暂时无法回复','warn')"</s:elseif>
			<s:else> class="reply" </s:else>
        >回复</span>
		
		<!-- 版主可以操作主题的“置顶、加精、移到、删除” -->
		<s:if test="user_degree == 5">
    	<span class="operateButton">操作
		<div class="operList">
			<ul>
				<s:if test="currentSubject.if_top">
					<li data="notop">取消置顶</li>
				</s:if>
				<s:else>
					<li data="top">置顶</li>
				</s:else>
				
				<s:if test="currentSubject.if_perfect">
					<li data="noperfect">取消加精</li>
				</s:if>
				<s:else>
					<li data="perfect">精品</li>
				</s:else>
				
				<li class="showMove" data="move">移动到
					<div class="moveTo">
						<ul>
							<!-- 遍历分类categoryList，生成链接 -->
							<s:iterator value="categoryList" id="category">
							<s:if test="cate_name != '精品区' && cate_name != '公告栏' && cate_id != currentSubject.cate_id.cate_id">
								<li data="${cate_id}">${cate_name}</li>
							</s:if>
							</s:iterator>
						</ul>
					</div>
				</li>
				<li data="delete">删除</li>
			</ul>
		</div></span>
		</s:if>
    </header>
<!-- 如果当前页为第一页, 显示1楼信息 -->
<s:if test="replyPage.currentPage <= 1">
    <article>
    	<div class="user">
        	<figure onClick="javascript:href('p?id=${currentSubject.author.userid_encode}');">
        		<img src="images/head_img/${currentSubject.author.photo}" alt="">
        		<figcaption>${currentSubject.author.user_id }</figcaption>
        	</figure>
            <div class="degree">
            	<span class="degree_name">${currentSubject.author.rank.rank_name }</span>
                <span class="degree_id">${currentSubject.author.rank.grade }</span>
            </div>
        </div>
        
      	<div class="tie_main" data="1">
      	<!-- 如果为楼主，显示楼主图片 -->
      		<div class="louzhu_super"><div class="louzhu"></div></div>
        	<!-- 帖子内容 -->
        	<div class="tie_content">${currentSubject.sub_content }</div>
            <!-- 举报回复链接 -->
        	<div class="info">
        	<!-- 版主具有删除主题、封禁ID权限，管理员具有删除主题权限 -->
        	<s:if test="user_degree == 5">
           		<a class="a_jubao" href="javascript:void(0);">封</a> | <a class="a_jubao">删</a> | 
            </s:if>	
        	<s:elseif test="user_degree == 7 ">
	        	<a class="a_jubao">删</a> | 
            </s:elseif>
            <s:date format="yyyy-MM-dd HH:mm" name="currentSubject.sub_time"/> | 1楼&nbsp;&nbsp;
            	
            </div>
        <!-- 仅当作者的签名不为空， 且作者使用了签名档方可显示签名 -->
        <s:if test="currentSubject.author.sign != '' && currentSubject.if_sign">
            <div class="clear_right"></div>
            
            <div class="sign">
            	<header></header>
                <div class="sign_content">${currentSubject.author.sign }</div>
           	</div>
        </s:if>
        </div>
		

	</article>
</s:if>

<!-- ---------------------------------------------------------------------------------- -->
<!-- 回复部分开始 -->
<s:iterator value="replyPage.list" id="reply" status="st">
<s:if test="if_delete == false">
    <article>
    	<div class="user">
        	<figure onClick="javascript:href('p?id=${user_id.userid_encode}');">
        		<img src="images/head_img/${user_id.photo }" alt="">
        		<figcaption><s:property value="user_id.user_id"/></figcaption>
        	</figure>
            <div class="degree">
            	<span class="degree_name">${user_id.rank.rank_name }</span>
                <span class="degree_id">${user_id.rank.grade }</span>
            </div>
        </div>
        
      	<div class="tie_main">
			<a class="reply_anchor" name="${reply_id}"></a>
      		<!-- 如果为楼主，显示楼主图片 -->
      		<s:if test="user_id.user_id == currentSubject.author.user_id">
      			<div class="louzhu_super"><div class="louzhu"></div></div>
      		</s:if>
      		<!-- 该回复不是楼主，则根据实际情况显示楼号 -->
      		<s:else>
      			<div class="louzhu_super"><div class="louceng">
      				<s:if test="#st.index + #attr.replyNum == 2">沙发</s:if>
      				<s:elseif test="#st.index + #attr.replyNum == 3">板凳</s:elseif>
      				<s:elseif test="#st.index + #attr.replyNum == 4">地板</s:elseif>
      				<s:else>
      					<s:property value="#st.index + #attr.replyNum"/>楼
      				</s:else>&nbsp;
      			</div></div>
      		</s:else>
        	<div class="tie_content">${reply_content}</div>
        	<div class="info">
        		<a class="a_huifu" href="javascript:void(0);">收起回复</a>	
        		
        	<!-- 版主可以封ID及删帖 -->
            <s:if test="user_degree == 5">
           		<a class="a_jubao" href="javascript:void(0);">封</a> | 
				<a class="a_jubao" href="javascript:void(0);">删</a> | 
            </s:if>	
        	<!-- 管理员和楼主可以删回复帖 -->
        	<s:elseif test="user_degree == 7 || user_degree == 3">
	        	<a class="a_jubao" href="javascript:void(0);">删</a> | 
            </s:elseif>
            
            <s:date name="reply_time" format="yyyy-MM-dd HH:mm"/>&nbsp;| 
            <s:property value="#st.index + #attr.replyNum"/>楼&nbsp; 
            </div>
            
            <div class="clear"></div>
            <!-- 回复列表 -->
        	<ul>
        	<s:iterator value="replys" status="st">
				<!-- 判断是否被删除 -->
				<s:if test="if_delete == false ">
        		<li class="reply_list_main">
					<a name="${reply_id}" class="reply_anchor"></a>
                	<a href="p?id=${user_id.userid_encode }" target="_blank" ><img src="images/head_img/${user_id.photo }" alt=""></a>
                    <div class="reply_list_content">
                    	<a href="p?id=${user_id.userid_encode }" target="_blank">${user_id.user_id }</a>：${reply_content }
                    </div>
                    <div class="reply_time">
                    	<!-- 版主可以删除回复和封禁用户 -->
                    	<s:if test="user_degree == 5">
                    		<a class="a_jubao" href="javascript:void(0);">封</a>&nbsp;&nbsp;|&nbsp;&nbsp; 
                    		<a class="a_jubao" href="javascript:void(0);">删</a>&nbsp;&nbsp;|&nbsp; 
                    	</s:if>
                    	<!-- 管理员和楼主可以删除回复 -->
                    	<s:elseif test="user_degree == 3 || user_degree == 7">
                    		<a class="a_jubao" href="javascript:void(0);">删</a>&nbsp;&nbsp;|&nbsp;                     	
                    	</s:elseif>
                    	
                    	
                    	<s:if test="reply_time.time >= today.time">
							<s:date name="reply_time" format="HH:mm"/>
						</s:if>
						<s:elseif test="reply_time.time > year.time">
							<s:date name="reply_time" format="MM-dd HH:mm"/>
						</s:elseif>
						<s:else>
							<s:date name="reply_time" format="yyyy-MM-dd"/>
						</s:else>
                    	&nbsp;&nbsp;
                    	<a class="a_huifu" href="javascript:void(0);">回复</a>
                    </div>
                </li>
				</s:if>
            </s:iterator>
            	<!-- 每个回复信息，包含回复者的头像，ID，回复内容、举报回复链接等 -->
            	<!-- <li class="reply_list_main">
                	<a href="#"><img src="images/head.png" alt=""></a>
                    <div class="reply_list_content">
                    	<a href="#">独孤觅雪</a>：还说把拍照的任务交给你呢，你不是要拍999个吧友笑脸吗？这是个机会啊！
                    </div>
                    <div class="reply_time">
                    	<a class="a_jubao" href="#">举报</a> | 2012-12-29 18:13  
                        <a class="a_huifu" href="#">回复</a>
                    </div>
                </li>
                <li class="reply_list_main">
                	<a href="#"><img src="images/head.png" alt=""></a>
                    <div class="reply_list_content">
                    	<a href="#">独孤觅雪</a>：还说把拍照的任务交给你呢，这是个机会啊！
                    </div>
                    <div class="reply_time">
                    	<a class="a_jubao" href="#">举报</a> | 2012-12-29 18:13  <a class="a_huifu" href="#">回复</a>
                    </div>
                </li> -->
                <!-- 我也说一句，包含还有几条隐藏回复信息 -->
                <li class="reply_more">
                    <div class="pageLink" style="float:left; width:100%;">
	                    <a href="javascript:void(0);"></a><button style="float:right; font-size:12px;">我也说一句</button>
                    </div>
                    
                    <div class="clear_right"></div>
                    
                    <!-- 我也说一句, 包含隐藏域当前回复的userid，回复内容。 -->
                    <div class="hide_reply_input" style="display:none;">
                    	<input type="hidden" name="speak_too_superid" value="<s:property value="reply_id"/>">
                    	<textarea class="speaktoo" id="myEditor" style="font-size:14px;"></textarea>
                    	<!-- 如果用户尚未登录或用户被禁言，则给data属性分别赋值为0、-1，只有登录的正常用户值为1 -->
                    	<button type="button" class="speaktoo_submit" data="<s:property value="user_degree"/>">提交</button>
                    	<div class="speak_too_tip">&nbsp;</div>
                    </div>
                </li>
            </ul>
            
            <div class="clear"></div>
        <!-- 仅当作者的签名不为空， 且作者我使用了签名档方可显示签名 -->
        <s:if test="user_id.sign != '' && if_sign">
            <div class="clear_right"></div>
        	<div class="sign">
            	<header></header>
                <div class="sign_content">${user_id.sign }</div>
           	</div>
        </s:if>
        </div>
	</article>	 
</s:if>
</s:iterator>
<!-- 回复End -->
	
	<div class="clear"></div>
	<footer></footer>
</section>
<!-- section end -->

<aside>
	<div style="position:relative;top:-12px;"><div id="search_list">
		<ul>
			<!-- <li><a href="#">123</a></li>
			<li><a href="#">123</a></li>
			<li><a href="#">123</a></li>
			<li><a href="#">123</a></li>
			<li><a href="#">123</a></li> -->
		</ul>
	</div></div>
	<s:if test="user_degree == 0">
	<!-- 在用户未登录时显示的部分，登录和注册按钮 -->
	<article id="login_register">
        <h3>欢迎来到中北校友论坛！</h3>
        <span id="login_button">登录</span>
        <span id="register_button">注册</span>
    </article>
    </s:if>
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
                <span class="degree_id">${sessionScope.user.rank.grade}</span>
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
    	<p>板块信息</p>
		&nbsp;&nbsp;&nbsp;版主：
		<s:if test="admin != null">
			<a href="p?id=${admin.userid_encode}" target="_blank">${admin.user_id}</a><br>
		</s:if>
		<s:else>
			暂无<br>
		</s:else>
		&nbsp;&nbsp;&nbsp;主题数：${currentSubject.cate_id.subject_num }
    </article>
    <article id="friend_link">
    	<p>友情链接</p>
        <a href="http://http://www.nuc.edu.cn/" target="_blank">中北大学</a><br>
        <a href="http://202.207.182.100/new/index.action" target="_blank">中北大学图书馆</a><br>
        <a href="http://www1.nuc.edu.cn/jwc/" target="_blank">中北大学教务处</a>
    </article>
</aside>

<!-- aside end -->
<div class="clear"></div>

<footer id="footer">
&copy;2013 Fengxu at North University of China
</footer>
<!-- footer end -->

<!-- 浮动div部分，包括到顶部底部按钮、遮罩层、登录窗口、发帖窗口等。
<!-- 标题显示浮动层 -->
<div id="title_detail">
	主题：${currentSubject.sub_title }
	
		<span 
        <s:if test="user_degree == 0">onClick="$.myPlugin.msgAlert('您还没有登录')"</s:if>
			<s:elseif test="#session.user.identity == 0">onClick="$.myPlugin.msgAlert('您被封禁，暂时无法回复','warn')"</s:elseif>
			<s:else> class="reply" </s:else>
        >回复</span>
		
		<!-- 版主可以操作主题的“置顶、加精、移到、删除” -->
		<s:if test="user_degree == 5">
    	<span class="operateButton">操作
		<div class="operList">
			<ul>
				<s:if test="currentSubject.if_top">
					<li data="notop">取消置顶</li>
				</s:if>
				<s:else>
					<li data="top">置顶</li>
				</s:else>
				
				<s:if test="currentSubject.if_perfect">
					<li data="noperfect">取消加精</li>
				</s:if>
				<s:else>
					<li data="perfect">精品</li>
				</s:else>
				
				<li class="showMove" data="move">移动到
					<div class="moveTo">
						<ul>
							<!-- 遍历分类categoryList，生成链接 -->
							<s:iterator value="categoryList" id="category">
							<s:if test="cate_name != '精品区' && cate_name != '公告栏' && cate_id != currentSubject.cate_id.cate_id">
								<li data="${cate_id}">${cate_name}</li>
							</s:if>
							</s:iterator>
						</ul>
					</div>
				</li>
				<li data="delete">删除</li>
			</ul>
		</div></span>
		</s:if>
</div>

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
	<s:if test="#session.user == null">
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
<!-- 发表新回复窗口 -->
<div id="new_reply">
	<header><span>回&nbsp;&nbsp;复</span><span class="close"></span></header>
	<article>
		<div class="reply_move1"></div>
		<form action="">
			<!-- 隐藏域，存储当前主题ID -->
			<input type="hidden" name="sub_id" value="${currentSubject.sub_id}"/>
            <!-- UEditor文本输入框 -->
			<script type="text/plain" id="new_reply_content" name="reply_content" style="width:570px; height:304px;" ></script>
			<div class="clear"></div>
            <label class="reply_sign"><input type="checkbox" name="sign"value="yes" 
            <s:if test="#session.user.sign==''">disabled title="您还没有签名档，进入“我的中北”添加您的签名档"</s:if>
             >&nbsp;使用签名档</label>
            <div class="reply_move3"></div>
            <button id="new_reply_submit" type="button">回复</button>
        </form>
		<div class="reply_move1"></div>
	</article>
</div>
	</s:else>


<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery.prettySelect.js"></script>
<script type="text/javascript" src="js/jquery.simpleMove.js"></script>
<script type="text/javascript" src="js/jquery.pageNum.js"></script>
<script type="text/javascript" src="js/jquery.myWindow.js"></script>
<script type="text/javascript" src="js/jquery.scrollto.js"></script>
<script type="text/javascript" src="ueditor/editor_config.js"></script>
<script type="text/javascript" src="ueditor/editor_all.js"></script>
<script type="text/javascript" charset="utf-8" src="ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
<script type="text/javascript" src="js/jquery.myPlugin.js"></script>
<script type="text/javascript" src="js/list.js"></script>
<script type="text/javascript" src="js/single.js"></script>
<script type="text/javascript">
//渲染编辑器 
function render(){
    UE.getEditor('myEditor')
}
</script>

<script type="text/javascript">
function href(s){
	window.open(s);
}
$(document).ready(function(){
	var section_height = $("#section_in_single").height();
	//如果section高度小于460px，隐藏掉友情链接模块
	if(section_height < 460){
		$("#friend_link").remove();
	}
	$("aside").height(section_height);
	
	$("section > footer").pageNum({
		totalPage : ${replyPage.totalPage},
		currentPage : ${replyPage.currentPage},
		currentClass : "currentPage",
		otherClass : ""
	});
	
});
</script>

<script type="text/javascript">
	//判断是否已登陆, 如果未登陆, 不初始化UEditor
	var login_flag = <s:property value="#session.user != null"/>;
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
	            //minFrameHeight:283,
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
	            //最大字符数
	            maximumWords:1000,
	            
	            wordOverFlowMsg:'<span style="color:red;">你输入的字符个数已经超出最大允许值，服务器可能会拒绝保存！</span>'
	    } );
	    ue.render( 'new_reply_content' );
	
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
    
    SyntaxHighlighter.highlight();
</script>

</body>
</html>
