var loginApp = angular.module("login", []);
loginApp.controller("LoginController", function ($scope, $http) {
    $scope.isError = false;
    $scope.login = function () {
        $http.post("user/login", {
            "name": $scope.username,
            "password": $scope.password,
            "kaptcha": $scope.kaptcha
        }).success(function (response) {
            if (response) {
                sessionStorage["userid"] = response.id;
                sessionStorage["username"] = response.username;
                document.location.href = "index.html";
            } else {
                $scope.isError = true;
            }
        }).error(function (data, status, headers) {
            //alert(headers("message"))
            if (status == 417) {
                alert("验证码错误")
            }
        });
    };

    $scope.refreshImage = function () {
        event.target.setAttribute('src', 'kaptcha/kaptcha.jpg?' + Math.floor(Math.random() * 100));
    }

    $scope.enterSubmit = function () {
        var e = event ? event : (window.event ? window.event : null);
        if (e.keyCode == 13) {
            $scope.login();
        }
    }
});