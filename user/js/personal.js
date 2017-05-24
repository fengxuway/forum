$(document).ready(function(e) {
	//初始化aside的高度
	var changeAsideHeightHandler = function(){
		$("aside").height($("div.main:visible").outerHeight(true) - 2);
		//alert("kk");
	};
	changeAsideHeightHandler();
	
	$("#option > a:first").click(function(){
		$("section > header > ul > li:last").click();
		changeAsideHeightHandler();
	});
	//刷新页面之前，重置所有的loadNum
	$(window).unload(function(e){
		$("input[type='hidden'][name='loadNum']").val("0");
		
	});
	
	
	//第一个列表点击，切换div.main的显示隐藏
	$("section > header > ul > li").each(function(index, element) {
		var $current = $(this);
		//该列表对应的div#main
		var $main = $("div#main"+index);
		//点击列表，对应的div显示，其他div隐藏，并且更改列表和动态线框active_line的css样式
        $current.click(function(e) {
            if(!$current.hasClass("current")){
				//更改列表样式
				$current.addClass("current").siblings("li.current").removeClass("current");
				//对应div显示，其他div隐藏
				$main.show().siblings("div.main:visible").hide();
				//排除第一个li即关注动态的li，其他li点击时恢复div中li的默认样式（第一个li为main_current样式），
				//以及相应的div.relate显示隐藏，并恢复active_line至默认位置
				if(index>0 && index<3){
					var $li_first = $main.children("ul").children("li:first");
					$("li.main_current").removeClass("main_current");
					$main.children("ul").children("li:first").addClass("main_current");
					$main.find("div.active_line")
						.css("left",$li_first.position().left+$li_first.width()/2-44);
					$main.children("div.relate:first:hidden").show().siblings("div.relate:visible").hide();
				}
			}
        });
    });
	//子列表导航点击时和hover时
    $("div.main > ul > li:not(.l)").each(function(index, element) {
		var $current = $(this);
		var $active_line = $current.parent("ul").next("div.line").children("div.active_line");
		//点击时，改变class，隐藏内容/显示对应内容，并定位active线
		$current.click(function(e) {
			if(!$current.hasClass("main_current")){
				$("li.main_current").removeClass("main_current");
				$current.addClass("main_current");
				$("div.relate:visible").hide();
				$("#relate"+index).show();
				$active_line.css("left",$current.position().left+$current.width()/2-44);
			}
		})
		//hover时定位active线的位置
		.hover(function(e){
			$active_line.first().stop(true, false).animate(
				{left:$current.position().left+$current.width()/2-44}, 400);
		},function(e){
			var $main_current = $current.parent().find("li.main_current");
			$active_line.first().stop(true, false).animate(
				{left:$main_current.position().left+$main_current.width()/2-44}, 400);
		});
    });
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////				
	
	
	//创建我关注的动态的主题DOM
	var createDynamicSubjectDom = function(value){
		return $("<article><header><a href='../p?id=" + value.author.userid_encode + "' target='_blank' class='reply_img'> "+
		    "<img alt=''	src='../images/head_img/" + value.author.photo + "'></a>"+
			"<div class='reply_title'><a href='../p?id=" + value.author.userid_encode + "' target='_blank'>" + value.author.user_id + "</a>&nbsp; 发表主题：</div>"+
			"<div class='clear'></div></header>"+
			"<div class='theme_head'><a href='../single?sub_id=" + value.sub_id + "' target='_blank'>" + value.sub_title + "</a> "+
			"<a href='../list?cate_id=" + value.cate_id.cate_id + "' class='theme_type' target='_blank'>【" + value.cate_id.cate_name + "】</a></div>"+
			"<time>" + value.sub_time + "</time></article>");
	}
    
    //创建我关注的动态的回复DOM
	var createDynamicReplyDom = function(value){
		var reg = /<.*?>/g;
		var content = value.reply_content;
		content = content.replace(reg, "");
		if($.trim(content) == ""){
			content = "【图片】";
		}
		var len = content.length;
		content = content.substring(0, len > 40? 40:len);
		return $("<article><header><a href='../p?id=" + value.user_id.userid_encode + "' class='reply_img' target='_blank'> "+
		    "<img alt=''	src='../images/head_img/" + value.user_id.photo + "'></a>"+
			"<div class='reply_title'><a href='../p?id=" + value.user_id.userid_encode + "' target='_blank'>" + value.user_id.user_id + "</a>&nbsp; 发表回复：</div>"+
			"<div class='clear'></div></header>"+
			"<div class='theme_head'><a href='../single?reply_id=" + value.reply_id + "#" + value.reply_id + "' target='_blank'>" + content + "</a>"+
			"<div class='theme_title'><span class='at_theme'>在主题：</span>"+
			"<a href='../single?sub_id=" + value.sub_id.sub_id + "' target='_blank'>" + value.sub_id.sub_title + "</a>"+
			"<a href='../list?cate_id=" + value.sub_id.cate_id.cate_id + "' class='theme_type' target='_blank'>【" + value.sub_id.cate_id.cate_name + "】</a></div>"+
			"<time>" + value.reply_time + "</time></div></article> ");
	}
    

	//创建个人主题列表节点
	var createSelfSubjectDom = function(value){
		return $("<article><header>发表主题</header><div class='theme_head'>"+
			"<a target='_blank' href='../single?sub_id=" + value.sub_id + "'>"+ value.sub_title +"</a>"+
			"<a target='_blank' class='theme_type' href='../list?cate_id=" + value.cate_id.cate_id + "'>【"+ value.cate_id.cate_name +"】</a></div>"+
			"<time>"+ value.sub_time +"</time></article>");
	};
	
	//创建个人回复列表节点
	var createSelfReplyDom = function(value){
		var reg = /<.*?>/g;
		var content = value.reply_content;
		content = content.replace(reg, "");
		if($.trim(content) == ""){
			content = "【图片】";
		}
		var len = content.length;
		content = content.substring(0, len > 40? 40:len);
		return $("<article><header>发表回复</header><div class='theme_head'>"+
			"<a target='_blank'  href='../single?reply_id="+value.reply_id+"#"+ value.reply_id +"'>"+content+"</a>"+
			"<div class='theme_title'>"+
				"<a target='_blank'  href='../single?sub_id=" + value.sub_id.sub_id + "'>"+value.sub_id.sub_title+"</a>"+
				"<a target='_blank'  href='../list?cate_id="+value.sub_id.cate_id.cate_id+"' class='theme_type'>【"+value.sub_id.cate_id.cate_name+"】</a>"+
			"</div><time>"+value.reply_time+"</time></article>");
	};
	
	//创建回复个人的列表节点
	var createReplyMeDom = function(value){
		var reg = /<.*?>/g;
		var content = value.reply_id.reply_content;
		content = content.replace(reg, "");
		if($.trim(content) == ""){
			content = "【图片】";
		}
		var len = content.length;
		content = content.substring(0, len > 40? 40:len);
		return $("<article><header><a href='../p?id=" + value.reply_id.user_id.userid_encode + "' target='_blank' class='reply_img'>"+
			"<img alt='' src='../images/head_img/" + value.reply_id.user_id.photo + "'></a>"+
			"<div class='reply_title'><a href='../p?id=" + value.reply_id.user_id.userid_encode + "' target='_blank' >" + value.reply_id.user_id.user_id + "</a>&nbsp; 回复了您：</div></header>"+
			"<div class='theme_head'><a target='_blank'  href='../single?reply_id=" + value.reply_id.reply_id + "#" + value.reply_id.reply_id + "'>" + content + "</a><div class='theme_title'>"+
				"<span class='at_theme'>在主题：</span><a  target='_blank' href='../single?sub_id=" + value.reply_id.sub_id.sub_id + "'>" + value.reply_id.sub_id.sub_title + "</a>"+
				"<a target='_blank'  href='../list?cate_id=" + value.reply_id.sub_id.cate_id.cate_id + "' class='theme_type'>【" + value.reply_id.sub_id.cate_id.cate_name + "】</a>"+
			"</div><time>" + value.time + "</time></div></article>");
		
	};
	
	
	//创建@个人的列表节点
	var createMentionMeDom = function(value){
		var reg = /<.*?>/g;
		var content = value.reply_id.reply_content;
		content = content.replace(reg, "");
		if($.trim(content) == ""){
			content = "【图片】";
		}
		var len = content.length;
		content = content.substring(0, len > 40? 40:len);
		return $("<article><header><a href='../p?id=" + value.reply_id.user_id.userid_encode + "' target='_blank' class='reply_img'> " +
			"<img alt=''	src='../images/head_img/" + value.reply_id.user_id.photo + "'></a>" +
			"<div class='reply_title'><a href='../p?id=" + value.reply_id.user_id.userid_encode + "' target='_blank' >" + value.reply_id.user_id.user_id + "</a>&nbsp; @了您：</div></header>" +
			"<div class='theme_head'><a  target='_blank' href='../single?reply_id=" + value.reply_id.reply_id + "#" + value.reply_id.reply_id + "'>" + content + "</a>" +
			"<div class='theme_title'><span class='at_theme'>在主题：</span>" +
				"<a  target='_blank' href='../single?sub_id=" + value.reply_id.sub_id.sub_id + "'>" + value.reply_id.sub_id.sub_title + "</a>" +
				"<a  target='_blank' href='../list?cate_id=" + value.reply_id.sub_id.cate_id.cate_id + "' class='theme_type'>【" + value.reply_id.sub_id.cate_id.cate_name + "】</a>" +
			"</div><time>" + value.time + "</time></div>" +
		"</article>");
		
	};
	
	//创建我的粉丝dom
    var createAtteMeDom = function(element, flag){
    	var atte_class = flag ? "atte_remove" : "atte_add";
    	return $("<li><a href='../p?id=" + element.userid_encode + "' target='_blank' ><img src='../images/head_img/" + element.photo + "' alt=''></a>"+
			"<div><a href='../p?id=" + element.userid_encode + "' target='_blank'  class='atte_name'>" + element.user_id + "</a> "+
			"<a href='javascript:void(0);' class='" + atte_class + "'></a></div></li>");
    }
    
    // 创建系统消息
    var createNewsDom = function(element){
    	return $("<article>"+
				"<div class='theme_head'>" + element.info_content + "</div>"+
				"<time>" + element.time + "</time>"+
			"</article>");
    }
	
	//获取主题贴、回复贴事件
	var moreHandler = function($relate, r){
		// var $relate1 = $("#relate1");
		var $current = $relate.children("div.more").children("a");
		var $loadNum = $relate.children("input[type='hidden'][name='loadNum']");
		var current_html = $.trim($current.html());
		//如果链接内容不是点击查看更多或网络异常，即如果为数据已全部显示，则return false
		if(current_html != "点击查看更多" && current_html != "网络异常，请稍候重试！"){
			return false;
		}
		//异步请求接下来的数据，
		//首先获取隐藏域loadNum的值，即已请求的页码数，为其+1
		$.ajax({
			url:"../userAjax/personal_requestData",
			data:{ 
				r: r,
				pageNum: parseInt($loadNum.val()) + 1
			},
			dataType:"json",
			type:"post",
			beforeSend: function(){
				$current.html("<img src='../images/loading.gif'>正在加载中...");
			},
			error: function (){
				$current.html("网络异常，请稍候重试！");
			},
			success: function (data){
				
				if(r == 21){
					if(data.selfSubjectBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.selfSubjectBean.list, function(index, element){
						var subjectDom = createSelfSubjectDom(element);
						$current.parent("div").before(subjectDom);
					}); 
					if(data.selfSubjectBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("您还没有发表过主题");
					}else if(data.selfSubjectBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
				}else if(r == 22){
					//如果长时间未动页面，session卸载user，获取到的数据为Null，则给出提示并return false;
					if(data.selfReplyBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.selfReplyBean.list, function(index, element){
						var replyDom = createSelfReplyDom(element);
						$current.parent("div").before(replyDom);
					}); 
					if(data.selfReplyBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("您还没有发表过回复");
					}else if(data.selfReplyBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
				}else if(r == 23){
					$.each(data.atteMeList, function(index, element){
						var flag = false;
						$.each(data.myAtteList, function(index2, element2){
							if(element.user_id == element2.user_id){
								flag = true;
								return false;
							}
						});
						var dom = createAtteMeDom(element, flag);
						$current.parent("div").prev("ul").append(dom);
					}); 
					if(data.atteMeList.length == 0){
						$current.html("您目前还没有粉丝");
					}else{
						$current.html("该页显示您的粉丝");
					}
					
				}else if(r == 24){
					$.each(data.myAtteList, function(index, element){
						var dom = createAtteMeDom(element, true);
						$current.parent("div").prev("ul").append(dom);
					}); 
					if(data.myAtteList.length == 0){
						$current.html("您目前还没有关注别人");
					}else{
						$current.html("该页显示您的关注");
					}
					
				}else if(r == 31){
					if(data.replyMeBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.replyMeBean.list, function(index, element){
						var replyMeDom = createReplyMeDom(element);
						$current.parent("div").before(replyMeDom);
					}); 
					if(data.replyMeBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("还没有人回复您");
					}else if(data.replyMeBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
					
				}else if(r == 32){
					if(data.mentionMeBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.mentionMeBean.list, function(index, element){
						var mentionMeDom = createMentionMeDom(element);
						$current.parent("div").before(mentionMeDom);
					}); 
					if(data.mentionMeBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("还没有人提到@您");
					}else if(data.mentionMeBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
					
				}else if(r == 33){
					if(data.newsBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.newsBean.list, function(index, element){
						var newsDom = createNewsDom(element);
						$current.parent("div").before(newsDom);
					}); 
					if(data.newsBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("您还没有系统消息");
					}else if(data.newsBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
					
				}else if(r == 11){
					$.each(data.atteDynamicBean.list, function(index, element){
						var dom;
						if(element.type == 1){
							dom = createDynamicSubjectDom(element.sub_id);
						}else if(element.type == 2){
							dom = createDynamicReplyDom(element.reply_id);
						}
						if(dom){
							$current.parent("div").before(dom);
						}
						
					});
					
					if(data.atteDynamicBean.list.length == 0 && $loadNum.val() == "0"){
						$current.html("您没有关注别人或您关注的用户还没有动态");
					}else if(data.atteDynamicBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
				}
				$loadNum.val(parseInt($loadNum.val()) + 1);
				changeAsideHeightHandler();
			}
			
		});
	};
	
	
	//“关注动态”点击时，如果11未加载，则发送请求
	$("section > header > ul > li:eq(0)").click(function(){
		var $main0 = $("#main0");
		var $loadNum = $main0.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#main0"), 11);
		}else{
			changeAsideHeightHandler();
		}
	});
	
	
	//“我在中北”点击时，如果21未加载，则发送请求
	$("section > header > ul > li:eq(1)").click(function(){
		var $relate0 = $("#relate0");
		var $loadNum = $relate0.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#relate0"), 21);
		}else{
			changeAsideHeightHandler();
		}
	});
	//我的消息点击时，如果31未加载，则发送请求
	$("section > header > ul > li:eq(2)").click(function(){
		var $relate4 = $("#relate4");
		var $loadNum = $relate4.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($relate4, 31);
		}else{
			changeAsideHeightHandler();
		}
	});
	
	$("section > header > ul > li:eq(3)").click(function(){
		changeAsideHeightHandler();
	});
	
	
	
	//个人主题列表ul子选项卡点击时，如隐藏域为0，则获取值
	$("#main1 > ul > li:not(.l):eq(0)").click(function(){
		var $relate0 = $("#relate0");
		var $loadNum = $relate0.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#relate0"), 21);
		}else{
			changeAsideHeightHandler();
		}
	});
	$("#main1 > ul > li:not(.l):eq(1)").click(function(){
		var $relate0 = $("#relate1");
		var $loadNum = $relate0.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#relate1"), 22);
		}else{
			changeAsideHeightHandler();
		}
	});
	//我的粉丝li点击时，判断是否已加载，获取我的粉丝
	$("#main1 > ul > li:not(.l):eq(2)").click(function(){
		var $relate = $("#relate2");
		var $loadNum = $relate.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#relate2"), 23);
		}else{
			changeAsideHeightHandler();
		}
	});
	//我的粉丝li点击时，判断是否已加载，获取我的粉丝
	$("#main1 > ul > li:not(.l):eq(3)").click(function(){
		var $relate = $("#relate3");
		var $loadNum = $relate.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($("#relate3"), 24);
		}else{
			changeAsideHeightHandler();
		}
	});
	
	//“回复我的消息”列表ul子选项卡点击时，如隐藏域为0，则获取值
	$("#main2 > ul > li:not(.l):eq(0)").click(function(){
		var $relate4 = $("#relate4");
		var $loadNum = $relate4.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($relate4, 31);
		}else{
			changeAsideHeightHandler();
		}
	});

	
	
	//@我的消息li点击时，发送请求
	$("#main2 > ul > li:not(.l):eq(1)").click(function(){
		var $relate5 = $("#relate5");
		var $loadNum = $relate5.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($relate5, 32);
		}else{
			changeAsideHeightHandler();
		}
	});
	
	//系统消息li点击时，发送请求
	$("#main2 > ul > li:not(.l):eq(2)").click(function(){
		var $relate6 = $("#relate6");
		var $loadNum = $relate6.children("input[type='hidden'][name='loadNum']");
		//如果当前内容没有进行过加载，则请求第1页
		if(parseInt($loadNum.val()) == 0){
			moreHandler($relate6, 33);
		}else{
			changeAsideHeightHandler();
		}
	});
	
	$("#main0").children("div.more").children("a").click(function(){moreHandler($("#main0"), 11);});
	//个人主题列表点击“查看更多”链接
	$("#relate0").children("div.more").children("a").click(function(){moreHandler($("#relate0"), 21);});
	//个人回复列表点击“查看更多”链接
	$("#relate1").children("div.more").children("a").click(function(){moreHandler($("#relate1"), 22);});
	$("#relate4").children("div.more").children("a").click(function(){moreHandler($("#relate4"), 31);});
	$("#relate5").children("div.more").children("a").click(function(){moreHandler($("#relate5"), 32);});
	$("#relate6").children("div.more").children("a").click(function(){moreHandler($("#relate6"), 33);});
	
	/*  //如果页面显示其它内容，如何防止该事件加载
	var scrollTime;
	$(window).scroll(function(){
		if($(window).height() + $(window).scrollTop() >= $(document).height() - 50){
			clearTimeout(scrollTime);
			scrollTime = setTimeout(function(){moreHandler($("#relate1"), 22);}, 300);
		}
	});*/
	
///////////////////////////////////////////////////////////////////////
	//点击我的粉丝的添加关注按钮
    $("div.relate").find("a.atte_add").live("click",function(){
    	var $current = $(this);
		var sessionUser = $("#data > input[type='hidden'][name='session_user_id']").val();
		var currentUser = $current.prev("a.atte_name").html();
		//如果用户尚未登陆，不允许请求
		if(sessionUser == currentUser){
			$.myPlugin.msgAlert("您要关注您自己？真逗！");
			return false;
		}else{
			$.ajax({
				url:"../ajax/user_method",
    			data:{ 
    				method:"addMention",
    				userid:currentUser
    			},
				dataType:"json",
				type:"post",
				error: function (){
					$.myPlugin.msgAlert("操作发生错误，请稍候重试！",'warn',1500);
				},
				success:function(){
					$current.removeClass("atte_add").addClass("atte_remove");
					$.myPlugin.msgAlert("关注成功！", "ok", 1500);
				}
			});
		}
	});
    
    
    //他的粉丝列表或他的关注列表中“取消关注”按钮点击事件
    $("#main1 > div.relate").find("a.atte_remove").live("click",function(){
    	var $current = $(this);
    	var sessionUser = $("#data > input[type='hidden'][name='session_user_id']").val();
    	var currentUser = $current.prev("a.atte_name").html();
    	//如果用户尚未登陆，不允许请求
    	if(sessionUser == ""){
    		$.myPlugin.msgAlert("您尚未登陆！");
    		return false;
    	}else{
    		$.ajax({
    			url:"../ajax/user_method",
    			data:{ 
    				method:"removeMention",
    				userid:currentUser
    			},
    			dataType:"json",
    			type:"post",
    			error: function (){
    				$.myPlugin.msgAlert("操作发生错误，请稍候重试！",'warn', 1500);
    			},
    			success:function(){
    				$current.removeClass("atte_remove").addClass("atte_add");
    				$.myPlugin.msgAlert("取消关注成功！","info", 1500);
    				
    				//如果取消关注的是“我的关注”，则1500ms之后移除li结点
    				if($("#main1 > ul > li.main_current").html() == "我的关注"){
    					setTimeout(function(){$current.parent().parent().remove();}, 1500);
    				}
    			}
    		});
    	}
    });
	
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////
	//个人资料部分
	//点击修改密码按钮
	$("button.updatePswdBtn").click(function(){
		var $current = $(this);
		var $div = $current.next("div.updatePswd");
		//隐藏域，记录是否修改密码
		var $hidden = $div.children("input:hidden");
		if($current.html() == "修改密码"){
			$div.slideDown("fast");
			$current.html("取消修改密码");
		}else{
			$div.slideUp("fast");
			$current.html("修改密码");
			$div.children("label").children("input").val("").removeClass();
		}
	});
	
	
	//密码框部分校验
	$("div.updatePswd > label > input").focus(function(){
		$(this).removeClass().addClass("right");
	}).blur(function(){
		var $current = $(this);
		//如果是当前密码
		if($current.attr("name") == "currentPswd"){
			if($current.val() == ""){
				$current.removeClass().addClass("wrong");
			}else{
				$(this).removeClass();
			}
		}
		//如果是新密码或确认密码，判断两个密码是否一致
		else if($current.attr("name") == "newPswd"){
			var newPswd = $("input[name='newPswd']").val();
			var newPsure = $("input[name='newPsure']").val();
			if(newPswd == ""){
				$current.removeClass().addClass("wrong");
			}else if( newPswd == newPsure){
				$current.removeClass();
				$("input[name='newPsure']").removeClass();
			}else{
				$current.removeClass();
			}
		}else if($current.attr("name") == "newPsure"){
			var newPswd = $("input[name='newPswd']").val();
			var newPsure = $("input[name='newPsure']").val();
			if(newPsure == "" || newPsure != newPswd){
				$current.removeClass().addClass("wrong");
			}else{
				$current.removeClass();
			}
		}
	});
	
	
	//点击提交修改按钮
	$("div.material").find("button[type='submit']").click(function(event){
		var currentPswd = $("input[name='currentPswd']").val();
		var newPswd = $("input[name='newPswd']").val();
		var newPsure = $("input[name='newPsure']").val();
		
		if(currentPswd != "" || newPswd != "" || newPsure != ""){
			if(currentPswd == ""){
				$.myPlugin.msgAlert("您还没输入旧密码！", "warn");
				event.preventDefault();
			}else if(newPswd == currentPswd && newPswd != ""){
				$.myPlugin.msgAlert("新密码不能和旧密码一致！", "warn");
				event.preventDefault();
			}else if(newPswd != newPsure){
				$.myPlugin.msgAlert("您两次输入的密码不一致！", "warn");
				event.preventDefault();
				
			}
				
		}
		
	});
	
	
	
	$("aside > p > a:first").click(function(){
		$("section > header > ul > li:eq(1)").click();
		$("#main1 > ul > li:not(.l):eq(2)").click();
	});
	$("aside > p > a:last").click(function(){
		$("section > header > ul > li:eq(1)").click();
		$("#main1 > ul > li:not(.l):eq(3)").click();
	});
    
	
});
