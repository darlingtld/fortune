angular.module("AdminApp", ['ngRoute']).
controller('indexController', function ($scope, $interval) {
    $scope.userid = sessionStorage['userid'];
    $scope.username = sessionStorage['username'];
    $scope.groupid = sessionStorage["pgroupid"];
    $scope.groupname = sessionStorage["pgroupname"];
    $scope.menu;

    //setTime();
    $interval(function() {
        var currentTime = new Date();
        $scope.nowTime = currentTime;
        console.log($scope.nowTime)
    }, 1000);
}).config(['$routeProvider', function ($routeProvider) {
    $routeProvider.when('/accounts', {
        controller: 'accountsController',
        templateUrl: 'includes/accounts.html'
    }).when('/result', {
        controller: 'resultController',
        templateUrl: 'includes/result.html'
    }).when('/rules', {
        controller: 'rulesController',
        templateUrl: 'includes/rules.html'
    }).when('/links', {
        controller: 'linksController',
        templateUrl: 'includes/links.html'
    }).when('/withdraw', {
        controller: 'withdrawController',
        templateUrl: 'includes/withdraw.html'
    }).when('/realtime', {
        controller: 'realtimeController',
        templateUrl: 'includes/realtime.html'
    }).otherwise({
        redirectTo: '/realtime'
    });
}]);


