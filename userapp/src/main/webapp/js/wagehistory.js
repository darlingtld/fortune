/**
 * Created by lingda on 2015/11/15.
 */
var wageHistoryApp = angular.module("wagehistory", []);
wageHistoryApp.controller("WageHistoryController", function ($scope, $http) {
        $scope.menu = 6;
        $http.get('user/' + sessionStorage["userid"]).success(function (data) {
            $scope.user = data;
            $scope.selectedPGroup = $scope.user.pGroupList[0];

        });
        $http.get('lottery/next_lottery_mark_six_info').success(function (data) {
            $scope.nextLottery = data;
            $scope.lotteryIssues = [];
            for (var i = 0; i < $scope.nextLottery.issue; i++) {
                $scope.lotteryIssues.push($scope.nextLottery.issue - i);
            }
            $scope.selectedIssue = $scope.lotteryIssues[0];
            $scope.getWageRecord($scope.user, $scope.selectedPGroup, $scope.selectedIssue)();
        });

        $scope.changePGroupAndIssue = function () {
            $scope.getWageRecord($scope.user, $scope.selectedPGroup, $scope.selectedIssue)();
        }

        $scope.getWageRecord = function (user, pgroup, issue) {
            return function () {
                $http.get('gamble/wage_record/' + user.id + '/pgroup/' + pgroup.id + '/lottery_issue/' + issue).success(function (data) {
                    $scope.wageHistory = data;
                    console.log($scope.wageHistory);
                });
            }


        }
    }
);

wageHistoryApp.filter('translate', function () {
    return function (input) {
        var typeMap = {
            "SPECIAL": "特码",
            "WAVE_GREEN_XIAO": "绿小",

        };
        return typeMap[input] == null ? input : typeMap[input];
    };
})