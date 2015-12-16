/**
 * Created by lingda on 2015/11/15.
 */
var wageHistoryApp = angular.module("wagehistory", []);
wageHistoryApp.controller("WageHistoryController", function ($scope, $http, $sce) {
    $scope.menu = 6;
    $http.get('user/' + sessionStorage["userid"]).success(function (data) {
        $scope.user = data;
        $scope.selectedPGroup = $scope.user.pGroupList[0];
        $http.get('lottery/next_lottery_mark_six_info').success(function (data) {
            $scope.nextLottery = data;
            $scope.lotteryIssues = [];
            for (var i = 0; i < $scope.nextLottery.issue; i++) {
                $scope.lotteryIssues.push($scope.nextLottery.issue - i);
            }
            $scope.selectedIssue = $scope.lotteryIssues[0];
            $scope.getWageRecord($scope.user, $scope.selectedPGroup, $scope.selectedIssue)();
        });

        $scope.changePGroupAndIssue = function () {
            $scope.getWageRecord($scope.user, $scope.selectedPGroup, $scope.selectedIssue)();
        };
    });
    
    var prefixHistoryMap={
    	'SPECIAL':{
    		typeName: '特码',
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<wageSubList.length;i++){
    				var item=wageSubList[i];
    				html+='<div class="ball ball'+item.number+'">'+item.number+'</div>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'ZODIAC':{
    		typeName: '生肖',
    		getWageHTML: function(wage){
    			var zodiac=wage.lotteryMarkSixType, zodiacName=zodiacTypeMap[zodiac], html='<div style="float:left;margin-right:5px;line-height:25px;">'+zodiacName+':</div>', balls=Zodiac.getBallsByName(zodiacName);
    			for(var i=0;i<balls.length;i++){
    				var ball=balls[i];
    				html+='<div class="ball ball'+ball+'">'+ball+'</div>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'RED':{
    		typeName: '色波',
    		getWageHTML: function(wage){
    			var html='<p style="color:red;">红波</div>';
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'GREEN':{
    		typeName: '色波',
    		getWageHTML: function(wage){
    			var html='<p style="color:green;">绿波</div>';
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'BLUE':{
    		typeName: '色波',
    		getWageHTML: function(wage){
    			var html='<p style="color:blue;">蓝波</div>';
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'WAVE':{
    		typeName: '半波',
    		getWageHTML: function(wage){
    			var arr=wage.lotteryMarkSixType.split("_"), color=zhengTypeMap[arr[1]], type=zhengTypeMap[arr[2]];
    			var html='<p style="color:'+arr[1].toLowerCase()+';">'+color+' '+type+'</p>';
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'SUM':{
    		typeName: '合肖',
    		getWageHTML: function(wage){
    			var html='<p>选择的生肖：', zodiacs=wage.subLotteryMarkSixTypes, wageList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<zodiacs.length;i++){
    				html+=zodiacTypeMap[zodiacs[i]];
    			}
    			html+='<br/>';
    			for(var i=0;i<wageList.length;i++){
    				var number=wageList[i].number, count=parseInt(number/10), isZhong=number%2==1;
    				html+=count+'肖'+(isZhong?'中':'不中')+" ";
    			}
    			html+='</p>';
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			var wageList=wage.lotteryMarkSixWagerStubList, stakes=wage.totalStakes+'(';
    			for(var i=0;i<wageList.length;i++){
    				var stake=wageList[i].stakes;
    				stakes+=stake+',';
    			}
    			stakes=stakes.substring(0,stakes.length-1)+')';
    			return stakes;
    		}
    	},
    	'ZHENG':{
    		typeName: function(wage){
    			if(wage.lotteryMarkSixType=='ZHENG_BALL'){
    				return '正码';
    			}
    			else if(wage.lotteryMarkSixType=='ZHENG_1_6'){
    				return '正码1~6';
    			}
    			else if(wage.lotteryMarkSixType.split("_")[1]=="SPECIFIC"){
    				return '正码特'+wage.lotteryMarkSixType.split("_")[2];
    			}
    		},
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			if(wage.lotteryMarkSixType=='ZHENG_BALL' || wage.lotteryMarkSixType.split("_")[1]=="SPECIFIC"){
    				for(var i=0;i<wageSubList.length;i++){
        				var item=wageSubList[i];
        				html+='<div class="ball ball'+item.number+'">'+item.number+'</div><div style="float:left;line-height:25px;margin-right:5px;">('+item.stakes+')</div>';
        			}
    			}
    			else if(wage.lotteryMarkSixType=='ZHENG_1_6'){
    				for(var i=0;i<wageSubList.length;i++){
        				var item=wageSubList[i];
        				html+='<span style="margin-right:10px;">正码'+item.number+': '+zhengTypeMap[item.lotteryMarkSixType]+'('+item.stakes+')</span>';
        			}
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'JOINT': {
    		typeName: function(wage){
    			return '连码'+jointTypeMap[wage.lotteryMarkSixType];
    		},
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<wageSubList.length;i++){
    				var item=wageSubList[i];
    				html+='<div class="ball ball'+item.number+'">'+item.number+'</div>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'NOT': {
    		typeName: function(wage){
    			return '自选'+wage.lotteryMarkSixType.split('_')[1]+'不中';
    		},
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<wageSubList.length;i++){
    				var item=wageSubList[i];
    				html+='<div class="ball ball'+item.number+'">'+item.number+'</div>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'PASS': {
    		typeName: '过关',
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<wageSubList.length;i++){
    				var item=wageSubList[i];
    				html+='<span style="margin-right:10px;">正码'+item.number+': '+passTypeMap[item.lotteryMarkSixType]+'</span>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'ONE': {
    		typeName: '一肖',
    		getWageHTML: function(wage){
    			var wageSubList=wage.lotteryMarkSixWagerStubList, html='';
    			for(var i=0;i<wageSubList.length;i++){
    				var zodiac=wageSubList[i].lotteryMarkSixType, zodiacName=zodiacTypeMap[zodiac];
    				html+=zodiacName+'('+wageSubList[i].stakes+')  ';
    			}
    			
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	},
    	'TAIL': {
    		typeName: '尾数',
    		getWageHTML: function(wage){
    			var html='', wageSubList=wage.lotteryMarkSixWagerStubList;
    			for(var i=0;i<wageSubList.length;i++){
    				var item=wageSubList[i];
    				html+='<span style="margin-right:10px;">'+item.number+'尾('+item.stakes+')</span>';
    			}
    			return $sce.trustAsHtml(html);
    		},
    		getStakes: function(wage){
    			return wage.totalStakes;
    		}
    	}
    };
    
    $scope.getWageRecord = function (user, pgroup, issue) {
        return function () {
            $http.get('gamble/wage_record/' + user.id + '/pgroup/' + pgroup.id + '/lottery_issue/' + issue).success(function (data) {
            	$scope.wageHistory=[];
                for(var i=0;i<data.length;i++){
                	var prefix=data[i].lotteryMarkSixType.split('_')[0], mapItem=prefixHistoryMap[prefix];
                	if(mapItem){
                		$scope.wageHistory.push({
                			typeName: (typeof mapItem.typeName=='string' ? mapItem.typeName: mapItem.typeName(data[i])),
                			wageHTML: mapItem.getWageHTML(data[i]),
                			stakes: mapItem.getStakes(data[i]),
                			panlei: data[i].panlei,
                			pgroup: pgroup.name,
                			timestamp: data[i].timestamp
                		});
                	}
                }
            });
        }
    };
});