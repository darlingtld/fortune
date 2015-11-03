angular.module("AdminApp", ['ngRoute']).
    controller('indexController', function ($scope) {
        $scope.userid = sessionStorage['userid'];
    }).config(['$routeProvider', function ($routeProvider) {
        $routeProvider
            .when('/result', {
                controller: 'resultController',
                templateUrl: 'includes/result.html'
            }).
            when('/rules', {
                templateUrl: 'includes/rules.html'
            }).
            when('/links', {
                templateUrl: 'includes/links.html'
            }).
            when('/withdraw', {
                templateUrl: 'includes/withdraw.html'
            })
            .otherwise({
                redirectTo: '/result'
            });
    }]);


