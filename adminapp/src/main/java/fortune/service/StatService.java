package fortune.service;

import com.google.common.collect.Lists;
import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private UserService userService;

    @Transactional
    public List<LotteryMarkSixGroupStat> getLotteryMarkSixStat(String groupid, int from, int count) {
        Utils.logger.info("get lottery mark six stat for group id {} from {}, count {}", groupid, from, count);
        return statDao.getLotteryMarkSixStat(groupid, from, count);
    }

    @Transactional
    public void saveLotteryMarkSixStat(LotteryMarkSixGroupStat stat) {
        Utils.logger.info("save lottery mark six group stat", stat);
        statDao.saveLotteryMarkSixStat(stat);
    }

    @Transactional
    public List<RealtimeStat> getRealTimeTransactionResult(String groupid) {
        Utils.logger.info("get real time transaction result of group id {}", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();
        LinkedHashMap<String, RealtimeStat> realTimeStatHashMap4Type = new LinkedHashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid);

        HashMap<Integer, Double> oddsMap4Special = new HashMap<>();
        HashMap<String, Double> oddsMap4Type = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            switch (odds.getLotteryMarkSixType()) {
                case SPECIAL:
                    oddsMap4Special.put(odds.getLotteryBallNumber(), odds.getOdds());
                    break;
                case ZODIAC_SHU:
                case ZODIAC_NIU:
                case ZODIAC_HU:
                case ZODIAC_TU:
                case ZODIAC_LONG:
                case ZODIAC_SHE:
                case ZODIAC_MA:
                case ZODIAC_YANG:
                case ZODIAC_HOU:
                case ZODIAC_JI:
                case ZODIAC_GOU:
                case ZODIAC_ZHU:
                case RED:
                case BLUE:
                case GREEN:
                case JIAQIN:
                case YESHOU:
                case WAVE_RED_SHUANG:
                case WAVE_RED_DAN:
                case WAVE_RED_DA:
                case WAVE_RED_XIAO:
                case WAVE_BLUE_SHUANG:
                case WAVE_BLUE_DAN:
                case WAVE_BLUE_DA:
                case WAVE_BLUE_XIAO:
                case WAVE_GREEN_SHUANG:
                case WAVE_GREEN_DAN:
                case WAVE_GREEN_DA:
                case WAVE_GREEN_XIAO:
                case SPECIAL_WEIDA:
                case SPECIAL_WEIXIAO:
                case DA:
                case ZHONG:
                case XIAO:
                case SPECIAL_DAN:
                case SPECIAL_SHUANG:
                case SPECIAL_DA:
                case SPECIAL_XIAO:
                case SPECIAL_HEDAN:
                case SPECIAL_HESHUANG:
                case SPECIAL_HEDA:
                case SPECIAL_HEXIAO:
                case SPECIAL_HEWEIXIAO:
                case SPECIAL_HEWEIDA:
                    oddsMap4Type.put(odds.getLotteryMarkSixType().getType(), odds.getOdds());
                    break;
            }
        }

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            switch (wager.getLotteryMarkSixType()) {
                case SPECIAL:
                    List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
                    for (LotteryMarkSixWagerStub stub : wagerStubList) {
                        if (realTimeStatHashMap4Number.containsKey(stub.getNumber())) {
                            realTimeStatHashMap4Number.get(stub.getNumber()).addStakes(stub.getStakes());
                            realTimeStatHashMap4Number.get(stub.getNumber()).addTransactions(1);
                        } else {
                            RealtimeStat realtimeStat = new RealtimeStat();
                            realtimeStat.setGroupId(groupid);
                            realtimeStat.setNumber(stub.getNumber());
                            realtimeStat.setBalance(0);
                            realtimeStat.setStakes(stub.getStakes());
                            realtimeStat.setOdds(oddsMap4Special.get(stub.getNumber()));
                            realtimeStat.setTransactions(1);
                            realTimeStatHashMap4Number.put(stub.getNumber(), realtimeStat);
                        }
                    }
                    break;
                case ZODIAC_SHU:
                case ZODIAC_NIU:
                case ZODIAC_HU:
                case ZODIAC_TU:
                case ZODIAC_LONG:
                case ZODIAC_SHE:
                case ZODIAC_MA:
                case ZODIAC_YANG:
                case ZODIAC_HOU:
                case ZODIAC_JI:
                case ZODIAC_GOU:
                case ZODIAC_ZHU:
                case RED:
                case BLUE:
                case GREEN:
                case JIAQIN:
                case YESHOU:
                case WAVE_RED_SHUANG:
                case WAVE_RED_DAN:
                case WAVE_RED_DA:
                case WAVE_RED_XIAO:
                case WAVE_BLUE_SHUANG:
                case WAVE_BLUE_DAN:
                case WAVE_BLUE_DA:
                case WAVE_BLUE_XIAO:
                case WAVE_GREEN_SHUANG:
                case WAVE_GREEN_DAN:
                case WAVE_GREEN_DA:
                case WAVE_GREEN_XIAO:
                case SPECIAL_WEIDA:
                case SPECIAL_WEIXIAO:
                case DA:
                case ZHONG:
                case XIAO:
                case SPECIAL_DAN:
                case SPECIAL_SHUANG:
                case SPECIAL_DA:
                case SPECIAL_XIAO:
                case SPECIAL_HEDAN:
                case SPECIAL_HESHUANG:
                case SPECIAL_HEDA:
                case SPECIAL_HEXIAO:
                case SPECIAL_HEWEIXIAO:
                case SPECIAL_HEWEIDA:
                    calculateRealTimeStat(wager, realTimeStatHashMap4Type, groupid, oddsMap4Type);
                    break;
            }
        }
//        特码缺省调整
        for (int number = 1; number <= 49; number++) {
            if (!realTimeStatHashMap4Number.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap4Special.get(number));
                realtimeStat.setTransactions(0);
                realTimeStatHashMap4Number.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4Number.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
//        其他种类缺省调整
        LotteryMarkSixType.getRealTimeSpecialTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        LotteryMarkSixType.getRealTimeAnimalTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        LotteryMarkSixType.getRealTimeWaveTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        LotteryMarkSixType.getRealTimeSpecialTailTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        LotteryMarkSixType.getRealTimeColorTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        LotteryMarkSixType.getRealTimeBigOrSmallTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        realtimeStatList.addAll(Lists.newArrayList(realTimeStatHashMap4Type.values().iterator()));
        return realtimeStatList;
    }

    private void calculateRealTimeStat(LotteryMarkSixWager wager, HashMap<String, RealtimeStat> realtimeStatHashMap, String groupid, HashMap<String, Double> oddsMap) {
        for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
            if (realtimeStatHashMap.containsKey(stub.getLotteryMarkSixType().getType())) {
                realtimeStatHashMap.get(stub.getLotteryMarkSixType().getType()).addStakes(stub.getStakes());
                realtimeStatHashMap.get(stub.getLotteryMarkSixType().getType()).addTransactions(1);
            } else {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setLotteryMarkSixType(stub.getLotteryMarkSixType().getType());
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(stub.getStakes());
                realtimeStat.setOdds(oddsMap.get(wager.getLotteryMarkSixType().getType()));
                realtimeStat.setTransactions(1);
                realtimeStatHashMap.put(stub.getLotteryMarkSixType().getType(), realtimeStat);
            }
        }
    }

    private void addToRealTimeStatMap(HashMap<String, Double> oddsMap4Type, LotteryMarkSixType type, String groupid, LinkedHashMap<String, RealtimeStat> realTimeStatHashMap4Type) {
        double odds = 0;
        if (oddsMap4Type.containsKey(type.getType())) {
            odds = oddsMap4Type.get(type.getType());
        }
        RealtimeStat realtimeStat = new RealtimeStat();
        realtimeStat.setGroupId(groupid);
        realtimeStat.setLotteryMarkSixType(type.getType());
        realtimeStat.setBalance(0);
        realtimeStat.setStakes(0);
        realtimeStat.setOdds(odds);
        realtimeStat.setTransactions(0);
        realTimeStatHashMap4Type.put(type.getType(), realtimeStat);
    }

    @Transactional
    public List<RealTimeWager> getStakeDetail4Special(String groupId, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfSpecialNumber(groupId, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei("A盘");
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", LotteryMarkSixType.SPECIAL.getType(), number));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds4LotteryIssue(issue, groupId, number).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }
}
