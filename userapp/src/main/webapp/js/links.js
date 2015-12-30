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
    });
});