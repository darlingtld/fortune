angular.module("AdminApp", ['ngRoute', 'ui.bootstrap']).
controller('indexController', function ($scope, $interval) {
    $scope.userid = sessionStorage['userid'];
    $scope.username = sessionStorage['username'];
    $scope.groupid = sessionStorage["pgroupid"];
    $scope.groupname = sessionStorage["pgroupname"];
    $scope.menu;

    //setTime();
    $interval(function () {
        var currentTime = new Date();
        $scope.nowTime = currentTime;
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
    }).when('/settings', {
        controller: 'settingsController',
        templateUrl: 'includes/settings.html'
    }).when('/realtime', {
        controller: 'realtimeController',
        templateUrl: 'includes/realtime.html'
    }).when('/stakesdetail/groupid/:groupid/:type/issue/:issue/ball/:number', {
        controller: 'stakesDetailController',
        templateUrl: 'includes/stakesdetail.html'
    }).otherwise({
        redirectTo: '/realtime'
    });
}]);


