/**
  * myWindow插件——窗口自动调整位置
  * @author: 冯旭
  * 实现div浮动窗口的位置的计算，以及根据窗口大小及滚动条改变浮动层的位置
  *	options参数：
  * @param left: 指定浮动层的left属性，可以为数字或字符串"center","left","right"
  * @param right: 指定浮动层的right属性，可以为数字或字符串"center","top","bottom"
  * @param close: 指定浮动层的关闭按钮，可以通过jquery对象的数组指定多个。
  * @param ifmove: 指定浮动层是否随窗口大小及滚动条改变位置，默认为true跟随
  * @param closeFunc: 关闭窗口时的回调函数
  */
(function($){
	$.fn.myWindow = function(options){
		var defaults = {left:"center", top:"center", ifmove:true, closeFunc:null};
		options = $.extend(defaults, options);
		
		return this.each(function(index, element){
			var $window = $(window);
			var $document = $(document);
			var $current = $(this);	
			var winWidth = $window.width();
			var winHeight = $window.height();
			var winScrollLeft = $window.scrollLeft();
			var winScrollTop = $window.scrollTop();
			var curWidth = $current.outerWidth(true);
			var curHeight = $current.outerHeight(true);
			
			var positionLeft = options.left;
			var positionTop = options.top;
			var left = 0;
			var top = 0;
			
			var myTime;
			
			//遮罩层display:none; background:#000; opacity:0.3; position:absolute; z-index:80; top:0; left:0;
			var $shade = $("<div></div>")
				.css({ backgroundColor:"#000000",display:"none", opacity:0.3, position:"fixed", top:0, left:0,
					width:$document.width(), height:$document.height(),zIndex:80
				}).appendTo("body").fadeIn(500);
			
			function calLeft(){
				if(typeof positionLeft == "string"){
					if(positionLeft == "center"){
						left = winScrollLeft + (winWidth - curWidth)/2;
					}else if(positionLeft == "right"){
						left = winWidth + winScrollLeft - curWidth;	
					}else if(positionLeft == "left"){
						left = winScrollLeft;
					}else{
						left = 0;
					}
				}else if(typeof positionLeft == "number"){
					left = positionLeft;
				}else{
					left = 0;
				}
			}
			
			function calTop(){
				if(typeof positionTop == "string"){
					if(positionTop == "center"){
						top = winScrollTop + (winHeight - curHeight)/2;
					}else if(positionTop == "bottom"){
						top = winHeight + winScrollTop - curHeight;	
					}else if(positionTop == "top"){
						top = winScrollTop;
					}else{
						top = 0;
					}
				}else if(typeof positionTop == "number"){
					top = positionTop;
				}else{
					top = 0;
				}
			}

			var handler = function(e) {
				//阴影css样式
				$shade.css({width:$document.width(), height:$document.height()});
				clearTimeout(myTime);
				myTime = setTimeout(function(){
					
					winWidth = $window.width();
					winHeight = $window.height();
					winScrollLeft = $window.scrollLeft();
					winScrollTop = $window.scrollTop();
					
					calLeft();
					calTop();
					if(!$current.is(":visible")){
						$current.css({left:left, top:top});
					}else{
						if(!options.ifmove) return;
						$current.stop(true, false).animate({left:left, top:top}, 600);
					}
				}, 10);
            };
			
			calLeft();
			calTop();
			$current.css({left:left, top:top, zIndex:100}).fadeIn(500);
			
			$window.resize(handler).scroll(handler);
			
			if(options.close){
				$(options.close).each(function(index, element){
					$(this).click(function(){
						$current.stop(true, false).fadeOut(500);
						$shade.fadeOut(500);
						if(options.closeFunc && typeof options.closeFunc =="function"){
							options.closeFunc();
						}
						setTimeout(function(){$shade.remove();}, 500);
					});		
				});
			}			
		});
	};
	
	//debug私有函数
	function debug($obj) {
		if (window.console && window.console.log){
			window.console.log('mywindow元素数量: ' + $obj.size());
		}
	};	
})(jQuery);