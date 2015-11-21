angular.module('AdminApp').
controller('accountsController', function ($rootScope) {
    $rootScope.menu = 2;
});

(function($){
	$(function(){
		var ROOT="-1";
		// 展开某层pgroup
		var showPGroupLevel=function(pgroupId, selector){
			$.get("pgroup/pgroups/"+pgroupId, function(data){
				var html="";
				for(var i=0;i<data.length;i++){
					var pgroup=data[i];
					html+='<li><p class="pgroup" data-id="'+pgroup.id+'">'+pgroup.name+'</p></li>';
				}
				$(html).appendTo($(selector));
				$(selector).show();
			});
			if(pgroupId!=ROOT){
				$.get("pgroup/users/"+pgroupId, function(data){
					if(data && data.length>0){
						var html="";
						for(var i=0;i<data.length;i++){
							var user=data[i];
							html+='<li><p class="user '+user.status.toLowerCase()+'" data-id="'+user.id+'">'+user.username+'</p></li>';
						}
						$(html).appendTo($(selector));
						$(selector).show();
					}
				});
			}
		};
		// 初始时候显示第一层
		showPGroupLevel(ROOT, ".first_level");
		
		$("body").on("click", "#account_tree .pgroup", function(){
			if(!$(this).hasClass("clicked")){
				var node=$("<ul></ul>").insertAfter($(this));
				showPGroupLevel($(this).attr("data-id"), node);
				$(this).addClass("clicked"); //用于标记已经取过远程数据的pgroup
				$("#account_tree p").removeClass("selected");
				$(this).addClass("selected"); //用于标记选中的pgroup
				$("#delete_user").show(); // 有selected才能有这两个按钮
				$("#add_user").show();
			}
			else{
				if($(this).hasClass("selected")){
					$(this).next("ul").hide();
					$(this).removeClass("selected");
				}
				else{
					$(this).next("ul").show();
					$("#account_tree p").removeClass("selected");
					$(this).addClass("selected"); //用于标记选中的pgroup
					$("#delete_user").show();// 有selected才能有这两个按钮
					$("#add_user").show();
				}
			}
		});
		
		$("body").on("click", "#account_tree .user", function(){
			$("#delete_user").show();
			$("#add_user").hide();
			$("#account_tree p").removeClass("selected");
			$(this).addClass("selected"); //用于标记选中的user
		});
		
		$("body").on("click", "#add_pgroup", function(){
			$(".dialog_title").html("增加新的代理商");
			// TODO validate null
			$(".dialog_body").html("<label for='pgroup_name_input'>代理商名称：</label><input type='text' id='pgroup_name_input'/>");
			$(".dialog").show();
		});
		
		$("body").on("click", "#add_user", function(){
			if($("#account_tree .pgroup.selected").length>0){
				$(".dialog_title").html("增加新的用户");
				// TODO validate null，可以选择已有用户
				$(".dialog_body").html("<label for='user_name_input'>用户名称：</label><input type='text' id='user_name_input'/><br/><br/><label for='user_pwd_input'>密码：</label><input type='password' id='user_pwd_input'/>");
				$(".dialog").show();
			}
			else{
				return false;
			}
		});
		
		$("body").on("click", "#dialog_confirm", function(){
			var parentId=ROOT;
			if($("#account_tree .pgroup.selected").length>0){
				parentId=$("#account_tree .pgroup.selected").attr("data-id");
			}
			if($("#pgroup_name_input").length>0){
				// 插入代理商
				var name=$("#pgroup_name_input").val(),
					parentPGroupID=parentId,
					admin={}, // TODO: admin怎么赛？
					userList=[];
				$.ajax({  
		            type: "POST",  
		            url: "pgroup/add_pgroup",  
		            contentType: "application/json",  
		            data: JSON.stringify({
						name: name,
						parentPGroupID: parentPGroupID,
						admin: admin,
						userList: userList
					}),  
		            success: function(){
						$(".dialog").hide();
						$(".first_level").empty();
						showPGroupLevel(ROOT, ".first_level");
					}
				});
			}
			else{
				// 插入用户
				var pgroup_id=parentId,
					username=$("#user_name_input").val(),
					password=$("#user_pwd_input").val();
				$.ajax({  
		            type: "POST",  
		            url: "pgroup/"+pgroup_id+"/add_user",  
		            contentType: "application/json",  
		            data: JSON.stringify({
						username: username,
						password: password
					}),  
		            success: function(){
						$(".dialog").hide();
						$(".first_level").empty();
						showPGroupLevel(ROOT, ".first_level");
					}
				});
			}
		});
		
		$("body").on("click", "#dialog_cancel", function(){
			$(".dialog").hide();
		});
	});
})(jQuery);