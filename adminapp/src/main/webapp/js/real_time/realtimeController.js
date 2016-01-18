/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, $interval, realtimeService, oddsService) {
        $rootScope.menu = 1;
        
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
            $scope.nextLotteryIssue = data.issue;
            $scope.nextLotteryDate = data.date;
            
            realtimeService.getCurrentGroup(sessionStorage['pgroupid']).then(function (data) {
                $scope.currentGroup = data;
                
                realtimeService.getAllLotteyMarkSix().then(function(data) {
                    $scope.issueList = [];
                    for (var i = data.length - 1; i >= 0; i --) {
                        // 只显示该代理商激活后的开奖期数
                        if (data[i].issue > $scope.currentGroup.activeAfterIssue) {
                            $scope.issueList.push(data[i].issue);
                        }
                    }
                    if ($scope.issueList.length > 0) {
                        $scope.firstLotteryIssue = $scope.issueList[0];
                        $scope.isGroupActive = true;
                        buildRealtimePage();
                    }
                });
            });
        });
        
        function buildRealtimePage() {
            $scope.gotoNextLottery = function(issue) {
                if (issue == $scope.issueList[$scope.issueList.length - 1]) {
                    $scope.lotteryMarkSixInfo.issue = $scope.nextLotteryIssue;
                    $scope.lotteryMarkSixInfo.date = $scope.nextLotteryDate;
                    $scope.goto($scope.pageType);
                } else {
                    var nextIssue = $scope.issueList[$scope.issueList.indexOf(issue) + 1];
                    realtimeService.getLotteryMarkSixInfo(nextIssue).then(function (data) {
                        $scope.lotteryMarkSixInfo.issue = data.issue;
                        $scope.lotteryMarkSixInfo.date = data.timestamp;
                        $scope.goto($scope.pageType);
                    });
                }
            }
            
            $scope.gotoLastLottery = function(issue) {
                var lastIssue = 
                    issue == $scope.nextLotteryIssue ? $scope.issueList[$scope.issueList.length - 1] : $scope.issueList[$scope.issueList.indexOf(issue) - 1];
                realtimeService.getLotteryMarkSixInfo(lastIssue).then(function (data) {
                    $scope.lotteryMarkSixInfo.issue = data.issue;
                    $scope.lotteryMarkSixInfo.date = data.timestamp;
                    $scope.goto($scope.pageType);
                });
            }
            
            $scope.zodiacNames = {
                    "zodiac_shu": "鼠",
                    "zodiac_niu": "牛",
                    "zodiac_hu": "虎",
                    "zodiac_tu": "兔",
                    "zodiac_long": "龙",
                    "zodiac_she": "蛇",
                    "zodiac_ma": "马",
                    "zodiac_yang": "羊",
                    "zodiac_hou": "猴",
                    "zodiac_ji": "鸡",
                    "zodiac_gou": "狗",
                    "zodiac_zhu": "猪"
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

            $scope.freqs = [{name: '60秒', value: '60'}, {name: '30秒', value: '30'}, {name: '15秒', value: '15'}];
            $scope.selectedFreq = $scope.freqs[0];
            $scope.autoRefresh;
            var promise;
            $scope.enableAutoRefresh = function () {
                if ($scope.autoRefresh == true) {
                    if (promise != null) {
                        $interval.cancel(promise);
                    }
                    promise = $interval($scope.getRealtimeTransactions, parseInt($scope.selectedFreq.value) * 1000);
                } else {
                    $interval.cancel(promise);
                }
            };

            $scope.getRealtimeTransactions = function () {
                $scope.goto($scope.pageType);
            }

            $scope.page = 'includes/realtime_special.html';
            
            function renderSpecial() {
                $scope.stats = {
                    specialTransactions: 0,
                    specialStakes: 0,
                    zodiacTransactions: 0,
                    zodiacStakes: 0,
                    halfWaveTransactions: 0,
                    halfWaveStakes: 0,
                    colorTransactions: 0,
                    colorStakes: 0
                };

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'special', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.specialTransactions = data;

                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.specialTransactions += data[i].transactions;
                        $scope.stats.specialStakes += data[i].stakes;
                    }

                    $scope.specialList = [];
                    $scope.specialList[0] = data.slice(0, 10);
                    $scope.specialList[1] = data.slice(10, 20);
                    $scope.specialList[2] = data.slice(20, 30);
                    $scope.specialList[3] = data.slice(30, 40);
                    $scope.specialList[4] = data.slice(40, 49);
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'two_faces', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.twoFacesNameMap = twoFaceTypeMap;
                    $scope.twoFacesMap = {};
                    for (var i = 0; i < data.length; i++) {
                        $scope.twoFacesMap[data[i].lotteryMarkSixType] = data[i];
                    }

                    $scope.twoFacesKeyList = [];
                    $scope.twoFacesKeyList[0] = ['DAN', 'SHUANG'];
                    $scope.twoFacesKeyList[1] = ['DA', 'XIAO'];
                    $scope.twoFacesKeyList[2] = ['HEDAN', 'HESHUANG'];
                    $scope.twoFacesKeyList[3] = ['HEDA', 'HEXIAO'];
                    $scope.twoFacesKeyList[4] = ['HEWEIDA', 'HEWEIXIAO'];

                    $scope.animalTypeKeyList = ['JIAQIN', 'YESHOU'];
                    $scope.weidaKeyList = ['WEIDA', 'WEIXIAO'];
                });
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zodiac', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.zodiacList = [];
                    $scope.zodiacList[0] = data.slice(0, 3);
                    $scope.zodiacList[1] = data.slice(3, 6);
                    $scope.zodiacList[2] = data.slice(6, 9);
                    $scope.zodiacList[3] = data.slice(9, 12);

                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.zodiacTransactions += data[i].transactions;
                        $scope.stats.zodiacStakes += data[i].stakes;
                    }
                });
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'half_wave', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.halfWaveList = [];
                    $scope.halfWaveList[0] = data.slice(0, 4);
                    $scope.halfWaveList[1] = data.slice(4, 8);
                    $scope.halfWaveList[2] = data.slice(8, 12);

                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.halfWaveTransactions += data[i].transactions;
                        $scope.stats.halfWaveStakes += data[i].stakes;
                    }
                });
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'color', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.colorList = data;

                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.colorTransactions += data[i].transactions;
                        $scope.stats.colorStakes += data[i].stakes;
                    }
                });

                // Mock data
                $scope.bsTypeList = ['大', '中', '小'];
            }

            function renderSumZodiac() {
                $scope.getSumZodiacName = function(number) {
                    return parseInt(number / 10);
                };
                
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'sum_zodiac', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.sumZodiacList = [];
                    $scope.sumZodiacList[0] = [];
                    $scope.sumZodiacList[0].push(data.slice(0, 2).reverse());
                    $scope.sumZodiacList[0].push(data.slice(2, 4).reverse());
                    $scope.sumZodiacList[0].push(data.slice(4, 6).reverse());
                    $scope.sumZodiacList[0].push(data.slice(6, 8).reverse());
                    
                    $scope.sumZodiacList[1] = [];
                    $scope.sumZodiacList[1].push(data.slice(8, 10).reverse());
                    $scope.sumZodiacList[1].push(data.slice(10, 12).reverse());
                    $scope.sumZodiacList[1].push(data.slice(12, 14).reverse());
                    $scope.sumZodiacList[1].push(data.slice(14, 16).reverse());
                    
                    $scope.sumZodiacList[2] = [];
                    $scope.sumZodiacList[2].push(data.slice(16, 18).reverse());
                    $scope.sumZodiacList[2].push(data.slice(18, 20).reverse());
                    $scope.sumZodiacList[2].push(data.slice(20, 22).reverse());
                });
            }

            function renderZhengBall() {
                $scope.stats = {
                    zhengBallTransactions: 0,
                    zhengBallStakes: 0
                };

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zheng_ball', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.list = [];
                    $scope.list[0] = data.slice(0, 10);
                    $scope.list[1] = data.slice(10, 20);
                    $scope.list[2] = data.slice(20, 30);
                    $scope.list[3] = data.slice(30, 40);
                    $scope.list[4] = data.slice(40, 49);

                    for (var i = 0; i < $scope.list.length; i++) {
                        for (var j = 0; j < $scope.list[i].length; j++) {
                            $scope.stats.zhengBallTransactions += $scope.list[i][j].transactions;
                            $scope.stats.zhengBallStakes += $scope.list[i][j].stakes;
                        }
                    }
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zheng_1_6', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    var map = {};
                    for (var i = 0; i < 54; i++) {
                        var key = data[i].number + '_' + data[i].lotteryMarkSixType;
                        map[key] = data[i];
                    }
                    $scope.zheng16Map = map;
                    $scope.zheng16Name = ['一', '二', '三', '四', '五', '六'];
                });
            }

            function renderZhengSpecific(page) {
                $scope.zhengSpecificType = page;
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], page, $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
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
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'one_zodiac', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {

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
                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'tail_num', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.tailNumTransactions += data[i].transactions;
                        $scope.stats.tailNumStakes += data[i].stakes;
                    }

                    $scope.tailNumList = [];
                    $scope.tailNumList[0] = data.slice(0, 2);
                    $scope.tailNumList[1] = data.slice(2, 4);
                    $scope.tailNumList[2] = data.slice(4, 6);
                    $scope.tailNumList[3] = data.slice(6, 8);
                    $scope.tailNumList[4] = data.slice(8, 10);
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

                //TODO panlei
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_3_all').then(function (oddsList) {
                    $scope.stats.joint3AllOdds = oddsList[0].odds;
                });
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_3_2').then(function (oddsList) {
                    $scope.stats.joint32Odds = oddsList[0].odds;
                });
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_2_all').then(function (oddsList) {
                    $scope.stats.joint2AllOdds = oddsList[0].odds;
                });
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_2_special').then(function (oddsList) {
                    $scope.stats.joint2SpecialOdds = oddsList[0].odds;
                });
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_special').then(function (oddsList) {
                    $scope.stats.jointSpecialOdds = oddsList[0].odds;
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_all', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.joint3AllTransactions += data[i].transactions;
                        $scope.stats.joint3AllStakes += data[i].stakes;
                    }

                    $scope.joint3AllList = data;
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_2', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.joint32Transactions += data[i].transactions;
                        $scope.stats.joint32Stakes += data[i].stakes;
                    }

                    $scope.joint32List = data;
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_all', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.joint2AllTransactions += data[i].transactions;
                        $scope.stats.joint2AllStakes += data[i].stakes;
                    }

                    $scope.joint2AllList = data;
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_special', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.joint2SpecialTransactions += data[i].transactions;
                        $scope.stats.joint2SpecialStakes += data[i].stakes;
                    }

                    $scope.joint2SpecialList = data;
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_special', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.jointSpecialTransactions += data[i].transactions;
                        $scope.stats.jointSpecialStakes += data[i].stakes;
                    }

                    $scope.jointSpecialList = data;
                });
            }

            function renderNot(page) {
                $scope.topNum = 20;
                $scope.notType = page;
                $scope.notNameMap = {5: '五', 6: '六', 7: '七', 8: '八', 9: '九', 10: '十', 11: '十一', 12: '十二'};
                $scope.notTypeName = $scope.notNameMap[parseInt(page.split('_')[1])] + '不中';

                if (page == 'not_top') {
                    $scope.isTopPage = true;
                    realtimeService.getRealTimeTransactionNotTop(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue, $scope.topNum).then(function (data) {
                        $scope.allTopStats = data;
                    });
                    return;
                }

                $scope.isTopPage = false;
                //TODO panlei
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, page).then(function (oddsList) {
                    $scope.notOdds = oddsList[0];
                });

                // odds
                var tmpNumList = [];
                for (var i = 1; i <= 49; i++) {
                    tmpNumList.push(i);
                }
                $scope.numList = [];
                $scope.numList[0] = tmpNumList.slice(0, 10);
                $scope.numList[1] = tmpNumList.slice(10, 20);
                $scope.numList[2] = tmpNumList.slice(20, 30);
                $scope.numList[3] = tmpNumList.slice(30, 40);
                $scope.numList[4] = tmpNumList.slice(40, 49);

                // transactions
                $scope.stats = {
                    totalTransactions: 0,
                    totalRemarks: 0
                };

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], page, $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.stats.totalTransactions += data[i].transactions;
                        $scope.stats.totalRemarks += data[i].stakes;   //TODO remark
                    }

                    $scope.statList = data;
                });
            }

            function renderJointZodiac() {
                $scope.odds = {
                    jointZodiacPing: [],
                    jointZodiacZheng: []
                }

                //TODO panlei, fix odds
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_zodiac_zheng').then(function (zhengList) {
                    $scope.zhengOdds = zhengList[0].odds;
                    
                    oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, 'joint_zodiac_ping').then(function (oddsList) {
                        //FIXME Hard Code
                        for (var i = 0; i < oddsList.length; i ++) {
                            if (oddsList[i].lotteryBallType == 'ZODIAC_HOU') {
                                oddsList[i].odds = $scope.zhengOdds;
                            }
                        }
                        
                        $scope.odds.jointZodiacPing[0] = oddsList.slice(0, 3);
                        $scope.odds.jointZodiacPing[1] = oddsList.slice(3, 6);
                        $scope.odds.jointZodiacPing[2] = oddsList.slice(6, 9);
                        $scope.odds.jointZodiacPing[3] = oddsList.slice(9, 12);
                    });
                });

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_zodiac_ping', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (pingList) {

                    realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_zodiac_zheng', $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (zhengList) {
                        var statMap = {'二肖': [], '三肖': [], '四肖': [], '五肖': []};
                        var allList = pingList.concat(zhengList);
                        for (var i = 0; i < allList.length; i++) {
                            var stat = allList[i];
                            statMap[stat.lotteryMarkSixTypeName].push(stat);
                        }

                        var totalStakesMap = {'二肖': 0, '三肖': 0, '四肖': 0, '五肖': 0};
                        for (key in totalStakesMap) {
                            var statList = statMap[key];
                            for (var i = 0; i < statList.length; i++) {
                                totalStakesMap[key] += statList[i].stakes;
                            }
                        }

                        $scope.statMap = statMap;
                        $scope.totalStakesMap = totalStakesMap;
                    });
                });
            }
            
            function renderJointTail() {
                $scope.odds = [];
                $scope.statList = [];
                $scope.totalStakes = [];
                $scope.columnName = ['二尾中','三尾中','四尾中','二尾不中','三尾不中','四不尾中'];
                
                var typeList = ['joint_tail_2', 'joint_tail_3', 'joint_tail_4', 'joint_tail_not_2', 'joint_tail_not_3', 'joint_tail_not_4'];
                
                //TODO panlei, fix odds
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                for (var i = 0; i < typeList.length; i ++) {
                    (function(i) {
                        oddsService.getOddsList($scope.lotteryMarkSixInfo.issue, sessionStorage['pgroupid'], panlei, typeList[i]).then(function (oddsList) {
                            $scope.odds[i] = [];
                            $scope.odds[i][0] = oddsList.slice(0, 3);
                            $scope.odds[i][1] = oddsList.slice(3, 6);
                            $scope.odds[i][2] = oddsList.slice(6, 9);
                            $scope.odds[i][3] = [oddsList[9], {}, {}];
                        });
                        
                        realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], typeList[i], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (statList) {
                            $scope.totalStakes[i] = 0;
                            for (var j = 0; j < statList.length; j ++) {
                                $scope.totalStakes[i] += statList[j].stakes;
                            }
                            $scope.statList[i] = statList;
                        });
                    })(i);
                }
            }

            function renderAll() {
                $scope.allStatsTotalTransactions = 0;
                $scope.allStatsTotalStakes = 0;

                realtimeService.getRealTimeTransactionAllStats(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.allStatsTotalTransactions += data[i].transactions;
                        $scope.allStatsTotalStakes += data[i].stakes;
                    }

                    $scope.allStats = data;
                });
            }

            $scope.zhengSpecificGoto = function (page) {
                var ele = $(event.target);
                if (ele.siblings().length == 0) {
                    ele = ele.parent();
                }
                ele.siblings().removeClass('real-time-tab-active');
                ele.addClass('real-time-tab-active');

                renderZhengSpecific(page);
            }

            $scope.notGoto = function (page) {
                var ele = $(event.target);
                if (ele.siblings().length == 0) {
                    ele = ele.parent();
                }
                ele.siblings().removeClass('real-time-tab-active');
                ele.addClass('real-time-tab-active');
                renderNot(page);
            }

            $scope.goto = function (page) {
                $scope.page = 'includes/realtime/' + page + '.html';
                $scope.pageType = page;

                if (event != undefined) {
                    var ele = $(event.target);
                    if (ele.siblings().length == 0) {
                        ele = ele.parent();
                    }
                    ele.siblings().removeClass('real-time-tab-active');
                    ele.addClass('real-time-tab-active');
                }

                realtimeService.getRealTimeTransactionTotalCount(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    $scope.transactionTotalCount = data;

                    // special
                    var tmpSum = 0;
                    tmpSum += $scope.transactionTotalCount.SPECIAL;
                    tmpSum += $scope.transactionTotalCount.TWO_FACES;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_GOU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_HOU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_HU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_JI;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_LONG;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_MA;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_NIU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_SHE;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_SHU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_TU;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_YANG;
                    tmpSum += $scope.transactionTotalCount.ZODIAC_ZHU;
                    tmpSum += $scope.transactionTotalCount.WAVE_BLUE_DA;
                    tmpSum += $scope.transactionTotalCount.WAVE_BLUE_DAN;
                    tmpSum += $scope.transactionTotalCount.WAVE_BLUE_SHUANG;
                    tmpSum += $scope.transactionTotalCount.WAVE_BLUE_XIAO;
                    tmpSum += $scope.transactionTotalCount.WAVE_GREEN_DA;
                    tmpSum += $scope.transactionTotalCount.WAVE_GREEN_DAN;
                    tmpSum += $scope.transactionTotalCount.WAVE_GREEN_SHUANG;
                    tmpSum += $scope.transactionTotalCount.WAVE_GREEN_XIAO;
                    tmpSum += $scope.transactionTotalCount.WAVE_RED_DA;
                    tmpSum += $scope.transactionTotalCount.WAVE_RED_DAN;
                    tmpSum += $scope.transactionTotalCount.WAVE_RED_SHUANG;
                    tmpSum += $scope.transactionTotalCount.WAVE_RED_XIAO;
                    tmpSum += $scope.transactionTotalCount.RED;
                    tmpSum += $scope.transactionTotalCount.BLUE;
                    tmpSum += $scope.transactionTotalCount.GREEN;
                    $scope.transactionTotalCount.SPECIAL_TOTAL = tmpSum;

                    // zheng ball
                    tmpSum = 0;
                    tmpSum += $scope.transactionTotalCount.ZHENG_BALL;
                    tmpSum += $scope.transactionTotalCount.ZHENG_1_6;
                    $scope.transactionTotalCount.ZHENG_BALL_TOTAL = tmpSum;

                    // zheng specific
                    $scope.transactionTotalCount.ZHENG_SPECIFIC = 0;
                    for (var i = 1; i <= 6; i++) {
                        $scope.transactionTotalCount.ZHENG_SPECIFIC += eval('$scope.transactionTotalCount.ZHENG_SPECIFIC_' + i);
                    }

                    // one zodiac and tail num
                    tmpSum = 0;
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

                    // not
                    tmpSum = 0;
                    tmpSum += $scope.transactionTotalCount.NOT_5;
                    tmpSum += $scope.transactionTotalCount.NOT_6;
                    tmpSum += $scope.transactionTotalCount.NOT_7;
                    tmpSum += $scope.transactionTotalCount.NOT_8;
                    tmpSum += $scope.transactionTotalCount.NOT_9;
                    tmpSum += $scope.transactionTotalCount.NOT_10;
                    tmpSum += $scope.transactionTotalCount.NOT_11;
                    tmpSum += $scope.transactionTotalCount.NOT_12;
                    $scope.transactionTotalCount.NOT_TOTAL = tmpSum;

                    // joint zodiac
                    tmpSum = 0;
                    tmpSum += $scope.transactionTotalCount.JOINT_ZODIAC_ZHENG;
                    tmpSum += $scope.transactionTotalCount.JOINT_ZODIAC_PING;
                    $scope.transactionTotalCount.JOINT_ZODIAC_TOTAL = tmpSum;
                    
                    // joint tail
                    tmpSum = 0;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_2;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_3;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_4;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_NOT_2;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_NOT_3;
                    tmpSum += $scope.transactionTotalCount.JOINT_TAIL_NOT_4;
                    $scope.transactionTotalCount.JOINT_TAIL_TOTAL = tmpSum;
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
                    case 'not':
                        $scope.notGoto('not_top');
                        break;
                    case 'joint_zodiac':
                        renderJointZodiac();
                        break;
                    case 'joint_tail':
                        renderJointTail();
                        break;
                    case 'all':
                        renderAll();
                        break;
                }
            }

            $scope.goto('special');
        }

    }).controller('stakesDetailController', function ($rootScope, $scope, $routeParams, realtimeService) {
        $scope.groupId = sessionStorage['pgroupid'];
        
        if ($routeParams.userid) {
            realtimeService.getStakesDetailByUser($routeParams.userid, $routeParams.groupid, $routeParams.issue).then(function (data) {
                $scope.wagerList = data;
                $scope.userLinkEnabled = false;
            });
        } else {
            realtimeService.getStakesDetail(
                $routeParams.type,
                $routeParams.groupid,
                $routeParams.panlei,
                $routeParams.issue,
                $routeParams.subtype,
                $routeParams.number,
                $routeParams.content,
                $routeParams.isAll).then(function (data) {
                    $scope.wagerList = data;
                    $scope.userLinkEnabled = true;
            });
        }
})