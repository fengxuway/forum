$(document).ready(function(e) {
	//当前用户ID
	var session_user_id = $("#links > input[name='session_user_id']").val();
	
	//aside中收信和写信的导航按钮
	var $shouxin = $("#shouxin");
	var $xiexin = $("#xiexin");
	
	//收信按钮点击时，显示收信list和收信内容
    $shouxin.click(function(e) {
		if(!$shouxin.hasClass("current")){
			$xiexin.removeClass("current");
			$shouxin.addClass("current");
			$("#for_xie").hide();
			if($(this).attr("data") == "1"){
				$("#for_shou").show();
			}else{
				$("#no_talk").show();
			}
		}
    });
	//写信按钮点击时，显示写信表单、隐藏收信内容
	$xiexin.click(function(e) {
		if(!$xiexin.hasClass("current")){
			$shouxin.removeClass("current");
			$xiexin.addClass("current");
			$("#for_shou").hide();
			$("#no_talk").hide();
			$("#for_xie").show();
		}
    });
	
	//点击“写私信”按钮
	$("#no_talk > button").click(function(){
		$xiexin.click();		
	});
	
////////////////////////////////////////////////////////////////////////////////////////
	//创建section对话框
	/*var createSectionHandler = function(data){
		
	}*/
	
	var createMyContent = function(element,ano){
		var anotherUser;
		if(!ano){
			if(session_user_id == element.dialog_id.userA.user_id){
				anotherUser = element.dialog_id.userB;
			}else{
				anotherUser = element.dialog_id.userA;
			}
		}else{
			anotherUser = ano;
		}
		return $("<article class='the_right'><div class='right_dialog'>"+
			"<div class='right_content'>" + element.content + "</div>"+
			"<div class='close'><input type='hidden' name='content_id' value='" + element.secret_id + "'></div><div class='clear'></div>"+
			"<time>" + element.content_time + "</time></div>"+
			"<div class='right_icon'></div></article><div class='clear'></div>");
	}
	
	
	
	
	var createOtherContent = function(element, ano){
		var anotherUser;
		if(!ano){
			if(session_user_id == element.dialog_id.userA.user_id){
				anotherUser = element.dialog_id.userB;
			}else{
				anotherUser = element.dialog_id.userA;
			}
		}else{
			anotherUser = ano;
		}
		return $("<article class='the_left'><div class='user_head'>"+
			"<img src='../images/head_img/" + anotherUser.photo + "' alt=''></div>"+
			"<div class='left_icon'></div><div class='left_dialog'>"+
			"<div class='close'><input type='hidden' name='content_id' value='" + element.secret_id + "'></div><div class='clear'></div>"+
			"<div class='left_content'>" + element.content + "</div>"+
			"<time>" + element.content_time + "</time></div></article><div class='clear'></div>");
	}
	
	
	//对话列表点击时，请求对应的对话内容
	$("#list > ul > li").live("click",function(event){
		var $current = $(this);
		var fontWeight = $current.children("div.user_link").children("span").css("fontWeight");
		var fwNum = parseInt(fontWeight);
		if(fwNum >= 0){
			fontWeight = fwNum;
		}
		
		if((fontWeight <= 400 || fontWeight =="normal" ) && $current.hasClass("main_current")){
			return false;
		}
		
		// 标记是否需要重新添加Dom结点，true为需要添加
		var flag = $("#content"+dialog_id).length == 0 ? true : false;
		
		//设置li样式及其兄弟节点的样式
		$current.addClass("main_current").siblings("li").removeClass("main_current");
		//获取对话ID
		var dialog_id = $current.attr("data");
		//如果对应的section#content+dialog_id的DOM结点不存在，在$("#main")中创建该结点，并进行ajax交互
		if(flag ||( typeof fontWeight == "number" && fontWeight > 400) ||
				(typeof fontWeight == "string" && fontWeight != "normal")){

			$.ajax({
				url:"../userAjax/secret",
				data:{method:"searchContent", dialog_id:dialog_id },
				dataType:"json",
				type:"post",
				beforeSend: function(){
					$("#loading").show();
				},
				error: function(){
					$("#loading").hide();
					$.myPlugin.msgAlert("网络异常，请稍候重试！", "warn");
				},
				success: function (data){
					$("#loading").hide();
					//标识session中的用户是否为对话中的A或B
					var currentSay = "A";
					if(data.contents.length>0){
						currentSay = session_user_id == data.contents[0].dialog_id.userA.user_id ? "A" :"B";
					}
					//另一个用户ID
					var another = $current.children("div.user_link").children("span").html();
					var $section = '';
					var $div_content = "";
					var iiii = typeof fontWeight;
					
					if(!flag){
						$section = $("#content"+data.dialog_id);
						$div_content = $section.children("div.content").empty();
					}else{
						$section = $("<section id='content" + data.dialog_id + "'></section>");
						$header = $("<header>我与&nbsp;<strong>" + another + "</strong>&nbsp;的私信</header>").appendTo($section);
						$div_content = $("<div class='content'></div>").appendTo($section);
					}
					$.each(data.contents, function(index, element){
						var dom;
						if(element.say == currentSay){
							//如果内容是当前用户说的
							dom = createMyContent(element);
						}else{
							//如果内容是another用户说的
							dom = createOtherContent(element);
						}
						dom.appendTo($div_content);
					});
					if(flag){
						$("#input").before($section);
					}
					$current.children("div.user_link").children("span").css("fontWeight", 400);
					
					$section.show().siblings("section").hide();
					$div_content.scrollTop($div_content.scrollTop() + 10000000);
				}
			});
			
			
		//如果存在，不再进行ajax交互，直接显示对应的DOM
		}else{
			$("#content"+dialog_id).show().siblings("section").hide();
		}
		
	});
	
	//网页加载完成后点击#list中的第一个li，加载内容
	$("#list > ul > li:first").click();
	
	//点击回复按钮
	$("#input_submit").click(function(){
		var content = getUE1Content();
		//如果用户没输入任何东西，不进行ajax请求
		if(content.length == 0){
			return false;
		}
		var anotherUser = $("#list > ul > li.main_current > div.user_link > span").html();

		$.ajax({
			url:"../userAjax/secret",
			data:{method:"publish", anotherUser_id:anotherUser, content:content},
			dataType:"json",
			type:"post",
			error: function (){
				$.myPlugin.msgAlert("网络异常，请稍候重试！", "warn");
			},
			success: function (data){
				// 请求成功
				if(data.msgAjax=="1"){
					var $content = $("#content"+data.secretContent.dialog_id.dialog_id);
					var $div_content = $content.children("div.content");
					createMyContent(data.secretContent).appendTo($div_content);
					$div_content.scrollTop($div_content.scrollTop() + 10000);
					
					//重置编辑器
					reset();
				}
				// 请求失败
				else{
					$.myPlugin.msgAlert("回复失败，请稍候重试！","warn");
				}
			}
			
		});
		
	});
	
	
	// 点击私信内容的 x
	$("#main > section > div > article > div > div.close").live("click", function(){
		var $current = $(this);
		//弹出confirm确认框
		$.myPlugin.prettyAlert("您确定要删除吗？","confirm",function(){
			var $article = $current.parents("article");
			var content_id = $current.children("input").val();
			// ajax请求更改当前用户visible为false
			$.ajax({
				url:"../userAjax/secret",
				data:{method:"deleteContent", content_id:content_id},
				dataType:"json",
				type:"post",
				error: function (){
					$.myPlugin.msgAlert("网络异常，请稍候重试！", "warn");
				},
				success: function (data){
					// 请求成功
					if(data.msgAjax=="1"){
						// 如果请求成功，移除对应的$article及div.clear
						$article.next("div.clear").remove();
						$article.remove();
					}
					// 请求失败
					else{
						$.myPlugin.msgAlert("删除失败，请稍候重试！","warn");
					}
				}
				
			});
		});
	});
	
	// 点击＃list下的li的 x
	$("#list > ul > li > div.main_close").live("click", function(){
		var $current = $(this);
		//弹出confirm确认框
		$.myPlugin.prettyAlert("您确定要删除吗？","confirm",function(){
			var $article = $current.parents("article");
			var dialog_id = $current.parent().attr("data");
			// ajax请求更改当前用户visible为false
			$.ajax({
				url:"../userAjax/secret",
				data:{method:"deleteDialog", dialog_id:dialog_id},
				dataType:"json",
				type:"post",
				error: function (){
					$.myPlugin.msgAlert("网络异常，请稍候重试！", "warn");
				},
				success: function (data){
					// 请求成功
					if(data.msgAjax=="1"){
						// 如果请求成功，移除对应的$article及div.clear
						$current.parent().remove();
						$("#content"+dialog_id).remove();
						if($("#list > ul > li").length >0){
							$("#list > ul > li:first").click();
						}else{
							location.reload();
						}
					}
					// 请求失败
					else{
						$.myPlugin.msgAlert("删除失败，请稍候重试！","warn");
					}
				}
				
			});
		});
		
	});
	
	
	
	//转换UserID, 正确计算UserID的长度
	var transformUserid = function(userid){
		var userReg = /^[a-zA-Z0-9_-]{3,14}$/;
		return userid.replace(/[\u2E80-\u9FFF]/g, "12");
		//return userReg.exec(s);
	}
	// 写私信提交按钮点击时
	// 如果填写的用户不存在，取消提交数据
	// 如果私信内容为空，取消提交数据
	$("#xie_submit").click(function(event){
		var $current = $(this);
		var $xie_name = $("#xie_name");
		var anotherUser_id = $.trim($xie_name.val());
		if(anotherUser_id == $("#links > a:first").html()){
			$.myPlugin.msgAlert("您不能给自己发私信！","warn",1500);
			return false;
		}
		var content = getUE2Content();
		$("#input_content").val(content);
		if(content == ""){
			$.myPlugin.msgAlert("私信内容不能为空！","warn",1500);
		}else if(anotherUser_id == ""){
			$.myPlugin.msgAlert("收信人不能为空！","warn",1500);
		}else{
			// 判断填写的用户是否存在
			//用户名正则表达式
			var userReg = /^[a-zA-Z0-9_-]{3,16}$/;
			var transUserid = transformUserid(anotherUser_id);
			
			if(userReg.exec(transUserid) != null){
				//正则表达式匹配成功
				$.ajax({
					url:"../ajax/user_userExist",
					data:{userid:anotherUser_id},
					type:"post",
					dataType:"json",	
					before: function(){
						$current.attr("disabled", "disabled");
					},
					success: function (data){
						$current.removeAttr("disabled");
						if(data.msgAjax == "0"){
							// 用户名不存在
							$.myPlugin.msgAlert("您填写的收信人不存在！","warn",1500);
							$("#publishSecretForm").submit();
						}else{
							$("#publishSecretForm").submit();
						}
					},
				});
			}else{
				$.myPlugin.msgAlert("您填写的收信人格式不正确！","warn",1500);
				e.preventDefault();
			}
		}
		
		
	});
	
	////////////////////////////////////////////////////////////////////////////////////////////
	
	var createListHandler = function(element){
		var anotherUser;
		if(element.userA.user_id == session_user_id){
			anotherUser = element.userB;
		}else{
			anotherUser = element.userA;
		}
		
		return $("<li data='" + element.dialog_id + "'><div class='select'>"+
			"</div><div class='user_link'>"+
			"<a href='../p?id=" + anotherUser.userid_encode + "' target='_blank'>"+
			"<img src='../images/head_img/" + anotherUser.photo + "' alt=''></a>"+
			"<span style='font-weight:bold;'>"+anotherUser.user_id+"</span>"+
			"</div><div class='main_close'></div></li>");
	};
	
	// 每隔一段时间，请求一次数据，查询是否存在未读数据
	setInterval(function(){
		
		var dsk = $("#list > ul > li.main_current");
		var content_data = $("#list > ul > li.main_current").attr("data");
		
		$.ajax({
			url:"../userAjax/secret",
			data:{method:"searchDialog"},
			type:"post",
			dataType:"json",	
			success: function (data){
				$.each(data.dialogs, function(index, element){
					var $content = $("#content"+element.dialog_id);
					
					// 如果不存在对应的content, 并且不存在对应的li[attr=dialog_id],则创建li，为其加粗
					if($content.length <= 0 && $("#list > ul > li[data='" + element.dialog_id + "']").length <= 0){
						var dom = createListHandler(element);
						$("#list > ul").append(dom);
					} else if($content.length > 0){
						// 如果存在，判断对应的li是否为main_content样式
						if(content_data != element.dialog_id){
							//　如果不是当前会话，对新增数据li字体加粗即可
							if($content.children("div.content").children("article").length < element.secretContents.length){
								$("#list > ul > li[data='"+element.dialog_id+"'] > div.user_link > span").css("fontWeight","bold");
							}
						}
					}
				});
			}
		});
	}, 10000);
	
	
	// 获取当前对话main_current中用户尚未读的内容（时间正序）
	setInterval(function(){
		var dialog_id = $("#list > ul > li.main_current").attr("data");
		//标识session中的用户是否为对话中的A或B
		var currentSay = "A";
		var $div_content = $("#content"+dialog_id+" > div.content");
		$.ajax({
			url:"../userAjax/secret",
			data:{method:"searchUnreadContent", dialog_id:dialog_id},
			type:"post",
			dataType:"json",	
			success: function (data){
				if(data.contents.length>0){
					currentSay = session_user_id == data.contents[0].dialog_id.userA.user_id ? "A" :"B";
				}
				// 请求成功
				$.each(data.contents, function(index, element){
					
					var dom;
					// 判断当前用户是A还是B
					if(element.say == currentSay){
						//如果内容是当前用户说的
						dom = createMyContent(element);
					}else{
						//如果内容是another用户说的
						dom = createOtherContent(element);
					}
					dom.appendTo($div_content);
					$div_content.scrollTop($div_content.scrollTop() + 200);
					
				});
				
			}
		});
		
	},3000);
	
	
	
});
	