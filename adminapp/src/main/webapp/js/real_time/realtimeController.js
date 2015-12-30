/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, $interval, realtimeService, oddsService) {
        $rootScope.menu = 1;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });

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
            $scope.goto($scope.pageType, $scope.selectedPan.name);
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

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'special', $scope.selectedPan.name).then(function (data) {
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

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'two_faces', $scope.selectedPan.name).then(function (data) {
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
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zodiac', $scope.selectedPan.name).then(function (data) {
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
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'half_wave', $scope.selectedPan.name).then(function (data) {
                $scope.halfWaveList = [];
                $scope.halfWaveList[0] = data.slice(0, 4);
                $scope.halfWaveList[1] = data.slice(4, 8);
                $scope.halfWaveList[2] = data.slice(8, 12);

                for (var i = 0; i < data.length; i++) {
                    $scope.stats.halfWaveTransactions += data[i].transactions;
                    $scope.stats.halfWaveStakes += data[i].stakes;
                }
            });
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'color', $scope.selectedPan.name).then(function (data) {
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
            $scope.stats = {
                zhengBallTransactions: 0,
                zhengBallStakes: 0
            };

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zheng_ball', $scope.selectedPan.name).then(function (data) {
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

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'zheng_1_6', $scope.selectedPan.name).then(function (data) {
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

            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                //TODO panlei
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_3_all').then(function (oddsList) {
                    $scope.stats.joint3AllOdds = oddsList[0].odds;
                });
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_3_2').then(function (oddsList) {
                    $scope.stats.joint32Odds = oddsList[0].odds;
                });
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_2_all').then(function (oddsList) {
                    $scope.stats.joint2AllOdds = oddsList[0].odds;
                });
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_2_special').then(function (oddsList) {
                    $scope.stats.joint2SpecialOdds = oddsList[0].odds;
                });
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_special').then(function (oddsList) {
                    $scope.stats.jointSpecialOdds = oddsList[0].odds;
                });
            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_all', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint3AllTransactions += data[i].transactions;
                    $scope.stats.joint3AllStakes += data[i].stakes;

                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 5)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0, 2) + ', ' + allNum.substring(2, 4) + ', ' + allNum.substring(4, 6);
                }

                $scope.joint3AllList = data;
            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_3_2', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint32Transactions += data[i].transactions;
                    $scope.stats.joint32Stakes += data[i].stakes;

                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 5)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0, 2) + ', ' + allNum.substring(2, 4) + ', ' + allNum.substring(4, 6);
                }

                $scope.joint32List = data;
            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_all', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint2AllTransactions += data[i].transactions;
                    $scope.stats.joint2AllStakes += data[i].stakes;

                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0, 2) + ', ' + allNum.substring(2, 4);
                }

                $scope.joint2AllList = data;
            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_2_special', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.joint2SpecialTransactions += data[i].transactions;
                    $scope.stats.joint2SpecialStakes += data[i].stakes;

                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0, 2) + ', ' + allNum.substring(2, 4);
                }

                $scope.joint2SpecialList = data;
            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_special', $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.jointSpecialTransactions += data[i].transactions;
                    $scope.stats.jointSpecialStakes += data[i].stakes;

                    // build string for wager numbers
                    var allNum = new String(data[i].number);
                    if (allNum.length == 3)
                        allNum = '0' + allNum;
                    data[i].numStr = allNum.substring(0, 2) + ', ' + allNum.substring(2, 4);
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
                realtimeService.getRealTimeTransactionNotTop(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.topNum).then(function (data) {
                    $scope.allTopStats = data;
                });
                return;
            }

            $scope.isTopPage = false;
            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                //TODO panlei
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, page).then(function (oddsList) {
                    $scope.notOdds = oddsList[0];
                });
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

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], page, $scope.selectedPan.name).then(function (data) {
                for (var i = 0; i < data.length; i++) {
                    $scope.stats.totalTransactions += data[i].transactions;
                    $scope.stats.totalRemarks += data[i].stakes;   //TODO remark
                }

                $scope.statList = data;
            });
        }

        function renderJointZodiac() {
            $scope.notNameMap = {5: '五', 6: '六', 7: '七', 8: '八', 9: '九', 10: '十', 11: '十一', 12: '十二'};

            $scope.odds = {
                jointZodiacPing: [],
                jointZodiacZheng: []
            }

            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                //TODO panlei, fix odds
                var panlei = $scope.selectedPan.name == 'ALL' ? 'A' : $scope.selectedPan.name;
                oddsService.getOddsList(data.issue, sessionStorage['pgroupid'], panlei, 'joint_zodiac_ping').then(function (oddsList) {
                    $scope.odds.jointZodiacPing[0] = oddsList.slice(0, 3);
                    $scope.odds.jointZodiacPing[1] = oddsList.slice(3, 6);
                    $scope.odds.jointZodiacPing[2] = oddsList.slice(6, 9);
                });

            });

            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_zodiac_ping', $scope.selectedPan.name).then(function (pingList) {

                realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'joint_zodiac_zheng', $scope.selectedPan.name).then(function (zhengList) {
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

        function renderAll() {
            $scope.allStatsTotalTransactions = 0;
            $scope.allStatsTotalStakes = 0;

            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                realtimeService.getRealTimeTransactionAllStats(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue).then(function (data) {
                    for (var i = 0; i < data.length; i++) {
                        $scope.allStatsTotalTransactions += data[i].transactions;
                        $scope.allStatsTotalStakes += data[i].stakes;
                    }

                    $scope.allStats = data;
                });
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

        $scope.goto = function (page, panlei) {
            $scope.page = 'includes/realtime_' + page + '.html';
            $scope.pageType = page;

            if (event != undefined) {
                var ele = $(event.target);
                if (ele.siblings().length == 0) {
                    ele = ele.parent();
                }
                ele.siblings().removeClass('real-time-tab-active');
                ele.addClass('real-time-tab-active');
            }

            realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
                $scope.lotteryMarkSixInfo = data;
                return realtimeService.getRealTimeTransactionTotalCount(sessionStorage['pgroupid'], $scope.selectedPan.name, $scope.lotteryMarkSixInfo.issue);
            }).then(function (data) {
                $scope.transactionTotalCount = data;

                //TODO simplify this
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
                case 'all':
                    renderAll();
                    break;
            }
        }

        $scope.goto('special');

    }).controller('stakesDetailController', function ($rootScope, $scope, $routeParams, realtimeService) {
    realtimeService.getStakesDetail(
        $routeParams.type,
        $routeParams.groupid,
        $routeParams.panlei,
        $routeParams.issue,
        $routeParams.subtype,
        $routeParams.number,
        $routeParams.content).then(function (data) {
        $scope.wagerList = data;
    });
})