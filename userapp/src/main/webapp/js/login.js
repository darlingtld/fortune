var loginApp = angular.module("login", []);
loginApp.controller("LoginController", function($scope, $http){
	$scope.isError = false;
	$scope.login = function(){
		$scope.isError = true;
		console.log("asdf");
	};
});