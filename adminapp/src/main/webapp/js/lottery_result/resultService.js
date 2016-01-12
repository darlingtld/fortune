/**
 * Created by tangl9 on 2015-11-03.
 */
angular.module('AdminApp').
service('resultService', function ($q, $http) {
    
    this.getTopTypeList = function () {
        var deferred = $q.defer();
        $http.get('lottery/top_type').success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    };
    
    this.getLotteryMarkSixStatList = function () {
        var deferred = $q.defer();
        $http.get('report/lottery_mark_six/groupid/' +sessionStorage['pgroupid'] + '/from/' + 0 + '/count/' + 20).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    };
    
    this.getSubGroupSummary = function (groupId, start, end) {
        var deferred = $q.defer();
        $http.get('report/group/summary/groupid/' + groupId + '/date_start/' + start + '/date_end/' + end).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }
    
    this.getSummary4AllType = function (groupId, start, end) {
        var deferred = $q.defer();
        $http.get('report/group/summary/alltype/groupid/' + groupId + '/date_start/' + start + '/date_end/' + end).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }
    
    this.getUserSummary = function (groupId, start, end) {
        var deferred = $q.defer();
        $http.get('report/user/summary/groupid/' + groupId + '/date_start/' + start + '/date_end/' + end).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }
    
    this.getUserList = function() {
        var deferred = $q.defer();
        $http.get("pgroup/admin_pgroup/" + sessionStorage["username"]).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }
    
    this.getSubGroupList = function() {
        var deferred = $q.defer();
        $http.get("pgroup/pgroups/" + sessionStorage["pgroupid"]).success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise;
    }
})