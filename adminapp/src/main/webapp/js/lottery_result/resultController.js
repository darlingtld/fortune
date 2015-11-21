/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module("AdminApp").
controller('resultController', function ($scope, $rootScope, resultService) {
    $rootScope.menu = 3;
    resultService.getLotteryMarkSixStatList().then(function (data) {
        $scope.lotteryMarkSixStatList = data;
        console.log($scope.lotteryMarkSixStatList);
    });
}).filter('chineseWeek', function () {
    return function (input) {
        return "日一二三四五六".charAt(new Date(input).getDay())
    };
}).filter('concatBall', function () {
    return function (input) {
        return '<span class="ball' + input.one + '">' + input.one + '</span>'
    }
});