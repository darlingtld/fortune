var registerApp = angular.module("register", []);
registerApp.controller("RegisterController", function ($scope, $http) {
    $scope.isError = false;
    $scope.register = function () {
        if (!$scope.username) {
            $scope.isError = true;
            $scope.errorMessage = "账号不能为空！";
        }
        else if (!$scope.password || !$scope.password2) {
            $scope.isError = true;
            $scope.errorMessage = "密码不能为空！";
        }
        else if ($scope.password != $scope.password2) {
            $scope.isError = true;
            $scope.errorMessage = "两次密码输入不一致！";
        } else {
            $http.post("user/register", {
                name: $scope.username,
                password: $scope.password
            }).success(function () {
                $scope.isError = false;
                document.location.href = "login.html";
            }).error(function (data, status, headers) {
                $scope.isError = true;
                $scope.errorMessage = headers('message');
            })
        }

    };
});