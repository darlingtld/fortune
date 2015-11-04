angular.module("index", []).
service("commonService", function ($q, $http) {
    this.getUser = function () {
        var deferred = $q.defer();
        $http.get("user/" + sessionStorage["userid"]).success(function (response) {
            deferred.resolve(response);
        });
        return deferred.promise;
    };
}).service("zodiacService", function ($q, $http) {
    this.getZodiacItems = function () {
        var deferred = $q.defer();
        // TODO
        var names = Zodiac.names, items = [];
        for (var i = 0; i < names.length; i++) {
            var item = {};
            item.name = names[i];
            item.balls = Zodiac.getBallsByName(names[i]);
            item.odds = 11; // TODO 赔率
            items.push(item);
        }
        return items;
    };
    this.getColorItems = function () {
        var deferred = $q.defer();
        var items = {};
        items.red = 11; // TODO 赔率
        items.blue = 12;
        items.green = 13;
        return items;
    };
}).service("tailBallService", function ($q, $http) {
    this.getTailItems = function () {
        var row = 10, col = 5, tailItems = [];
        for (var i = 0; i < row; i++) {
            var itemRow = [];
            for (var j = 0; j < col; j++) {
                var ball = i * col + j + 1;
                if (ball > 49) {
                    break;
                }
                var item = {};
                item.ball = ball;
                item.odds = 11; // TODO 赔率
                itemRow.push(item);
            }
            tailItems.push(itemRow);
        }
        return tailItems;
    };
    this.wage = function (ball) {
        $http.post("gamble/wage", {
            userId: sessionStorage["userid"],
            pgroupId: -1, //TODO
            lotteryMarkSixWagerStubList: [{"number": ball, "stakes": 1}],
            lotteryMarkSixType: null //TODO
        }).success(function () {

        }).error(function (data, status, headers) {

        });
    };
}).service("sumZodiacService", function ($q, $http) {
    this.getSumZodiacItems = function () {
        // TODO 赔率
        var sumZodiacItems = [];
        for (var i = 1; i <= 11; i++) {
            var item = {};
            item.name = i + "肖";
            item.odds = 11;
            sumZodiacItems.push(item);
        }
        return sumZodiacItems;
    };
}).service("jointBallService", function ($q, $http) {
    this.getJointItems = function () {
        var row = 10, col = 5, jointItems = [];
        for (var i = 0; i < row; i++) {
            var itemRow = [];
            for (var j = 0; j < col; j++) {
                var ball = i * col + j + 1;
                if (ball > 49) {
                    break;
                }
                var item = {};
                item.ball = ball;
                item.odds = 11; // TODO 赔率
                itemRow.push(item);
            }
            jointItems.push(itemRow);
        }
        return jointItems;
    };
}).service("notBallService", function ($q, $http) {
    this.getNotItems = function () {
        var row = 10, col = 5, notItems = [];
        for (var i = 0; i < row; i++) {
            var itemRow = [];
            for (var j = 0; j < col; j++) {
                var ball = i * col + j + 1;
                if (ball > 49) {
                    break;
                }
                var item = {};
                item.ball = ball;
                item.odds = 11; // TODO 赔率
                itemRow.push(item);
            }
            notItems.push(itemRow);
        }
        return notItems;
    };
}).service("lotteryService", function ($q, $http) {
    this.getNextLotteryMarkSixInfo = function () {
        var deferred = $q.defer();
        $http.get("lottery/next_lottery_mark_six_info").success(function (response) {
            deferred.resolve(response);
        });
        return deferred.promise;
    }
    this.getOddsList = function (lotteryIssue, pgroupId) {
        var deferred = $q.defer();
        $http.get("odds/lottery_issue/" + lotteryIssue + "/group/" + pgroupId).success(function (response) {
            deferred.resolve(response);
        });
        return deferred.promise;
    };
}).controller("IndexController", function ($scope, commonService, zodiacService,
                                           tailBallService, sumZodiacService, jointBallService, notBallService, lotteryService) {
    $scope.items = ["特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "连码", "自选不中",
        "过关", "一肖尾数", "连肖", "连尾", "正码特"];
    $scope.selectedIndex = 0;
    $scope.menu = 1;
    $scope.goTab = function (index) {
        $scope.selectedIndex = index;
    }
    $scope.username = sessionStorage['username'];
    $scope.colorMap = colorMap;
    // 生肖
    $scope.zodiacItems = zodiacService.getZodiacItems();
    // 色波
    $scope.colorItems = zodiacService.getColorItems();
    // 特码
    $scope.tailItems = tailBallService.getTailItems();
    // 半波
    // TODO
    // 合肖
    $scope.sumZodiacItems = sumZodiacService.getSumZodiacItems();
    // 连码
    $scope.jointItems = jointBallService.getJointItems();
    // 自选不中
    $scope.notItems = notBallService.getNotItems();

    // 当前选择的球
    $scope.selectedBalls = "";


    commonService.getUser().then(function (data) {
        $scope.user = data;
        $scope.selectedPGroup = $scope.user.pGroupList[0];
    });


    lotteryService.getNextLotteryMarkSixInfo().then(function (data) {
        $scope.nextLotteryMarkSixInfo = data;
        lotteryService.getOddsList($scope.nextLotteryMarkSixInfo.issue, $scope.selectedPGroup.id).then(function (data) {
            $scope.oddsList = data;
        })
    })

    // 下注
    $scope.wage = function (type, balls) {
        if (type == "tailball") {
            tailBallService.wage(balls);
        }
    }
});