/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, realtimeService) {
        $rootScope.menu = 5;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });

        $scope.list = [1, 2, 3, 4, 5]
        realtimeService.getRealTimeTransaction(sessionStorage['pgroupid']).then(function (data) {
            $scope.realTimeTranscations = data;
            console.log($scope.realTimeTranscations);
        })
    })