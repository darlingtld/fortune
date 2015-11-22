/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .service('realtimeService', function ($q, $http) {
        this.getNextLotteryMarkSixInfo = function () {
            var deferred = $q.defer();
            $http.get('lottery/next_lottery_mark_six_info').success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };

        this.getRealTimeTransaction = function (groupid) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/groupid/' + groupid).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        this.getStakesDetail4Special = function (groupid, issue, number) {
            var deferred = $q.defer();
            $http.get('stat/realtime/stake_detail/special/groupid/' + groupid + '/issue/' + issue + '/ball/' + number).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        }
    })
