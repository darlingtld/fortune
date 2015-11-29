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
	// 多个下注
	this.wages = function(wagers) {
		$http.post("gamble/wages", wagers).success(function() {
			alert('下注成功');
		}).error(function(data, status, headers) {
			alert('下注失败');
		});
	};
});

// 生肖相关
app.service("zodiacService", function($q, $http, $sce) {
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
	this.renderWageConfirmHTML = function(wagerList){
		var html="", zodiacs={};
		for (var i = 0; i < Zodiac.zodiacMap.length; i++) {
			zodiacs[Zodiac.zodiacMap[i].id]=Zodiac.zodiacMap[i].name;
		}
		for(var i=0;i<wagerList.length;i++){
			var wager=wagerList[i], type=wager.lotteryMarkSixType;
			// 生肖
			if(type.indexOf("ZODIAC_")==0){
				html+="<div style='height:40px;width:300px;margin:10px auto;'><span style='line-height:25px;float:left;'>";
				html+=zodiacs[type];
				html+="</span><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额："+wager.totalStakes+"</span></div>";
			}
			// 色波
			else if(type=="RED" || type=="BLUE" || type=="GREEN"){
				html+="<div style='height:40px;width:300px;margin:10px auto;'><span style='line-height:25px;float:left;'>";
				var color="红波";
				if(type=="BLUE"){
					color="蓝波";
				}
				else if(type=="GREEN"){
					color="绿波";
				}
				html+=color;
				html+="</span><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额："+wager.totalStakes+"</span></div>";
			}
		}
		return $sce.trustAsHtml(html);
	}
});

// 特码相关
app.service("tailBallService", function($q, $http, $sce) {
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
	this.renderWageConfirmHTML = function(wager){
		var wagerList=wager.lotteryMarkSixWagerStubList, html="";
		for(var i=0;i<wagerList.length;i++){
			var item=wagerList[i], number=item.number, stakes=item.stakes;
			html+="<div style='height:40px;width:300px;margin:10px auto;'><div class='ball "+colorMap[number]+"'>"+number+"</div><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额："+stakes+"</span></div>";
		}
		return	$sce.trustAsHtml(html);
	};
});

//半波相关
app.service("halfWaveService", function($q, $http, $sce) {
	this.getHalfWaveItems = function(oddsMap) {
		var items={};
		for(var type in oddsMap){
			if(type.indexOf("WAVE_")==0){
				items[type]=oddsMap[type];
			}
		}
		return items;
	};
	this.renderWageConfirmHTML = function(wagerList){
		var html="";
		for(var i=0;i<wagerList.length;i++){
			var item=wagerList[i], type=item.lotteryMarkSixType, stakes=item.totalStakes, arr=type.split("_");
			var color="红";
			if(arr[1]=="BLUE"){
				color="蓝";
			}
			else if(arr[1]=="GREEN"){
				color="绿";
			}
			var type="单";
			if(arr[2]=="SHUANG"){
				type="双";
			}
			else if(arr[2]=="DA"){
				type="大";
			}
			else if(arr[2]=="XIAO"){
				type="小";
			}
			html+="<div style='height:40px;width:300px;margin:10px auto;'>"+color+type+"<span style='color:red;margin-left:10px;'>下注金额："+stakes+"</span></div>";
		}
		return $sce.trustAsHtml(html);
	}
});

// 合肖相关
app.service("sumZodiacService", function($q, $http, $sce) {
	this.getSumZodiacItems = function(oddsMap) {
		var sumZodiacItems = [];
		for (var i = 1; i <= 11; i++) {
			var item = {};
			item.name = i + "肖";
			item.odds = oddsMap["SUM_ZODIAC#"+i];
			sumZodiacItems.push(item);
		}
		return sumZodiacItems;
	};
	this.renderWageConfirmHTML = function(wager){
		var html="<div style='height:40px;width:300px;margin:10px auto;'>选择的生肖为：";
		var zodiacs=wager.subLotteryMarkSixTypes;
		for(var i=0;i<zodiacs.length;i++){
			var zodiac=zodiacs[i];
			for(var j=0;j<Zodiac.zodiacMap.length;j++){
				if(Zodiac.zodiacMap[j].id==zodiac){
					html+=Zodiac.zodiacMap[j].name;
					break;
				}
			}
		}
		html+="</div>";
		var wagers=wager.lotteryMarkSixWagerStubList;
		for(var i=0;i<wagers.length;i++){
			var item=wagers[i], number=item.number, stakes=item.stakes;
			var zodiacNum=parseInt(number/10), isHit=number%10==1;
			html+="<div style='height:40px;width:300px;margin:10px auto;'>"+zodiacNum+"肖"+(isHit?"中":"不中")+"<span style='color:red;margin-left:10px;'>下注金额："+stakes+"</span></div>";
		}
		return $sce.trustAsHtml(html);
	}
});

// 正码相关
app.service("zhengBallService", function($q, $http, $sce) {
	this.getZheng16Items = function(oddsMap) {
		var items={};
		for(var type in oddsMap){
			if(type.indexOf("ZHENG_1_6")==0){
				items[type]=oddsMap[type];
			}
		}
		return items;
	};
	this.renderWageConfirmHTML = function(wager){
		var html="", wagers=wager.lotteryMarkSixWagerStubList;
		for(var i=0;i<wagers.length;i++){
			var item=wagers[i], number=item.number, stakes=item.stakes, lotteryType=item.lotteryMarkSixType;
			var type="单";
			if(lotteryType=="SHUANG"){
				type="双";
			}
			else if(lotteryType=="DA"){
				type="大";
			}
			else if(lotteryType=="XIAO"){
				type="小";
			}
			else if(lotteryType=="HEDAN"){
				type="合单";
			}
			else if(lotteryType=="HESHUANG"){
				type="合双";
			}
			else if(lotteryType=="RED"){
				type="红波";
			}
			else if(lotteryType=="GREEN"){
				type="绿波";
			}
			else if(lotteryType=="BLUE"){
				type="蓝波";
			}
			html+="<div style='height:40px;width:300px;margin:10px auto;'>正码"+number+"："+type+"<span style='color:red;margin-left:10px;'>下注金额："+stakes+"</span></div>";
		}
		return $sce.trustAsHtml(html);
	}
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
				itemRow.push(item);
			}
			jointItems.push(itemRow);
		}
		return jointItems;
	};
	this.renderWageConfirmHTML = function(wager) {
		
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
				itemRow.push(item);
			}
			notItems.push(itemRow);
		}
		return notItems;
	};
});

app.controller("IndexController", function($scope, commonService,
		zodiacService, tailBallService, halfWaveService, sumZodiacService, zhengBallService, 
		jointBallService, notBallService) {
	$scope.items = [ "特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "连码", "自选不中", "过关", "一肖尾数", "连肖", "连尾", "正码特" ];
	$scope.selectedIndex = 0;
	$scope.menu = 1;
	$scope.goTab = function(index) {
		$scope.selectedIndex = index;
		$scope.reset();
	};
	
	// 重置
	$scope.reset = function(){
		$scope.selectedBalls = {};
		$scope.selectedBalls2 = {};
		$scope.sumZodiacList = [];
		$scope.quickChooses = {};
		$scope.isConfirmDialogVisible=false;
		$scope.closeConfirmDialog = function(){
			$scope.isConfirmDialogVisible=false;
		};
		$scope.confirmDialog = function(){
			$scope.isConfirmDialogVisible=false;
		};
	};
	// 秋色
	$scope.colorMap = colorMap;
	
	// 初始化数据
	$scope.reset();

	// 根据期数和代理商重新获取彩票
	var generateLotteryList = function() {
		// 获取赔率
		commonService.getOddsList($scope.nextLottery.issue,
				$scope.selectedPGroup.id).then(function(oddsList) {					
			var oddsMap={}, jointOddsMap={};
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
				else if(odds.lotteryMarkSixType.indexOf("WAVE_")==0){
					oddsMap[odds.lotteryMarkSixType]=odds.odds;
				}
				else if(odds.lotteryMarkSixType=="SUM_ZODIAC"){
					oddsMap[odds.lotteryMarkSixType+"#"+odds.lotteryBallNumber]=odds.odds;
				}
				else if(odds.lotteryMarkSixType=="ZHENG_1_6"){
					oddsMap[odds.lotteryMarkSixType+"#"+odds.lotteryBallType]=odds.odds;
				}
				else if(odds.lotteryMarkSixType.indexOf("JOINT_")==0){
					jointOddsMap[odds.lotteryMarkSixType]=odds.odds;
				}
			}
			// 获取特码数据
			$scope.tailItems = tailBallService.getTailItems(oddsMap);
			// 获取生肖数据
			$scope.zodiacItems = zodiacService.getZodiacItems(oddsMap);
			// 色波
			$scope.colorItems = zodiacService.getColorItems(oddsMap);
			// 半波
			$scope.halfWaveItems = halfWaveService.getHalfWaveItems(oddsMap);
			// 合肖
			$scope.sumZodiacItems = sumZodiacService.getSumZodiacItems(oddsMap);
			// 正码1-6
			$scope.zheng16Items = zhengBallService.getZheng16Items(oddsMap);
			// 连码
			$scope.jointItems = jointBallService.getJointItems();
			$scope.jointOddsMap = jointOddsMap;
			// 自选不中
			$scope.notItems = notBallService.getNotItems();
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
	
	// 合码的选择类型函数
	$scope.chooseSumZodiac = function($event, zodiac){
		var checkbox = $event.target;
		if(checkbox.checked){
			$scope.sumZodiacList.push(zodiac);
		}
		else{
			var copy=[];
			for(var i=0;i<$scope.sumZodiacList.length;i++){
				if($scope.sumZodiacList[i]!=zodiac){
					copy.push($scope.sumZodiacList[i]);
				}
			}
			$scope.sumZodiacList=copy;
		}
	};
	
	$scope.isCheckedSumZodiac = function(zodiac){
		for(var i=0;i<$scope.sumZodiacList.length;i++){
			if($scope.sumZodiacList[i]==zodiac){
				return true;
			}
		}
		return false;
	};
	
	// 正码1-6的选择类型函数
	$scope.chooseZheng16 = function($event, ballNum, type){
		var checkbox = $event.target;
		if(checkbox.checked){
			$scope.selectedBalls[ballNum]=type;
		}
		else{
			$scope.selectedBalls[ballNum]=undefined;
		}
	};
	
	// 其他选择类型函数
	$scope.chooseBall = function($event, ballNum){
		var checkbox = $event.target;
		if(checkbox.checked){
			$scope.selectedBalls[ballNum]=true;
		}
		else{
			$scope.selectedBalls[ballNum]=false;
		}
	};
		
	// 下注
	$scope.wage = function() {
		// 特码下注
		if ($scope.selectedIndex == 0) {
			// 组装下注对象
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
			var html=tailBallService.renderWageConfirmHTML(wager);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为特码，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wage(wager);
			}
		}
		// 生肖色波下注
		else if($scope.selectedIndex == 1){
			// 组装下注对象
			var wagerList=[];
			for(var zodiac in $scope.selectedBalls){
				var wager={
					userId : $scope.user.id,
					pgroupId : $scope.selectedPGroup.id,
					lotteryMarkSixWagerStubList: [],
					lotteryMarkSixType: zodiac,
					totalStakes: parseInt($scope.selectedBalls[zodiac])
				};
				wagerList.push(wager);
			}
			for(var color in $scope.selectedBalls2){
				var wager={
					userId : $scope.user.id,
					pgroupId : $scope.selectedPGroup.id,
					lotteryMarkSixWagerStubList: [],
					lotteryMarkSixType: color,
					totalStakes: parseInt($scope.selectedBalls2[color])
				};
				wagerList.push(wager);
			}
			var html=zodiacService.renderWageConfirmHTML(wagerList);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为生肖色波，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wages(wagerList);
			}
		}
		// 半波下注
		else if($scope.selectedIndex == 2){
			// 组装下注对象
			var wagerList=[];
			for(var type in $scope.selectedBalls){
				var wager={
					userId : $scope.user.id,
					pgroupId : $scope.selectedPGroup.id,
					lotteryMarkSixWagerStubList: [],
					lotteryMarkSixType: type,
					totalStakes: parseInt($scope.selectedBalls[type])
				};
				wagerList.push(wager);
			}
			var html=halfWaveService.renderWageConfirmHTML(wagerList);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为半波，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wages(wagerList);
			}
		}
		// 合肖下注
		else if($scope.selectedIndex == 3){
			// 组装下注对象
			var lotteryMarkSixWagerStubList = [];
			for(var ball in $scope.selectedBalls){
				lotteryMarkSixWagerStubList.push({
					number: parseInt(ball)*10+parseInt($scope.selectedBalls2[ball]), // 101表示10肖中，20表示2肖不中。个位表示中不中，前面几位表示几肖,
					stakes: parseInt($scope.selectedBalls[ball])
				});
			}
			var wager={
				userId : $scope.user.id,
				pgroupId : $scope.selectedPGroup.id,
				lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
				subLotteryMarkSixTypes: $scope.sumZodiacList,
				lotteryMarkSixType: "SUM_ZODIAC"
			};
			var html=sumZodiacService.renderWageConfirmHTML(wager);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为合肖，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wage(wager);
			}
		}
		// 正码1-6下注
		else if($scope.selectedIndex == 5){
			// 组装下注对象
			var lotteryMarkSixWagerStubList = [];
			for(var ballNum in $scope.selectedBalls){
				if(typeof $scope.selectedBalls[ballNum]=="undefined"){
					continue;
				}
				lotteryMarkSixWagerStubList.push({
					number: ballNum, // 表示第几个正码
					lotteryMarkSixType: $scope.selectedBalls[ballNum], // 表示该正码x的类型
					stakes: $scope.selectedBalls2[ballNum]
				});
			}
			var wager={
				userId : $scope.user.id,
				pgroupId : $scope.selectedPGroup.id,
				lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
				lotteryMarkSixType: "ZHENG_1_6"
			};
			var html=zhengBallService.renderWageConfirmHTML(wager);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为正码1~6，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wage(wager);
			}
		}
		// 连码下注
		// TODO
		else if($scope.selectedIndex == 6){
			// 组装下注对象
			var lotteryMarkSixWagerStubList = [];
			for(var ball in $scope.selectedBalls){
				if($scope.selectedBalls[ball]){
					lotteryMarkSixWagerStubList.push({
						number: parseInt(ball)
					});
				}
			}
			var wager={
				userId : $scope.user.id,
				pgroupId : $scope.selectedPGroup.id,
				lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
				lotteryMarkSixType: $scope.jointLotteryType,
				totalStakes: $scope.jointBallStakes
			};
			console.log(wager);
			var html=jointBallService.renderWageConfirmHTML(wager);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为连码，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wage(wager);
			}
		}
		// 自选不中下注
		// TODO
		else if($scope.selectedIndex == 7){
			// 组装下注对象
			var lotteryMarkSixWagerStubList = [];
			for(var ball in $scope.selectedBalls){
				if($scope.selectedBalls[ball]){
					lotteryMarkSixWagerStubList.push({
						number: parseInt(ball)
					});
				}
			}
			var wager={
				userId : $scope.user.id,
				pgroupId : $scope.selectedPGroup.id,
				lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
				lotteryMarkSixType: null, //$scope.jointLotteryType,
				totalStakes: $scope.notBallStakes
			};
			console.log(wager);
			var html=notBallService.renderWageConfirmHTML(wager);
			$scope.confirmDialogHTML=html;
			// 对话框确认
			$scope.isConfirmDialogVisible=true;
			$scope.confirmDialogTitle="下注类型为自选不中，请确认：";
			// 确认下注
			$scope.confirmDialog=function(){
				$scope.isConfirmDialogVisible=false;
				commonService.wage(wager);
			}
		}
	};
	
	// 快选
	$scope.quickChoose = function($event,type){
		var checkbox = $event.target;
		if(checkbox.checked){
			$scope.quickChooses[type]=true;
		}
		else{
			$scope.quickChooses[type]=false;
		}
	};
	$scope.isQuickChoose = function(type){
		return $scope.quickChooses[type];
	}
	var methodTableForNorm={
		"DAN":{
			method:"getSingleOrDouble",
			result:"single"
		},
		"SHUANG":{
			method:"getSingleOrDouble",
			result:"double"
		},
		"DA":{
			method:"getBigOrSmall",
			result:"big"
		},
		"XIAO":{
			method:"getBigOrSmall",
			result:"small"
		},
		"HEDAN":{
			method:"getSumSingleOrDouble",
			result:"single"
		},
		"HESHUANG":{
			method:"getSumSingleOrDouble",
			result:"double"
		},
		"HEDA":{
			method:"getSumBigOrSmall",
			result:"big"
		},
		"HEXIAO":{
			method:"getSumBigOrSmall",
			result:"small"
		},
		"TAILDA":{
			method:"getTailBigOrSmall",
			result:"big"
		},
		"TAILXIAO":{
			method:"getTailBigOrSmall",
			result:"small"
		},
		"SUMTAILDA":{
			method:"getTailSumBigOrSmall",
			result:"big"
		},
		"SUMTAILXIAO":{
			method:"getTailSumBigOrSmall",
			result:"small"
		}
	};
	// 特码快选
	$scope.quickChooseTail = function($event,type){
		$scope.selectedBalls={};
		$scope.quickChoose($event,type);
		for(var type in $scope.quickChooses){
			if($scope.quickChooses[type]){
				for(var i=1;i<=49;i++){
					if(methodTableForNorm[type] && Compare[methodTableForNorm[type].method](i)==methodTableForNorm[type].result){
						$scope.selectedBalls[i]=1;
					}
					else if(colorMap[i]==type){
						$scope.selectedBalls[i]=1;
					}
				}
			}
		}
	};
	// 生肖快选
	$scope.quickChooseZodiac = function($event,type){
		$scope.selectedBalls={};
		$scope.quickChoose($event,type);
		for(var type in $scope.quickChooses){
			if($scope.quickChooses[type]){
				if(type=="POULTRY"){
					var poultries=["ZODIAC_NIU", "ZODIAC_MA", "ZODIAC_YANG", "ZODIAC_JI", "ZODIAC_GOU", "ZODIAC_ZHU"];
					for(var i in poultries){
						$scope.selectedBalls[poultries[i]]=1;						
					}
				}
				else if(type=="BEAST"){
					var beasts=["ZODIAC_SHU", "ZODIAC_HU", "ZODIAC_LONG", "ZODIAC_SHE", "ZODIAC_TU", "ZODIAC_HOU"];
					for(var i in beasts){
						$scope.selectedBalls[beasts[i]]=1;						
					}
				}
			}
		}
	};
});