angular.module('AdminApp').
controller('accountsController', function ($rootScope) {
    $rootScope.menu = 2;
});

(function($){
	$(function(){
		$("body").on("click", "#account_tree .pgroup", function(){
			$(".first_level ul").slideUp();
			$(this).next("ul").slideDown();
		});
	});
})(jQuery);