var app = angular.module("index", []);

app.service("commonService", function($q, $http) {
	// 获取当前用户
	this.getUser = function() {
		var deferred = $q.defer();
		$http.get("user/" + sessionStorage["userid"]).success(
				function(response) {
					deferred.resolve(response);
				});
		return deferred.promise;
	};
	// 获得下一期
	this.getNextLottery = function() {
		var deferred = $q.defer();
		$http.get("lottery/next_lottery_mark_six_info").success(
				function(response) {
					deferred.resolve(response);
				});
		return deferred.promise;
	};
	// 获取某代理商对于某期的赔率
	this.getOddsList = function(lotteryIssue, pgroupId) {
		var deferred = $q.defer();
		$http.get("odds/lottery_issue/" + lotteryIssue + "/group/" + pgroupId)
				.success(function(response) {
					deferred.resolve(response);
				});
		return deferred.promise;
	};
	// 下注
	this.wage = function(wager) {
		$http.post("gamble/wage", wager).success(function() {
			alert('下注成功');
		}).error(function(data, status, headers) {
			alert('下注失败');
		});
	};
});

// 生肖相关
app.service("zodiacService", function($q, $http) {
	this.getZodiacItems = function(oddsMap) {
		var deferred = $q.defer();
		var zodiacMap = Zodiac.zodiacMap, items = [];
		for (var i = 0; i < zodiacMap.length; i++) {
			var item = {};
			item.id=zodiacMap[i].id;
			item.name = zodiacMap[i].name;
			item.balls = Zodiac.getBallsByName(zodiacMap[i].name);
			item.odds = oddsMap[zodiacMap[i].id];
			items.push(item);
		}
		return items;
	};
	this.getColorItems = function(oddsMap) {
		var deferred = $q.defer();
		var items = {};
		items.red = oddsMap["RED"]; 
		items.blue = oddsMap["BLUE"];
		items.green = oddsMap["GREEN"];
		return items;
	};
});

// 特码相关
app.service("tailBallService", function($q, $http) {
	this.getTailItems = function(oddsMap) {
		var row = 10, col = 5, tailItems = [];
		for (var i = 0; i < row; i++) {
			var itemRow = [];
			for (var j = 0; j < col; j++) {
				var ball = i * col + j + 1;
				if (ball > 49) {
					break;
				}
				var item = {};
				item.ball = ball;
				item.odds = oddsMap[ball];
				itemRow.push(item);
			}
			tailItems.push(itemRow);
		}
		return tailItems;
	};
});

// 合肖相关
app.service("sumZodiacService", function($q, $http) {
	this.getSumZodiacItems = function() {
		// TODO 赔率
		var sumZodiacItems = [];
		for (var i = 1; i <= 11; i++) {
			var item = {};
			item.name = i + "肖";
			item.odds = 11;
			sumZodiacItems.push(item);
		}
		return sumZodiacItems;
	};
});

// 连码相关
app.service("jointBallService", function($q, $http) {
	this.getJointItems = function() {
		var row = 10, col = 5, jointItems = [];
		for (var i = 0; i < row; i++) {
			var itemRow = [];
			for (var j = 0; j < col; j++) {
				var ball = i * col + j + 1;
				if (ball > 49) {
					break;
				}
				var item = {};
				item.ball = ball;
				item.odds = 11; // TODO 赔率
				itemRow.push(item);
			}
			jointItems.push(itemRow);
		}
		return jointItems;
	};
});

// 自选不中相关
app.service("notBallService", function($q, $http) {
	this.getNotItems = function() {
		var row = 10, col = 5, notItems = [];
		for (var i = 0; i < row; i++) {
			var itemRow = [];
			for (var j = 0; j < col; j++) {
				var ball = i * col + j + 1;
				if (ball > 49) {
					break;
				}
				var item = {};
				item.ball = ball;
				item.odds = 11; // TODO 赔率
				itemRow.push(item);
			}
			notItems.push(itemRow);
		}
		return notItems;
	};
});

app.controller("IndexController", function($scope, commonService,
		zodiacService, tailBallService, sumZodiacService, jointBallService,
		notBallService) {
	$scope.items = [ "特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "连码", "自选不中",
			"过关", "一肖尾数", "连肖", "连尾", "正码特" ];
	$scope.selectedIndex = 0;
	$scope.menu = 1;
	$scope.goTab = function(index) {
		$scope.selectedIndex = index;
		$scope.selectedBalls = {};
		$scope.selectedBalls2 = {};
	};
	$scope.colorMap = colorMap;
	
	// 半波
	// TODO
	// 合肖
	$scope.sumZodiacItems = sumZodiacService.getSumZodiacItems();
	// 连码
	$scope.jointItems = jointBallService.getJointItems();
	// 自选不中
	$scope.notItems = notBallService.getNotItems();

	// 根据期数和代理商重新获取彩票
	var generateLotteryList = function() {
		// 获取赔率
		commonService.getOddsList($scope.nextLottery.issue,
				$scope.selectedPGroup.id).then(function(oddsList) {
			var oddsMap={};
			for(var i=0;i<oddsList.length;i++){
				var odds=oddsList[i];
				if(odds.lotteryMarkSixType=="SPECIAL"){
					oddsMap[odds.lotteryBallNumber+""]=odds.odds;
				}
				else if(odds.lotteryMarkSixType=="BLUE" || odds.lotteryMarkSixType=="RED" || odds.lotteryMarkSixType=="GREEN"){
					oddsMap[odds.lotteryMarkSixType]=odds.odds;
				}
				else if(odds.lotteryMarkSixType.indexOf("ZODIAC_")==0){
					oddsMap[odds.lotteryMarkSixType]=odds.odds;
				}
			}
			// 获取特码数据
			$scope.tailItems = tailBallService.getTailItems(oddsMap);
			// 获取生肖数据
			$scope.zodiacItems = zodiacService.getZodiacItems(oddsMap);
			// 色波
			$scope.colorItems = zodiacService.getColorItems(oddsMap);
		});
	};
	
	// 更改代理商后，需要重新获取彩票数据
	$scope.changePGroup = generateLotteryList;

	// 初始化数据
	commonService.getUser().then(function(data) {
		// 用户与组
		$scope.user = data;
		$scope.selectedPGroup = $scope.user.pGroupList[0];
		// 获取下一期
		return commonService.getNextLottery();
	}).then(function(data) {
		$scope.nextLottery = data;
		generateLotteryList();
	});
	
	// 选择球
	$scope.selectedBalls = {};
	// 第二个类别的选择球
	$scope.selectedBalls2 = {};

	// 下注
	$scope.wage = function() {
		// 特码下注
		if ($scope.selectedIndex == 0) {
			var lotteryMarkSixWagerStubList = [];
			for(var ball in $scope.selectedBalls){
				lotteryMarkSixWagerStubList.push({
					number: parseInt(ball),
					stakes: parseInt($scope.selectedBalls[ball])
				});
			}
			var wager={
				userId : $scope.user.id,
				pgroupId : $scope.selectedPGroup.id,
				lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
				lotteryMarkSixType: "SPECIAL"
			};
			commonService.wage(wager);
		}
		// 生肖色波下注
		else if($scope.selectedIndex == 1){
			for(var zodiac in $scope.selectedBalls){
				var wager={
					userId : $scope.user.id,
					pgroupId : $scope.selectedPGroup.id,
					lotteryMarkSixWagerStubList: [],
					lotteryMarkSixType: zodiac,
					totalStakes: parseInt($scope.selectedBalls[zodiac])
				};
				commonService.wage(wager);
			}
			for(var color in $scope.selectedBalls2){
				var wager={
					userId : $scope.user.id,
					pgroupId : $scope.selectedPGroup.id,
					lotteryMarkSixWagerStubList: [],
					lotteryMarkSixType: color,
					totalStakes: parseInt($scope.selectedBalls2[color])
				};
				commonService.wage(wager);
			}
		}
	}
});