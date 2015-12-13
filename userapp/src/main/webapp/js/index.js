var app = angular.module("index", []);

app.service("commonService", function ($q, $http) {
    // 获取当前用户
    this.getUser = function () {
        var deferred = $q.defer();
        $http.get("user/" + sessionStorage["userid"]).success(
            function (response) {
                deferred.resolve(response);
            });
        return deferred.promise;
    };
    // 获得下一期
    this.getNextLottery = function () {
        var deferred = $q.defer();
        $http.get("lottery/next_lottery_mark_six_info").success(
            function (response) {
                deferred.resolve(response);
            });
        return deferred.promise;
    };
    // 获取某代理商对于某期的赔率
    this.getOddsList = function (lotteryIssue, pgroupId) {
        var deferred = $q.defer();
        $http.get("odds/lottery_issue/" + lotteryIssue + "/group/" + pgroupId)
            .success(function (response) {
                deferred.resolve(response);
            });
        return deferred.promise;
    };
    // 下注
    this.wage = function (wager, scope) {
        var _self = this;
        $http.post("gamble/wage", wager).success(function () {
            alert('下注成功');
            // 更新下用户额度
            _self.getUser().then(function (data) {
                scope.user = data;
                scope.reset();
            });
        }).error(function (data, status, headers) {
            alert('下注失败');
        });
    };
    // 多个下注
    this.wages = function (wagers, scope) {
        var _self = this;
        $http.post("gamble/wages", wagers).success(function () {
            alert('下注成功');
            // 更新下用户额度
            _self.getUser().then(function (data) {
                scope.user = data;
                scope.reset();
            });
        }).error(function (data, status, headers) {
            alert('下注失败');
        });
    };
    // 下注校验
    this.validateWage = function (wage, scope){
    	scope.wageError=undefined;
    	if(wage.lotteryMarkSixType=="SPECIAL" || wage.lotteryMarkSixType=="ZHENG_BALL" || wage.lotteryMarkSixType=="ZHENG_1_6" || wage.lotteryMarkSixType.indexOf("ZHENG_SPECIFIC")==0){
    		if(wage.lotteryMarkSixType.indexOf("ZHENG_SPECIFIC")==0){
    			if(typeof scope.otherParams.zhengSpecificNum==="undefined"){
    				scope.wageError="请选择正码号！";
        			return false;
    			}
    		}
    		var subList=wage.lotteryMarkSixWagerStubList;
    		if(subList.length==0){
    			scope.wageError="请选择下注！";
    			return false;
    		}
    		else{
    			for(var i=0;i<subList.length;i++){
    				var stakes=subList[i].stakes;
    				if(isNaN(stakes) || stakes<=0){
    					scope.wageError="下注的注数必须为正整数！";
    					return false;
    				}
    			}
    		}
    		return true;
    	}
    	else if(wage.lotteryMarkSixType.indexOf("ZODIAC")==0 || wage.lotteryMarkSixType=="RED" || wage.lotteryMarkSixType=="GREEN" || wage.lotteryMarkSixType=="BLUE" || wage.lotteryMarkSixType.indexOf("WAVE")==0){
    		if(isNaN(wage.totalStakes) || wage.totalStakes<=0){
    			scope.wageError="下注的注数必须为正整数！";
				return false;
    		}
    		return true;
    	}
    	else if(wage.lotteryMarkSixType.indexOf("SUM")==0){
    		var subList=wage.lotteryMarkSixWagerStubList;
    		if(subList.length==0){
    			scope.wageError="请选择合肖下注！";
    			return false;
    		}
    		else{
    			var maxZodiacCount=0;
    			for(var i=0;i<subList.length;i++){
    				var stakes=subList[i].stakes;
    				if(isNaN(stakes) || stakes<=0){
    					scope.wageError="下注的注数必须为正整数！";
    					return false;
    				}
    				if(subList[i].number>maxZodiacCount){
    					maxZodiacCount=subList[i].number;
    				}
    			}
    			maxZodiacCount=parseInt(maxZodiacCount/10);
    			var zodiacList=wage.subLotteryMarkSixTypes;
    			if(zodiacList.length==0){
    				scope.wageError="请选择需要下注的生肖！";
        			return false;
    			}
    			else if(zodiacList.length>11){
    				scope.wageError="选择的生肖不能多于11个！";
        			return false;
    			}
    			else if(zodiacList.length<maxZodiacCount){
    				scope.wageError="选择的生肖少于下注所需的数量！";
        			return false;
    			}
    		}
    		return true;
    	}
    };
    // 多个下注校验
    this.validateWages = function(wageList, scope){
    	scope.wageError=undefined;
    	if(wageList.length==0){
    		scope.wageError="下注内容为空！";
			return false;
    	}
    	for(var i=0;i<wageList.length;i++){
    		if(!this.validateWage(wageList[i],scope)){
    			return false;
    		}
    	}
    	return true;
    };
});

// 生肖相关
app.service("zodiacService", function ($q, $http, $sce) {
    this.getZodiacItems = function (oddsMap) {
        var zodiacMap = Zodiac.zodiacMap, items = [];
        for (var i = 0; i < zodiacMap.length; i++) {
            var item = {};
            item.id = zodiacMap[i].id;
            item.name = zodiacMap[i].name;
            item.balls = Zodiac.getBallsByName(zodiacMap[i].name);
            item.odds = oddsMap[zodiacMap[i].id];
            items.push(item);
        }
        return items;
    };
    this.getColorItems = function (oddsMap) {
        var items = {};
        items.red = oddsMap["RED"];
        items.blue = oddsMap["BLUE"];
        items.green = oddsMap["GREEN"];
        return items;
    };
    this.renderWageConfirmHTML = function (wagerList) {
        var html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var wager = wagerList[i], type = wager.lotteryMarkSixType;
            // 生肖
            if (type.indexOf("ZODIAC_") == 0) {
                html += "<div style='height:40px;width:300px;margin:10px auto;'><span style='line-height:25px;float:left;'>";
                html += zodiacTypeMap[type];
                html += "</span><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额：" + wager.totalStakes + "</span></div>";
            }
            // 色波
            else if (type == "RED" || type == "BLUE" || type == "GREEN") {
                html += "<div style='height:40px;width:300px;margin:10px auto;'><span style='line-height:25px;float:left;'>";
                var color = "红波";
                if (type == "BLUE") {
                    color = "蓝波";
                }
                else if (type == "GREEN") {
                    color = "绿波";
                }
                html += color;
                html += "</span><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额：" + wager.totalStakes + "</span></div>";
            }
        }
        return $sce.trustAsHtml(html);
    }
});

// 特码相关
app.service("tailBallService", function ($q, $http, $sce) {
    this.getTailItems = function (oddsMap) {
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
    this.renderWageConfirmHTML = function (wager) {
        var wagerList = wager.lotteryMarkSixWagerStubList, html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], number = item.number, stakes = item.stakes;
            html += "<div style='height:40px;width:300px;margin:10px auto;'><div class='ball " + colorMap[number] + "'>" + number + "</div><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额：" + stakes + "</span></div>";
        }
        return $sce.trustAsHtml(html);
    };
});

//半波相关
app.service("halfWaveService", function ($q, $http, $sce) {
    this.getHalfWaveItems = function (oddsMap) {
        var items = {};
        for (var type in oddsMap) {
            if (type.indexOf("WAVE_") == 0) {
                items[type] = oddsMap[type];
            }
        }
        return items;
    };
    this.renderWageConfirmHTML = function (wagerList) {
        var html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], type = item.lotteryMarkSixType, stakes = item.totalStakes, arr = type.split("_");
            var color = "红";
            if (arr[1] == "BLUE") {
                color = "蓝";
            }
            else if (arr[1] == "GREEN") {
                color = "绿";
            }
            var type = "单";
            if (arr[2] == "SHUANG") {
                type = "双";
            }
            else if (arr[2] == "DA") {
                type = "大";
            }
            else if (arr[2] == "XIAO") {
                type = "小";
            }
            html += "<div style='height:40px;width:300px;margin:10px auto;'>" + color + type + "<span style='color:red;margin-left:10px;'>下注金额：" + stakes + "</span></div>";
        }
        return $sce.trustAsHtml(html);
    }
});

// 合肖相关
app.service("sumZodiacService", function ($q, $http, $sce) {
    this.getSumZodiacItems = function (oddsMap) {
        var sumZodiacItems = [];
        for (var i = 1; i <= 11; i++) {
            var item = {};
            item.name = i + "肖";
            item.odds = {
                zhong: oddsMap["SUM_ZODIAC#" + i + "1"],
                buzhong: oddsMap["SUM_ZODIAC#" + i + "0"]
            }
            sumZodiacItems.push(item);
        }
        return sumZodiacItems;
    };
    this.renderWageConfirmHTML = function (wager) {
        var html = "<div style='height:40px;width:300px;margin:10px auto;'>选择的生肖为：";
        var zodiacs = wager.subLotteryMarkSixTypes;
        for (var i = 0; i < zodiacs.length; i++) {
            var zodiac = zodiacs[i];
            html += zodiacTypeMap[zodiac];
        }
        html += "</div>";
        var wagers = wager.lotteryMarkSixWagerStubList;
        for (var i = 0; i < wagers.length; i++) {
            var item = wagers[i], number = item.number, stakes = item.stakes;
            var zodiacNum = parseInt(number / 10), isHit = number % 10 == 1;
            html += "<div style='height:40px;width:300px;margin:10px auto;'>" + zodiacNum + "肖" + (isHit ? "中" : "不中") + "<span style='color:red;margin-left:10px;'>下注金额：" + stakes + "</span></div>";
        }
        return $sce.trustAsHtml(html);
    }
});

// 正码相关
app.service("zhengBallService", function ($q, $http, $sce) {
    this.getZheng16Items = function (oddsMap) {
        var items = {};
        for (var type in oddsMap) {
            if (type.indexOf("ZHENG_1_6") == 0) {
                items[type] = oddsMap[type];
            }
        }
        return items;
    };
    this.getZhengBallItems = function (oddsMap) {
        var row = 10, col = 5, items = [];
        for (var i = 0; i < row; i++) {
            var itemRow = [];
            for (var j = 0; j < col; j++) {
                var ball = i * col + j + 1;
                if (ball > 49) {
                    break;
                }
                var item = {};
                item.ball = ball;
                item.odds = oddsMap["ZHENG_BALL#" + ball];
                itemRow.push(item);
            }
            items.push(itemRow);
        }
        return items;
    };
    this.getZhengSpecificItems = function () {
        var row = 10, col = 5, items = [];
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
            items.push(itemRow);
        }
        return items;
    };
    this.renderZhengWageConfirmHTML = function (wager) {
        var wagerList = wager.lotteryMarkSixWagerStubList, html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], number = item.number, stakes = item.stakes;
            html += "<div style='height:40px;width:300px;margin:10px auto;'><div class='ball " + colorMap[number] + "'>" + number + "</div><span style='color:red;margin-left:10px;line-height:25px;float:left;'>下注金额：" + stakes + "</span></div>";
        }
        return $sce.trustAsHtml(html);
    };
    this.renderZheng16WageConfirmHTML = function (wager) {
        var html = "", wagers = wager.lotteryMarkSixWagerStubList;
        for (var i = 0; i < wagers.length; i++) {
            var item = wagers[i], number = item.number, stakes = item.stakes, lotteryType = item.lotteryMarkSixType, type = zhengTypeMap[lotteryType];
            html += "<div style='height:40px;width:300px;margin:10px auto;'>正码" + number + "：" + type + "<span style='color:red;margin-left:10px;'>下注金额：" + stakes + "</span></div>";
        }
        return $sce.trustAsHtml(html);
    };
});

// 连码相关
app.service("jointBallService", function ($q, $http, $sce) {
    this.getJointItems = function () {
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
    this.renderWageConfirmHTML = function (wager) {
        var wagerList = wager.lotteryMarkSixWagerStubList, stakes = wager.totalStakes, type = wager.lotteryMarkSixType, html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], number = item.number;
            html += "<div class='ball " + colorMap[number] + "'>" + number + "</div>";
        }
        html += "<div class='clear'><br/><span>连码类型：" + jointTypeMap[type] + "</span>";
        html += "<br/><br/><span style='color:red'>下注金额：" + stakes + "</span>";
        return $sce.trustAsHtml(html);
    };
});

// 自选不中相关
app.service("notBallService", function ($q, $http, $sce) {
    this.getNotItems = function () {
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
    this.renderWageConfirmHTML = function (wager) {
        var wagerList = wager.lotteryMarkSixWagerStubList, stakes = wager.totalStakes, type = wager.lotteryMarkSixType, html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], number = item.number;
            html += "<div class='ball " + colorMap[number] + "'>" + number + "</div>";
        }
        html += "<div class='clear'><br/><span>自选不中类型：" + type.split("_")[1] + "不中</span>";
        html += "<br/><br/><span style='color:red'>下注金额：" + stakes + "</span>";
        return $sce.trustAsHtml(html);
    };
});

//过关相关
app.service("passBallService", function ($q, $http, $sce) {
    this.renderWageConfirmHTML = function (wager) {
        var wagerList = wager.lotteryMarkSixWagerStubList, stakes = wager.totalStakes, html = "";
        for (var i = 0; i < wagerList.length; i++) {
            var item = wagerList[i], number = item.number, type = item.lotteryMarkSixType;
            html += "<div>正码" + number + ": " + passTypeMap[type] + "</div>";
        }
        html += "<br/><br/><span style='color:red'>下注金额：" + stakes + "</span>";
        return $sce.trustAsHtml(html);
    };
});

//一肖尾数相关
app.service("zodiacTailService", function ($q, $http, $sce) {
    this.getOneZodiacItems = function (oddsMap) {
        var zodiacMap = Zodiac.zodiacMap, oneZodiacItems = [];
        for (var i = 0; i < zodiacMap.length; i++) {
            var item = {};
            item.id = zodiacMap[i].id;
            item.name = zodiacMap[i].name;
            item.balls = Zodiac.getBallsByName(zodiacMap[i].name);
            item.odds = oddsMap["ONE_ZODIAC#" + zodiacMap[i].id];
            oneZodiacItems.push(item);
        }
        return oneZodiacItems;
    };
    this.renderWageConfirmHTML = function (wagerList) {
        var html = "<table>";
        for (var i = 0; i < wagerList.length; i++) {
            var wager = wagerList[i];
            if (wager.lotteryMarkSixType == "ONE_ZODIAC") {
                html += "<tr><th>一肖下注：</th><td>";
                var subWagerList = wager.lotteryMarkSixWagerStubList;
                for (var j = 0; j < subWagerList.length; j++) {
                    var subWager = subWagerList[j];
                    html += zodiacTypeMap[subWager.lotteryMarkSixType] + "<span style='color:red'>(" + subWager.stakes + ")</span>, ";
                }
                html += "</td></tr>";
            }
            else if (wager.lotteryMarkSixType == "TAIL_NUM") {
                html += "<tr><th>尾数下注：</th><td>";
                var subWagerList = wager.lotteryMarkSixWagerStubList;
                for (var j = 0; j < subWagerList.length; j++) {
                    var subWager = subWagerList[j];
                    html += subWager.number + "尾<span style='color:red'>(" + subWager.stakes + ")</span>, ";
                }
                html += "</td></tr>";
            }
        }
        html += "</table>";
        return $sce.trustAsHtml(html);
    };
});

app.controller("IndexController", function ($scope, commonService,
                                            zodiacService, tailBallService, halfWaveService, sumZodiacService, zhengBallService,
                                            jointBallService, notBallService, passBallService, zodiacTailService) {
    $scope.items = ["特码", "生肖色波", "半波", "合肖", "正码", "正码1~6", "正码特", "连码", "自选不中", "过关", "一肖尾数"];
    $scope.selectedIndex = 0;
    $scope.menu = 1;
    $scope.goTab = function (index) {
        $scope.selectedIndex = index;
        $scope.reset();
    };

    // 重置
    $scope.reset = function () {
        $scope.selectedBalls = {};
        $scope.selectedBalls2 = {};
        $scope.sumZodiacList = [];
        $scope.quickChooses = {};
        $scope.isConfirmDialogVisible = false;
        $scope.closeConfirmDialog = function () {
            $scope.isConfirmDialogVisible = false;
        };
        $scope.confirmDialog = function () {
            $scope.isConfirmDialogVisible = false;
        };
        $scope.otherParams = {};
        $scope.wageError = undefined;
    };
    
    // 秋色
    $scope.colorMap = colorMap;

    // 初始化数据
    $scope.reset();

    // 根据期数和代理商重新获取彩票
    var generateLotteryList = function () {
        // 获取赔率
        commonService.getOddsList($scope.nextLottery.issue,
            $scope.selectedPGroup.id).then(function (oddsList) {
                var oddsMap = {}, jointOddsMap = {}, notOddsMap = {}, passOddsMap = {}, tailNumOddsMap = {}, zhengSpecificOddsMap = {};
                for (var i = 0; i < oddsList.length; i++) {
                    var odds = oddsList[i];
                    if (odds.lotteryMarkSixType == "SPECIAL") {
                        oddsMap[odds.lotteryBallNumber + ""] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "BLUE" || odds.lotteryMarkSixType == "RED" || odds.lotteryMarkSixType == "GREEN") {
                        oddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("ZODIAC_") == 0) {
                        oddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("WAVE_") == 0) {
                        oddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "SUM_ZODIAC") {
                        oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "ZHENG_BALL") {
                        oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "ZHENG_1_6") {
                        oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("ZHENG_SPECIFIC_") == 0) {
                        zhengSpecificOddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("JOINT_") == 0) {
                        jointOddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("NOT_") == 0) {
                        notOddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType.indexOf("PASS_") == 0) {
                        passOddsMap[odds.lotteryMarkSixType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "ONE_ZODIAC") {
                        oddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallType] = odds.odds;
                    }
                    else if (odds.lotteryMarkSixType == "TAIL_NUM") {
                        tailNumOddsMap[odds.lotteryMarkSixType + "#" + odds.lotteryBallNumber] = odds.odds;
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
                // 正码
                $scope.zhengBallItems = zhengBallService.getZhengBallItems(oddsMap);
                // 正码1-6
                $scope.zheng16Items = zhengBallService.getZheng16Items(oddsMap);
                // 正码特
                $scope.zhengSpecificItems = zhengBallService.getZhengSpecificItems();
                $scope.zhengSpecificOddsMap = zhengSpecificOddsMap;
                // 连码
                $scope.jointItems = jointBallService.getJointItems();
                $scope.jointOddsMap = jointOddsMap;
                // 自选不中
                $scope.notItems = notBallService.getNotItems();
                $scope.notOddsMap = notOddsMap;
                // 过关
                $scope.passOddsMap = passOddsMap;
                // 一肖
                $scope.oneZodiacItems = zodiacTailService.getOneZodiacItems(oddsMap);
                // 尾数
                $scope.tailNumOddsMap = tailNumOddsMap;
            });
    };

    // 更改代理商后，需要重新获取彩票数据
    $scope.changePGroup = generateLotteryList;

    // 初始化数据
    commonService.getUser().then(function (data) {
        // 用户与组
        $scope.user = data;
        $scope.selectedPGroup = $scope.user.pGroupList[0];
        // 获取下一期
        return commonService.getNextLottery();
    }).then(function (data) {
        $scope.nextLottery = data;
        generateLotteryList();
    });

    // 合码的选择类型函数
    $scope.chooseSumZodiac = function ($event, zodiac) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            $scope.sumZodiacList.push(zodiac);
        }
        else {
            var copy = [];
            for (var i = 0; i < $scope.sumZodiacList.length; i++) {
                if ($scope.sumZodiacList[i] != zodiac) {
                    copy.push($scope.sumZodiacList[i]);
                }
            }
            $scope.sumZodiacList = copy;
        }
    };

    $scope.isCheckedSumZodiac = function (zodiac) {
        for (var i = 0; i < $scope.sumZodiacList.length; i++) {
            if ($scope.sumZodiacList[i] == zodiac) {
                return true;
            }
        }
        return false;
    };

    // 正码1-6的选择类型函数
    $scope.chooseZheng16 = function ($event, ballNum, type) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            $scope.selectedBalls[ballNum] = type;
        }
        else {
            $scope.selectedBalls[ballNum] = undefined;
        }
    };

    // 过关的类型选择函数
    $scope.choosePassBall = function ($event, ballNum, type) {
        var checkbox = $event.target;
        if (typeof $scope.selectedBalls[ballNum] === 'undefined') {
            $scope.selectedBalls[ballNum] = {};
        }
        $scope.selectedBalls[ballNum][type] = checkbox.checked;
    }

    // 其他选择类型函数
    $scope.chooseBall = function ($event, ballNum) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            $scope.selectedBalls[ballNum] = true;
        }
        else {
            $scope.selectedBalls[ballNum] = false;
        }
    };

    // 下注
    $scope.wage = function () {
        // 特码下注
        if ($scope.selectedIndex == 0) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
            	if($scope.selectedBalls[ball]!=""){
            		lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball),
                        stakes: parseInt($scope.selectedBalls[ball])
                    });
            	}
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "SPECIAL"
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = tailBallService.renderWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为特码，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 生肖色波下注
        else if ($scope.selectedIndex == 1) {
            // 组装下注对象
            var wagerList = [];
            for (var zodiac in $scope.selectedBalls) {
            	if($scope.selectedBalls[zodiac]!=""){
            		var wager = {
                        userId: $scope.user.id,
                        pgroupId: $scope.selectedPGroup.id,
                        lotteryMarkSixWagerStubList: [],
                        lotteryMarkSixType: zodiac,
                        totalStakes: parseInt($scope.selectedBalls[zodiac])
                    };
                    wagerList.push(wager);
            	}
            }
            for (var color in $scope.selectedBalls2) {
            	if($scope.selectedBalls2[color]!=""){
            		var wager = {
                        userId: $scope.user.id,
                        pgroupId: $scope.selectedPGroup.id,
                        lotteryMarkSixWagerStubList: [],
                        lotteryMarkSixType: color,
                        totalStakes: parseInt($scope.selectedBalls2[color])
                    };
                    wagerList.push(wager);
            	}
            }
            if(!commonService.validateWages(wagerList, $scope)){
            	return;
            }
            var html = zodiacService.renderWageConfirmHTML(wagerList);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为生肖色波，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wages(wagerList, $scope);
            }
        }
        // 半波下注
        else if ($scope.selectedIndex == 2) {
            // 组装下注对象
            var wagerList = [];
            for (var type in $scope.selectedBalls) {
            	if($scope.selectedBalls[type]!=""){
            		var wager = {
                        userId: $scope.user.id,
                        pgroupId: $scope.selectedPGroup.id,
                        lotteryMarkSixWagerStubList: [],
                        lotteryMarkSixType: type,
                        totalStakes: parseInt($scope.selectedBalls[type])
                    };
                    wagerList.push(wager);
            	}
            }
            if(!commonService.validateWages(wagerList, $scope)){
            	return;
            }
            var html = halfWaveService.renderWageConfirmHTML(wagerList);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为半波，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wages(wagerList, $scope);
            }
        }
        // 合肖下注
        else if ($scope.selectedIndex == 3) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
            	if($scope.selectedBalls[ball]!=""){
            		lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball) * 10 + parseInt($scope.selectedBalls2[ball]), // 101表示10肖中，20表示2肖不中。个位表示中不中，前面几位表示几肖,
                        stakes: parseInt($scope.selectedBalls[ball])
                    });
            	}
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                subLotteryMarkSixTypes: $scope.sumZodiacList,
                lotteryMarkSixType: "SUM_ZODIAC"
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = sumZodiacService.renderWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为合肖，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 正码下注
        else if ($scope.selectedIndex == 4) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
            	if($scope.selectedBalls[ball]!=""){
            		lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball),
                        stakes: parseInt($scope.selectedBalls[ball])
                    });
            	}
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "ZHENG_BALL"
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = zhengBallService.renderZhengWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为正码，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 正码1-6下注
        else if ($scope.selectedIndex == 5) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ballNum in $scope.selectedBalls) {
                if (typeof $scope.selectedBalls[ballNum] == "undefined" || $scope.selectedBalls2[ballNum]=="") {
                    continue;
                }
                lotteryMarkSixWagerStubList.push({
                    number: ballNum, // 表示第几个正码
                    lotteryMarkSixType: $scope.selectedBalls[ballNum], // 表示该正码x的类型
                    stakes: parseInt($scope.selectedBalls2[ballNum])
                });
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "ZHENG_1_6"
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = zhengBallService.renderZheng16WageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为正码1~6，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 正码特下注
        else if ($scope.selectedIndex == 6) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
            	if($scope.selectedBalls[ball]!=""){
            		lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball),
                        stakes: parseInt($scope.selectedBalls[ball])
                    });
            	}
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "ZHENG_SPECIFIC_" + $scope.otherParams.zhengSpecificNum
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = zhengBallService.renderZhengWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为正码特" + $scope.otherParams.zhengSpecificNum + "，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 连码下注
        else if ($scope.selectedIndex == 7) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
                if ($scope.selectedBalls[ball]) {
                    lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball)
                    });
                }
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: $scope.otherParams.type,
                totalStakes: $scope.otherParams.stakes
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = jointBallService.renderWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为连码，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 自选不中下注
        else if ($scope.selectedIndex == 8) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
                if ($scope.selectedBalls[ball]) {
                    lotteryMarkSixWagerStubList.push({
                        number: parseInt(ball)
                    });
                }
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: $scope.otherParams.type,
                totalStakes: $scope.otherParams.stakes
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = notBallService.renderWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为自选不中，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 过关
        else if ($scope.selectedIndex == 9) {
            // 组装下注对象
            var lotteryMarkSixWagerStubList = [];
            for (var ball in $scope.selectedBalls) {
                if ($scope.selectedBalls[ball]) {
                    for (var type in $scope.selectedBalls[ball]) {
                        if ($scope.selectedBalls[ball][type]) {
                            lotteryMarkSixWagerStubList.push({
                                number: parseInt(ball),
                                lotteryMarkSixType: type
                            });
                        }
                    }
                }
            }
            var wager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "PASS",
                totalStakes: $scope.otherParams.stakes
            };
            if(!commonService.validateWage(wager, $scope)){
            	return;
            }
            var html = passBallService.renderWageConfirmHTML(wager);
            $scope.confirmDialogHTML = html;
            // 对话框确认
            $scope.isConfirmDialogVisible = true;
            $scope.confirmDialogTitle = "下注类型为过关，请确认：";
            // 确认下注
            $scope.confirmDialog = function () {
                $scope.isConfirmDialogVisible = false;
                commonService.wage(wager, $scope);
            }
        }
        // 一肖尾数下注
        else if ($scope.selectedIndex == 10) {
            // 组装下注对象
            // 一肖
            var lotteryMarkSixWagerStubList = [];
            for (var zodiac in $scope.selectedBalls) {
                if ($scope.selectedBalls[zodiac] && $scope.selectedBalls[zodiac]!="") {
                    lotteryMarkSixWagerStubList.push({
                        lotteryMarkSixType: zodiac,
                        stakes: parseInt($scope.selectedBalls[zodiac])
                    });
                }
            }
            var zodiacWager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList,
                lotteryMarkSixType: "ONE_ZODIAC"
            };
            // 尾数
            var lotteryMarkSixWagerStubList2 = [];
            for (var num in $scope.selectedBalls2) {
                if ($scope.selectedBalls2[num] && $scope.selectedBalls2[num]!="") {
                    lotteryMarkSixWagerStubList2.push({
                        number: num,
                        stakes: parseInt($scope.selectedBalls2[num])
                    });
                }
            }
            var tailWager = {
                userId: $scope.user.id,
                pgroupId: $scope.selectedPGroup.id,
                lotteryMarkSixWagerStubList: lotteryMarkSixWagerStubList2,
                lotteryMarkSixType: "TAIL_NUM"
            };
            var wagerList = [];
            if (lotteryMarkSixWagerStubList.length > 0) {
                wagerList.push(zodiacWager);
            }
            if (lotteryMarkSixWagerStubList2.length > 0) {
                wagerList.push(tailWager);
            }
            if(!commonService.validateWages(wagerList, $scope)){
            	return;
            }
            if (wagerList.length > 0) {
                var html = zodiacTailService.renderWageConfirmHTML(wagerList);
                $scope.confirmDialogHTML = html;
                // 对话框确认
                $scope.isConfirmDialogVisible = true;
                $scope.confirmDialogTitle = "下注类型为一肖尾数，请确认：";
                // 确认下注
                $scope.confirmDialog = function () {
                    $scope.isConfirmDialogVisible = false;
                    commonService.wages(wagerList, $scope);
                }
            }
        }
    };

    // 快选
    $scope.quickChoose = function ($event, type) {
        var checkbox = $event.target;
        if (checkbox.checked) {
            $scope.quickChooses[type] = true;
        }
        else {
            $scope.quickChooses[type] = false;
        }
    };
    $scope.isQuickChoose = function (type) {
        return $scope.quickChooses[type];
    }
    var methodTableForNorm = {
        "DAN": {
            method: "getSingleOrDouble",
            result: "single"
        },
        "SHUANG": {
            method: "getSingleOrDouble",
            result: "double"
        },
        "DA": {
            method: "getBigOrSmall",
            result: "big"
        },
        "XIAO": {
            method: "getBigOrSmall",
            result: "small"
        },
        "HEDAN": {
            method: "getSumSingleOrDouble",
            result: "single"
        },
        "HESHUANG": {
            method: "getSumSingleOrDouble",
            result: "double"
        },
        "HEDA": {
            method: "getSumBigOrSmall",
            result: "big"
        },
        "HEXIAO": {
            method: "getSumBigOrSmall",
            result: "small"
        },
        "TAILDA": {
            method: "getTailBigOrSmall",
            result: "big"
        },
        "TAILXIAO": {
            method: "getTailBigOrSmall",
            result: "small"
        },
        "SUMTAILDA": {
            method: "getTailSumBigOrSmall",
            result: "big"
        },
        "SUMTAILXIAO": {
            method: "getTailSumBigOrSmall",
            result: "small"
        }
    };
    // 特码快选
    $scope.quickChooseTail = function ($event, type) {
        $scope.selectedBalls = {};
        $scope.quickChoose($event, type);
        for (var type in $scope.quickChooses) {
            if ($scope.quickChooses[type]) {
                for (var i = 1; i <= 49; i++) {
                    if (methodTableForNorm[type] && Compare[methodTableForNorm[type].method](i) == methodTableForNorm[type].result) {
                        $scope.selectedBalls[i] = 1;
                    }
                    else if (colorMap[i] == type) {
                        $scope.selectedBalls[i] = 1;
                    }
                }
            }
        }
    };
    // 生肖快选
    $scope.quickChooseZodiac = function ($event, type) {
        $scope.selectedBalls = {};
        $scope.quickChoose($event, type);
        for (var type in $scope.quickChooses) {
            if ($scope.quickChooses[type]) {
                if (type == "POULTRY") {
                    var poultries = ["ZODIAC_NIU", "ZODIAC_MA", "ZODIAC_YANG", "ZODIAC_JI", "ZODIAC_GOU", "ZODIAC_ZHU"];
                    for (var i in poultries) {
                        $scope.selectedBalls[poultries[i]] = 1;
                    }
                }
                else if (type == "BEAST") {
                    var beasts = ["ZODIAC_SHU", "ZODIAC_HU", "ZODIAC_LONG", "ZODIAC_SHE", "ZODIAC_TU", "ZODIAC_HOU"];
                    for (var i in beasts) {
                        $scope.selectedBalls[beasts[i]] = 1;
                    }
                }
            }
        }
    };
});