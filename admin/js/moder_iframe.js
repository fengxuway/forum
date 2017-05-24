$(document).ready(function(){
	$("a.regain").click(function(){
		var $current = $(this);
		$.myPlugin.prettyAlert("您确定要恢复该主题吗？", "confirm", function(){
			var sub_id = $current.attr("data");
			
			$.post("../../userAjax/subject_updateState",{ operate_flag:"nodelete", sub_id:sub_id },
				function (data){
				if(data.msg == "1"){
					location.reload();
				}else{
					$.myPlugin.msgAlert("操作失败！");
				}
			},"json");
		}, function(){}, false);
		
	});
	
	$("a.delete").click(function(){
		var $current = $(this);
		$.myPlugin.prettyAlert("您确定要彻底该主题吗（不可恢复）？", "confirm", function(){
			var sub_id = $current.attr("data");
			
			$.post("../../adminAjax/iframeAjax",{ method:"deleteSubject", sub_id:sub_id },
				function (data){
				if(data.msgAjax == "1"){
					location.reload();
				}else{
					$.myPlugin.msgAlert("操作失败！");
				}
			},"json");
		}, function(){}, false);
		
	});
	
	$("a.removePerfect").click(function(){
		var $current = $(this);
		var sub_id = $current.attr("data");
		
		$.post("../../userAjax/subject_updateState",{ operate_flag:"noperfect", sub_id:sub_id },
			function (data){
			if(data.msg == "1"){
				location.reload();
			}else{
				$.myPlugin.msgAlert("操作失败！");
			}
		},"json");
	});
	
	$("a.removeTop").click(function(){
		var $current = $(this);
		var sub_id = $current.attr("data");
		
		$.post("../../userAjax/subject_updateState",{ operate_flag:"notop", sub_id:sub_id },
			function (data){
			if(data.msg == "1"){
				location.reload();
			}else{
				$.myPlugin.msgAlert("操作失败！");
			}
		},"json");
	});
	
	$("a.removeForbid").click(function(){
		var $current = $(this);
		var forbid_id = $current.attr("data");
		
		$.post("../../adminAjax/iframeAjax",{ method:"removeForbid", forbid_id:forbid_id },
			function (data){
			if(data.msgAjax == "1"){
				location.reload();
			}else{
				$.myPlugin.msgAlert("操作失败！");
			}
		},"json");
	});
	
	$("#shenqing").find("tr").children("td.register_button").each(function(index){
		var $current = $(this);
		var userid = $current.children("input").val();
		var $tongyi = $current.children("button:first");
		var $fouding = $current.children("button:last");
		//点击同意按钮
		$tongyi.click(function(){
//			$(this).remove();
//			alert($(this).parent().parent().siblings("tr").length);
			$.ajax({
				url:"../../adminAjax/adminAjax_agreeRegister",
				data:{
					user_id: userid
				},
				dataType:"json",
				type:"post",
				context: $current.parent(),
 				error: function (){
					alert("服务器异常，请稍后重试！");
				},
				success: function (data){
					if(data.msgAjax == "1"){
						location.reload();
					}else{
						$.myPlugin.msgAlert("服务器异常，请稍候重试！","error");
					}
				}
			});
		});
		//点击否定按钮
		$fouding.click(function(){
			$.myPlugin.prettyAlert("您确定要禁止该用户注册吗？","confirm", function(){
				$.post("../../adminAjax/adminAjax_againstRegister",{user_id:userid},function(data){
					if(data.msgAjax == "1"){
						location.reload();
					}else{
						$.myPlugin.msgAlert("删除失败！");
					}
				})
			},function(){},false);
		});		
	})
	
	// 会员管理页面点击封禁用户
	var forbid = function(user_id, $obj){
		var currentUser = $("input[name='session_user']").val();
		if(user_id == currentUser){
			$.myPlugin.msgAlert("您不能封禁自己！");
			return false;
		}
		var result = false;
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
				$.post("../../ajax/user_method", {method:"forbid", userid: user_id, days:days},
					function(data){
						if(data.msgAjax == "1"){
							$.myPlugin.msgAlert("封禁成功！");
							$obj.removeClass("forbid").addClass("unforbid");
						}else{
							$.myPlugin.msgAlert(data.msgAjax);
						}
					}
					,"json");
			},function(){},false
		);
		
		return result;
	}
	$("#huiyuan p.forbid").live("click",function(){
		forbid($(this).attr("data"), $(this));
	});
	
	$("#huiyuan p.unforbid").live("click",function(){
		var $current = $(this);
		var user_id = $current.attr("data");
		
		$.post("../../adminAjax/iframeAjax",{ method:"removeUserForbid", user_id:user_id },
			function (data){
			if(data.msgAjax == "1"){
				$current.removeClass("unforbid").addClass("forbid");
				$.myPlugin.msgAlert("解除封禁成功！");
			}else{
				$.myPlugin.msgAlert("操作失败！");
			}
		},"json");
	});
	
	$("#huiyuan a.deleteUser").live("click",function(){
		var user_id = $(this).attr("data");
		$.myPlugin.prettyAlert("您确定要删除该用户吗？（同时删除用户发表过的主题，回复等）", "confirm",function(){
			$.post("../../adminAjax/iframeAjax", {method:"deleteUser",user_id:user_id},
				function(data){
				if(data.msgAjax == "1"){
					location.reload();
				}else{
					$.myPlugin.msgAlert("删除失败！");
				}
			}, "json");
		},function(){},false);
	});
	
});