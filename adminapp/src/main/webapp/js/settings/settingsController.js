/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
controller('settingsController', function ($rootScope, $scope, $http, realtimeService) {
    $rootScope.menu = 6;
    $scope.pans = {
        panlei: [{name: 'A', title: 'A盘'},
            {name: 'B', title: 'B盘'},
            {name: 'C', title: 'C盘'},
            {name: 'D', title: 'D盘'}],
    };
    
    $scope.selectedPan = 'A';
    
    $scope.zodiacTypeMap = zodiacTypeMap;

    $scope.getOdds = function () {
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
            $http.get("odds/lottery_issue/" + $scope.lotteryMarkSixInfo.issue + "/group/" + sessionStorage['pgroupid'] + "/pan/" + $scope.selectedPan.name).success(function (data) {
            	$scope.specialOdds = [], $scope.oddsMap = {};
                for (var i = 0; i < data.length; i++) {
                    var odds = data[i];
                    if (odds.lotteryMarkSixType == "SPECIAL") {
                    	$scope.specialOdds.push(odds);
                    }
                    else if (odds.lotteryMarkSixType == "BLUE" || odds.lotteryMarkSixType == "RED" || odds.lotteryMarkSixType == "GREEN") {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("ZODIAC_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("WAVE_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "SUM_ZODIAC") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "ZHENG_BALL") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "ZHENG_1_6") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("ZHENG_SPECIFIC_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "JOINT_ZODIAC_PING" || odds.lotteryMarkSixType == "JOINT_ZODIAC_ZHENG") {
                    	$scope.oddsMap[odds.lotteryMarkSixType+"#"+odds.lotteryBallType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("JOINT_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("NOT_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("PASS_") == 0) {
                    	$scope.oddsMap[odds.lotteryMarkSixType] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "ONE_ZODIAC") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallType] = odds;
                    }
                    else if (odds.lotteryMarkSixType == "TAIL_NUM") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds;
                    }
                    console.log($scope.oddsMap);
                }
            });
        })
    };

    $scope.getOdds();
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
            $('#' + data.id).text(data.odds);
        })
    };
    
})