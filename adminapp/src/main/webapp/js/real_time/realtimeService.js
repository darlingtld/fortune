/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .service('realtimeService', function ($q, $http) {
        this.getCurrentGroup = function(groupId) {
            var deferred = $q.defer();
            $http.get('pgroup/' + groupId).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getNextLotteryMarkSixInfo = function () {
            var deferred = $q.defer();
            $http.get('lottery/next_lottery_mark_six_info').success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getLatestLotteryMarkSixIssue = function () {
            var deferred = $q.defer();
            $http.get('lottery/latest_lottery_issue').success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getAllLotteyMarkSix = function() {
            var deferred = $q.defer();
            $http.get('lottery/all').success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        }
        
        this.getLotteryMarkSixInfo = function (issue) {
            var deferred = $q.defer();
            $http.get('lottery/lottery_issue/' + issue).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };

        this.getRealTimeTransaction = function (groupid, type, panlei, issue) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/' + type + '/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue).success(function (data) {
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
        
        this.getRealTimeTransactionNotTop = function (groupid, panlei, issue, top) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue + '/not_top/' + top).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getRealTimeTransactionAllStats = function (groupid, panlei, issue) {
            var deferred = $q.defer();
            $http.get('stat/realtime/transaction_result/all/groupid/' + groupid + '/pan/' + panlei + '/issue/' + issue).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getStakesDetail = function (type, groupid, panlei, issue, subtype, number, content, isAll) {
            var url = '';
            
            if (isAll) {
                url = 'stake_detail/groupid/' + groupid + '/all/type/' + type + '/pan/' + panlei + '/issue/' + issue;
            } else {
                var url = 'stake_detail/groupid/' + groupid + '/type/' + type + '/pan/' + panlei + '/issue/' + issue + '/ball/' + number;
                var param = '?', hasParam = false;
                if (subtype) {
                    param += 'subtype=' + subtype + '&';
                    hasParam = true;
                }
                if (content) {
                    param += 'content=' + content + '&';
                    hasParam = true;
                }
                if (hasParam) {
                    url += param.substring(0, param.length - 1);
                }
            }
            
            var deferred = $q.defer();
            $http.get(url).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
        this.getStakesDetailByUser = function (userid, groupid, issue) {
            var deferred = $q.defer();
            $http.get('stake_detail/' + userid + '/groupid/' + groupid + '/issue/' + issue).success(function (data) {
                deferred.resolve(data);
            });
            return deferred.promise;
        };
        
    })
