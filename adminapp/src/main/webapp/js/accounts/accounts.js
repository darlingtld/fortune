angular.module('AdminApp').
controller('accountsController', function ($rootScope) {
    $rootScope.menu = 2;
});

(function($){
	$(function(){
		$.get("/pgroup/pgroups/null", function(data){
			var html="";
			for(var i=0;i<data.length;i++){
				var pgroup=data[i], users=pgroup.userList;
				html+='<li><p class="pgroup" data-id="'+pgroup.id+'">'+pgroup.name+'</p>';
				if(users && users.length>0){
					html+="<ul>";
					for(var j=0;j<users.length;j++){
						var user=users[j];
						if(user.status=="DISABLED"){
							html+='<li><p class="user disabled" data-id="'+user.id+'">'+user.username+'</p></li>';
						}
						else if(user.status=="ENABLED"){
							html+='<li><p class="user" data-id="'+user.id+'">'+user.username+'</p></li>';
						}
					}
					html+="</ul>";
				}
				html+="</li>";
			}
			$(".first_level").html(html);
		});
		$("body").on("click", "#account_tree .pgroup", function(){
			$("#delete_user").show();
			$(".first_level ul").hide();
			$(this).next("ul").show();
			$("#account_tree .pgroup").removeAttr("selected");
			$(this).attr({
				"clicked": "clicked", //用于标记已经取过远程数据的pgroup
				"selected": "selected" //用于标记选中的pgroup
			});
		});
	});
})(jQuery);