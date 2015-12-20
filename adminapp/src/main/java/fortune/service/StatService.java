package fortune.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.*;
import org.hibernate.jpa.criteria.expression.function.AggregationFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static fortune.pojo.LotteryMarkSixType.*;

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
    public List<RealtimeStat> getRealTimeTransactionResult(LotteryMarkSixType type, String groupid, String panlei) {
        switch (type) {
            case SPECIAL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4Special(groupid, "A"));
                    list.addAll(getRealTimeTransactionResult4Special(groupid, "B"));
                    list.addAll(getRealTimeTransactionResult4Special(groupid, "C"));
                    list.addAll(getRealTimeTransactionResult4Special(groupid, "D"));
                    return list;
                } else {
                    return getRealTimeTransactionResult4Special(groupid, panlei);
                }
            case SUM_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4SumZodiac(groupid, "A"));
                    list.addAll(getRealTimeTransactionResult4SumZodiac(groupid, "B"));
                    list.addAll(getRealTimeTransactionResult4SumZodiac(groupid, "C"));
                    list.addAll(getRealTimeTransactionResult4SumZodiac(groupid, "D"));
                    return list;
                } else {
                    return getRealTimeTransactionResult4SumZodiac(groupid, panlei);
                }
            case ZHENG_BALL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4ZhengBall(groupid, "A"));
                    list.addAll(getRealTimeTransactionResult4ZhengBall(groupid, "B"));
                    list.addAll(getRealTimeTransactionResult4ZhengBall(groupid, "C"));
                    list.addAll(getRealTimeTransactionResult4ZhengBall(groupid, "D"));
                    return list;
                } else {
                    return getRealTimeTransactionResult4ZhengBall(groupid, panlei);
                }
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4ZhengSpecific(groupid, "A", type));
                    list.addAll(getRealTimeTransactionResult4ZhengSpecific(groupid, "B", type));
                    list.addAll(getRealTimeTransactionResult4ZhengSpecific(groupid, "C", type));
                    list.addAll(getRealTimeTransactionResult4ZhengSpecific(groupid, "D", type));
                    return list;
                } else {
                    return getRealTimeTransactionResult4ZhengSpecific(groupid, panlei, type);
                }
            case ONE_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4OneZodiac(groupid, "A"));
                    list.addAll(getRealTimeTransactionResult4OneZodiac(groupid, "B"));
                    list.addAll(getRealTimeTransactionResult4OneZodiac(groupid, "C"));
                    list.addAll(getRealTimeTransactionResult4OneZodiac(groupid, "D"));
                    return list;
                } else {
                    return getRealTimeTransactionResult4OneZodiac(groupid, panlei);
                }
            case TAIL_NUM:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealtimeStat> list = new ArrayList<>();
                    list.addAll(getRealTimeTransactionResult4TailNum(groupid, "A"));
                    list.addAll(getRealTimeTransactionResult4TailNum(groupid, "B"));
                    list.addAll(getRealTimeTransactionResult4TailNum(groupid, "C"));
                    list.addAll(getRealTimeTransactionResult4TailNum(groupid, "D"));
                    return list;
                } else {
                    return getRealTimeTransactionResult4TailNum(groupid, panlei);
                }
            default:
                return null;
        }
    }

    private List<RealtimeStat> getRealTimeTransactionResult4SumZodiac(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type sum zodiac", groupid);
        HashMap<Integer, RealtimeStat> realtimeStatHashMap = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);
        HashMap<Integer, Double> oddsMap4Sumzodiac = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            if (odds.getLotteryMarkSixType().equals(SUM_ZODIAC)) {
                oddsMap4Sumzodiac.putIfAbsent(odds.getLotteryBallNumber(), odds.getOdds());
            }
        }

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(SUM_ZODIAC)) {
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    if (realtimeStatHashMap.containsKey(stub.getNumber())) {
                        RealtimeStat stat = realtimeStatHashMap.get(stub.getNumber());
                        stat.addStakes(stub.getStakes());
                        stat.addTransactions(1);
                    } else {
                        RealtimeStat stat = new RealtimeStat();
                        stat.setGroupId(groupid);
                        stat.setLotteryMarkSixType(SUM_ZODIAC.getType());
                        stat.setNumber(stub.getNumber());
                        stat.setStakes(stub.getStakes());
                        stat.setTransactions(1);
                        stat.setBalance(0);
                        stat.setOdds(oddsMap4Sumzodiac.get(stub.getNumber()));
                        realtimeStatHashMap.put(stub.getNumber(), stat);
                    }
                }
            }
        }
//        fill in missing sum zodiac
        for (Integer zodiacKey : oddsMap4Sumzodiac.keySet()) {

            if (!realtimeStatHashMap.containsKey(zodiacKey)) {
                RealtimeStat stat = new RealtimeStat();
                stat.setGroupId(groupid);
                stat.setLotteryMarkSixType(SUM_ZODIAC.getType());
                stat.setNumber(zodiacKey);
                stat.setStakes(0);
                stat.setTransactions(0);
                stat.setBalance(0);
                stat.setOdds(oddsMap4Sumzodiac.get(zodiacKey));
                realtimeStatHashMap.put(zodiacKey, stat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realtimeStatHashMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
        return realtimeStatList;
    }

    //    获取特码即时注单数据
    @Transactional
    private List<RealtimeStat> getRealTimeTransactionResult4Special(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type special", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();
        LinkedHashMap<String, RealtimeStat> realTimeStatHashMap4Type = new LinkedHashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);

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

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
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
        getRealTimeSpecialTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeAnimalList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeAnimalTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeWaveTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeSpecialTailTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeColorTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        getRealTimeBigOrSmallTypeList().stream().filter(type -> !realTimeStatHashMap4Type.containsKey(type.getType())).forEach(type -> {
            addToRealTimeStatMap(oddsMap4Type, type, groupid, realTimeStatHashMap4Type);
        });
        realtimeStatList.addAll(Lists.newArrayList(realTimeStatHashMap4Type.values().iterator()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4ZhengBall(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);

        HashMap<Integer, Double> oddsMap4ZhengBall = new HashMap<>();
        HashMap<String, Double> oddsMap4Type = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            if (odds.getLotteryMarkSixType().equals(ZHENG_BALL)) {
                oddsMap4ZhengBall.put(odds.getLotteryBallNumber(), odds.getOdds());
            }
        }

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(ZHENG_BALL)) {
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
                        realtimeStat.setOdds(oddsMap4ZhengBall.get(stub.getNumber()));
                        realtimeStat.setTransactions(1);
                        realTimeStatHashMap4Number.put(stub.getNumber(), realtimeStat);
                    }
                }
            }
        }

//      缺省调整
        for (int number = 1; number <= 49; number++) {
            if (!realTimeStatHashMap4Number.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap4ZhengBall.get(number));
                realtimeStat.setTransactions(0);
                realTimeStatHashMap4Number.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4Number.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }
    
    private List<RealtimeStat> getRealTimeTransactionResult4ZhengSpecific(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);

        HashMap<Integer, Double> oddsMap4ZhengSpecial = new HashMap<>();
        
        //TODO Odds for Zheng Specific
        for (LotteryOdds odds : oddsList) {
            if (odds.getLotteryMarkSixType().equals(type)) {
                for (int i = 1; i <= 49; i ++) {
                    oddsMap4ZhengSpecial.put(i, odds.getOdds());
                }
            }
        }

        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(type)) {
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
                        realtimeStat.setOdds(oddsMap4ZhengSpecial.get(stub.getNumber()));
                        realtimeStat.setTransactions(1);
                        realTimeStatHashMap4Number.put(stub.getNumber(), realtimeStat);
                    }
                }
            }
        }

        //缺省调整
        for (int number = 1; number <= 49; number++) {
            if (!realTimeStatHashMap4Number.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap4ZhengSpecial.get(number));
                realtimeStat.setTransactions(0);
                realTimeStatHashMap4Number.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4Number.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4OneZodiac(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type one zodiac", groupid);

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);

        // e.g. <ZODIAC_SHU, 19.0>
        HashMap<LotteryMarkSixType, Double> oddsMap4OneZodiac = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            if (odds.getLotteryMarkSixType().equals(ONE_ZODIAC)) {
                oddsMap4OneZodiac.put(odds.getLotteryBallType(), odds.getOdds());
            }
        }
        
        LinkedHashMap<LotteryMarkSixType, RealtimeStat> realTimeStatHashMap4OneZodiac = new LinkedHashMap<>();
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(ONE_ZODIAC)) {
                List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
                for (LotteryMarkSixWagerStub stub : wagerStubList) {
                    LotteryMarkSixType lotteryType = stub.getLotteryMarkSixType();
                    
                    // use lottery type as key
                    if (realTimeStatHashMap4OneZodiac.containsKey(lotteryType)) {
                        realTimeStatHashMap4OneZodiac.get(lotteryType).addStakes(stub.getStakes());
                        realTimeStatHashMap4OneZodiac.get(lotteryType).addTransactions(1);
                    } else {
                        RealtimeStat realtimeStat = new RealtimeStat();
                        realtimeStat.setGroupId(groupid);
                        realtimeStat.setNumber(lotteryType.ordinal());  // work around, use number to specify order
                        realtimeStat.setBalance(0);
                        realtimeStat.setLotteryMarkSixType(lotteryType.name().toLowerCase());
                        realtimeStat.setStakes(stub.getStakes());
                        realtimeStat.setOdds(oddsMap4OneZodiac.get(lotteryType));
                        realtimeStat.setTransactions(1);
                        realTimeStatHashMap4OneZodiac.put(lotteryType, realtimeStat);
                    }
                }
            }
        }

        // default value
        getRealTimeAnimalList().stream().filter(type -> !realTimeStatHashMap4OneZodiac.containsKey(type)).forEach(type -> {
            RealtimeStat realtimeStat = new RealtimeStat();
            realtimeStat.setGroupId(groupid);
            realtimeStat.setNumber(type.ordinal());     // work around, use number to specify order
            realtimeStat.setBalance(0);
            realtimeStat.setStakes(0);
            realtimeStat.setLotteryMarkSixType(type.name().toLowerCase());
            realtimeStat.setOdds(oddsMap4OneZodiac.get(type));
            realtimeStat.setTransactions(0);
            realTimeStatHashMap4OneZodiac.put(type, realtimeStat);
        });

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4OneZodiac.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
        return realtimeStatList;
    }
    
    private List<RealtimeStat> getRealTimeTransactionResult4TailNum(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type tail number", groupid);

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue, groupid, panlei);

        HashMap<Integer, Double> oddsMap4TailNum = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            if (odds.getLotteryMarkSixType().equals(TAIL_NUM)) {
                oddsMap4TailNum.put(odds.getLotteryBallNumber(), odds.getOdds());
            }
        }
        
        LinkedHashMap<Integer, RealtimeStat> realTimeStatHashMap4TailNum = new LinkedHashMap<>();
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupid, panlei, lotteryIssue);
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(TAIL_NUM)) {
                List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
                for (LotteryMarkSixWagerStub stub : wagerStubList) {
                    if (realTimeStatHashMap4TailNum.containsKey(stub.getNumber())) {
                        realTimeStatHashMap4TailNum.get(stub.getNumber()).addStakes(stub.getStakes());
                        realTimeStatHashMap4TailNum.get(stub.getNumber()).addTransactions(1);
                    } else {
                        RealtimeStat realtimeStat = new RealtimeStat();
                        realtimeStat.setGroupId(groupid);
                        realtimeStat.setNumber(stub.getNumber());
                        realtimeStat.setBalance(0);
                        realtimeStat.setStakes(stub.getStakes());
                        realtimeStat.setOdds(oddsMap4TailNum.get(stub.getNumber()));
                        realtimeStat.setTransactions(1);
                        realtimeStat.setLotteryMarkSixType(TAIL_NUM.name());    //TODO name or type? (not used in front-end)
                        realTimeStatHashMap4TailNum.put(stub.getNumber(), realtimeStat);
                    }
                }
            }
        }

        // default value
        for (int number = 0; number <= 9; number++) {
            if (!realTimeStatHashMap4TailNum.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap4TailNum.get(number));
                realtimeStat.setTransactions(0);
                realtimeStat.setLotteryMarkSixType(TAIL_NUM.name());    //TODO name or type? (not used in front-end)
                realTimeStatHashMap4TailNum.put(number, realtimeStat);
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4TailNum.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
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
    public List<RealTimeWager> getStakeDetailByBallType(LotteryMarkSixType type, String groupId, String panlei, int issue, LotteryMarkSixType ballType) {
        switch (type) {
            case ONE_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetailByBallType(groupId, pan, issue, type, ballType));
                    }
                    return wagerList;
                } else {
                    return getStakeDetailByBallType(groupId, panlei, issue, type, ballType);
                }
        }
        return null;
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetail(LotteryMarkSixType type, String groupId, String panlei, int issue, int number) {
        switch (type) {
            case SPECIAL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Special(groupId, pan, issue, number));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4Special(groupId, panlei, issue, number);
                }
            case SUM_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4SumZodiac(groupId, pan, issue, number));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4SumZodiac(groupId, panlei, issue, number);
                }
            case ZHENG_BALL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4ZhengBall(groupId, pan, issue, number));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4ZhengBall(groupId, panlei, issue, number);
                }
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4ZhengSpecific(groupId, pan, issue, number, type));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4ZhengSpecific(groupId, panlei, issue, number, type);
                }
            case TAIL_NUM:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4TailNum(groupId, pan, issue, number));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4TailNum(groupId, panlei, issue, number);
                }
        }
        return null;
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetail4Special(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SPECIAL, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", SPECIAL.getType(), number));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds4LotteryIssue(issue, groupId, number, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4SumZodiac(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SUM_ZODIAC, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s肖 %s", SUM_ZODIAC.getType(), number / 10, number % 10 == 1 ? "中" : "不中"));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, SUM_ZODIAC, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }

    @Transactional
    public List<RealTimeWager> getStakeDetail4ZhengBall(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.ZHENG_BALL, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", ZHENG_BALL.getType(), number));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, ZHENG_BALL, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetail4ZhengSpecific(String groupId, String panlei, int issue, int number, LotteryMarkSixType type) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", type.getType(), number));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, type, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetail4TailNum(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.TAIL_NUM, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", TAIL_NUM.getType(), number));
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number == stub.getNumber()) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, TAIL_NUM, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetailByBallType(String groupId, String panlei, int issue, LotteryMarkSixType type, LotteryMarkSixType ballType) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListByBallType(type, groupId, panlei, issue, ballType);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(0);
            realTimeWager.setPanlei(wager.getPanlei());
            realTimeWager.setIssue(wager.getLotteryIssue());
            LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
            Date openTs = new Date();
            if (lotteryMarkSix != null) {
                openTs = lotteryMarkSix.getTimestamp();
            }
            realTimeWager.setOpenTs(openTs);
            realTimeWager.setWageContent(String.format("%s %s", type.getType(), ballType.getType()));
            realTimeWager.setStakes(wager.getLotteryMarkSixWagerStubList().get(0).getStakes());
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (stub.getLotteryMarkSixType().equals(ballType)) {
                    realTimeWager.setStakes(stub.getStakes());
                    break;
                }
            }
            realTimeWager.setOdds(oddsService.getOdds4LotteryIssueByBallType(issue, groupId, type.name(), wager.getPanlei(), ballType.name()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }

    @Transactional
    public JSONObject getRealTimeTransactionTotalCount(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupId, panlei, issue);
        Map<String, AtomicInteger> transactionMap = new HashMap<>();
//        transactionMap.put(LotteryMarkSixType.SPECIAL.name(), new AtomicInteger(0));
//        transactionMap.put(LotteryMarkSixType.SUM_ZODIAC.name(), new AtomicInteger(0));
//        transactionMap.put(LotteryMarkSixType.ZHENG_BALL.name(), new AtomicInteger(0));
        
        Arrays.asList(LotteryMarkSixType.values()).stream().forEach(type -> {
            transactionMap.put(type.name(), new AtomicInteger(0));
        });
        transactionMap.put("ALL", new AtomicInteger(0));

        for (LotteryMarkSixWager wager : wagerList) {
            if (transactionMap.containsKey(wager.getLotteryMarkSixType().name())) {
                transactionMap.get(wager.getLotteryMarkSixType().name()).incrementAndGet();
            }
            transactionMap.get("ALL").incrementAndGet();
        }
        JSONObject json = new JSONObject();
        json.putAll(transactionMap);
        return json;
    }
}
