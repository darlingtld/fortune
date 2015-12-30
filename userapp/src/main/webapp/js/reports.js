var reportsApp = angular.module("reports", ['ui.bootstrap']);
reportsApp.controller("ReportsController", function ($scope, $http) {
    if (!sessionStorage["userid"]) {
        location.href = "login.html";
    }
    $scope.user = {
        username: sessionStorage["username"]
    }
    $scope.menu = 2;
    $http.get('common/platform_name').success(function (data) {
        $scope.platformName = data.name;
    });
    $scope.query = function () {
        var toDate = $scope.toDate == undefined ? $scope.maxDate : $scope.toDate;
        $http.get('report/userid/' + sessionStorage['userid'] + '/from/' + $scope.fromDate + '/to/' + toDate).success(function (data) {
            $scope.userStatList = data;
            $scope.stakesTotal = 0;
            $scope.validStakesTotal = 0;
            $scope.tuishuiTotal = 0;
            $scope.resultTotal = 0;
            $scope.userStatList.forEach(function (ele) {
                $scope.stakesTotal += ele.stakes;
                $scope.validStakesTotal += ele.validStakes;
                $scope.tuishuiTotal += ele.tuishui;
                $scope.resultTotal += ele.result;
            })
            console.log(data);
        })

    }

    $scope.today = function () {
        $scope.dt = new Date();
    };
    $scope.today();

    $scope.clear = function () {
        $scope.dt = null;
    };

    // Disable weekend selection
    $scope.disabled = function (date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    };

    $scope.toggleMin = function () {
        $scope.minDate = $scope.minDate ? null : new Date();
    };
    $scope.toggleMin();
    $scope.maxDate = new Date(2099, 1, 1);
    $scope.fromDate = new Date();

    $scope.open = function ($event) {
        $scope.status.opened = true;
    };

    $scope.setDate = function (year, month, day) {
        $scope.dt = new Date(year, month, day);
    };

    $scope.dateOptions = {
        formatYear: 'yy',
        startingDay: 1
    };

    $scope.formats = ['yyyy年MM月dd日', 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
    $scope.format = $scope.formats[0];

    $scope.status = {
        opened: false
    };

    var tomorrow = new Date();
    tomorrow.setDate(tomorrow.getDate() + 1);
    var afterTomorrow = new Date();
    afterTomorrow.setDate(tomorrow.getDate() + 2);
    $scope.events =
        [
            {
                date: tomorrow,
                status: 'full'
            },
            {
                date: afterTomorrow,
                status: 'partially'
            }
        ];

    $scope.getDayClass = function (date, mode) {
        if (mode === 'day') {
            var dayToCheck = new Date(date).setHours(0, 0, 0, 0);

            for (var i = 0; i < $scope.events.length; i++) {
                var currentDay = new Date($scope.events[i].date).setHours(0, 0, 0, 0);

                if (dayToCheck === currentDay) {
                    return $scope.events[i].status;
                }
            }
        }

        return '';
    };
});