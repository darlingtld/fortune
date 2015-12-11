/**
 * Created by lingda on 2015/11/9.
 */
angular.module('AdminApp')
    .controller('realtimeController', function ($rootScope, $scope, realtimeService) {
        $rootScope.menu = 1;
        realtimeService.getNextLotteryMarkSixInfo().then(function (data) {
            $scope.lotteryMarkSixInfo = data;
        });

        $scope.page = 'includes/realtime_special.html';
        function renderSpecial() {
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'special').then(function (data) {
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
            realtimeService.getRealTimeTransaction(sessionStorage['pgroupid'], 'sum_zodiac').then(function (data) {
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

        $scope.goto = function (page) {
            $scope.page = 'includes/realtime_' + page + '.html';
            var ele = $(event.target);
            if (ele.siblings().length == 0) {
                ele = ele.parent();
            }
            ele.siblings().removeClass('real-time-tab-active');
            ele.addClass('real-time-tab-active');
            switch (page) {
                case 'special':
                    renderSpecial();
                    break;
                case 'sum_zodiac':
                    renderSumZodiac();
                    break;
            }

        }

        $scope.goto('special');


    }).controller('stakesDetailController', function ($rootScope, $scope, $routeParams, realtimeService) {
        realtimeService.getStakesDetail4Special($routeParams.type, $routeParams.groupid, $routeParams.issue, $routeParams.number).then(function (data) {
            $scope.wagerList = data;
        });
    })