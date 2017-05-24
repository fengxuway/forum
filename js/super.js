$(document).ready(function(e) {
	//设置div的显示隐藏
	$("#main1").show().siblings("article").hide();
	
	//主导航点击事件
    $("nav > ul > li").each(function(index, element) {
       $(this).click(function(e) {   
			if(!$(this).hasClass("current"))     
			$(this).addClass("current").siblings("li.current").removeClass("current");
			$("#main"+index).show().siblings("article").hide();
		}); 
    });
	
	//#main7中的导航事件
	$("#main7_nav > ul > li").each(function(index, element) {
		var $main7_current = $(this);
        $main7_current.click(function(e) {
			if(!$main7_current.hasClass("main7_current")){
				$main7_current.addClass("main7_current").siblings("li.main7_current").removeClass("main7_current");
				$("#main7_content" + index).show().siblings("div.main7_content").hide();
			}
				
  		});
    });
	
	$("#main1").find("tr").children("td.register_button").each(function(index){
		var $current = $(this);
		var userid = $current.children("input").val();
		var $tongyi = $current.children("button:first");
		var $fouding = $current.children("button:last");
		//点击同意按钮
		$tongyi.click(function(){
//			$(this).remove();
//			alert($(this).parent().parent().siblings("tr").length);
			$.ajax({
				url:"../adminAjax/adminAjax_agreeRegister",
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
						if($(this).siblings("tr").length<=1){
							$(this).after($("<tr><td colspan='6'>目前暂无新注册的用户</td></tr>"));
						}
						$(this).remove();
					}else{
						$.myPlugin.msgAlert("服务器异常，请稍候重试！","error");
					}
				}
			});
		});
		//点击否定按钮
		$fouding.click(function(){
			alert("fouding "+userid);
			
		});
		
		
	});
});