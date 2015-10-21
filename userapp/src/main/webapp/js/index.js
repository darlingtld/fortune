var indexApp = angular.module("index", []);

indexApp.service("zodiacService", function($q, $http){
	this.getZodiacItems=function(){
		var deferred = $q.defer();
	    //TODO
		var names=Zodiac.names, items=[];
		for(var i=0;i<names.length;i++){
			var item={};
			item.name=names[i];
			item.balls=Zodiac.getBallsByName(names[i]);
			item.odds=11; //TODO 赔率
			items.push(item);
		}
		return items;
	};
	this.getColorItems=function(){
		var deferred = $q.defer();
		var items={};
		items.red=11;
		items.blue=12;
		items.green=13;
		return items;
	};
});

indexApp.controller("IndexController", function($scope, zodiacService) {
	$scope.items = [ "特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "连码", "自选不中",
			"过关", "一肖尾数", "连肖", "连尾", "正码特" ];
	$scope.selectedIndex = 0;
	$scope.menu = 1;
	$scope.goTab = function(index) {
		$scope.selectedIndex = index;
	}
	$scope.colorMap=colorMap;
	// 生肖
	$scope.zodiacItems=zodiacService.getZodiacItems();
	// 色波
	$scope.colorItems=zodiacService.getColorItems();
});

