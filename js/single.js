$(document).ready(function(){
	//回复浮动窗口
	var $new_reply = $("#new_reply");
	//发表回复按钮
	var $publishReply = $("#publishReply");
	//初始化浮动窗口
	$new_reply.simpleMove({
		target:[$new_reply.find("header"), $new_reply.find("div.reply_move1"), $new_reply.find("div.reply_move3")],
		except:$new_reply.find("span.close")
	});
	
	$publishReply.click(function(){
		$new_reply.myWindow({close:$new_reply.children("header").children("span.close"), ifmove:false});		
	});

	//点击回复按钮，弹出回复窗口
	$("#section_in_single > header > span.reply").click(function(){
		$new_reply.myWindow({close:$new_reply.children("header").children("span.close"), ifmove:false});		
	});
	$("#title_detail > span.reply").click(function(){
		$new_reply.myWindow({close:$new_reply.children("header").children("span.close"), ifmove:false});		
	});
	
	//点击提交回复按钮，提交表单
	$("#new_reply_submit").click(function(){
		var reply_content = getContent();
		var if_sign = ($("#new_reply").find("input[type='checkbox'][name='sign']:checked").val()==undefined) ? false : true;
		var sub_id = $("#new_reply").find("input[type='hidden'][name='sub_id']").val();
		var reply = getContentTxt();
		
		//字符没超过1000，添加到服务器
		if(getContentTxt().length <= 1000){
			//Ajax交互
			$.ajax({
				url:"userAjax/reply_publishReply",
				data:{ 
					sub_id: sub_id,
					if_sign: if_sign,
					reply_content: reply_content
				},
				dataType:"json",
				type:"post",
				context: $("#new_reply").find("div.reply_move3"),
				beforeSend: function(){
					$(this).html("<span style='color: #666; background:url(images/wait.gif) no-repeat; padding-left:16px;'>正在提交到服务器。。。</span>");
				},
				error: function (){
					$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
				},
				success: function (data){
					if(data.msgAjax == "1"){
						$(this).html("<span style='color: #0412D1;'>发表成功！</span>");
						setTimeout(function(){
							var anchor = data.reply_id;
							var hrefFinal = "single?sub_id="+$("input[type='hidden'][name='currentSubject_id']").val();
							var href = window.location.href;
							
							//更改page页码的值+1，以使页面能够刷新
							var pageReg = /&page=\d+/;
							if(pageReg.exec(href) != null ){
								var regTmp = pageReg.exec(href)[0];
								var pageNum = parseInt(regTmp.substring(6, regTmp.length))+1;
								hrefFinal = hrefFinal + "&page="+pageNum;
							}else{
								hrefFinal = hrefFinal + "&page=10000";
							}
							
							hrefFinal = hrefFinal + "#" + anchor;
							window.location.href = hrefFinal;
						}, 300);
					}else{
						$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
					}
				}
				
			});
		}else{
			$(this).html("<span style='color: #E30F06;'>您输入的内容已超限，请控制在1000字符内。</span>");
		}
		
	});
	
	
	
	//更新“还有几条回复，点击查看”链接信息
	var nextPageLinkUpdateHandler = function($ul){
		//点击显示更多链接
		var $nextPageLink = $ul.children("li.reply_more").children("div.pageLink").children("a");
		
		var $hiddenReply = $ul.children("li.reply_list_main:hidden");
		//未显示总记录数
		var hiddenRow = $hiddenReply.length;
		
		if(hiddenRow > 0){
			$nextPageLink.html("还有 "+ hiddenRow +" 条回复，点击查看")
		}else{
			$nextPageLink.html("");
		}
	}
	
	
	//初始化页面时，将没有回复列表的
	$("div.tie_main").each(function(index, element){
		var $current = $(this);
		var $ul = $current.children("ul");
		var $a_huifu = $current.children("div.info").children("a.a_huifu");
		if($ul.children("li.reply_list_main").length==0){
			$ul.hide();
			$a_huifu.html("回复");
		}else{
			$a_huifu.html("收起回复");
			$ul.children("li.reply_list_main").each(function(index){
				if(index >= 0 && index < 5 && index < $ul.children("li.reply_list_main").length){
					$(this).show();
				}else{
					$(this).hide();
				}
			});
		}
		
		
		nextPageLinkUpdateHandler($ul);
		
		
		//每个楼层点击回复链接，展开回复列表
		//并将名字改为“收起回复”
		$a_huifu.click(function(){
			var flag = $ul.css("display")=="none";
			if(flag){
				$ul.children("li.reply_list_main").each(function(index){
					if(index >= 0 && index < 5 && index < $ul.children("li.reply_list_main").length){
						$(this).show();
					}else{
						$(this).hide();
					}
				});
				$ul.slideDown("fast");
				$a_huifu.html("收起回复");
				$ul.find("textarea").focus();
				
				//当前回复部分输入框显示，隐藏其他回复部分的输入框
				$("div.hide_reply_input:visible").hide();
				$current.find("div.hide_reply_input").show();
			}else{
				$ul.slideUp("fast");
				$a_huifu.html("回复");
				//重置提示信息内容为空
				$ul.find("div.speak_too_tip").html("&nbsp;");
			}
			
			nextPageLinkUpdateHandler($ul);
			
			//更改aside高度与section一致
			setTimeout(function(){$("aside").delay(500).height($("#section_in_single").height());}, 300);
		});
		
		//我也说一句按钮点击时，显示当前回复部分的输入框， 并隐藏其他输入框
		$ul.children("li.reply_more").children("div.pageLink").children("button").click(function(){
			var $current_button =$(this);
			//var $reply_input = $ul.find("div.hide_reply_input");
			var $reply_input = $current_button.parent().next().next("div.hide_reply_input");
			//判断当前输入框是否显示，如果未显示，显示该输入框，隐藏其他输入框
			if($reply_input.is(":hidden")){
				$("div.hide_reply_input:visible").not($reply_input).slideUp("fast").children("textarea").html("");
				$reply_input.slideDown("fast");
			}
			
			var $textarea = $current_button.parent().next().next("div.hide_reply_input").children("textarea");//$ul.find("textarea");
			var reply_content = $textarea.val();
			if(reply_content!=null && reply_content != ""){
				$textarea.val(reply_content.replace(/^回复\s+[a-zA-Z0-9_\u2E80-\u9FFF-]{3,14}?：/, "")).focus();
			}else{
				$textarea.val("").focus();
			}
			//更改aside高度与section一致, 延迟300毫秒等待动画执行完毕，“fast”=200毫秒
			setTimeout(function(){$("aside").height($("#section_in_single").height());}, 300);
		});
		
		
		
		
		//回复列表下面的分页链接
		$ul.children("li.reply_more").children("div.pageLink").children("a").click(function(){
			var $hiddenReply = $ul.children("li.reply_list_main:hidden");
			$hiddenReply.each(function(index){
				if(index >= 0 && index < 5){
					$(this).slideDown(400);
				}
			});
			
			nextPageLinkUpdateHandler($ul);
		});
	});
	
	/**
	 * 创建回复结点，用于异步请求回复回复帖时，正确请求后创建回复结点
	 * @param sub_reply_id 刚刚保存的回复的id
	 * @param content 回复的内容
	 * @param 用户权限
	 * @returns 返回创建的结点的jquery对象
	 */
	var createReplyDomHandler = function(sub_reply_id, content, lmt){
		//头像与用户名来自于aside部分中个人信息#own_photo>img  #own_name>span
		var $createDom = $("<li class='reply_list_main'><a name='"+sub_reply_id+"' class='reply_anchor'></a><a href='#'>"+$("#own_photo").html()+"</a>" +
				"<div class='reply_list_content'><a href='#'>"+$("#own_name > span").html()+"</a>："+content+"</div>" +
				"</li>");
		var $reply_time = $("<div class='reply_time'></div>");
		if(lmt == 5){
			$reply_time.html("<a class='a_jubao' href='javascript:void(0);'>封</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a class='a_jubao' href='javascript:void(0);'>删</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}else if(lmt == 3 || lmt == 7){
			$reply_time.html("<a class='a_jubao' href='javascript:void(0);'>删</a>&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		$reply_time.append("刚刚&nbsp;&nbsp;&nbsp;&nbsp;<a class='a_huifu' href='javascript:void(0);'>回复</a>");
		$reply_time.appendTo($createDom);
		
		return $createDom;
	}
	
	//点击每个回复部分的“提交按钮”时，提交回复内容。
	$("button.speaktoo_submit").click(function(){
		var $current = $(this);
		//获取data属性值并判断用户权限，data=0表示用户未登录，-1表示用户被封禁
		var lmt = $current.attr("data");
		if(lmt == "0"){
			$.myPlugin.msgAlert("您还没有登录！");
			return false;
		}else if(lmt == "-1"){
			$.myPlugin.msgAlert("对不起，您已被禁言！","warn");
			return false;
		}
		
		/**
		 * sub_id 当前主题ID
		 * reply_id 当前回复的楼层ID
		 * content 回复内容
		 */
		var sub_id = $("body > header > input[type='hidden'][name='currentSubject_id']").val();
		var reply_id = $current.prevAll("input[type='hidden'][name='speak_too_superid']").val();
		var content = $.trim($current.prev("textarea").val());
		
		if(content == ""){
			$current.next("div.speak_too_tip").html("<span style='color: #E30F06;'>您还没有输入内容！</span>");
			return false;
		}
		//Ajax交互提交回复内容
		$.ajax({
			url:"userAjax/reply_publishReply",
			data:{ 
				sub_id: sub_id,
				if_sign: false,
				reply_content: content,
				if_reply: true,
				reply_superid: reply_id
			},
			dataType:"json",
			type:"post",
			context: $current.next("div.speak_too_tip"),
			beforeSend: function(){
				$(this).html("<span style='color: #666; background:url(images/wait.gif) no-repeat; padding-left:16px;'>正在提交到服务器。。。</span>");
			},
			error: function (){
				$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
			},
			success: function (data){
				if(data.msgAjax == "1"){
					$(this).html("<span style='color: #0412D1;'>&nbsp;</span>");
					$(this).parents("li.reply_more").before(createReplyDomHandler(data.reply_id, content, lmt));
//					showPageNumHandler($(this).parents("ul"),1000);
					$current.prev("textarea").val("");
					
					nextPageLinkUpdateHandler($(this).parents("ul"));
					//添加结点后，更新aside高度
					$("aside").height($("#section_in_single").height());
				}else{
					$(this).html("<span style='color: #E30F06;'>服务器发生异常，请稍后重试。</span>");
				}
			}
			
		});
		
		
	});	
	
	
	
	//点击回复的回复的回复链接，利用正则表达式更改textarea的内容
	$("#section_in_single > article > div.tie_main > ul > li.reply_list_main > div.reply_time > a.a_huifu").live("click",function(){
		var contentRegex = /^回复\s+[a-zA-Z0-9_\u2E80-\u9FFF-]{3,14}?：/;
		//被回复的用户名：
		var reply_user = "回复 "+$(this).parent("div.reply_time").prev("div.reply_list_content").children("a").html()+"：";
		
		var $textarea = $(this).parents("ul").children("li.reply_more").children("div.hide_reply_input").children("textarea");
		var reply_content = $textarea.val();
		
		if(reply_content == ""){
			$textarea.val(reply_user);
		}else if(contentRegex.exec(reply_content) == null){
			$textarea.val(reply_user + reply_content);				
		}else{
			$textarea.val(reply_content.replace(/^回复\s+[a-zA-Z0-9_\u2E80-\u9FFF-]{3,14}?：/, reply_user));
			
		}
		
		var $reply_input = $(this).parents("ul").find("div.hide_reply_input");
		//判断当前输入框是否显示，如果未显示，显示该输入框，隐藏其他输入框
		if($reply_input.is(":hidden")){
			$("div.hide_reply_input:visible").hide().html("");
			$reply_input.slideDown("fast");
		}
		$textarea.focus();
		
		//更改aside高度与section一致, 延迟300毫秒等待动画执行完毕，“fast”=200毫秒
		setTimeout(function(){$("aside").height($("#section_in_single").height());}, 300);
	});
	
	
	var forbid = function(user_id){
		var currentUser = $("#own_name > span").html();
		if(user_id == currentUser){
			$.myPlugin.msgAlert("您不能封禁自己！");
			return false;
		}
		//封禁楼主
		$.myPlugin.prettyAlert("请选择封禁天数：" +
			"<select id='days'>" +
			"	<option value='1'>1</option>" +
			"	<option value='3'>3</option>" +
			"	<option value='5'>5</option>" +
			"	<option value='10'>10</option>" +
			"</select>",
			"confirm",
			function(){
				var days = $("#days").val();
				$.post("ajax/user_method", {method:"forbid", userid: user_id, days:days},
					function(data){
						if(data.msgAjax == "1"){
							$.myPlugin.msgAlert("封禁成功！");
						}else{
							$.myPlugin.msgAlert(data.msgAjax);
						}
					}
					,"json");
			}
		);
	}
	
	
	//删除回复楼层
	$("#section_in_single > article > div.tie_main > div.info >  a.a_jubao").click(function(){
		var $current = $(this);
		
		//如果点击了1楼删除和封禁，按操作主题处理
		if($current.parents("div.tie_main").attr("data")=="1"){
			if($current.html() == "删"){
				$.myPlugin.prettyAlert("您确定要删除该主题吗？","confirm", function(){
					
					$.ajax({
						url:"userAjax/subject_updateState",
						data:{ 
							sub_id: $("input[type='hidden'][name='currentSubject_id']").val(),
							operate_flag: "delete"
						},
						dataType:"json",
						type:"post",
						error: function (){
							$.myPlugin.msgAlert("服务器异常！","warn");
						},
						success: function (data){
							if(data.msg == "1"){
								$.myPlugin.msgAlert("删除成功！");
								setTimeout(function(){
									window.location.href='list?cate_id='+$("input[type='hidden'][name='currentCate_id']").val();
								}, 500);
								return false;
							}else{
								$.myPlugin.msgAlert("删除失败！","warn");
							}
						}					
					});
					
				});
				
			}else if($current.html() == "封"){
				var user_id = $current.parents("article").children("div.user").children("figure").children("figcaption").html();
				forbid(user_id);
			
			}			
			return false;
		}
		
		//删除回复
		if($current.html()=="删"){			
			$.myPlugin.prettyAlert("您确定要删除该回复吗？","confirm",
				function(){
					var reply_id = $current.parents("div.tie_main").children("a.reply_anchor").attr("name");
					$.ajax({
						url:"userAjax/reply_deleteReply",
						data:{ 
							reply_id: reply_id
						},
						dataType:"json",
						type:"post",
						error: function (){
							$.myPlugin.msgAlert("服务器异常！","error");
						},
						success: function (data){
							if(data.msgAjax == "1"){
								$.myPlugin.msgAlert("删除成功！");
								setTimeout(function(){window.location.reload();},1000);
							}else{
								$.myPlugin.msgAlert("删除失败！","warn");
							}
						}					
					});
				}
			);
			
		}else if ($current.html()=="封"){
		//封禁
			var user_id = $current.parents("article").children("div.user").children("figure").children("figcaption").html();
			forbid(user_id);
		}	
	});
	
	
	
	
	
	$("#section_in_single > article > div.tie_main > ul > li.reply_list_main > div.reply_time > a.a_jubao").live("click",function(){
	//删除回复的回复与封禁楼主
		var $current = $(this);
		//删除回复贴
		if($current.html()=="删"){
			$.myPlugin.prettyAlert("您确定要删除该回复吗？","confirm",
				function(){
					var reply_id = $current.parents("li.reply_list_main").children("a.reply_anchor").attr("name");
					$.ajax({
						url:"userAjax/reply_deleteReply",
						data:{ 
							reply_id: reply_id
						},
						dataType:"json",
						type:"post",
						error: function (){
							$.myPlugin.msgAlert("服务器异常！","warn");
						},
						success: function (data){
							if(data.msgAjax == "1"){
								$.myPlugin.msgAlert("删除成功！");
								$current.parents("li.reply_list_main").remove();
							}else{
								$.myPlugin.msgAlert("删除失败！","warn");
							}
						}					
					});
				}
			);
			
			
		}else if($current.html()=="封"){
			var user_id = $current.parent().prev("div.reply_list_content").children("a").html();
			forbid(user_id);			
		}
	});
	
	//点击“操作”按钮，显示操作列表；点击网页其他位置，隐藏列表
	$("span.operateButton").click(function(){
		var $operList = $(this).find("div.operList");
		if($operList.is(":hidden")){
			$operList.slideDown("fast");
		}
		return false;
	});	
	$(document).click(function(){$("div.operList").hide();});
	$(window).resize(function(){$("div.operList").hide();}).scroll(function(){$("#section_in_single div.operList").hide();});
	
	//点击操作列表的节点
	$("div.operList > ul > li").click(function(){
		var $current = $(this);
		var current_html = $current.html();
		var current_data = $current.attr("data");
		
		if(current_data != 'move'){
			$("div.operList").fadeOut();
			$.ajax({
				url:"userAjax/subject_updateState",
				data:{ 
					sub_id: $("input[type='hidden'][name='currentSubject_id']").val(),
					operate_flag: current_data
				},
				dataType:"json",
				type:"post",
				error: function (){
					$.myPlugin.msgAlert("服务器异常！","warn");
				},
				success: function (data){
					if(data.msg == "1"){
						$.myPlugin.msgAlert(current_html + "成功！");
						if(current_data == "delete"){
							setTimeout(function(){
								window.location.href='list?cate_id='+$("input[type='hidden'][name='currentCate_id']").val();
							}, 500);
							return false;
						}
						
						var dataReg = /^no\w+$/;
						
						if(dataReg.exec(current_data)!=null){
							$current.attr("data", current_data.substring(2, current_data.length));
							$current.html(current_html.substring(2, current_html.length));
						}else{
							$current.attr("data", 'no' + current_data);
							$current.html('取消' + current_html);
						}
						
						
					}else{
						$.myPlugin.msgAlert(current_html + "失败！","warn");
					}
				}					
			});
		}
		
		
	});
	
	
	//移动到指定类别
	$("div.moveTo > ul > li").click(function(){
		var $current = $(this);
		var data = $current.attr("data");
		$.ajax({
			url:"userAjax/subject_moveToCategory",
			data:{ 
				sub_id: $("input[type='hidden'][name='currentSubject_id']").val(),
				cate_id: data
			},
			dataType:"json",
			type:"post",
			error: function (){
				$.myPlugin.msgAlert("服务器异常！","warn");
			},
			success: function (data){
				if(data.msg == "1"){
					$.myPlugin.msgAlert("移动成功！");
					setTimeout(function(){
						window.location.reload();
					}, 1000);
					
				}else{
					$.myPlugin.msgAlert(current_html + "失败！","warn");
				}
			}					
		});
	});
	
	
});