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

        $scope.setCurDate = function () {
            $scope.dateStart = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
            $scope.dateEnd = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
        };

        $scope.setCurDate();

        resultService.getTopTypeList().then(function (data) {
            $scope.wagerTypeList = data;
            $scope.wagerTypeList.splice(0, 0, {name: 'ALL', title: '全部'});
            $scope.selectedWagerType = $scope.wagerTypeList[0];
        });

        resultService.getLotteryMarkSixStatList().then(function (data) {
            $scope.lotteryMarkSixGroupStatList = data;
        });

        resultService.getSubGroupList().then(function (data) {
            $scope.subGroupList = data;
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
        $scope.pageType = $routeParams.pageType;
        $scope.curDate = new Date();
        $scope.userId = $routeParams.userId;
        //前后多了两个双引号。。。
        $scope.start = $routeParams.start.substr(1, $routeParams.start.length - 2);
        $scope.end = $routeParams.end.substr(1, $routeParams.end.length - 2);

        function formatDate(date) {
            date = new Date(date);
            var dateStr = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate();
            return dateStr;
        }

        if ($scope.pageType == 0) {
            resultService.getStatSummaryByDateRange(sessionStorage['pgroupid'], formatDate($scope.start), formatDate($scope.end)).then(function (data) {
                $scope.summaryList = data;
            });

        } else if ($scope.pageType == 1) {
            // 代理商报表
            resultService.getStatSummaryByDateRange($routeParams.userId, formatDate($scope.start), formatDate($scope.end)).then(function (data) {
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