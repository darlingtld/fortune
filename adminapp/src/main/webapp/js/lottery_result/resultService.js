/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
service('resultService', function ($q, $http) {
    this.getLotteryMarkSixStatList = function () {
        var deferred = $q.defer();
        $http.get('stat/lottery_mark_six/groupid/' +sessionStorage['pgroupid'] + '/from/' + 0 + '/count/' + 20).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    };
})