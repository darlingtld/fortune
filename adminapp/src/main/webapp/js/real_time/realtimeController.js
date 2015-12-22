/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, realtimeService) {
        $rootScope.menu = 1;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });
        
        $scope.zodiacNames = {
            "zodiac_shu" : "鼠",
            "zodiac_niu" : "牛",
            "zodiac_hu" : "虎",
            "zodiac_tu" : "兔",
            "zodiac_long" : "龙",
            "zodiac_she" : "蛇",
            "zodiac_ma" : "马",
            "zodiac_yang" : "羊",
            "zodiac_hou" : "猴",
            "zodiac_ji" : "鸡",
            "zodiac_gou" : "狗",
            "zodiac_zhu" : "猪"
        };
        
        $scope.pans = {
            panlei: [
                {name: 'ALL', title: '全部'},
                {name: 'A', title: 'A盘'},
                {name: 'B', title: 'B盘'},
                {name: 'C', title: 'C盘'},
                {name: 'D', title: 'D盘'}],
        }
        $scope.selectedPan = $scope.pans.panlei[0];

        $scope.getRealtimeTransactions = function () {
            $scope.goto($scope.pageType, $scope.selectedPan.name);
        }

        $scope.page = 'includes/realtime_special.html';
        function renderSpecial() {
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'special', $scope.selectedPan.name).then(function (data) {
                $scope.realTimeTranscations = data;
                $scope.list = [];
                $scope.list[0] = $scope.realTimeTranscations.slice(0, 10);
                $scope.list[1] = $scope.realTimeTranscations.slice(10, 20);
                $scope.list[2] = $scope.realTimeTranscations.slice(20, 30);
                $scope.list[3] = $scope.realTimeTranscations.slice(30, 40);
                $scope.list[4] = $scope.realTimeTranscations.slice(40, 49);
                $scope.specialtypelist = [];
                $scope.specialtypelist[0] = $scope.realTimeTranscations.slice(49, 51);
                $scope.specialtypelist[1] = $scope.realTimeTranscations.slice(51, 53);
                $scope.specialtypelist[2] = $scope.realTimeTranscations.slice(53, 55);
                $scope.specialtypelist[3] = $scope.realTimeTranscations.slice(55, 57);
                $scope.specialtypelist[4] = $scope.realTimeTranscations.slice(57, 59);
                $scope.animaltypelist = [];
                $scope.animaltypelist[0] = $scope.realTimeTranscations.slice(59, 62);
                $scope.animaltypelist[1] = $scope.realTimeTranscations.slice(62, 65);
                $scope.animaltypelist[2] = $scope.realTimeTranscations.slice(65, 68);
                $scope.animaltypelist[3] = $scope.realTimeTranscations.slice(68, 71);
                $scope.animaltypelist[4] = $scope.realTimeTranscations.slice(71, 73);
                $scope.wavetypelist = [];
                $scope.wavetypelist[0] = $scope.realTimeTranscations.slice(73, 76);
                $scope.wavetypelist[1] = $scope.realTimeTranscations.slice(76, 79);
                $scope.wavetypelist[2] = $scope.realTimeTranscations.slice(79, 82);
                $scope.wavetypelist[3] = $scope.realTimeTranscations.slice(82, 85);
                $scope.othertypelist = [];
                $scope.othertypelist[0] = $scope.realTimeTranscations.slice(85, 87);
                $scope.othertypelist[1] = $scope.realTimeTranscations.slice(87, 90);
                $scope.othertypelist[2] = $scope.realTimeTranscations.slice(90, 93);


                $scope.stats = {
                    specialTransactions: 0,
                    specialStakes: 0,
                    specialtypeTransactions: 0,
                    specialtypeStakes: 0,
                    animaltypeTransactions: 0,
                    animaltypeStakes: 0,
                    wavetypeTransactions: 0,
                    wavetypeStakes: 0,
                    specialtailtypeTransactions: 0,
                    specialtailtypeStakes: 0,
                    colortypeTransactions: 0,
                    colortypeStakes: 0,
                    bstypeTransactions: 0,
                    bstypeStakes: 0
                };
                for (var i = 0; i < $scope.list.length; i++) {
                    for (var j = 0; j < $scope.list[i].length; j++) {
                        $scope.stats.specialTransactions += $scope.list[i][j].transactions;
                        $scope.stats.specialStakes += $scope.list[i][j].stakes;
                    }
                }
                for (var i = 0; i < $scope.specialtypelist.length; i++) {
                    for (var j = 0; j < $scope.specialtypelist[i].length; j++) {
                        $scope.stats.specialtypeTransactions += $scope.specialtypelist[i][j].transactions;
                        $scope.stats.specialtypeStakes += $scope.specialtypelist[i][j].stakes;
                    }
                }
                for (var i = 0; i < $scope.animaltypelist.length; i++) {
                    for (var j = 0; j < $scope.animaltypelist[i].length; j++) {
                        $scope.stats.animaltypeTransactions += $scope.animaltypelist[i][j].transactions;
                        $scope.stats.animaltypeStakes += $scope.animaltypelist[i][j].stakes;
                    }
                }
                for (var i = 0; i < $scope.wavetypelist.length; i++) {
                    for (var j = 0; j < $scope.wavetypelist[i].length; j++) {
                        $scope.stats.wavetypeTransactions += $scope.wavetypelist[i][j].transactions;
                        $scope.stats.wavetypeStakes += $scope.wavetypelist[i][j].stakes;
                    }
                }
                for (var j = 0; j < $scope.othertypelist[0].length; j++) {
                    $scope.stats.specialtailtypeTransactions += $scope.othertypelist[0][j].transactions;
                    $scope.stats.specialtailtypeStakes += $scope.othertypelist[0][j].stakes;
                }
                for (var j = 0; j < $scope.othertypelist[1].length; j++) {
                    $scope.stats.colortypeTransactions += $scope.othertypelist[1][j].transactions;
                    $scope.stats.colortypeStakes += $scope.othertypelist[1][j].stakes;
                }
                for (var j = 0; j < $scope.othertypelist[2].length; j++) {
                    $scope.stats.bstypeTransactions += $scope.othertypelist[2][j].transactions;
                    $scope.stats.bstypeStakes += $scope.othertypelist[2][j].stakes;
                }

                $scope.specialTransactionTotal = $scope.stats.specialTransactions + $scope.stats.specialtypeTransactions + $scope.stats.animaltypeTransactions
                    + $scope.stats.wavetypeTransactions + $scope.stats.specialtailtypeTransactions + $scope.stats.colortypeTransactions + $scope.stats.bstypeTransactions;

            })
        }

        function renderSumZodiac() {
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'sum_zodiac', $scope.selectedPan.name).then(function (data) {
                $scope.list1 = [];
                $scope.list1.push(data.slice(0, 2).reverse());
                $scope.list1.push(data.slice(2, 4).reverse());
                $scope.list1.push(data.slice(4, 6).reverse());
                $scope.list1.push(data.slice(6, 8).reverse());
                $scope.list2 = [];
                $scope.list2.push(data.slice(8, 10).reverse());
                $scope.list2.push(data.slice(10, 12).reverse());
                $scope.list2.push(data.slice(12, 14).reverse());
                $scope.list2.push(data.slice(14, 16).reverse());
                $scope.list3 = [];
                $scope.list3.push(data.slice(16, 18).reverse());
                $scope.list3.push(data.slice(18, 20).reverse());
                $scope.list3.push(data.slice(20, 22).reverse());

                $scope.transactions = [];
                $scope.stakes = [];
                $scope.transactions.push(data[0].transactions + data[1].transactions);
                $scope.stakes.push(data[0].stakes + data[1].stakes);
                $scope.transactions.push(data[2].transactions + data[3].transactions);
                $scope.stakes.push(data[2].stakes + data[3].stakes);
                $scope.transactions.push(data[4].transactions + data[5].transactions);
                $scope.stakes.push(data[4].stakes + data[5].stakes);
                $scope.transactions.push(data[6].transactions + data[7].transactions);
                $scope.stakes.push(data[6].stakes + data[7].stakes);
                $scope.transactions.push(data[8].transactions + data[9].transactions);
                $scope.stakes.push(data[8].stakes + data[9].stakes);
                $scope.transactions.push(data[10].transactions + data[11].transactions);
                $scope.stakes.push(data[10].stakes + data[12].stakes);
                $scope.transactions.push(data[12].transactions + data[13].transactions);
                $scope.stakes.push(data[12].stakes + data[13].stakes);
                $scope.transactions.push(data[14].transactions + data[15].transactions);
                $scope.stakes.push(data[14].stakes + data[15].stakes);
                $scope.transactions.push(data[16].transactions + data[17].transactions);
                $scope.stakes.push(data[16].stakes + data[17].stakes);
                $scope.transactions.push(data[18].transactions + data[19].transactions);
                $scope.stakes.push(data[18].stakes + data[19].stakes);
                $scope.transactions.push(data[20].transactions + data[21].transactions);
                $scope.stakes.push(data[20].stakes + data[21].stakes);
            });
        }

        function renderZhengBall() {
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zheng_ball', $scope.selectedPan.name).then(function (data) {
                $scope.realTimeTranscations = data;
                $scope.list = [];
                $scope.list[0] = $scope.realTimeTranscations.slice(0, 10);
                $scope.list[1] = $scope.realTimeTranscations.slice(10, 20);
                $scope.list[2] = $scope.realTimeTranscations.slice(20, 30);
                $scope.list[3] = $scope.realTimeTranscations.slice(30, 40);
                $scope.list[4] = $scope.realTimeTranscations.slice(40, 49);

                $scope.stats = {
                    zhengBallTransactions: 0,
                    zhengBallStakes: 0
                };
                for (var i = 0; i < $scope.list.length; i++) {
                    for (var j = 0; j < $scope.list[i].length; j++) {
                        $scope.stats.zhengBallTransactions += $scope.list[i][j].transactions;
                        $scope.stats.zhengBallStakes += $scope.list[i][j].stakes;
                    }
                }

                $scope.zhengBallTransactionTotal = $scope.stats.zhengBallTransactions;
            })
        }
        
        function renderZhengSpecific(page) {
            $scope.zhengSpecificType = page;
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], page, $scope.selectedPan.name).then(function (data) {
                $scope.realTimeTranscations = data;
                
                $scope.stats = {
                    zhengSpecificTransactions: 0,
                    zhengSpecificStakes: 0
                };
                for (var i = 0; i < $scope.realTimeTranscations.length; i++) {
                    $scope.stats.zhengSpecificTransactions += $scope.realTimeTranscations[i].transactions;
                    $scope.stats.zhengSpecificStakes += $scope.realTimeTranscations[i].stakes;
                }
                $scope.zhengSpecificTransactionTotal = $scope.stats.zhengSpecificTransactions;
                
                $scope.list = [];
                $scope.list[0] = $scope.realTimeTranscations.slice(0, 10);
                $scope.list[1] = $scope.realTimeTranscations.slice(10, 20);
                $scope.list[2] = $scope.realTimeTranscations.slice(20, 30);
                $scope.list[3] = $scope.realTimeTranscations.slice(30, 40);
                $scope.list[4] = $scope.realTimeTranscations.slice(40, 49);
            })
        }
        
        function renderOneZodiacTailNum() {
            $scope.stats = {
                oneZodiacTransactions: 0,
                oneZodiacStakes: 0,
                tailNumTransactions: 0,
                tailNumStakes: 0
            };
            
            // one zodiac section
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'one_zodiac', $scope.selectedPan.name).then(function (data) {
                
                for (var i = 0; i < data.length; i++) {
                    data[i].zodiacName = $scope.zodiacNames[data[i].lotteryMarkSixType];
                    $scope.stats.oneZodiacTransactions += data[i].transactions;
                    $scope.stats.oneZodiacStakes += data[i].stakes;
                }
                
                $scope.oneZodiacList = [];
                $scope.oneZodiacList[0] = data.slice(0, 3);
                $scope.oneZodiacList[1] = data.slice(3, 6);
                $scope.oneZodiacList[2] = data.slice(6, 9);
                $scope.oneZodiacList[3] = data.slice(9, 12);
            });
            
            // tail number section
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'tail_num', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.tailNumTransactions += data[i].transactions;
                    $scope.stats.tailNumStakes += data[i].stakes;
                }
                
                $scope.tailNumList = [];
                $scope.tailNumList[0] = data.slice(1, 3);   // start from 1
                $scope.tailNumList[1] = data.slice(3, 5);
                $scope.tailNumList[2] = data.slice(5, 7);
                $scope.tailNumList[3] = data.slice(7, 9);
                $scope.tailNumList[4] = data.slice(9, 10);
                $scope.tailNumList[4].push(data[0]);
            });
        }
        
        function renderJoint() {
            $scope.stats = {
                joint3AllTransactions: 0,
                joint3AllStakes: 0,
                joint32Transactions: 0,
                joint32Stakes: 0,
                joint2AllTransactions: 0,
                joint2AllStakes: 0,
                joint2SpecialTransactions: 0,
                joint2SpecialStakes: 0,
                jointSpecialTransactions: 0,
                jointSpecialStakes: 0,
                
                joint3AllOdds: 0,
                joint32Odds: 0,
                joint2AllOdds: 0,
                joint2SpecialOdds: 0,
                jointSpecialOdds: 0
            };
            
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_all', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint3AllTransactions += data[i].transactions;
                    $scope.stats.joint3AllStakes += data[i].stakes;
                    
                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 5)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0,2) + ', ' + allNum.substring(2,4) + ', ' + allNum.substring(4,6);
                }
                
                $scope.joint3AllList = data;
                
                //FIXME get odds by sending requests
                $scope.stats.joint3AllOdds = data[0].odds;
            });
            
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_2', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint32Transactions += data[i].transactions;
                    $scope.stats.joint32Stakes += data[i].stakes;
                    
                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 5)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0,2) + ', ' + allNum.substring(2,4) + ', ' + allNum.substring(4,6);
                }
                
                $scope.joint32List = data;
                $scope.stats.joint32Odds = data[0].odds;
            });
            
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_all', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint2AllTransactions += data[i].transactions;
                    $scope.stats.joint2AllStakes += data[i].stakes;
                    
                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0,2) + ', ' + allNum.substring(2,4);
                }
                
                $scope.joint2AllList = data;
                $scope.stats.joint2AllOdds = data[0].odds;
            });
            
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_special', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint2SpecialTransactions += data[i].transactions;
                    $scope.stats.joint2SpecialStakes += data[i].stakes;
                    
                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0,2) + ', ' + allNum.substring(2,4);
                }
                
                $scope.joint2SpecialList = data;
                $scope.stats.joint2SpecialOdds = data[0].odds;
            });
            
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_special', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.jointSpecialTransactions += data[i].transactions;
                    $scope.stats.jointSpecialStakes += data[i].stakes;
                    
                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0,2) + ', ' + allNum.substring(2,4);
                }
                
                $scope.jointSpecialList = data;
                $scope.stats.jointSpecialOdds = data[0].odds;
            });
            
        }

        $scope.zhengSpecificGoto = function(page) {
            var ele = $(event.target);
            if (ele.siblings().length == 0) {
                ele = ele.parent();
            }
            ele.siblings().removeClass('real-time-tab-active');
            ele.addClass('real-time-tab-active');

            renderZhengSpecific(page);
        }

        $scope.goto = function (page, panlei) {
            $scope.page = 'includes/realtime_' + page + '.html';
            $scope.pageType = page;
            
            var ele = $(event.target);
            if (ele.siblings().length == 0) {
                ele = ele.parent();
            }
            ele.siblings().removeClass('real-time-tab-active');
            ele.addClass('real-time-tab-active');
            
            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                $scope.lotteryMarkSixInfo = data;
                return realtimeService.getRealTimeTransactionTotalCount(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue);
            }).then(function (data) {
                $scope.transactionTotalCount = data;
                
                // zheng specific
                $scope.transactionTotalCount.ZHENG_SPECIFIC = 0;
                for (var i = 1; i <= 6; i++) {
                    $scope.transactionTotalCount.ZHENG_SPECIFIC += eval('$scope.transactionTotalCount.ZHENG_SPECIFIC_' + i);
                }
                
                // one zodiac and tail num
                var tmpSum = 0;
                tmpSum += $scope.transactionTotalCount.ONE_ZODIAC;
                tmpSum += $scope.transactionTotalCount.TAIL_NUM;
                $scope.transactionTotalCount.ONE_ZODIAC_TAIL_NUM = tmpSum;
                
                // joint
                tmpSum = 0;
                tmpSum += $scope.transactionTotalCount.JOINT_2_ALL;
                tmpSum += $scope.transactionTotalCount.JOINT_2_SPECIAL;
                tmpSum += $scope.transactionTotalCount.JOINT_3_2;
                tmpSum += $scope.transactionTotalCount.JOINT_3_ALL;
                tmpSum += $scope.transactionTotalCount.JOINT_SPECIAL;
                $scope.transactionTotalCount.JOINT_TOTAL = tmpSum;
            });
            
            switch (page) {
                case 'special':
                    renderSpecial();
                    break;
                case 'sum_zodiac':
                    renderSumZodiac();
                    break;
                case 'zheng_ball':
                    renderZhengBall();
                    break;
                case 'zheng_specific':
                    $scope.zhengSpecificGoto('zheng_specific_1');
                    break;
                case 'one_zodiac_tail_num':
                    renderOneZodiacTailNum();
                    break;
                case 'joint':
                    renderJoint();
                    break;
            }
        }

        $scope.goto('joint');

    }).controller('stakesDetailController', function ($rootScope, $scope, $routeParams, realtimeService) {
        if ($routeParams.number) {
            realtimeService.getStakesDetail4Special($routeParams.type, $routeParams.groupid, $routeParams.panlei, $routeParams.issue, $routeParams.number).then(function (data) {
                $scope.wagerList = data;
            });
        } else if ($routeParams.subtype) {
            realtimeService.getStakesDetailBySubType($routeParams.type, $routeParams.groupid, $routeParams.panlei, $routeParams.issue, $routeParams.subtype).then(function (data) {
                $scope.wagerList = data;
            });
        }
    })