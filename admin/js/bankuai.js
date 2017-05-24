$(document).ready(function(){
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
	
	$("#table tr").each(function(index){
		var tr = $(this);
		var td = $(this).children("td.text");
		td.click(function(){
			$("#table tr td").each(function(){
				if($(this) != td && $(this).children("input").length > 0){
					var t = $(this);
					t.html(t.children("input").val());
				}
			});
			var $current = $(this);
			if($current.children("input").length > 0){
				return false;
			}
			var width = $current.width();
			var height = $current.height();
			var data = $current.html();
			$current.html("");
			var inputObj = $("<input type='text' value='"+data+"'>")
					.height($current.height()-15).width($current.width()-2);
			$current.append(inputObj);
			inputObj.trigger("focus").trigger("select");
			inputObj.click(function(){
				return false;
			});
			inputObj.keyup(function(e){
				var keyCode = e.keyCode;
				if(keyCode == 13){
					// Enter
					var data = $(this).val();
					$current.html(data);
				}else if(keyCode == 27){
					// Esc
					var data = $(this).val();
					$current.html($current.attr("data"));
				}
			});
			inputObj.keydown(function(e){
				if(e.keyCode == 9){
					// Tab
					if(td.index($current) <2){
						$current.next("td").click();
						e.preventDefault();
					}else{
						$current.html(inputObj.val());
					}
				}
			})
			
		});
			
		// 保存点击时
		$(this).children("td.save_button").children("button:not(#addNewCate):first").click(function(){
			$("#table tr td").each(function(){
				if($(this).children("input").length > 0){
					var t = $(this);
					t.html(t.children("input").val());
				}
			});
			var $current = $(this).parent("td");
			var $tr = $current.parent();
			var $td0 = $tr.children("td:eq(0)")
			var $td1 = $tr.children("td:eq(1)")
			var $td2 = $tr.children("td:eq(2)")
			var $td3 = $tr.children("td:eq(3)")
			var id = $td0.html();
			var name = $td1.html();
			var info = $td2.html();
			var admin = $td3.html();
			$.post("../../adminAjax/adminAjax_updateCate",{cate_id:id,cate_name:name,cate_info:info,user_id:admin},function(data){
				if(data.msgAjax == "1"){
					$.myPlugin.msgAlert("更新成功！");
					$td1.attr("data",name);
					$td2.attr("data",info);
					$td3.attr("data",admin);				
				}else{
					$.myPlugin.msgAlert(data.msgAjax,"warn");
				}
			},"json");
		});
		
		// 取消点击时
		$(this).children("td.save_button").children("button:last").click(function(){
			$("#table tr td").each(function(){
				if($(this).children("input").length > 0){
					$(this).html($(this).children("input").val());
				}
			});
			$(this).parents("tr").children("td.text").each(function(index){
				$(this).html($(this).attr("data"));
			});
		});
	})
	
	// 每行中删除链接当点击tab键时，焦点对准下一列表格的第一个带input的表格，
	$("td a.deleteCate").keydown(function(e){
		if(e.keyCode == 9){
			$(this).parents("tr").next("tr").children("td.text:first").click();
			e.preventDefault();
		}
	}).click(function(e){
		// 删除链接点击时，提示信息，并发送请求
		var $current = $(this);
		$.myPlugin.prettyAlert("您确定要删除该版块吗？（不可恢复，主题贴会同时被删除）","confirm", function(){
			var $tr = $current.parents("tr");
			var cate_id = $tr.children("td:eq(0)").html();
			// TODO ajax请求
			$.post("../../adminAjax/adminAjax_deleteCate",{cate_id:cate_id},function(data){
				if(data.msgAjax == "1"){
					$.myPlugin.msgAlert("删除成功！");
					setTimeout(function(){
						location.reload();		
					},1000);
				}else{
					$.myPlugin.msgAlert(data.msgAjax,"warn");
				}
			},"json");
		},function(){},false);
	});
	
	// 点击“添加版块”，显示新增行
	$("a.addCate").click(function(){
		$("#addTr").show();
	});
	
	// 新增行中，点击删除链接，清空行内数据并隐藏该行
	$("a.deleteNewCate").click(function(){
		var $trNew = $("#addTr");
		$trNew.children("td.text").html("");
		$trNew.hide();
	});
	
	// 新增行中，点击保存按钮，获取刚刚输入的数据，判断数据是否为空，并发送请求写入数据库
	$("#addNewCate").click(function(){
		var $trNew = $("#addTr");
		var name = $trNew.children("td.text:eq(0)").html();
		var info = $trNew.children("td.text:eq(1)").html();
		var admin = $trNew.children("td.text:eq(2)").html();
		// ajax请求交互，新增数据
		$.post("../../adminAjax/adminAjax_addCate",{cate_name:name,cate_info:info,user_id:admin},function(data){
			if(data.msgAjax == "1"){
				$.myPlugin.msgAlert("添加成功！");
				setTimeout(function(){
				location.reload();		
				},1000);
			}else{
				$.myPlugin.msgAlert(data.msgAjax,"warn");
			}
		},"json");
	});
	
	// 论坛信息部分
	$("#forum_info_save").click(function(){
		var info = $("#forum_info > input").val();
		if($.trim(info) != ""){
			$.post("../../adminAjax/adminAjax_updateForum",{forum_info:info},function(data){
				if(data.msgAjax == "1"){
					$.myPlugin.msgAlert("更新成功！");
				}else{
					$.myPlugin.msgAlert(data.msgAjax);
				}
			},"json");
		}else{
			$.myPlugin.msgAlert("论坛信息不能为空！");
		}
	});
});