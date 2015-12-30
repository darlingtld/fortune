var historyApp = angular.module("history", ['ui.bootstrap']);
historyApp.controller("HistoryController", function ($scope, $http) {
    if (!sessionStorage["userid"]) {
        location.href = "login.html";
    }
    $scope.menu = 3;
    $http.get('common/platform_name').success(function (data) {
        $scope.platformName = data.name;
    });
    $scope.user = {
        username: sessionStorage["username"]
    }
    $scope.colorMap = colorMap;

    $scope.totalItems = 0;
    $scope.currentPage = 1;

    $scope.setPage = function (pageNo) {
        $scope.currentPage = pageNo;
    };

    $scope.pageChanged = function () {
        getLotteryMarkSixList();
    };

    $scope.itemsPerPage = 20;
    getLotteryMarkSixList();

    function getLotteryMarkSixList() {
        $http.get('lottery/pagination/from/' + $scope.itemsPerPage * ($scope.currentPage - 1) + '/count/' + $scope.itemsPerPage).success(function (data) {
            $scope.lotteryMarkSixList = data.lotteryMarkSixList;
            $scope.totalItems = data.totalItems;
        })
    }

}).filter('chineseWeek', function () {
    return function (input) {
        return "日一二三四五六".charAt(new Date(input).getDay())
    };
}).filter('specialDanShuang', function () {
    return function (input) {
        if (input == 49) {
            return '和'
        } else {
            return input % 2 == 1 ? '单' : '双';
        }

    };
}).filter('specialDaXiao', function () {
    return function (input) {
        if (input == 49) {
            return '和'
        } else {
            return input >= 25 ? '大' : '小';
        }
    };
}).filter('specialDaZhongXiao', function () {
    //开出之特码落在1~16为小；17~32为中；33~48为大；49则为和
    return function (input) {
        if (input == 49) {
            return '和'
        } else if (input >= 1 && input <= 16) {
            return '小';
        } else if (input >= 17 && input <= 32) {
            return '中';
        } else if (input >= 33 && input <= 48) {
            return '大';
        }

    };
}).filter('danShuang', function () {
    return function (input) {
        return input % 2 == 1 ? '单' : '双';

    };
}).filter('daXiao', function () {
    return function (input) {
        return input >= 175 ? '大' : '小';

    };
}).filter('zodiac', function () {
    return function (input) {
        return Zodiac.getNameByBall(input);

    };
}).filter('heDanShuang', function () {
    return function (input) {
        if (input == 49) {
            return '和';
        } else {
            var sum = parseInt(input % 10) + parseInt(input / 10);
            return sum % 2 == 1 ? '单' : '双';
        }

    };
}).filter('heDaXiao', function () {
    return function (input) {
        if (input == 49) {
            return '和';
        } else {
            var sum = parseInt(input % 10) + parseInt(input / 10);
            return sum >= 7 ? '大' : '小';
        }

    };
}).filter('wave', function () {
    return function (input) {
        if (input == 'red') {
            return '红';
        } else if (input == 'green') {
            return '绿';
        } else if (input == 'blue') {
            return '蓝';
        }
    };
});