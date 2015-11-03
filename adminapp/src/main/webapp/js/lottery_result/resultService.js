/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
    service('resultService', function ($q, $http) {
        this.getLotteryResult = function () {
            var deferred = $q.defer();
            $http.get("lottery/" + sessionStorage["userid"]).success(function (response) {
                deferred.resolve(response.pGroupList);
            });
            return deferred.promise;
        };
    })