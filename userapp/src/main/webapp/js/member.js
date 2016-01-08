/**
 * Created by lingda on 2015/11/15.
 */
var memberApp = angular.module("memberApp", ["ui.bootstrap"]);
memberApp.controller("MemberController", function ($scope, $http, $q) {
    if (!sessionStorage["userid"]) {
        location.href = "login.html";
    }
    $scope.user = {
        username: sessionStorage["username"]
    }
    $scope.menu = 7;
    $http.get('common/platform_name').success(function (data) {
        $scope.platformName = data.name;
        $scope.corpName = data.corp;
    });
    $http.get('lottery/lottery_issue/last').success(function (data) {
        $scope.lastLotteryMarkSix = data;
    });
    $scope.colorMap = colorMap;
    $http.get('user/' + sessionStorage["userid"]).success(function (data) {
        $scope.user = data;
        $scope.selectedPGroup = $scope.user.pGroupList[0];
        $scope.changePGroup();
    });
    $scope.changePGroup = function () {
        $scope.tuishuiList = [];
        $scope.getTuishuiList($scope.user, $scope.selectedPGroup, 'A').then(function (data) {
            $scope.tuishuiListA = data;
            return $scope.getTuishuiList($scope.user, $scope.selectedPGroup, 'B');
        }).then(function (data) {
            $scope.tuishuiListB = data;
            return $scope.getTuishuiList($scope.user, $scope.selectedPGroup, 'C');
        }).then(function (data) {
            $scope.tuishuiListC = data;
            return $scope.getTuishuiList($scope.user, $scope.selectedPGroup, 'D')
        }).then(function (data) {
            $scope.tuishuiListD = data;
            for (var i = 0; i < $scope.tuishuiListA.length; i++) {
                $scope.tuishuiList.push({
                    lotteryMarkSixType: $scope.tuishuiListA[i].lotteryMarkSixType,
                    tuishuiA: $scope.tuishuiListA[i].tuishui,
                    tuishuiB: $scope.tuishuiListB[i].tuishui,
                    tuishuiC: $scope.tuishuiListC[i].tuishui,
                    tuishuiD: $scope.tuishuiListD[i].tuishui
                });
            }
        });
    };
    $scope.getTuishuiList = function (user, pgroup, panlei) {
        var deferred = $q.defer();
        $http.get('tuishui/user/' + user.id + '/group/' + pgroup.id + '/pan/' + panlei)
            .success(function (response) {
                deferred.resolve(response);
            });
        return deferred.promise;
    };

    $scope.changePass = function () {
        if ($scope.newPass.trim() == "" && $scope.newPass != $scope.newPass2) {
            alert("密码不一致");
            return;
        } else {
            $http.post('user/change_pass', {
                userid: $scope.user.id,
                originalPass: $scope.originalPass,
                newPass: $scope.newPass,
                newPass2: $scope.newPass2
            }).success(function () {
                alert("修改密码成功");
                $('a.close').click();
            }).error(function () {
                alert("原始密码错误");
            })
        }
    }

});

memberApp.filter('issueProcessor', function () {
    return function (issue) {
        if (issue != null) {
            issue = issue.toString().substr(4);
            return parseInt(issue);
        }
    }
});