/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, realtimeService) {
        $rootScope.menu = 1;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });

        realtimeService.getRealTimeTransaction(sessionStorage['pgroupid']).then(function (data) {
            $scope.realTimeTranscations = data;
            $scope.list = [];
            $scope.list[0] = $scope.realTimeTranscations.slice(0, 10);
            $scope.list[1] = $scope.realTimeTranscations.slice(10, 20);
            $scope.list[2] = $scope.realTimeTranscations.slice(20, 30);
            $scope.list[3] = $scope.realTimeTranscations.slice(30, 40);
            $scope.list[4] = $scope.realTimeTranscations.slice(40, 49);
            $scope.specialtypelist = [];
            $scope.specialtypelist[0] = $scope.realTimeTranscations.slice(49, 51);
            $scope.specialtypelist[1] = $scope.realTimeTranscations.slice(51, 53);
            $scope.specialtypelist[2] = $scope.realTimeTranscations.slice(53, 55);
            $scope.specialtypelist[3] = $scope.realTimeTranscations.slice(55, 57);
            $scope.specialtypelist[4] = $scope.realTimeTranscations.slice(57, 59);
            $scope.animaltypelist = [];
            $scope.animaltypelist[0] = $scope.realTimeTranscations.slice(59, 62);
            $scope.animaltypelist[1] = $scope.realTimeTranscations.slice(62, 65);
            $scope.animaltypelist[2] = $scope.realTimeTranscations.slice(65, 68);
            $scope.animaltypelist[3] = $scope.realTimeTranscations.slice(68, 71);
            $scope.animaltypelist[4] = $scope.realTimeTranscations.slice(71, 73);
            $scope.wavetypelist = [];
            $scope.wavetypelist[0] = $scope.realTimeTranscations.slice(73, 76);
            $scope.wavetypelist[1] = $scope.realTimeTranscations.slice(76, 79);
            $scope.wavetypelist[2] = $scope.realTimeTranscations.slice(79, 82);
            $scope.wavetypelist[3] = $scope.realTimeTranscations.slice(82, 85);
            $scope.othertypelist = [];
            $scope.othertypelist[0] = $scope.realTimeTranscations.slice(85, 87);
            $scope.othertypelist[1] = $scope.realTimeTranscations.slice(87, 90);
            $scope.othertypelist[2] = $scope.realTimeTranscations.slice(90, 93);
        })
    })