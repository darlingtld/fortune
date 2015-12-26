/**
 * Created by Jason on 2015/12/26.
 */
angular.module('AdminApp')
    .service('oddsService', function ($q, $http) {
        // 获取某个代理商对于某一期的博彩的某一种类型的赔率
        this.getOddsList = function (lotteryIssue, pgroupId, selectedPan, type) {
            var deferred = $q.defer();
            $http.get("odds/lottery_issue/" + lotteryIssue + "/group/" + pgroupId + "/lottery_mark_six_type/" + type + "/pan/" + selectedPan)
                .success(function (data) {
                    deferred.resolve(data);
                });
            return deferred.promise;
        };
    });
