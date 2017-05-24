$(document).ready(function(e) {
    var pswd = $("#pswd");
//    $("#pswd_new").passwordStrength();
	pswd.passwordStrength();
	
	//导航aside部分按钮及其点击事件
	var reg_button = $("#reg_button");
	var log_button = $("#log_button");
	var bac_button = $("#bac_button");
	reg_button.click(function(e) {
        if(!reg_button.hasClass("current")){
        	$("#register_msg").empty();
			reg_button.addClass("current").siblings(".current").removeClass("current");
			$("#main").show().siblings("div").hide();			
		}
    });
	log_button.click(function(e) {
        if(!log_button.hasClass("current")){
			log_button.addClass("current").siblings(".current").removeClass("current");
			$("#main_login").show().siblings("div").hide();			
		}
    });
	bac_button.click(function(e){
		if(!bac_button.hasClass("current")){
			bac_button.addClass("current").siblings(".current").removeClass("current");
			$("#pswd_back").show().siblings("div").hide();
		}
	});
	
	
	
	
	//用户名正则表达式
	var userReg = /^[a-zA-Z0-9_-]{3,16}$/;
	var emailReg = /^([a-zA-Z0-9_\.-]+)@([\da-zA-Z\.-]+)\.([a-zA-Z\.]{2,6})$/;
	var pswdReg = /^.{3,16}$/;
	var nameReg = /^[\u2E80-\u9FFF]{1,5}$/;
	var studentIdReg = /^\d{8,12}$/;
	
	
//////////////////////////////////////////#main部分///////////////////////////////////////////////////
	//注册表单获取焦点后，改变背景颜色、并将提示图标显示
	$("#main").find("input").each(function(index, element){
		$(this).focus(function(){
			$(this).css("background-position","0 -44px");
			$tip = $(this).parent().next("td.tip");
			$tip.children("span").removeClass().addClass("tishi").show();
		});
	});
	
	//转换UserID, 正确计算UserID的长度
	var transformUserid = function(userid){
		var userReg = /^[a-zA-Z0-9_-]{3,14}$/;
		return userid.replace(/[\u2E80-\u9FFF]/g, "12");
		//return userReg.exec(s);
	}
	//表单获取焦点后，改变背景；取消背景后，根据正则表达式及ajax判断正误，改变背景及tip文字
	$("#userid").focus(function(){
		$(this).parent().next("td.tip").children("small").html("使用中文、英文或数字作为您的ID").show();
	}).blur(function(){
		//失去焦点
		//1. 获取表单值，正则表达式匹配
		//2. 匹配成功，进行ajax请求，
		//3. 返回的结果通过与否，显示相应的信息及背景图标，
		//4. 正则表达式匹配不成功，显示用户不能为空或其他信息
		var $current = $(this);
		var value = $current.val();
		var regResult = userReg.exec(value);
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small");
		var transUserid = transformUserid(value);
		
		if(userReg.exec(transUserid) != null){
			//正则表达式匹配成功
			$.ajax({
				url:"ajax/user_userExist",
				data:{userid:value},
				type:"post",
				dataType:"json",	
				context: $("#userid"),
				beforeSend: function(xhr){
					$tip = $(this).parent().next("td.tip");
					$tip.children("span").removeClass().addClass("ajaxLoading").show();
					$tip.children("small").html("正在验证用户名...").show();	
				},
				error: function (){
					$current.css("background-position","0 -88px");
					$span.removeClass().addClass("jinggao");
					$small.html("服务器发生故障，请稍后重试");
				},
				success: function (data){
					if(data.msgAjax == "0"){
						$current.css("background-position","0 0");
						$span.removeClass().addClass("zhengque");
						$small.html("用户名【"+ value +"】可以使用");
					}else{
						$current.css("background-position","0 -88px");
						$span.removeClass().addClass("jinggao");
						$small.html("用户名【"+ value +"】已被注册");
					}
				},
			});
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(value == ""){
				//如果输入用户为空				
				$small.html("用户名不能为空！");
			}else if(transUserid.length<3 || transUserid.length>14){
				$small.html("长度在3-16位之间(中文2个字符)！");
			}
			else {
				//如果正则表达式匹配失败	
				$small.html("用户名不能使用特殊字符！");				
			}
			
		}				
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#pswd").focus();
		}
	});
	
	//填写邮箱的表单
	$("#email").focus(function(){
		$(this).parent().next("td.tip").children("small").html("请输入你的常用邮箱，以便找回密码").show();
	}).blur(function(){
		var $current = $(this);
		var email = $current.val();
		var regResult = emailReg.exec(email);
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small");
		if(regResult != null){
			//正则表达式匹配成功
			$.ajax({
				url:"ajax/user_emailExist",
				data: {email: email},
				type:"post",
				dataType:"json",	
				context: $("#email"),
				beforeSend: function(xhr){
					$tip = $(this).parent().next("td.tip");
					$tip.children("span").removeClass().addClass("ajaxLoading").show();
					$tip.children("small").html("正在验证邮箱...").show();
				},
				error: function (){
					$current.css("background-position","0 -88px");
					$span.removeClass().addClass("jinggao");
					$small.html("服务器发生故障，请稍后重试");
				},
				success: function (data){
					if(data.msgAjax == "0"){
						//邮箱通过
						$current.css("background-position","0 0");
						$span.removeClass().addClass("zhengque");
						$small.html("该邮箱可以使用");
					}else{
						//邮箱已被占用
						$current.css("background-position","0 -88px");
						$span.removeClass().addClass("jinggao");
						$small.html("该邮箱已被占用");
					}
				}
			});
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(email == ""){
				//邮箱为空
				$small.html("邮箱不能为空！");
			}else{
				//邮箱正则匹配不正确
				$small.html("邮箱格式不正确！");
			}
			
		}
		
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#userid").focus();
		}
	});
	
	
	//密码输入框
	$("#pswd").focus(function(){
		$(this).parent().next("td.tip").children("small").html("密码强度").show();
		$("#passwordStrengthDiv").show();
	}).blur(function(){
		var $current = $(this);
		var pswd = $current.val();
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small"); 
		$("#passwordStrengthDiv").hide();
		var regResult = pswdReg.exec(pswd);
		if(regResult != null){
			//正则表达式匹配密码成功
			$current.css("background-position","0 0");
			$span.removeClass().addClass("zhengque");
			$small.hide();
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(pswd == ""){
				//密码为空
				$small.html("密码不能为空！")
			}else if(pswd.length<3 || pswd.length>16){
				$small.html("密码长度在3-16位之间");
			}
			else{
				$small.html("密码不能使用特殊字符！")
			}
		}
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#psure").focus();
		}
	});
	
	//密码确认框
	$("#psure").focus(function(){
		$(this).parent().next("td.tip").children("small").html("请再次确认您的密码").show();
	}).blur(function(){
		var $current = $(this);
		var psure = $current.val();
		var regResult = pswdReg.exec(psure);
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small");
		
		if(pswdReg.exec(psure) != null && psure == $("#pswd").val()){
			$small.hide();
			$current.css("background-position", "0 0");
			$span.removeClass().addClass("zhengque");
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(psure == ""){
				$small.html("确认密码不能为空！")
			}else if(psure != $("#pswd").val()){
				$small.html("您两次输入的密码不一致！");
			}else {
				$small.html("密码格式不正确！");
			}
		}
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#main").find("button").focus();
			nextHandler();
		}
	});
	
	
	
	
	var previousHandler = function(event){
		$("#main2").hide();
		$("#main").show();
	}
	
	//当下一步按钮点击时，判断用户输入的数据是否有效，然后隐藏#main, 显示#main2
	var nextHandler = function(event){
		var userid = $("#userid").val();
		var email = $("#email").val();
		var pswd = $("#pswd").val();
		var psure = $("#psure").val();
		
		//如果有提示图标有警告class，则停止提交表单
		if($("#main").find("td.tip").find("span").hasClass("jinggao")){
			$.myPlugin.msgAlert("请仔细检查您填写的内容！","warn");
			return false;
		}
		
		//正则表达式验证所有字段
		if(userReg.exec(transformUserid(userid)) != null && emailReg.exec(email) !=null && pswdReg.exec(pswd)!=null && pswd == psure){
			$("#main").hide();
			$("#main2").show();
		}else{
			$.myPlugin.msgAlert("请仔细检查您填写的内容！","warn");
		}
	};
	//点击注册按钮提交表单
	$("#next").click(nextHandler);
	

////////////////////////////////////#main2填写真实身份信息部分////////////////////////////////////////////////
	
	//填写用户名密码
	$("#name").focus(function(){
		$(this).css("backgroundPosition","0 -44px");
		var $tip = $(this).parent().next("td.tip");
		$tip.children("span").removeClass().addClass("tishi").show();
		$tip.children("small").html("请输入你的真实姓名（不会被公布）").show();
	}).blur(function(){
		var $current = $(this);
		var name = $current.val();
		var $tip = $current.parent().next("td.tip");
		//如果用户输入正确
		if(nameReg.exec(name)!=null){
			$current.css("backgroundPosition","0 0");
			$tip.children("span").removeClass().addClass("zhengque");
			$tip.children("small").html("");
		}else{
			//用户格式不正确
			$current.css("backgroundPosition","0 -88px");
			$tip.children("span").removeClass().addClass("jinggao");
			$tip.children("small").html("请输入1-5位汉字");
		}
	});
	
	//学号
	$("#student_id").focus(function(){
		$(this).css("backgroundPosition","0 -44px");
		var $tip = $(this).parent().next("td.tip");
		$tip.children("span").removeClass().addClass("tishi").show();
		$tip.children("small").html("请输入你的学号").show();
	}).blur(function(){
		var current = $(this);
		var student_id = current.val();
		var $tip = $(this).parent().next("td.tip");
		if(studentIdReg.exec(student_id)!=null){
			current.css("backgroundPosition","0 0");
			$tip.children("span").removeClass().addClass("zhengque");
			$tip.children("small").html("");
		}else{
			current.css("backgroundPosition","0 -88px");
			$tip.children("span").removeClass().addClass("jinggao");
			$tip.children("small").html("您的学号格式输入错误！");
		}
	});
	
	
	
	//学院下拉框美化，及其点击列表的事件
	$("#collegeSelect").prettySelect({width:"250px",
		func:function(data){
			if(data!=""){
				$.ajax({
					url:"ajax/user_selectMajor",
					data:{
						college_id: data
					},
					dataType:"json",
					type:"post",
					context: $("#beforeMajorSelect"),
					error: function (){
						//$("#register_shade").hide();	
						//$("#main").find("button.register_shade").removeClass("register_shade");
						alert("服务器发生异常，请稍后重试！");
					},
					success: function (data){
						//清除以前所有创建的专业列表
						//隐藏select间隔#beforeMajorSelect
						//将专业隐藏域清除
						var $current = $(this);
						
						$current.nextAll(".select").remove();
						$current.hide();
						$("input[name='major'][type='hidden']").remove();
						//标记传递过来的专业是否为空，如果为空，则不会执行$.each()函数
						var flag = false;
						var $majorSelect = $("<select name='major'></select>");
						$majorSelect.append("<option value=''>请选择专业</option>");
						
						$.each(data.majorAjax, function(key, value){
							flag = true;
							$majorSelect.append("<option value='"+key+"'>"+value+"</option>");
						});
						//如果数据不为空，则添加专业结点、并转换为漂亮的select，插入到指定位置
						if(flag){
							$current.show();
							$current.after($majorSelect);
							$majorSelect.prettySelect({width:"250px"});
						}
					}
				});
			}
		}
	});
	
	//上一步按钮
	$("#previous").click(previousHandler);
	
	
	
	//注册事件
	var registerHandler = function(event){
		var userid = $("#userid").val();//用户ID
		var email = $("#email").val();//邮箱
		var pswd = $("#pswd").val();//密码
		var psure = $("#psure").val();//密码确认
		
		var name= $("#name").val();//真实姓名
		var sex = $("input[type='radio'][name='sex']:checked").val();//性别
		var student_id = $("#student_id").val();//学号
		var college = $("input[type='hidden'][name='college']").val();//所在学院
		var major = $("input[type='hidden'][name='major']").val();//专业
		//如果有提示图标有警告class，则停止提交表单
		if($("#main").find("td.tip").find("span").hasClass("jinggao")){
			$("#main2").hide();
			$("#main").show();
			return false;
		}else if($("#main2").find("td.tip").find("span").hasClass("jinggao")){
			return false;
		}
		
		//正则表达式验证所有字段
		if(userReg.exec(transformUserid(userid)) != null && emailReg.exec(email) !=null && pswdReg.exec(pswd)!=null && pswd == psure
				&& nameReg.exec(name) !=null && studentIdReg.exec(student_id) != null
				&& college !=null && college!="" && (major ==null || major!="")
		){
//			alert("userId:"+userid+"\n"+"email:"+email+"\n"+"pswd:"+pswd+"\n"+"name:"+name+"\nsex:"+sex+"\nstudent_id:"+student_id+
//					"\ncollege:"+college+"\nmajor:"+major);
			
			
			
			$.ajax({
				url:"ajax/user_register",
				data:{
					userid: userid,
					email: email,
					pswd: pswd,
					name: name,
					sex: sex=="male"?false:true,
					student_id: student_id,
					college_id:college,
					major_id:major==null? 0:major
				},
				dataType:"json",
				type:"post",
				context: $("#main").find("button"),
				beforeSend: function(xhr){
					$("#register_shade").show();	
					//$(this).addClass("register_shade").attr("disabled", "disabled").bind("mouseover", function(){$(this).addClass("register_shade");});
					$("#register_msg").html("<img src='images/loading.gif'>正在注册。。。");
				},
				error: function (){
					$("#register_shade").hide();	
					//$("#main").find("button.register_shade").removeClass("register_shade");
					alert("服务器发生异常，请稍后重试！");
				},
				complete: function(){
					//$(this).removeClass("register_shade").removeAttr("disabled").unbind("mouseover");
				},
				success: function (data){
					//$("#register_shade").hide();	
					//$(this).removeClass("register_shade").removeAttr("disabled");
					$("#register_msg").empty();
					if(data.msgAjax == "1"){
						//注册成功
						$("#main2").hide();
						$("#main3").show();
						$("#register_shade").hide();
						//将即将显示的页面的邮箱写入
						$("#main3_email").html(data.email);
					}else{
						//注册失败
						$("#register_msg").html("注册失败！请稍后重试");
					}
				}
			});
		}else{
			alert("请仔细检查您填写的内容！");			
		}
	};
	
	
	//申请注册按钮点击
	$("#register_shenqing").click(registerHandler);
	
	
	
	
///////////////////////////////////////#main_login登录部分//////////////////////////////////////////
	
	//登录表单部分
	//用户名
	$("#username").focus(function(){
		$(this).css("background-position", "0 -44px");
	}).blur(function(){
		var name = $(this).val();
//		if(userReg.exec(transformUserid(name)) != null){
		if(userReg.exec(transformUserid(name)) != null || emailReg.exec(name) != null){
			//用户名格式输入正确
			$(this).css("background-position", "0 0px");
		}else{
			//用户名输入错误
			$(this).css("background-position", "0 -88px");
		}
	}).keyup(function(event){
		if(event.keyCode == 13){
			$("#password").focus();
		}
	});
	//密码
	$("#password").focus(function(){
		$(this).css("background-position", "0 -44px");
	}).blur(function(){
		var pswd = $(this).val();
		if(pswdReg.exec(pswd) != null){
			$(this).css("background-position", "0 0px");
		}else{
			$(this).css("background-position", "0 -88px");
		}
		
	}).keyup(function(event){
		//如果点击回车，提交表单并去除focus，将focus加到按钮
		if(event.keyCode == 13){
			$(this).blur();
			$("#main_login").focus();
			loginHandler();
		}
	});
	//登录请求处理
	var loginHandler = function(){
		var name = $("#username").val();
		var pswd = $("#password").val();
		if((userReg.exec(transformUserid(name)) != null  || emailReg.exec(name) != null) && pswdReg.exec(pswd) != null){
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
					$("#login_shade").show();
					$("#login_msg").html("<img src='images/loading.gif'>正在登陆。。。");
				},
				error: function (){
					$("#login_msg").html("服务器发生异常，请稍后重试！");
				},
				complete: function(){
					$("#login_shade").hide();
				},
				success: function (data){
					if(data.msgAjax == "1"){
						//登录成功
						$("#login_msg").html("<span style='color:#565656'>登录成功，正在转到论坛首页</span>");
						setTimeout(function(){
							window.location.href="index";
						}, 500);
						
					}else{
						$("#login_msg").html("<span style='color:#e3030f'>登录失败，用户名或密码输入错误！</span>");
					}
				}
			});
		}else{
			$("#login_msg").html("<span style='color:#e3030f'>用户名或密码格式不正确，请检查您的输入</span>");
		}			
	};
	//登录按钮
	$("#main_login").find("button").click(loginHandler);
	
////////////////////////////////////找回密码部分////////////////////////////////////////////////////////////
	
	//验证用户输入的邮箱是否
	$("#pswd_email").focus(function(){
		var $current = $(this);
		var email = $current.val();
		if(!emailReg.exec(email) == null){
			// 用户输入的数据格式不正确
			$current.css("backgroundPosition", "left -88px");
		}		
	}).keyup(function(){
		var $current = $(this);
		var email = $current.val();
		if(emailReg.exec(email) != null){
			$current.css("backgroundPosition", "left -44px");
		}else{
			$current.css("backgroundPosition", "left -88px");
		}		
	});
	
	// 验证码删除设置时间变量
	var deleteCW;
	// 验证码提示td内容页面的循环延时时间变量
	var intervalCW;
	// 该函数用于设置倒计时，以提醒用户还剩的时间
	var daojishi = function(){
		var td = $("#pswd_cw").parent().next("td");
		var m = 5;
		var n = 0;
		clearInterval(intervalCW);
		intervalCW = setInterval(function(){
			if (m == 0 && n == 0 ){
				clearInterval(intervalCW);
				td.html("请重新获取验证码");
				// 设置5分钟删除验证码
				$.post("ajax/register_deleteCW");						
			}else{
				if(n > 0){
					n --;
				}else if(m > 0){
					n = 59;
					m --;
				}
				td.html("请在"+m+"分"+n+"秒内输入验证码");
			}
		}, 1000);
	}
	
	// 获取验证码按钮点击
	$("#getCheckWord").click(function(){
		var email = $("#pswd_email").val();
		if(emailReg.exec(email) != null){
			$.post("ajax/register_checkWord", {email:email},
				function(data){
					$.myPlugin.msgAlert(data.msgAjax);
					daojishi();
					
				}	
			,"json");
		}		
	});
	
	// 验证码输入成功后，点击下一步
	$("#back_next").click(function(){
		var cw = $("#pswd_cw").val();
		if(cw.length !=6 ){
			$.myPlugin.msgAlert("验证码长度是6位！");
			return false;
		}else{
			$.post("ajax/register_validateCWAjax", {checkWord:cw},function(data){
				if(data.msgAjax == "1"){
					$("#pswd_back > form").submit();			
				}else{
					$.myPlugin.msgAlert("您输入的验证码有误！");
				}
			},"json");
		}
	});
	
	// 找回密码第二部分
	
	// 第一个密码框
	//密码输入框
	$("#pswd_new").focus(function(){
		$(this).parent().next("td.tip").children("small").html("请输入3-16位密码").show();
		$(this).parent().next("td.tip").children("span").removeClass().addClass("tishi").show();
		$(this).css("backgroundPosition","0 -44px");
	}).blur(function(){
		var $current = $(this);
		var pswd = $current.val();
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small"); 
//		$("#passwordStrengthDiv").hide();
		var regResult = pswdReg.exec(pswd);
		if(regResult != null){
			//正则表达式匹配密码成功
			$current.css("background-position","0 0");
			$span.removeClass().addClass("zhengque");
			$small.hide();
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(pswd == ""){
				//密码为空
				$small.html("密码不能为空！")
			}else if(pswd.length<3 || pswd.length>16){
				$small.html("密码长度在3-16位之间");
			}
			else{
				$small.html("密码不能使用特殊字符！")
			}
		}
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#pswd_new_sure").focus();
		}
	});
	
	// 密码确认框
	$("#pswd_new_sure").focus(function(){
		$(this).parent().next("td.tip").children("small").html("请再次确认您的密码").show();
		$(this).css("backgroundPosition","0 -44px");
		$(this).parent().next("td.tip").children("span").removeClass().addClass("tishi").show();
	}).blur(function(){
		var $current = $(this);
		var psure = $current.val();
		var $span = $current.parent().next("td.tip").children("span");
		var $small = $span.next("small");
		
		if(pswdReg.exec(psure) != null && psure == $("#pswd_new").val()){
			$small.hide();
			$current.css("background-position", "0 0");
			$span.removeClass().addClass("zhengque");
		}else{
			$current.css("background-position","0 -88px");
			$span.removeClass().addClass("jinggao");
			if(psure == ""){
				$small.html("确认密码不能为空！")
			}else if(psure != $("#pswd_new").val()){
				$small.html("您两次输入的密码不一致！");
			}else {
				$small.html("密码格式不正确！");
			}
		}
	}).keyup(function(event){
		if(event.keyCode == 13){
			$(this).blur();
			$("#pswd_save").focus();
		}
	});
	
	// 保存密码点击后
	$("#pswd_save").click(function(){
		var pswd = $("#pswd_new").val();
		if(pswd == ""){
			$.myPlugin.msgAlert("密码不能为空！");
			return false;
		}else if($("#pswd_new_sure").val() != pswd){
			$.myPlugin.msgAlert("您两次输入的密码不一致 ！");
			return false;
		}else if(pswdReg.exec(pswd) == null){
			$.myPlugin.msgAlert("密码必须在3-16位之间！");
			return false;
		}else{
			$("#pswd_reset > form").submit();
		}
	});
});