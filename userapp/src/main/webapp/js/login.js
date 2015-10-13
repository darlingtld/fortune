var loginApp = angular.module("login", []);
loginApp.controller("LoginController", function($scope, $http) {
	$scope.isError = false;
	$scope.login = function() {
		$scope.isError = false;
		$http.post("user/login", {
			"name" : $scope.username,
			"password" : $scope.password
		}).success(function(response) {
			if (response) {
				document.location.href = "index.html";
			} else {
				$scope.isError = true;
			}
		});
	};
});