/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module("AdminApp").
controller('resultController', function ($scope, $rootScope, resultService) {
    $rootScope.menu = 3;
    $scope.parentGroupId = sessionStorage['pgroupid'];
    
    $scope.reportTypeList = [{name: 'ALL', title: '总帐'}, {name: 'SUB', title: '分类帐'}];
    $scope.reportOptionList = [{name: 'ALL', title: '全部'}, {name: 'SHANGJIAO', title: '上缴'}, {name: 'YINGFEN', title: '赢分'}];
    $scope.curDate = new Date();
    $scope.reportDateType = 0;
        
    $scope.setCurDate = function() {
        $scope.dateStart = new Date();
        $scope.dateEnd = new Date();
    };
    
    $scope.getDateStr = function(time) {
        var month = time.getMonth() + 1;
        var date = time.getDate();
        return time.getFullYear() + '-' + (month < 10 ? '0' : '') + month + '-' + (date < 10 ? '0' : '') + date;
    }
    
    function addDay(time, days) {
        var t = new Date(time);
        t.setDate(t.getDate() + days);
        return t;
    }
    
    $scope.setTimeRange = function(type) {
        var start, end;
        var now = new Date();
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);
        now.setMilliseconds(0);
        
        switch (type) {
            case 0: // 昨日
                start = addDay(now, -1);
                end = start;
                break;
            case 1: // 上周
                var nowDayOfWeek = (now.getDay() == 0) ? 7 : now.getDay() - 1;
                start = addDay(now, -nowDayOfWeek - 7);
                end = addDay(start, 6);
                break;
            case 2: // 本周
                var nowDayOfWeek = (now.getDay() == 0) ? 7 : now.getDay() - 1;
                start = addDay(now, -nowDayOfWeek);
                end = addDay(start, 6);
                break;
            case 3: // 上月
                start = new Date(now.getFullYear(), now.getMonth() - 1, 1);
                end = addDay(new Date(now.getFullYear(), now.getMonth(), 1), -1);
                break;
            case 4: // 本月
                start = new Date(now.getFullYear(), now.getMonth(), 1);
                end = now;
                break;
        }
        $scope.dateStart = start;
        $scope.dateEnd = end;
    }
    
    $scope.setCurDate();
    
    resultService.getTopTypeList().then(function (data) {
       $scope.wagerTypeList = data;
       $scope.wagerTypeList.splice(0, 0, {name: 'ALL', title: '全部'});
       $scope.selectedWagerType = $scope.wagerTypeList[0];
    });
    
    resultService.getLotteryMarkSixStatList().then(function (data) {
        $scope.lotteryMarkSixGroupStatList = data;
    });
    
    resultService.getSubGroupList().then(function(data) {
       $scope.subGroupList = data;
       $scope.subGroupList.splice(0, 0, {id: 'ALL', name: '全部'});
       $scope.selectedSubGroup = data[0];
    });
    
}).filter('chineseWeek', function () {
    return function (input) {
        return "日一二三四五六".charAt(new Date(input).getDay())
    };
}).filter('concatBall', function () {
    return function (input) {
        return '<span class="ball' + input.one + '">' + input.one + '</span>'
    }
    
}).controller('resultDetailController', function ($scope, $rootScope, $routeParams, resultService) {
    $scope.groupId = $routeParams.subGroupId;
    $scope.reportDateType = $routeParams.reportDateType;
    $scope.reportType = $routeParams.reportType;
    $scope.curDate = new Date();
    $scope.parentGroupId = $routeParams.parentGroupId;
    $scope.start = $routeParams.start;
    $scope.end = $routeParams.end;
    
    $scope.groupSummaryList = [];
    $scope.userSummaryList = [];
    
    if ($scope.reportType == 'ALL') {
        resultService.getSubGroupSummary($routeParams.parentGroupId, $routeParams.start, $routeParams.end).then(function(data) {
            if ($scope.groupId == 'ALL') {
                $scope.groupSummaryList = data;
            } else {
                for (var i = 0; i < data.length; i ++) {
                    if (data[i].pgroupId == $scope.groupId) {
                        $scope.groupSummaryList.push(data[i]);
                        break;
                    }
                }
            }
        });
       
        resultService.getUserSummary($routeParams.parentGroupId, $routeParams.start, $routeParams.end).then(function(data) {
            $scope.userSummaryList = data;
            
            $scope.userStakesTotal = 0;
            $scope.userValidStakesTotal = 0;
            $scope.userTuishuiTotal = 0;
            $scope.userResultTotal = 0;
            $scope.userSummaryList.forEach(function (ele) {
                $scope.userStakesTotal += ele.stakes;
                $scope.userValidStakesTotal += ele.validStakes;
                $scope.userTuishuiTotal += ele.tuishui;
                $scope.userResultTotal += ele.result;
            })
        });
        
    } else {  // 分类账
        resultService.getSummary4Type($routeParams.wagerType, $routeParams.parentGroupId, $routeParams.start, $routeParams.end).then(function(data) {
            $scope.wagerTypeReportList = data;
        });
    }
    
}).controller('reportStakeDetailController', function ($rootScope, $scope, $routeParams, resultService) {
    $scope.groupId = $routeParams.groupid;
    $scope.from = $routeParams.from;
    $scope.to = $routeParams.to;

    if ($routeParams.userid) {
        realtimeService.getStakesDetailByUser($routeParams.userid, $routeParams.groupid, $routeParams.issue).then(function (data) {
            $scope.wagerList = data;
            $scope.userLinkEnabled = false;
        });
    } else {
        realtimeService.getStakesDetail(
            $routeParams.type,
            $routeParams.groupid,
            $routeParams.panlei,
            $routeParams.issue,
            $routeParams.subtype,
            $routeParams.number,
            $routeParams.content,
            $routeParams.isAll).then(function (data) {
                $scope.wagerList = data;
                $scope.userLinkEnabled = true;
        });
    }
});
