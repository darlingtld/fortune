/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, realtimeService) {
        $rootScope.menu = 1;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });

        $scope.list = [1, 2, 3, 4, 5]
        realtimeService.getRealTimeTransaction(sessionStorage['pgroupid']).then(function (data) {
            $scope.realTimeTranscations = data;
            $scope.list[0] = $scope.realTimeTranscations.slice(0, 10);
            $scope.list[1] = $scope.realTimeTranscations.slice(10, 20);
            $scope.list[2] = $scope.realTimeTranscations.slice(20, 30);
            $scope.list[3] = $scope.realTimeTranscations.slice(30, 40);
            $scope.list[4] = $scope.realTimeTranscations.slice(40);
            console.log($scope.realTimeTranscations);
            console.log($scope.list);
        })
    })