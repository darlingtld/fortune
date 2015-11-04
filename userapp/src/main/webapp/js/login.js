var loginApp = angular.module("login", []);
loginApp.controller("LoginController", function ($scope, $http) {
    $scope.isError = false;
    $scope.login = function () {
        $http.post("user/login", {
            "name": $scope.username,
            "password": $scope.password
        }).success(function (response) {
            if (response) {
                sessionStorage["userid"] = response.id;
                sessionStorage["username"] = $scope.username;
                document.location.href = "index.html";
            } else {
                $scope.isError = true;
            }
        });
    };
});