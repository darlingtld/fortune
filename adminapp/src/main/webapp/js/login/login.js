var loginApp = angular.module("login", []);
loginApp.controller("LoginController", function ($scope, $http) {
    $scope.isError = false;
    $scope.login = function () {
        $http.post("administrator/login", {
            "username": $scope.username,
            "password": $scope.password
        }).success(function (response) {
            if (response) {
                sessionStorage["username"] = response.username;
                sessionStorage["userid"] = response.admin.id;
                sessionStorage["pgroupid"] = response.id;
                sessionStorage["pgroupname"] =  response.name;
                document.location.href = "index.html";
            } else {
                $scope.isError = true;
            }
        });
    };
});