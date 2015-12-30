var rulesApp = angular.module("rules",['ui.bootstrap']);
rulesApp.controller("RulesController", function ($scope, $http) {
    if (!sessionStorage["userid"]) {
        location.href = "login.html";
    }
    $scope.user = {
        username: sessionStorage["username"]
    }
    $http.get('common/platform_name').success(function (data) {
        $scope.platformName = data.name;
    });
});