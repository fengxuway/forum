/**
  * 移动Div层插件
  * @author: 冯旭
  * 实现鼠标拖动div，可指定鼠标拖动的区域元素(jquery对象)，指定排除的元素（如关闭按钮等）
  *	options参数：
  * @param target: 设定拖动的目标，通常不需要整个div都作为可拖动的对象(jquery对象)
  * @param except: 指定不可作为拖动的对象, 即排除的元素, 可使用jquery对象的数组指定多个对象(jquery对象)
  * 
  */
(function($){
	$.fn.simpleMove = function(options){
		debug(this);
		//设置默认参数
		var $current = this;
		var defaults = {target:this, except: this.children()};
		options = $.extend(defaults, options);

		var $target = $(options.target);
		return this.each(function(index, element){
			var bool = false;
			var offsetX = 0;
			var offsetY = 0;
			
			//排除options.except中指出的对象不会触发移动div事件
			$(options.except).each(function(index, element){
				$(this).mousedown(function(e) {
					if($current != $(this) && e.target==$(this)[0]){
						//停止冒泡即可，不需阻止默认，否则该元素将无法正常使用
						e.stopPropagation();
					}
				});
			});	
			$target.each(function(index, element) {
                $(this).mousedown(function(e){
					bool = true;
					var position = $current.position();
					offsetX = e.pageX - position.left ;
					offsetY = e.pageY - position.top ;
					$target.css("cursor","move");		
				}).mouseup(function(e) {
					bool = false;
					$target.css("cursor","auto");
				});
            });
			
			
			$(document).mousemove(function(e) {
				if(!bool) return;
				var x = e.pageX - offsetX;
				var y = e.pageY - offsetY;
				$current.css({left:x, top:y});
			});
		});
	};
	
	//debug私有函数
	function debug($obj) {
		if (window.console && window.console.log){
			// window.console.log('simpleMove对象数量: ' + $obj.size());		
		}
	};
	
})(jQuery);