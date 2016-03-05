angular.module("AdminApp", ['ngRoute', 'ui.bootstrap']).
    controller('indexController', function ($scope, $http, $interval) {
        $scope.userid = sessionStorage['userid'];
        $scope.username = sessionStorage['username'];
        $scope.groupid = sessionStorage["pgroupid"];
        $scope.groupname = sessionStorage["pgroupname"];
        $scope.menu;

        if (!$scope.userid || !$scope.username || !$scope.groupid || !$scope.groupname) {
            location.href = "login.html";
        }
        $http.get('common/platform_name').success(function (data) {
            $scope.platformName = data.name;
            $scope.corpName = data.corp;
        });
        $http.get('common/platform_period').success(function (data) {
            $scope.setOddsPeriod = data.setOddsPeriod;
        });
        $interval(function () {
            var currentTime = new Date();
            $scope.nowTime = currentTime;

        }, 1000);
        $http.get('lottery/next_lottery_mark_six_info').success(function(data){
            var drawTime = new Date(data.date);
            $interval(function () {
                var now = new Date();
                var distance = (drawTime.getTime() - now.getTime()) / 1000;
                if (distance >= 0) {
                    var hour = parseInt(distance / 3600);
                    var minute = parseInt((distance % 3600) / 60);
                    var second = parseInt(((distance % 3600) % 60));
                    $scope.time2Draw = hour + "小时" + minute + "分" + second + "秒";
                }
            }, 1000);
        })
        $http.get('lottery/lottery_issue/last').success(function (data) {
            $scope.lastLotteryMarkSix = data;
        })
    }).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/accounts', {
            controller: 'accountsController',
            templateUrl: 'includes/accounts.html'
        }).when('/result', {
            controller: 'resultController',
            templateUrl: 'includes/result.html'
        }).when('/resultdetail', {
            controller: 'resultDetailController',
            templateUrl: 'includes/resultdetail.html'
        }).when('/rules', {
            controller: 'rulesController',
            templateUrl: 'includes/rules.html'
        }).when('/links', {
            controller: 'linksController',
            templateUrl: 'includes/links.html'
        }).when('/withdraw', {
            controller: 'withdrawController',
            templateUrl: 'includes/withdraw.html'
        }).when('/history', {
            controller: 'historyController',
            templateUrl: 'includes/history.html'
        }).when('/settings', {
            controller: 'settingsController',
            templateUrl: 'includes/settings.html'
        }).when('/realtime', {
            controller: 'realtimeController',
            templateUrl: 'includes/realtime.html'
        }).when('/stakesdetail/groupid/:groupid/pan/:panlei/:type/issue/:issue/ball/:number', {
            controller: 'stakesDetailController',
            templateUrl: 'includes/stakesdetail.html'
        }).when('/stakesdetail/user/:userid/groupid/:groupid/issue/:issue', {
            controller: 'stakesDetailController',
            templateUrl: 'includes/stakesdetail.html'
        }).when('/stakesdetail/user/:userid/groupid/:groupid', {
            controller: 'stakesDetailController',
            templateUrl: 'includes/stakesdetail.html'
        }).when('/stakesdetail/report/groupid/:groupid/from/:from/to/:to', {
            controller: 'reportStakeDetailController',
            templateUrl: 'includes/stakesdetail.html'
        }).otherwise({
            redirectTo: '/realtime'
        });
    }]).filter('issueProcessor', function () {
        return function (issue) {
            if (issue != null) {
                issue = issue.toString().substr(4);
                return parseInt(issue);
            }
        }
    });


