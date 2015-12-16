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
        this.getStakesDetail4Special = function (type, groupid, issue, number, panlei) {
            var deferred = $q.defer();
            $http.get('stat/realtime/stake_detail/' + type + '/groupid/' + groupid + '/issue/' + issue + '/ball/' + number + '/pan/' + panlei).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        }
        this.getRealTimeTransactionTotalCount = function (groupid, issue) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/total_count/groupid/' + groupid + '/issue/' + issue).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        }
    })
