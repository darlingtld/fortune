/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module("AdminApp").
controller('resultController', function ($scope, $rootScope, resultService) {
    $rootScope.menu = 3;
    
    $scope.formats = ['yyyy年MM月dd日', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];
    
    $scope.resultTypeList = [{name: '0', title: '总帐'}, {name: '1', title: '分类帐'}];
    $scope.resultOptionList = [{name: '0', title: '全部'}, {name: '1', title: '上缴'}, {name: '2', title: '赢分'}];
    $scope.curDate = new Date();
    $scope.dateType = 0;
        
    $scope.setCurDate = function() {
        var dateStr = new Date().getFullYear() + '-' + (new Date().getMonth() + 1) + '-' + new Date().getDate();
        $scope.dateStart = dateStr;
        $scope.dateEnd = dateStr;
    };
    
    $scope.setCurDate();
    
    resultService.getLotteryMarkSixStatList().then(function (data) {
        $scope.lotteryMarkSixGroupStatList = data;
    });
    
    resultService.getUserList().then(function(data) {
       $scope.userList = data.userList;
       $scope.selectedUser = data.userList[0];
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
    $scope.pageType = $routeParams.pageType;
    $scope.curDate = new Date();
    $scope.userId = $routeParams.userId;
    $scope.start = $routeParams.start;
    $scope.end = $routeParams.end;
    
    if ($scope.pageType == 0) {
        // 大股东报表
        
        resultService.getStatSummaryByDateRange(sessionStorage['pgroupid'], $routeParams.start, $routeParams.end).then(function(data) {
            //FIXME mock data
            $scope.summaryList = [];
            $scope.summaryList[0] = {
                username: $routeParams.userId,
                transactions: 1234,
                stakes: 213242,
                memberResult: -123323,
                groupResult: -23454,
                groupIncome: -23421,
                shoufei: 2343,
                shoufeiResult: -32423,
                zongjian: 0,
                companyResult: -23432
            }
        });
        
    } else if ($scope.pageType == 1) {
        // 代理商报表
        resultService.getStatSummaryByDateRange($routeParams.userId, $routeParams.start, $routeParams.end).then(function(data) {
            //FIXME mock data
            $scope.summaryList = [];
            $scope.summaryList[0] = {
                username: $routeParams.userId,
                transactions: 1234,
                stakes: 213242,
                memberResult: -123323,
                groupResult: -23454,
                groupIncome: -23421,
                shoufei: 2343,
                shoufeiResult: -32423,
                zongjian: 0,
                companyResult: -23432
            }
        });
    }
    
});