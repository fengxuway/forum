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
	
	
	
	$.fn.pageNum = function(options){
		//设置默认参数
		var defaults = {pageParameter:"page",ifFirst:true, ifLast:true, otherClass:"", currentClass:""};
		options = $.extend(defaults, options);
		
		return this.each(function(index, element){
			var $obj = this;
			var totalPage = parseInt(options.totalPage);
			var currentPage = parseInt(options.currentPage);
			var currentClass = options.currentClass;
			var otherClass = options.otherClass;
			var pageParameter = options.pageParameter;
			var ifFirst = options.ifFirst;
			var ifLast = options.ifLast;
			
			var href = window.location.href;
			//去掉锚点
			href = (href.indexOf("#")>=0)? href.substring(0, href.indexOf("#")) : href;
			
			//如果href没有查询字符串，直接连接page=...
			if(href.indexOf("?") < 0){
				href = href + "?" + pageParameter + "=~";
			}else{
				//如果href中存在查询字符串，运用正则表达式判断是否存在page字符串
				//var pageExec = eval("/"+ pageParameter + "=\d+/");
				var pageExec = /page=\d+/;
				//alert(pageParameter + "  "+pageExec.exec(href) +" ")
				//如果存在，替换之
				if(pageExec.exec(href) != null){
//					href = href.replace(pageExec, pageParameter + "=~");
					href = href.replace(pageExec, "page=~");
				//如果不存在，添加之
				}else{
//					href = href + "&" + pageParameter + "=~";
					href = href + "&page=~";
				}
				
			}
			if(totalPage <= 1){
				$("<a class='"+currentClass+"' href='javascript:void(0);'>只此一页，请君止步</a>").appendTo($obj);
			}else{
				if(ifFirst){
					if(currentPage == 1){
						$("<a class='"+currentClass+"' href='javascript:void(0);'>首页</a>").appendTo($obj);
					}else{
						$("<a class='"+otherClass+"' href='" + href.replace('~', 1) + "'>首页</a>").appendTo($obj);
						$("<a class='"+otherClass+"' href='" + href.replace('~', currentPage - 1) + "'>上一页</a>").appendTo($obj);
					}
				}else{
					if(currentPage == 1){
						$("<a class='"+currentClass+"' href='javascript:void(0);'>上一页</a>").appendTo($obj);
					}else{
						$("<a class='"+otherClass+"' href='" + href.replace('~', currentPage - 1) + "'>上一页</a>").appendTo($obj);
					}
				}
				
				var max = (currentPage + 2 > totalPage) ? totalPage : (currentPage + 2);
				var min = (currentPage - 2 <= 1) ? 1 : (currentPage - 2);
				var i = min;
				for(; i<= max ; i++){
					if(currentPage == i){
						$("<a class='"+currentClass+"' href='javascript:void(0);'>" + i + "</a>").appendTo($obj);
					}else{
						$("<a class='"+otherClass+"' href='" + href.replace('~', i) + "'>" + i + "</a>").appendTo($obj);
					}
				}
				if(ifLast){
					if(currentPage == totalPage){
						$("<a class='"+currentClass+"' href='javascript:void(0);'>末页</a>").appendTo($obj);
					}else{
						$("<a class='"+otherClass+"' href='" + href.replace('~', currentPage + 1) + "'>下一页</a>").appendTo($obj);
						$("<a class='"+otherClass+"' href='" + href.replace('~', totalPage) + "'>末页</a>").appendTo($obj);
					}
				}else{
					if(currentPage == totalPage){
						$("<a class='"+currentClass+"' href='javascript:void(0);'>下一页</a>").appendTo($obj);
					}else{
						$("<a class='"+otherClass+"' href='" + href.replace('~', currentPage + 1) + "'>下一页</a>").appendTo($obj);
					}
				}
				
			}
				
		});
		
		
	};
	
	//debug私有函数
	function debug($obj) {
		if (window.console && window.console.log){
			window.console.log('prettySelect元素数量: ' + $obj.size());
		}
	};
	
})(jQuery);