/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
controller('settingsController', function ($rootScope, $scope, $http, realtimeService) {
    $rootScope.menu = 6;
    
    $scope.gotoPage = function (page) {
        $scope.curPage = page;
        switch (page) {
            case 'odds':
                $scope.getOdds();
                break;
            case 'tuishui':
                $http.get("pgroup/users/" + sessionStorage['pgroupid']).success(function (data) {
                    $scope.model.userList = data;
                    $scope.model.selectedUser = data[0];
                    $scope.getTuishui();
                });
                break;
        }
    };

    /*--------------- Odds --------------*/
    
    $scope.getOdds = function () {
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
            $http.get("odds/lottery_issue/" + $scope.lotteryMarkSixInfo.issue + "/group/" + sessionStorage['pgroupid'] + "/pan/" + $scope.model.selectedPan.name).success(function (data) {
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
                    	$scope.oddsMap[odds.lotteryMarkSixType+"#"+odds.lotteryBallNumber+"#"+odds.lotteryBallType] = odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("JOINT_TAIL_") == 0){
                    	$scope.oddsMap[odds.lotteryMarkSixType+"#"+odds.lotteryBallNumber] = odds;
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
                    else if (odds.lotteryMarkSixType == "TWO_FACES") {
                    	$scope.oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallType] = odds;
                    }
                }
            });
        })
    };

    $scope.markOdds = function (oddsId, oddsToChange) {
        $scope.model.oddsId = oddsId;
        $scope.model.oddsToChange = oddsToChange;
    };

    $scope.changeOdds = function () {
        console.log($scope.model.oddsId + " " + $scope.model.oddsToChange);
        $http.post('odds/change', {
            oddsId: $scope.model.oddsId,
            odds: $scope.model.oddsToChange
        }).success(function (data) {
            $('a.close').click();
            $('#' + data.id).text(data.odds);
        })
    };
    
    /* ------------ Tuishui -------------- */
    
    $scope.getTuishui = function () {
        $scope.panleiList = ["A", "B", "C", "D"];
        $scope.tuishuiList = [];
        for (var panIndex = 0; panIndex < $scope.panleiList.length; panIndex ++) {
            (function(panIndex) {
                $http.get("tuishui/user/" + $scope.model.selectedUser.id + "/group/" + sessionStorage['pgroupid'] + "/pan/" + $scope.panleiList[panIndex]).success(function (data) {
                    $scope.tuishuiList[panIndex] = data;
                });
            })(panIndex);
        }
    };
    
    $scope.markTuishui = function (tuishuiId, tuishuiToChange) {
        $scope.model.tuishuiId = tuishuiId;
        $scope.model.tuishuiToChange = tuishuiToChange;
    };

    $scope.changeTuishui = function () {
        console.log($scope.model.tuishuiId + " " + $scope.model.tuishuiToChange);
        $http.post('tuishui/change', {
            tuishuiId: $scope.model.tuishuiId,
            tuishui: $scope.model.tuishuiToChange
        }).success(function (data) {
            $('a.close').click();
            $('#' + data.id).text(data.tuishui);
        })
    };
    

    /*--------------- Init Page --------------*/
    $scope.settingType = [{id: 'odds', name: '赔率'}, {id: 'tuishui', name: '退水'}];
    
    $scope.pans = {
        panlei: [{name: 'A', title: 'A盘'},
            {name: 'B', title: 'B盘'},
            {name: 'C', title: 'C盘'},
            {name: 'D', title: 'D盘'}],
    };
    
    $scope.model = {};  //for child scope in directives
    $scope.model.selectedPan = $scope.pans.panlei[0];
    
    $scope.zodiacTypeMap = zodiacTypeMap;

    $scope.gotoPage('odds');

})