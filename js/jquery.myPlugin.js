/**
 * 自定义jQuery全局函数
 * 调用方法: $.myPlugin.函数名(参数列表)
 * 使用CSS资源: css/jquery.myPlugin.css
 * 使用图片资源: images/icon_dialogs.png, image/scs_alert.png
 *
 */
jQuery.myPlugin = {

	/**
	 * msgAlert简易弹出提示信息框
	 * @param content (string)要显示的信息
	 * @param theClass (string)信息显示类型, 取值('ok'(默认), 'warn', 'stop', 'error', 'info',  'confirm')
	 * @param timeout (number)弹出提示信息显示时间，默认3000ms
	 */
	msgAlert: function(content, theClass, timeout){
		
		if($("div.msgAlert_main").length>0)return false;
		
		if(!timeout || !typeof timeout == "number"){
			timeout = 3000;
		}
		
		var $msgAlert = $("<div class='msgAlert_main'></div>");
		var p = $("<p>"+content+"</p>").appendTo($msgAlert);
		if(theClass!=null && typeof theClass == "string"){
			p.addClass("msg_"+theClass + "_Alert");
		}
		//计算$msgAlert长度，= 字符串长度 * 16px + 30padding-left + 62px div.padding+border
		var width = content.length*12 + 30 + 62;
		$msgAlert.css({left:($(window).width()-width)/2, top: -62}).appendTo("body").show();
		//更改图标及内容
		$msgAlert.animate({top:0}, 300);
		setTimeout(function(){ 
			$msgAlert.animate({top:-62}, 300, function(){$msgAlert.remove();});
		}, timeout); 
	
	},
	

	/**
	 * prettyAlert另一种美化alert & confirm弹出窗口的函数
	 * @param content 要显示的内容
	 * @param theClass 信息显示类型, 
	 *		取值('ok'-正确信息, 'error'-错误信息, 'warn'-警告信息, 'confirm'-询问信息等待用户确认) 默认没有类型
	 * @param ok 点击"确定"按钮的回调函数
	 * @param error 点击"取消"按钮的回调函数
	 *
	 */	
	prettyAlert: function(content, theClass, ok, error, ifShade){
		var $window = $(window);
	
		var $alert = $("<div id='pretty_alert'><div id='pretty_alert_head'></div><div id='pretty_alert_main'><div id='pretty_alert_main_side' class='pretty_alert_main_side'></div><div id='pretty_alert_main_content'>"+content+"</div><div id='pretty_alert_buttons'><button id='pretty_alert_ok'>确定</button></div></div><div id='pretty_alert_foot'></div></div>");
		var $shade = $("<div id='pretty_alert_shade'></div>");
		$alert.appendTo($("body")).fadeIn(500);
		if(typeof ifShade == "undefined" || ifShade){
			$shade = $("<div id='pretty_alert_shade'></div>");
		}else{
			$shade = $("<div id='pretty_alert_shade' style='background:none;opacity:0;'></div>");
		}
		$shade.appendTo($("body")).fadeIn(500);
		
		
		$alert.css({top: ($window.height()-$alert.outerHeight(true))/2, 
					left:($window.width()-$alert.outerWidth(true))/2});
		$shade.css({width: $window.width(), height:$window.height()});
		
		$window.resize(function(){
			$alert.css({top: ($window.height()-$alert.outerHeight(true))/2, 
					left:($window.width()-$alert.outerWidth(true))/2});
			$shade.css({width: $window.width(), height:$window.height()});
		});
		
		$window.scroll(function(){			
			$alert.css({top: ($window.height()-$alert.outerHeight(true))/2, 
					left:($window.width()-$alert.outerWidth(true))/2});	
		});
		

		var $ok = $("#pretty_alert_ok");
		var $error = $("#pretty_alert_error");
		var $quxiao = $("<button id='pretty_alert_error'>取消</button>");
		
		if(theClass != null && typeof theClass == "string"){
			$("#pretty_alert_main_side").addClass(theClass+"_Alert");
			if(theClass == "confirm"){
				$quxiao.appendTo($("#pretty_alert_buttons"));
				$ok.css("margin-left", 0);		
			}
		}
		
		$ok.click(function(){
			if(ok!= null && typeof ok == "function"){
				ok();
			}
			$alert.fadeOut(500);
			$shade.fadeOut(500);
			setTimeout(function(){ $alert.remove();
			$shade.remove();}, 500);
		});
		$quxiao.click(function(e) {
            if(error!= null && typeof error == "function"){
				error();
			}
			$alert.fadeOut(500);
			$shade.fadeOut(500);
			setTimeout(function(){ $alert.remove();
			$shade.remove();}, 500);
        });
	}

};