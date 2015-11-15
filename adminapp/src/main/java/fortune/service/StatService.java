package fortune.service;

import com.google.common.base.Functions;
import com.google.common.collect.Lists;
import com.sun.imageio.plugins.common.ReaderUtil;
import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-11-03.
 */
@Service
public class StatService {
    @Autowired
    private StatDao statDao;

    @Autowired
    private WagerService wagerService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private OddsService oddsService;

    @Transactional
    public List<LotteryMarkSixStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Utils.logger.info("get lottery mark six stat for group id {} from {}, count {}", groupid, from, count);
        return statDao.getLotteryMarkSixStat(groupid, from, count);
    }

    @Transactional
    public void saveLotteryMarkSixStat(LotteryMarkSixStat stat) {
        Utils.logger.info("save lottery mark six", stat);
        statDao.saveLotteryMarkSixStat(stat);
    }

    @Transactional
    public List<RealtimeStat> getRealTimeTransactionResult(String groupid) {
        Utils.logger.info("get real time transaction result of group id {}", groupid);
        HashMap<Integer, RealtimeStat> realtimeStatHashMap = new HashMap<>(49);
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid);
        HashMap<Integer, Double> oddsMap = new HashMap<>(49);
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallNumber(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            // 特码统计
            if (wager.getLotteryMarkSixType().equals(LotteryMarkSixType.SPECIAL)) {
                List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
                for (LotteryMarkSixWagerStub stub : wagerStubList) {
                    if (realtimeStatHashMap.containsKey(stub.getNumber())) {
                        realtimeStatHashMap.get(stub.getNumber()).addStakes(stub.getStakes());
                        realtimeStatHashMap.get(stub.getNumber()).addTransactions(1);
                    } else {
                        RealtimeStat realtimeStat = new RealtimeStat();
                        realtimeStat.setGroupId(groupid);
                        realtimeStat.setNumber(stub.getNumber());
                        realtimeStat.setBalance(0);
                        realtimeStat.setStakes(stub.getStakes());
                        realtimeStat.setOdds(oddsMap.get(stub.getNumber()));
                        realtimeStat.setTransactions(1);
                        realtimeStatHashMap.put(stub.getNumber(), realtimeStat);
                    }
                }
            }
        }
        for (int number = 1; number <= 49; number++) {
            if (!realtimeStatHashMap.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap.get(number));
                realtimeStat.setTransactions(0);
                realtimeStatHashMap.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realtimeStatHashMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }
}
