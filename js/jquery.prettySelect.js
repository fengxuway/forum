/**
  * 美化select插件
  * @author: 冯旭&史阳阳
  * 将select表单元素美化为固定的样子
  *	options参数：
  * @param width: 设定select的width属性，类型为数字字符串
  * @param func:  设定点击子列表option的内容时的回调函数，函数参数为点击对象的value
  * 
  * 需要引入的CSS样式：prettySelect.css
  * 需要引入的图片资源
  * 	ui-icons_ffffff_256x240.png
  *		ui-icons_808080_256x240.png
  *		select-shen.png
  *		select-qian.png
  *		select-bai.png
  */
(function($){
	
	$.fn.prettySelect = function(options){
		debug(this);
		//设置默认参数
		var defaults = {width:"100", func:function(data){}};
		options = $.extend(defaults, options);
		
		//调用插件的对象如果是对象集合，每个都进行处理
		return this.each(function(index, element){
			var $this = $(this);
			
			//获取select的name属性值并创建相同name的隐藏域
			var name = $this.attr("name");
			var val = $this.val()
			$this.before("<input type='hidden' name='"+name+"' value='"+val+"'>");
			//定义对象替代div
			var $select_head = $("<div class='select select-head'><span class='word'></span><span class='jiantou'>&nbsp;&nbsp;</span></div>");
			
			$select_head.children("span.word").html($this.children("option:first").html());
			var $select_main = $("<div class='select-main'></div>");
			//根据options的width属性设置select替代div的宽度。
			$select_head.width(options.width);
			$select_main.width(options.width);
			var $ul = $("<ul></ul>");
			//获取select下的所有子选项，并添加到替代div中。
			var $option = $this.children("option");
			$option.each(function(index, element) {
				var $tmp = $("<li>"+$(element).html()+"</li>");
				$tmp.data(name, $(element).val());
				$tmp.appendTo($ul);
			});
			$ul.appendTo($select_main);
			//限制下拉菜单的高度为行高25px * 8 = 200px
			if($option.length>8){ $select_main.height(200);}
		
			//隐藏下拉菜单替代div
			$select_main.hide();
			
			//添加结点select的head和main
			$this.after($select_head);
			$select_head.after($select_main);
			$select_main.appendTo($select_head);
			$this.remove();
			
			//点击select的替代div的head时
			$select_head.each(function(index, element){
				var $current = $(this);
				$current.click(function(){
				if($current.hasClass("select-head")){
					//点击出现下拉菜单
					$("div.select-head-click").removeClass("select-head-click").addClass("select-head");
					$current.removeClass("select-head").addClass("select-head-click");
					var $child = $current.children("div.select-main")
					$("div.select-main").not($child[0]).slideUp("fast");
					$child.slideDown("fast");
					return false;
				}
				else{
					//点击隐藏下拉菜单	
					$current.removeClass("select-head-click").addClass("select-head");
					$current.children("div.select-main").slideUp("fast");
					return false;
				}	
			});
			});
			
			
			//点击网页其他部分隐藏$select_main
			$(document).click(function(){
				$select_head.removeClass("select-head-click").addClass("select-head");
				$select_main.hide();	
			});
			
			//点击$select_main中的子项目时，将对应的数据写入隐藏域和select_head的内容，并隐藏$select_main
			$select_main.children("ul").children("li").each(function(index, element){
				var $current = $(this);
				$current.click(function(){
					$("input[type='hidden'][name='"+name+"']").val($current.data(name));
					$select_head.children("span.word").html($current.html());
					$select_head.removeClass("select-head-click").addClass("select-head");
					$select_main.hide();
					
					//执行回调函数
					options.func($current.data(name));
					
					return false;	
				});
			});
		});	
		
	};
	
	//debug私有函数
	function debug($obj) {
		if (window.console && window.console.log){
			// window.console.log('prettySelect元素数量: ' + $obj.size());
		}
	};
	
})(jQuery);