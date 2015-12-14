/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
    controller('settingsController', function ($rootScope, $scope, $http, realtimeService) {
        $rootScope.menu = 6;

        $scope.getOdds = function (type) {
            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                $scope.lotteryMarkSixInfo = data;
                $http.get('odds/lottery_issue/' + $scope.lotteryMarkSixInfo.issue + '/group/' + sessionStorage['pgroupid'] + '/lottery_mark_six_type/' + type).success(function (data) {
                    $scope.odds = data;
                })
            })
        };

        $scope.getOdds('special');

        $scope.markOdds = function (oddsId, oddsToChange) {
            $scope.oddsId = oddsId;
            $scope.oddsToChange = oddsToChange;
        };

        $scope.changeOdds = function () {
            console.log($scope.oddsId + " " + $scope.oddsToChange);
            $http.post('odds/change', {
                oddsId: $scope.oddsId,
                odds: $scope.oddsToChange
            }).success(function (data) {
                $('a.close').click();
                $('#'+data.id).find('td:nth(1)').text(data.odds);
            })
        }
    })