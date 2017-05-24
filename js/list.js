$(document).ready(function(e) {
	var $window = $(window);
	var $document = $(document);
	var $header = $("header:first");
	var headerOffset = $header.offset();
	var $main_nav = $("#main_nav");
	var $title_detail = $("#title_detail");
	//到顶部底部按钮的容器
	var $top_bottom = $("#top_bottom");
	//到顶部到底部
	var $totop = $("#totop");
	var $tobottom = $("#tobottom");
	//登录浮动窗口
	var $login = $("#login");
	//发表新帖浮动窗口
	var $new_theme = $("#new_theme");

	//美化下拉菜单
	$("#new_type").prettySelect({width:150});
	$("#search_select").prettySelect();
	

	
	
	//窗口改变大小时重新计算top按钮与bottom按钮的位置，以及阴影#shade的大小
	$top_bottom.css("top", headerOffset.top + 400).css("left", headerOffset.left + 990);
	$window.resize(function(e){
		$top_bottom.css("top", $header.offset().top + 400).css("left", $header.offset().left + 990);
		//$shade.css({width:$document.width(), height:$document.height()});
	});
	
	
	$window.scroll(function(e) {		
		//滚动条滚动到某位置，固定nav位置
		var scrT = $window.scrollTop();
		if(scrT > 160){
			$main_nav.css("position","fixed").css("top", 0);	
		}else{
			$main_nav.css("position","absolute").css("top","160px");	
		}
		if(scrT>210){
			$title_detail.fadeIn(100);
		}else{
			$title_detail.fadeOut(100);
		}
		//滑动滚动条时top与bottom按钮的显示与隐藏
        if(scrT>10){
			$totop.stop(false, true).fadeIn("fast");	
		}else{
			$totop.stop(false, true).fadeOut("fast");
		}
		if($document.height() - scrT -$window.height() < 10){
			$tobottom.stop(false, true).fadeOut("fast");
		}else{
			$tobottom.stop(false, true).fadeIn("fast");
		}
    });
	
	//设置登录窗口移动
	$login.simpleMove({
		target:$("#login").children("header"),
		except:$("#login > header").children("span")
	});
	
	//发表新帖窗口可移动、可关闭
	$new_theme.simpleMove({
		target:[$new_theme.find("header"), $new_theme.find("div.moves1"), $new_theme.find("div.moves2"), $new_theme.find("div.moves3")],
		except:$new_theme.find("span.close")
	});
	
	
	
	//登陆按钮点击
	$("#login_button").click(function(){
//		$shade.fadeIn("fast");
		$login
		//点击登录按钮弹出窗口及其关闭
		.myWindow({left:"center",right:"right",close: $login.children("header").children("span.close")});
	});
	
	//用户名正则表达式
	var userReg = /^[a-zA-Z0-9_-]{3,14}$/;
	var emailReg = /^([a-zA-Z0-9_\.-]+)@([\da-zA-Z\.-]+)\.([a-zA-Z\.]{2,6})$/;
	var pswdReg = /^.{3,16}$/;
	var transformUserid = function(userid){
		var userReg = /^[a-zA-Z0-9_-]{3,14}$/;
		return userid.replace(/[\u2E80-\u9FFF]/g, "12");
		//return userReg.exec(s);
	}
	//登录请求
	var loginHandler = function(){
		var name = $login.find("input[name='userid']").val();
		var pswd = $login.find("input[name='pswd']").val();
		var trannsUserid = transformUserid(name);
		if((userReg.exec(trannsUserid) != null || emailReg.exec(name) != null) && pswdReg.exec(pswd) != null){
			//用户名和密码格式输入正确，验证登陆
			
			$.ajax({
				url:"ajax/user_login",
				data:{
					userid: name,
					pswd: pswd
				},
				dataType:"json",
				type:"post",
				beforeSend: function(xhr){
					//$("#login_shade").show();
					$("#tip").html("<img src='images/loading.gif'>正在登陆。。。");
				},
				error: function (){
					$("#tip").html("服务器发生异常，请稍后重试！");
				},
				complete: function(){
					//$("#login_shade").hide();
				},
				success: function (data){
					if(data.msgAjax == "1"){
						//登录成功
						$("#tip").html("<span style='color:#565656'>登录成功</span>");
						setTimeout(function(){
							window.location.reload();
						}, 300);
						
					}else{
						$("#tip").html("<span style='color:#e3030f'>登录失败，用户名或密码输入错误！</span>");
					}
				}
			});
		}else{
			$("#tip").html("<span style='color:#e3030f'>用户名或密码格式不正确，请检查您的输入</span>");
		}		
	};
	
	//点击登录button开始登录请求
	$login.find("button").click(loginHandler);
	
	$login.find("input[name='userid']").keydown(function(event){
		if(event.keyCode == 13){
			$login.find("input[name='pswd']").focus();
		}
	});
	$login.find("input[name='pswd']").keydown(function(event){
		if(event.keyCode == 13){
			loginHandler();
		}
	});
	
	
	
	//部分表单获取焦点与失去焦点改变CSS样式
	$("#section_in_single textarea").focus(function(e) {$(this).css("border-color", "#8BDB57");})
		.blur(function(e) {$(this).css("border-color", "#cdcdcd");});
	
	
	$("#section_in_index article").hover(function(){
		$(this).stop(false, true).addClass("article_hover", 300);
		
		}, function(){
		$(this).stop(false, true).removeClass("article_hover", 300);		
	});
	
	/**
	 * 点击发表新帖按钮
	 */
	$("#publish").click(function(){
			$new_theme.myWindow({
				close:$new_theme.children("header").children("span.close"), 
				ifmove:false, 
				closeFunc:function(){$("#new_theme").find("div.moves3").html("");}
			});
	});
	
	//点击发表新帖#new_theme_submit按钮，提交请求
	$("#new_theme_submit").click(function(){
		var cate_id = $new_theme.find("input[name='cate_id']").val();
		var sub_title = $new_theme.find("input[name='sub_title']").val();
		var sub_content = getContent();
		var if_sign = false;
		if($new_theme.find("input[name='if_sign']").is(":checked") &&  $new_theme.find("input[name='if_sign']").attr("disabled")==null ){
			if_sign = true;
		}
		 
		if(cate_id!="" && sub_title !="" && sub_title.length<=30 && getContentTxt().length<=2000){
			$.ajax({
				url:"userAjax/subject_publishSubject",
				data:{
					cate_id:cate_id,
					sub_title:sub_title,
					sub_content:sub_content,
					if_sign:if_sign
				},
				dataType:"json",
				type:"post",
				context: $("#new_theme").find("div.moves3"),
				beforeSend: function(xhr){
					$(this).html("<span style='color: #666;'>正在提交到服务器。。。</span>");
				},
				error: function (){
					$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
				},
				success: function (data){
					//如果响应正确，显示信息并刷新页面
					if(data.msg == "1"){
						$(this).html("<span style='color: #0412D1;'>发表成功！</span>");
						setTimeout(function(){
							window.location.href="list?cate_id="+cate_id;
						}, 300);
					//否则，显示错误信息
					}else{
						$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
					}
				}
			});
			
		}else{
			if(cate_id==""){
				$("#select_msg").html("请选择分类");
			}else if(sub_title ==""){
				$new_theme.find("input[name='sub_title']").focus();
			//如果输入内容超出1000个字符，获取焦点
			}else if(getContentTxt().length>2000){
				setFocus();
			}
		}
		
	});
	//点击select时，后面的提示语隐藏
	$("div.select-head").click(function(){$("#select_msg").html("&nbsp;");})
	
	
	
	/**********************************以下是搜索部分*****************************************/
	var $search_text = $("#search_text");
	var $search_list = $("#search_list");
	var $search_list_ul = $("#search_list > ul");
	
	var showSearch = function(value){
		var search_type = $("#search input[name='search_type']").val();
		var kw = $.trim(value);
		if(kw != "" && search_type == "0"){				
			// 搜索用户
			$.post("ajax/user_method",{method:"searchUser", kw:kw},function(data){
				$search_list_ul.empty();
				if(data.searchUserList.length > 0){
					$.each(data.searchUserList, function(index, element){
						$search_list_ul.append("<li><a target='_blank' href='p?id="+element.userid_encode+"'>"+element.user_id+"</a></li>");
					});
					$search_list.show();
				}else{
					$search_list.hide();				
				}
			},"json");
		}else if(kw != "" && search_type =="1"){
			$.post("ajax/user_method",{method:"searchSubject", kw:kw},function(data){
				$search_list_ul.empty();
				if(data.searchSubjectList.length > 0){
					$.each(data.searchSubjectList, function(index, element){
						$search_list_ul.append("<li><a target='_blank' href='single?sub_id="+element.sub_id+"'>"+element.sub_title+"</a></li>");
					});
					$search_list.show();
				}else{
					$search_list.hide();				
				}
			},"json");
			
		}else{
			$search_list_ul.empty();
			$search_list.hide();
		}
	}
	$search_text.keyup(function(e){
		showSearch($(this).val());
		/*if(e.keyCode == 13){
			// $("#search_submit").click();
			e.preventDefault();
			var search_type = $("#search input[name='search_type']").val();
			var kw = $.trim($search_text.val());
			if(search_type == "0" && $search_list_ul.children("li").length > 0){
				// 搜索会员
				// 如果搜索结果有信息，转到第一个结果
				window.open($search_list_ul.children("li:first").children("a").attr("href"));
			}else if(search_type == "1" && kw != ""){
				$("#search > form").submit();			
			}else if(search_type == "0" && $search_list_ul.children("li").length == 0){
				$.myPlugin.msgAlert("没有搜索到该会员！","info",1500);
			}
			e.stopPropatation();
		}*/
	}).keydown(function(e){
		if(e.keyCode == 13){
			$("#search_submit").click();
			e.preventDefault();
		}
	})
	.focus(function(){
		showSearch($(this).val());
	});
	$(document).click(function(){
		setTimeout(function(){$search_list.hide();},100);
	});
	$search_list.add($search_text).click(function(e){
		e.stopPropagation();
	});
	$search_list.click(function(){
		setTimeout(function(){$search_list.hide();},100);
	});
	// 点击搜索提交按钮
	$("#search_submit").click(function(){
		var search_type = $("#search input[name='search_type']").val();
		var kw = $.trim($search_text.val());
		if(search_type == "0" && $search_list_ul.children("li").length > 0){
			// 搜索会员
			// 如果搜索结果有信息，转到第一个结果
			window.open($search_list_ul.children("li:first").children("a").attr("href"));
		}else if(search_type == "1" && kw != ""){
			$("#search > form").submit();			
		}else if(search_type == "0" && $search_list_ul.children("li").length == 0){
			$.myPlugin.msgAlert("没有搜索到该会员！","info",1500);
		}
	});
	
	$(window).scroll(function(){
		setTimeout(function(){$search_list.hide();},100);
	});

});
