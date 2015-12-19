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

        this.getRealTimeTransaction = function (groupid, type, panlei) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/' + type + '/groupid/' + groupid + '/pan/' + panlei).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        this.getStakesDetail4Special = function (type, groupid, panlei, issue, number) {
            var deferred = $q.defer();
            $http.get('stat/realtime/stake_detail/' + type + '/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue + '/ball/' + number).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        this.getStakesDetailBySubType = function (type, groupid, panlei, issue, subtype) {
            var deferred = $q.defer();
            $http.get('stat/realtime/stake_detail/' + type + '/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue + '/subtype/' + subtype).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        this.getRealTimeTransactionTotalCount = function (groupid, panlei, issue) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/total_count/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
    })
