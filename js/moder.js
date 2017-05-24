$(document).ready(function(e) {
	
	//主导航点击事件
    $("nav > ul > li").each(function(index, element) {
       $(this).click(function(e) {   
			if(!$(this).hasClass("current"))     
			$(this).addClass("current").siblings("li.current").removeClass("current");
			$("#main"+index).show().siblings("article").hide();
		}); 
    });
	
	//#main4中的导航事件
	$("#main4_nav > ul > li").each(function(index, element) {
		var $main4_current = $(this);
        $main4_current.click(function(e) {
			if(!$main4_current.hasClass("main4_current")){
				$main4_current.addClass("main4_current").siblings("li.main4_current").removeClass("main4_current");
				$("#main4_content" + index).show().siblings("div.main4_content").hide();
			}
				
  		});
    });
	
	
	
});