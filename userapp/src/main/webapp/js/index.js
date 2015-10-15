var indexApp = angular.module("index", []);
indexApp.controller("TabController", function ($scope, $http) {
	$scope.items=["特码","生肖色波","半波","合肖","正码","正码1~6","连码","自选不中","过关","一肖尾数","连肖","连尾","正码特"];
	$scope.selectedIndex=0;
	$scope.goTab=function(index){
		$scope.selectedIndex=index;
	}
});