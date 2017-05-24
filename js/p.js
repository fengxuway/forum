$(document).ready(function(e) {
	
	
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
    
    
    //点击“+关注”链接
    $("#option > a:first").click(function(){
    	var $current = $(this);
    	if($("#data > input[type='hidden'][name='session_user_id']").val() == ""){
    		$.myPlugin.msgAlert("您尚未登陆！");
    		return false;
    	}
    	if($current.html() == "+关注"){
    		//加关注操作
    		$.ajax({
    			url:"ajax/user_method",
    			data:{ 
    				method:"addMention",
    				userid:$("#data > input[type='hidden'][name='current_user_id']").val()
    			},
    			dataType:"json",
    			type:"post",
    			success: function (data){
    				//操作成功
    				if(data.msgAjax == "1"){
    					$current.html("已关注");
    					$.myPlugin.msgAlert("添加关注成功！");
    				}
    				//操作失败
    				else{
    					$.myPlugin.msgAlert("添加关注失败！", "warn");
    					
    				}
    			}
    			
    		});
    		
    	}else if($current.html() == "取消关注"){
    		//取消关注操作
    		 //加关注操作
    		$.ajax({
    			url:"ajax/user_method",
    			data:{ 
    				method:"removeMention",
    				userid:$("#data > input[type='hidden'][name='current_user_id']").val()
    			},
    			dataType:"json",
    			type:"post",
//    			context:
    			success: function (data){
    				//操作成功
    				if(data.msgAjax == "1"){
    					$current.html("+关注");
    					$.myPlugin.msgAlert("取消关注成功！");
    				}
    				//操作失败
    				else{
    					$.myPlugin.msgAlert("取消关注失败！", "warn");
    					
    				}
    			}
    			
    		});
    	}
    	
    }).hover(function(){
	    	var $current = $(this);
	    	if($current.html() == "已关注"){
	    		$current.html("取消关注");
	    	}
	    },function(){
	    	var $current = $(this);
	    	if($current.html() == "取消关注"){
	    		$current.html("已关注");
	    	}
	    }
	);
    
    
    //创建主题Dom
	var createSubjectDom = function(value){
		return $("<article><header>发表主题</header>"+
			"<div class='theme_head'>"+
				"<a target='_blank'  href='single?sub_id=" + value.sub_id + "'>" + value.sub_title + "</a>"+ 
				"<a target='_blank'  href='list?cate_id=" + value.cate_id.cate_id + "' class='theme_type'>【" + value.cate_id.cate_name + "】</a></div>"+
			"<time>" + value.sub_time + "</time></article> ");
	}
    
    //创建回复dom
    var createReplyDom = function(value){
    	return $("<article><header>发表回复</header><div class='theme_head'>"+
		    "<a target='_blank'  href='single?reply_id=" + value.reply_id + "#" + value.reply_id + "'>" + value.reply_content + "</a> "+
			"<div class='theme_title'><a href='single?sub_id=" + value.sub_id.sub_id + "'>" + value.sub_id.sub_title + "</a>"+ 
			"<a target='_blank'  href='list?cate_id=" + value.sub_id.cate_id.cate_id + "' class='theme_type'>【" + value.sub_id.cate_id.cate_name + "】</a></div>"+
			"<time>" + value.reply_time + "</time></div></article>");
    }
    
    
    //创建他的粉丝dom或他的关注
    var createAtteUserDom = function(element, flag){
    	var atte_class = flag ? "atte_remove" : "atte_add";
    	return $("<li><a href='p?id=" + element.userid_encode + "'><img src='images/head_img/" + element.photo + "' alt=''></a>"+
			"<div><a href='p?id=" + element.userid_encode + "' class='atte_name'>" + element.user_id + "</a> "+
			"<a href='javascript:void(0);' class='" + atte_class + "'></a></div></li>");
    }
    
    
	//获取主题贴、回复贴事件
	var moreHandler = function($relate, r){
		var $current = $relate.children("div.more").children("a");
		var $loadNum = $relate.children("input[type='hidden'][name='loadNum']");
		var current_html = $.trim($current.html());
		//如果链接内容不是点击查看更多或网络异常，即如果为数据已全部显示，则return false
		if(current_html != "点击查看更多" && current_html != "网络异常，请稍候重试！"){
			return false;
		}
		if(r == 13 && $loadNum.val() == "1"){
			return false;
		}
		//异步请求接下来的数据，
		//首先获取隐藏域loadNum的值，即已请求的页码数，为其+1
		$.ajax({
			url:"ajax/p_requestData",
			data:{ 
				r: r,
				pageNum: parseInt($loadNum.val()) + 1,
				userid:$("#data > input[type='hidden'][name='current_user_id']").val()
			},
			dataType:"json",
			type:"post",
			beforeSend: function(){
				$current.html("<img src='images/loading.gif'>正在加载中...");
			},
			error: function (){
				$current.html("网络异常，请稍候重试！");
			},
			success: function (data){
				
				if(r == 11){
					if(data.pSubjectBean == null){
						$current.html("您还没有登陆！");
						return false;
					}
					$.each(data.pSubjectBean.list, function(index, element){
						var dom = createSubjectDom(element);
						$current.parent("div").before(dom);
					}); 
					if(data.pSubjectBean.size == 0 && $loadNum.val() == "0"){
						$current.html("他还没有发表过主题");
					}else if(data.pSubjectBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
				}else if(r == 12){
					$.each(data.pReplyBean.list, function(index, element){
						var dom = createReplyDom(element);
						$current.parent("div").before(dom);
					}); 
					if(data.pReplyBean.allRow == 0 && $loadNum.val() == "0"){
						$current.html("他还没有发表过回复");
					}else if(data.pReplyBean.lastPage){
						$current.html("已到最后一页");
					}else{
						$current.html("点击查看更多");
					}
				}else if(r == 13){
					$.each(data.atteUserList, function(index, element){
						var flag = false;
						if($("#data > input[name='session_user_id']").val() != ""){
							$.each(data.myAtteList, function(index2, element2){
								if(element.user_id == element2.user_id){
									flag = true;
									return false;
								}
							});
						}
						
						var dom = createAtteUserDom(element, flag);
						$current.parent("div").prev("ul").append(dom);
					}); 
					if(data.atteUserList.length == 0){
						$current.html("他目前还没有粉丝");
					}else{
						$current.html("该页显示他的粉丝");
					}
				}else if(r == 14){
					$.each(data.userAtteList, function(index, element){
						var flag = false;
						if($("#data > input[name='session_user_id']").val() != ""){
							$.each(data.myAtteList, function(index2, element2){
								if(element.user_id == element2.user_id){
									flag = true;
									return false;
								}
							});
						}
						
						var dom = createAtteUserDom(element, flag);
						$current.parent("div").prev("ul").append(dom);
					}); 
					if(data.userAtteList.length == 0){
						$current.html("他目前还没有关注");
					}else{
						$current.html("该页显示他的关注");
					}
				}
				$loadNum.val(parseInt($loadNum.val()) + 1);
				$("aside").height($("div.main:visible").outerHeight(true) - 2);
				
			}
			
		});
	};
    
    //他的回复li点击时，加载回复列表
    $("#main0 > ul > li:not(.l):eq(1)").click(function(){
    	moreHandler($("#relate1"),12);
    });
    
    //他的粉丝li点击时，加载粉丝列表
    $("#main0 > ul > li:not(.l):eq(2)").click(function(){
    	moreHandler($("#relate2"),13);
    });
    
    //他的关注li点击时，加载关注列表
    $("#main0 > ul > li:not(.l):eq(3)").click(function(){
    	moreHandler($("#relate3"),14);
    });
    
    //点击查看他的主题的更多内容
    $("#relate0").children("div.more").children("a").click(function(){moreHandler($("#relate0"), 11);});
    //点击查看他的回复的更多内容
    $("#relate1").children("div.more").children("a").click(function(){moreHandler($("#relate1"), 12);});
    
    
    
////////////////////////////////////////////////////////////////////////////////
    //他的粉丝列表或他的关注列表中“关注”按钮点击事件
    $("div.relate").find("a.atte_add").live("click",function(){
    	var $current = $(this);
		var sessionUser = $("#data > input[type='hidden'][name='session_user_id']").val();
		var currentUser = $current.prev("a.atte_name").html();
		//如果用户尚未登陆，不允许请求
		if(sessionUser == ""){
			$.myPlugin.msgAlert("您尚未登陆！");
			return false;
		//如果用户关注的是自己，不允许请求
		}else if(sessionUser == currentUser){
			$.myPlugin.msgAlert("您要关注您自己？真逗！");
			return false;
		}else{
			$.ajax({
				url:"ajax/user_method",
    			data:{ 
    				method:"addMention",
    				userid:currentUser
    			},
				dataType:"json",
				type:"post",
				error: function (){
					$.myPlugin.msgAlert("操作发生错误，请稍候重试！",'warn');
				},
				success:function(){
					$current.removeClass("atte_add").addClass("atte_remove");
					$.myPlugin.msgAlert("关注成功！");
				}
			});
		}
	});
    
    
    //他的粉丝列表或他的关注列表中“取消关注”按钮点击事件
    $("div.relate").find("a.atte_remove").live("click",function(){
    	var $current = $(this);
    	var sessionUser = $("#data > input[type='hidden'][name='session_user_id']").val();
    	var currentUser = $current.prev("a.atte_name").html();
    	//如果用户尚未登陆，不允许请求
    	if(sessionUser == ""){
    		$.myPlugin.msgAlert("您尚未登陆！");
    		return false;
    		//如果用户关注的是自己，不允许请求
    	}else{
    		$.ajax({
    			url:"ajax/user_method",
    			data:{ 
    				method:"removeMention",
    				userid:currentUser
    			},
    			dataType:"json",
    			type:"post",
    			error: function (){
    				$.myPlugin.msgAlert("操作发生错误，请稍候重试！",'warn');
    			},
    			success:function(){
    				$current.removeClass("atte_remove").addClass("atte_add");
    				$.myPlugin.msgAlert("取消关注成功！");
    			}
    		});
    	}
    });
    
    $("aside").height($("div.main:visible").outerHeight(true) - 2);
    
	$("aside > p > a:first").click(function(){
		$("#main0 > ul > li:not(.l):eq(2)").click();
	});
	$("aside > p > a:last").click(function(){
		$("#main0 > ul > li:not(.l):eq(3)").click();
	});
});
