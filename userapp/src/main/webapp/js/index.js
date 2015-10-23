var indexApp = angular.module("index", []);

indexApp.service("zodiacService", function($q, $http) {
	this.getZodiacItems = function() {
		var deferred = $q.defer();
		// TODO
		var names = Zodiac.names, items = [];
		for (var i = 0; i < names.length; i++) {
			var item = {};
			item.name = names[i];
			item.balls = Zodiac.getBallsByName(names[i]);
			item.odds = 11; // TODO 赔率
			items.push(item);
		}
		return items;
	};
	this.getColorItems = function() {
		var deferred = $q.defer();
		var items = {};
		items.red = 11; // TODO 赔率
		items.blue = 12;
		items.green = 13;
		return items;
	};
});

indexApp.service("tailBallService", function($q, $http) {
	this.getTailItems = function() {
		var row = 10, col = 5, tailItems = [];
		for (var i = 0; i < row; i++) {
			var itemRow = [];
			for (var j = 0; j < col; j++) {
				var ball = i * col + j + 1;
				if (ball > 49) {
					break;
				}
				var item={};
				item.ball=ball;
				item.odds=11; // TODO 赔率
				itemRow.push(item);
			}
			tailItems.push(itemRow);
		}
		return tailItems;
	};
});

indexApp.controller("IndexController", function($scope, zodiacService,
		tailBallService) {
	$scope.items = [ "特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "连码", "自选不中",
			"过关", "一肖尾数", "连肖", "连尾", "正码特" ];
	$scope.selectedIndex = 0;
	$scope.menu = 1;
	$scope.goTab = function(index) {
		$scope.selectedIndex = index;
	}
	$scope.colorMap = colorMap;
	// 生肖
	$scope.zodiacItems = zodiacService.getZodiacItems();
	// 色波
	$scope.colorItems = zodiacService.getColorItems();
	// 特码
	$scope.tailItems = tailBallService.getTailItems();
});
