var linksApp = angular.module("links",['ui.bootstrap']);
linksApp.controller("LinksController", function ($scope, $http) {
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

linksApp.filter('issueProcessor', function () {
    return function (issue) {
        if (issue != null) {
            issue = issue.toString().substr(4);
            return parseInt(issue);
        }
    }
});