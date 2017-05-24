$(document).ready(function(){
	$("a.regain").click(function(){
		var $current = $(this);
		$.myPlugin.prettyAlert("您确定要恢复该主题吗？", "confirm", function(){
			var sub_id = $current.attr("data");
			
			$.post("../../userAjax/subject_updateState",{ operate_flag:"nodelete", sub_id:sub_id },
				function (data){
				if(data.msgAjax == "1"){
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
});