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
        $scope.corpName = data.corp;
    });
    $http.get('lottery/lottery_issue/last').success(function (data) {
        $scope.lastLotteryMarkSix = data;
    });
    $scope.colorMap = colorMap;
});

rulesApp.filter('issueProcessor', function () {
    return function (issue) {
        if (issue != null) {
            issue = issue.toString().substr(4);
            return parseInt(issue);
        }
    }
});