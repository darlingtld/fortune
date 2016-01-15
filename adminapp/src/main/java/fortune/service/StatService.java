package fortune.service;

import static fortune.pojo.LotteryMarkSixType.COLOR;
import static fortune.pojo.LotteryMarkSixType.HALF_WAVE;
import static fortune.pojo.LotteryMarkSixType.JOINT_2_ALL;
import static fortune.pojo.LotteryMarkSixType.JOINT_2_SPECIAL;
import static fortune.pojo.LotteryMarkSixType.JOINT_3_2;
import static fortune.pojo.LotteryMarkSixType.JOINT_3_ALL;
import static fortune.pojo.LotteryMarkSixType.JOINT_SPECIAL;
import static fortune.pojo.LotteryMarkSixType.JOINT_TAIL;
import static fortune.pojo.LotteryMarkSixType.JOINT_ZODIAC;
import static fortune.pojo.LotteryMarkSixType.ONE_ZODIAC;
import static fortune.pojo.LotteryMarkSixType.SPECIAL;
import static fortune.pojo.LotteryMarkSixType.SUM_ZODIAC;
import static fortune.pojo.LotteryMarkSixType.TAIL_NUM;
import static fortune.pojo.LotteryMarkSixType.TWO_FACES;
import static fortune.pojo.LotteryMarkSixType.ZHENG_1_6;
import static fortune.pojo.LotteryMarkSixType.ZHENG_BALL;
import static fortune.pojo.LotteryMarkSixType.ZHENG_SPECIFIC;
import static fortune.pojo.LotteryMarkSixType.ZODIAC;
import static fortune.pojo.LotteryMarkSixType.getRealTimeAnimalList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeColorTypeList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeNotList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeTwoFacesList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeWaveTypeList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeZheng16TypeList;
import static fortune.pojo.LotteryMarkSixType.getWagerTypeList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AtomicDouble;

import common.Utils;
import fortune.dao.StatDao;
import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.LotteryOdds;
import fortune.pojo.RealtimeStat;
import fortune.util.CommonUtils;

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
    private OddsService oddsService;

    @Autowired
    private PredictionService predictionService;

    @Transactional
    public void saveLotteryMarkSixStat(LotteryMarkSixGroupStat stat) {
        Utils.logger.info("save lottery mark six group stat", stat);
        statDao.saveLotteryMarkSixStat(stat);
    }

    public List<List<RealtimeStat>> getRealTimeTransactionResult4NotTop(String groupid, String panlei, int issue, int top) {
        List<List<RealtimeStat>> resultList = Lists.newArrayList();

        getRealTimeNotList().stream().forEach(type -> {
            List<RealtimeStat> statList = getRealTimeTransactionResult4Not(groupid, panlei, type, issue);
            //FIXME order by 'remark' desc
            Collections.sort(statList, (stat1, stat2) -> (int) (stat2.getStakes() - stat1.getStakes()));
            int end = statList.size() > top ? top : statList.size();
            resultList.add(statList.subList(0, end));
        });

        return resultList;
    }

    @Transactional
    public List<RealtimeStat> getRealTimeTransactionResult(LotteryMarkSixType type, String groupid, String panlei, int issue) {
        switch (type) {
            case SPECIAL:
                return getRealTimeTransactionResult4Special(groupid, panlei, issue);
            case TWO_FACES:
                return getRealTimeTransactionResult4TwoFaces(groupid, panlei, issue);
            case ZODIAC:
            case HALF_WAVE:
            case COLOR:
                return getRealTimeTransactionResultByType(groupid, panlei, type, issue);
            case SUM_ZODIAC:
                return getRealTimeTransactionResult4SumZodiac(groupid, panlei, issue);
            case ZHENG_BALL:
                return getRealTimeTransactionResult4ZhengBall(groupid, panlei, issue);
            case ZHENG_1_6:
                return getRealTimeTransactionResult4Zheng1To6(groupid, panlei, issue);
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                return getRealTimeTransactionResult4ZhengSpecific(groupid, panlei, type, issue);
            case ONE_ZODIAC:
                return getRealTimeTransactionResult4OneZodiac(groupid, panlei, issue);
            case TAIL_NUM:
                return getRealTimeTransactionResult4TailNum(groupid, panlei, issue);
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
                return getRealTimeTransactionResult4Joint(groupid, panlei, type, issue);
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
                return getRealTimeTransactionResult4Not(groupid, panlei, type, issue);
            case JOINT_ZODIAC_PING:
            case JOINT_ZODIAC_ZHENG:
                return getRealTimeTransactionResult4JointZodiac(groupid, panlei, type, issue);
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                return getRealTimeTransactionResult4JointTail(groupid, panlei, type, issue);
            default:
                return new ArrayList<>();
        }
    }

    @Transactional
    private List<RealtimeStat> getRealTimeTransactionResult4Special(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type special", groupid);

        HashMap<Integer, RealtimeStat> statMap = new HashMap<>();

        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, SPECIAL.name(), pan4Odds);
        HashMap<Integer, Double> oddsMap = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallNumber(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = new ArrayList<>();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SPECIAL, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SPECIAL, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SPECIAL, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SPECIAL, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SPECIAL, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                if (statMap.containsKey(stub.getNumber())) {
                    statMap.get(stub.getNumber()).addStakes(stub.getStakes());
                    statMap.get(stub.getNumber()).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(stub.getNumber());
                    realtimeStat.setBalance(0);
                    realtimeStat.setStakes(stub.getStakes());
                    realtimeStat.setOdds(oddsMap.get(stub.getNumber()));
                    realtimeStat.setTransactions(1);
                    statMap.put(stub.getNumber(), realtimeStat);
                }
            }
        }

        // 缺省调整
        for (int number = 1; number <= 49; number++) {
            if (!statMap.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap.get(number));
                realtimeStat.setTransactions(0);
                statMap.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(statMap.values().iterator());

        predictionService.predictBalance(realtimeStatList, LotteryMarkSixType.SPECIAL, lotteryIssue);

        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4TwoFaces(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for two faces", groupid);

        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, TWO_FACES.name(), pan4Odds);
        HashMap<LotteryMarkSixType, Double> oddsMap = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallType(), odds.getOdds());
        }

        HashMap<LotteryMarkSixType, RealtimeStat> statMap = new HashMap<>();

        List<LotteryMarkSixWager> wagerList = new ArrayList<>();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TWO_FACES, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TWO_FACES, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TWO_FACES, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TWO_FACES, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TWO_FACES, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                LotteryMarkSixType lotteryType = stub.getLotteryMarkSixType();

                // use lottery type as key
                if (statMap.containsKey(lotteryType)) {
                    statMap.get(lotteryType).addStakes(stub.getStakes());
                    statMap.get(lotteryType).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(0);
                    realtimeStat.setBalance(0);
                    realtimeStat.setLotteryMarkSixType(lotteryType.name());
                    realtimeStat.setLotteryMarkSixTypeName(lotteryType.getType());
                    realtimeStat.setStakes(stub.getStakes());
                    realtimeStat.setOdds(oddsMap.get(lotteryType));
                    realtimeStat.setTransactions(1);
                    statMap.put(lotteryType, realtimeStat);
                }
            }
        }

        // default
        getRealTimeTwoFacesList().stream().filter(type -> !statMap.containsKey(type)).forEach(type -> {
            RealtimeStat realtimeStat = new RealtimeStat();
            realtimeStat.setGroupId(groupid);
            realtimeStat.setNumber(0);
            realtimeStat.setBalance(0);
            realtimeStat.setStakes(0);
            realtimeStat.setLotteryMarkSixType(type.name());
            realtimeStat.setLotteryMarkSixTypeName(type.getType());
            realtimeStat.setOdds(oddsMap.get(type));
            realtimeStat.setTransactions(0);
            statMap.put(type, realtimeStat);
        });

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(statMap.values().iterator());
        predictionService.predictBalance(realtimeStatList, LotteryMarkSixType.TWO_FACES, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    /**
     * 根据下注类型获取注单统计信息，适用于以下类型(赔率需按一级类型设置)：<br>
     * - 生肖 <br>
     * - 半波 <br>
     * - 色波 <br>
     *
     * @param groupid
     * @param panlei
     * @param lotteryType
     * @return
     */
    private List<RealtimeStat> getRealTimeTransactionResultByType(String groupid, String panlei, LotteryMarkSixType lotteryType, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for half wave", groupid);

        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;

        List<LotteryMarkSixType> allTypeList = new ArrayList<>();
        switch (lotteryType) {
            case TWO_FACES:
                allTypeList.addAll(getRealTimeTwoFacesList());
                break;
            case ZODIAC:
                allTypeList.addAll(getRealTimeAnimalList());
                break;
            case HALF_WAVE:
                allTypeList.addAll(getRealTimeWaveTypeList());
                break;
            case COLOR:
                allTypeList.addAll(getRealTimeColorTypeList());
                break;
            default:
                break;
        }

        List<RealtimeStat> realtimeStatList = new ArrayList<>();
        allTypeList.stream().forEach(type -> {
            Double odds = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds).get(0).getOdds();

            List<LotteryMarkSixWager> wagerList = new ArrayList<>();
            if (isPanleiAll(panlei)) {
                wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
                wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
                wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
                wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
            } else {
                wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
            }

            Double stakes = 0.0;
            for (LotteryMarkSixWager wager : wagerList) {
                stakes += wager.getTotalStakes();
            }

            RealtimeStat realtimeStat = new RealtimeStat();
            realtimeStat.setGroupId(groupid);
            realtimeStat.setNumber(0);
            realtimeStat.setBalance(0);
            realtimeStat.setLotteryMarkSixType(type.name());
            realtimeStat.setLotteryMarkSixTypeName(type.getType());
            realtimeStat.setStakes(stakes);
            realtimeStat.setOdds(odds);
            realtimeStat.setTransactions(wagerList.size());

            realtimeStatList.add(realtimeStat);
        });

        predictionService.predictBalance(realtimeStatList, lotteryType, lotteryIssue);

        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4SumZodiac(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type sum zodiac", groupid);
        HashMap<Integer, RealtimeStat> realtimeStatHashMap = new HashMap<>();

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, SUM_ZODIAC.name(), pan4Odds);

        HashMap<Integer, Double> oddsMap4Sumzodiac = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap4Sumzodiac.putIfAbsent(odds.getLotteryBallNumber(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SUM_ZODIAC, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SUM_ZODIAC, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SUM_ZODIAC, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SUM_ZODIAC, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(SUM_ZODIAC, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (realtimeStatHashMap.containsKey(stub.getNumber())) {
                    RealtimeStat stat = realtimeStatHashMap.get(stub.getNumber());
                    stat.addStakes(stub.getStakes());
                    stat.addTransactions(1);
                    stat.getSubLotteryMarkSixTypeList4Wager().add(wager.getSubLotteryMarkSixTypes());
                } else {
                    RealtimeStat stat = new RealtimeStat();
                    stat.setGroupId(groupid);
                    stat.setLotteryMarkSixType(SUM_ZODIAC.getType());
                    stat.setNumber(stub.getNumber());
                    stat.setStakes(stub.getStakes());
                    stat.setTransactions(1);
                    stat.setBalance(0);
                    stat.setOdds(oddsMap4Sumzodiac.get(stub.getNumber()));
                    stat.setSubLotteryMarkSixTypeList4Wager(Arrays.asList(wager.getSubLotteryMarkSixTypes()));
                    realtimeStatHashMap.put(stub.getNumber(), stat);
                }
            }
        }

        //default
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
        predictionService.predictBalance(realtimeStatList, SUM_ZODIAC, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4ZhengBall(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatMap = new HashMap<>();

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, ZHENG_BALL.name(), pan4Odds);

        HashMap<Integer, Double> oddsMap = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallNumber(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_BALL, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_BALL, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_BALL, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_BALL, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_BALL, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                if (realTimeStatMap.containsKey(stub.getNumber())) {
                    realTimeStatMap.get(stub.getNumber()).addStakes(stub.getStakes());
                    realTimeStatMap.get(stub.getNumber()).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(stub.getNumber());
                    realtimeStat.setLotteryMarkSixType(wager.getLotteryMarkSixType().name());
                    realtimeStat.setBalance(0);
                    realtimeStat.setStakes(stub.getStakes());
                    realtimeStat.setOdds(oddsMap.get(stub.getNumber()));
                    realtimeStat.setTransactions(1);
                    realTimeStatMap.put(stub.getNumber(), realtimeStat);
                }
            }
        }

//      缺省调整
        for (int number = 1; number <= 49; number++) {
            if (!realTimeStatMap.containsKey(number)) {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(number);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_BALL.name());
                realtimeStat.setOdds(oddsMap.get(number));
                realtimeStat.setTransactions(0);
                realTimeStatMap.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        predictionService.predictBalance(realtimeStatList, LotteryMarkSixType.ZHENG_BALL, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4ZhengSpecific(String groupid, String panlei, LotteryMarkSixType type, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);
        Double odds = oddsList.get(0).getOdds();

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
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
                    realtimeStat.setOdds(odds);
                    realtimeStat.setTransactions(1);
                    realTimeStatHashMap4Number.put(stub.getNumber(), realtimeStat);
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
                realtimeStat.setOdds(odds);
                realtimeStat.setTransactions(0);
                realTimeStatHashMap4Number.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4Number.values().iterator());
        predictionService.predictBalance(realtimeStatList, type, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4Zheng1To6(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng 1 - 6", groupid);

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;

        HashMap<LotteryMarkSixType, Double> oddsMap = new HashMap<>();
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, ZHENG_1_6.name(), pan4Odds);
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallType(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_1_6, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_1_6, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_1_6, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_1_6, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ZHENG_1_6, groupid, panlei, lotteryIssue, null));
        }

        HashMap<Integer, HashMap<LotteryMarkSixType, RealtimeStat>> realTimeStatMap = new HashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                if (!realTimeStatMap.containsKey(stub.getNumber())) {
                    realTimeStatMap.put(stub.getNumber(), new HashMap<>());
                }

                HashMap<LotteryMarkSixType, RealtimeStat> subMap = realTimeStatMap.get(stub.getNumber());

                LotteryMarkSixType stubType = stub.getLotteryMarkSixType();
                if (subMap.containsKey(stubType)) {
                    subMap.get(stubType).addStakes(stub.getStakes());
                    subMap.get(stubType).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(stub.getNumber());
                    realtimeStat.setBalance(0);
                    realtimeStat.setLotteryMarkSixType(stubType.getType());
                    realtimeStat.setStakes(stub.getStakes());
                    realtimeStat.setOdds(oddsMap.get(stub.getLotteryMarkSixType()));
                    realtimeStat.setTransactions(1);
                    subMap.put(stubType, realtimeStat);
                }
            }
        }

        //缺省调整
        for (int number = 1; number <= 6; number++) {
            if (!realTimeStatMap.containsKey(number)) {
                realTimeStatMap.put(number, new HashMap<LotteryMarkSixType, RealtimeStat>());
            }
            HashMap<LotteryMarkSixType, RealtimeStat> subMap = realTimeStatMap.get(number);

            final int curNum = number;
            getRealTimeZheng16TypeList().stream().filter(type -> !subMap.containsKey(type)).forEach(type -> {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(curNum);
                realtimeStat.setLotteryMarkSixType(type.getType());
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(0);
                realtimeStat.setOdds(oddsMap.get(type));
                realtimeStat.setTransactions(0);
                subMap.put(type, realtimeStat);
            });
        }

        List<RealtimeStat> realtimeStatList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            realtimeStatList.addAll(Lists.newArrayList(realTimeStatMap.get(i).values().iterator()));
        }
        predictionService.predictBalance(realtimeStatList, LotteryMarkSixType.ZHENG_1_6, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4OneZodiac(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type one zodiac", groupid);

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, ONE_ZODIAC.name(), pan4Odds);
        HashMap<LotteryMarkSixType, Double> oddsMap = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap.put(odds.getLotteryBallType(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ONE_ZODIAC, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ONE_ZODIAC, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ONE_ZODIAC, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ONE_ZODIAC, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(ONE_ZODIAC, groupid, panlei, lotteryIssue, null));
        }

        LinkedHashMap<LotteryMarkSixType, RealtimeStat> realTimeStatMap = new LinkedHashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                LotteryMarkSixType lotteryType = stub.getLotteryMarkSixType();

                if (realTimeStatMap.containsKey(lotteryType)) {
                    realTimeStatMap.get(lotteryType).addStakes(stub.getStakes());
                    realTimeStatMap.get(lotteryType).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(lotteryType.ordinal());  // work around, use number to specify order
                    realtimeStat.setBalance(0);
                    realtimeStat.setLotteryMarkSixType(lotteryType.name().toLowerCase());
                    realtimeStat.setStakes(stub.getStakes());
                    realtimeStat.setOdds(oddsMap.get(lotteryType));
                    realtimeStat.setTransactions(1);
                    realTimeStatMap.put(lotteryType, realtimeStat);
                }
            }
        }

        // default value
        getRealTimeAnimalList().stream().filter(type -> !realTimeStatMap.containsKey(type)).forEach(type -> {
            RealtimeStat realtimeStat = new RealtimeStat();
            realtimeStat.setGroupId(groupid);
            realtimeStat.setNumber(type.ordinal());     // work around, use number to specify order
            realtimeStat.setBalance(0);
            realtimeStat.setStakes(0);
            realtimeStat.setLotteryMarkSixType(type.name().toLowerCase());
            realtimeStat.setOdds(oddsMap.get(type));
            realtimeStat.setTransactions(0);
            realTimeStatMap.put(type, realtimeStat);
        });

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        predictionService.predictBalance(realtimeStatList, LotteryMarkSixType.ONE_ZODIAC, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4TailNum(String groupid, String panlei, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type tail number", groupid);

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, TAIL_NUM.name(), pan4Odds);
        HashMap<Integer, Double> oddsMap4TailNum = new HashMap<>();
        for (LotteryOdds odds : oddsList) {
            oddsMap4TailNum.put(odds.getLotteryBallNumber(), odds.getOdds());
        }

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TAIL_NUM, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TAIL_NUM, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TAIL_NUM, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TAIL_NUM, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(TAIL_NUM, groupid, panlei, lotteryIssue, null));
        }

        LinkedHashMap<Integer, RealtimeStat> realTimeStatHashMap4TailNum = new LinkedHashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
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
                    realtimeStat.setLotteryMarkSixType(TAIL_NUM.name());
                    realtimeStat.setLotteryMarkSixTypeName(TAIL_NUM.getType());
                    realTimeStatHashMap4TailNum.put(stub.getNumber(), realtimeStat);
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
                realtimeStat.setLotteryMarkSixType(TAIL_NUM.name());
                realtimeStat.setLotteryMarkSixTypeName(TAIL_NUM.getType());
                realTimeStatHashMap4TailNum.put(number, realtimeStat);
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap4TailNum.values().iterator());
        predictionService.predictBalance(realtimeStatList, TAIL_NUM, lotteryIssue);
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4Joint(String groupid, String panlei, LotteryMarkSixType type, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;

        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);
        Double odds = oddsList.get(0).getOdds();

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
        }

        LinkedHashMap<String, RealtimeStat> realTimeStatHashMap = new LinkedHashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(type)) {
                List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();

                String wagerContent = "";
                if (type.equals(JOINT_3_ALL) || type.equals(JOINT_3_2)) {
                    int num1 = wagerStubList.get(0).getNumber();
                    int num2 = wagerStubList.get(1).getNumber();
                    int num3 = wagerStubList.get(2).getNumber();
                    wagerContent = String.format("%s,%s,%s", Utils.formatNumber00(num1), Utils.formatNumber00(num2), Utils.formatNumber00(num3));

                } else if (type.equals(JOINT_2_ALL) || type.equals(JOINT_2_SPECIAL) || type.equals(JOINT_SPECIAL)) {
                    int num1 = wagerStubList.get(0).getNumber();
                    int num2 = wagerStubList.get(1).getNumber();
                    wagerContent = String.format("%s,%s", Utils.formatNumber00(num1), Utils.formatNumber00(num2));
                }

                if (realTimeStatHashMap.containsKey(wagerContent)) {
                    realTimeStatHashMap.get(wagerContent).addStakes(wager.getTotalStakes());
                    realTimeStatHashMap.get(wagerContent).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(0);
                    realtimeStat.setBalance(0);
                    realtimeStat.setStakes(wager.getTotalStakes());
                    realtimeStat.setOdds(odds);
                    realtimeStat.setTransactions(1);
                    realtimeStat.setWagerContent(wagerContent);
                    realtimeStat.setLotteryMarkSixType(type.name());
                    realtimeStat.setLotteryMarkSixTypeName(type.getType());
                    realTimeStatHashMap.put(wagerContent, realtimeStat);
                }

            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o1.getStakes() - o2.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4Not(String groupid, String panlei, LotteryMarkSixType type, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);
        Double odds = oddsList.get(0).getOdds();

        HashMap<String, RealtimeStat> realTimeStatMap = new HashMap<>();

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
        }

        for (LotteryMarkSixWager wager : wagerList) {
            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                String num = (stub.getNumber() < 10 ? "0" : "") + stub.getNumber();
                sb.append(num).append(",");
            }
            String numStr = sb.substring(0, sb.length() - 1);

            if (realTimeStatMap.containsKey(numStr)) {
                realTimeStatMap.get(numStr).addStakes(wager.getTotalStakes());
                realTimeStatMap.get(numStr).addTransactions(1);
            } else {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(0);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(wager.getTotalStakes());
                realtimeStat.setOdds(odds);
                realtimeStat.setTransactions(1);
                realtimeStat.setWagerContent(numStr);
                realtimeStat.setLotteryMarkSixType(type.name());
                realtimeStat.setLotteryMarkSixTypeName(type.getType());
                realTimeStatMap.put(numStr, realtimeStat);
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o1.getStakes() - o2.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4JointZodiac(String groupid, String panlei, LotteryMarkSixType type, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        //TODO odds for each zodiac
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);
        Double odds = oddsList.get(0).getOdds();

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
        }

        Map<Integer, LotteryMarkSixType> typeMap = new HashMap<>();
        typeMap.put(2, LotteryMarkSixType.JOINT_ZODIAC_2);
        typeMap.put(3, LotteryMarkSixType.JOINT_ZODIAC_3);
        typeMap.put(4, LotteryMarkSixType.JOINT_ZODIAC_4);
        typeMap.put(5, LotteryMarkSixType.JOINT_ZODIAC_5);

        HashMap<String, RealtimeStat> realTimeStatMap = new HashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            LotteryMarkSixType wagerType = typeMap.get(wagerStubList.size());

            Collections.sort(wagerStubList, (w1, w2) -> (w1.getLotteryMarkSixType().ordinal() - w2.getLotteryMarkSixType().ordinal()));

            // wager content
            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                String zodiac = stub.getLotteryMarkSixType().getType();
                sb.append(zodiac).append(",");
            }
            String contentStr = sb.substring(0, sb.length() - 1);

            if (realTimeStatMap.containsKey(contentStr)) {
                realTimeStatMap.get(contentStr).addStakes(wager.getTotalStakes());
                realTimeStatMap.get(contentStr).addTransactions(1);
            } else {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(0);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(wager.getTotalStakes());
                realtimeStat.setOdds(odds);
                realtimeStat.setTransactions(1);
                realtimeStat.setWagerContent(contentStr);
                realtimeStat.setLotteryMarkSixType(wagerType.name());
                realtimeStat.setLotteryMarkSixTypeName(wagerType.getType());
                realTimeStatMap.put(contentStr, realtimeStat);
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4JointTail(String groupid, String panlei, LotteryMarkSixType type, int lotteryIssue) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);

        Double odds = 8.3; //FIXME oddsList.get(0).getOdds();

        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        if (isPanleiAll(panlei)) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "A", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "B", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "C", lotteryIssue, null));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, "D", lotteryIssue, null));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerList(type, groupid, panlei, lotteryIssue, null));
        }

        HashMap<String, RealtimeStat> realTimeStatMap = new HashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            Collections.sort(wagerStubList, (w1, w2) -> (w1.getNumber() - w2.getNumber()));

            // wager content
            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                String numStr = Utils.formatNumber00(stub.getNumber());
                sb.append(numStr).append(",");
            }
            String contentStr = sb.substring(0, sb.length() - 1);

            if (realTimeStatMap.containsKey(contentStr)) {
                realTimeStatMap.get(contentStr).addStakes(wager.getTotalStakes());
                realTimeStatMap.get(contentStr).addTransactions(1);
            } else {
                RealtimeStat realtimeStat = new RealtimeStat();
                realtimeStat.setGroupId(groupid);
                realtimeStat.setNumber(0);
                realtimeStat.setBalance(0);
                realtimeStat.setStakes(wager.getTotalStakes());
                realtimeStat.setOdds(odds);
                realtimeStat.setTransactions(1);
                realtimeStat.setWagerContent(contentStr);
                realtimeStat.setLotteryMarkSixType(type.name());
                realtimeStat.setLotteryMarkSixTypeName(type.getType());
                realTimeStatMap.put(contentStr, realtimeStat);
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }

    

    @Transactional
    public JSONObject getRealTimeTransactionTotalCount(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = new ArrayList<>();
        if (panlei.equalsIgnoreCase("ALL")) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "A", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "B", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "C", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "D", issue));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, panlei, issue));
        }

        Map<String, AtomicInteger> transactionMap = new HashMap<>();

        // initialize count map for all types
        Arrays.asList(LotteryMarkSixType.values()).stream().forEach(type -> {
            transactionMap.put(type.name(), new AtomicInteger(0));
        });
        transactionMap.put("ALL", new AtomicInteger(0));

        for (LotteryMarkSixWager wager : wagerList) {
            if (transactionMap.containsKey(wager.getLotteryMarkSixType().name())) {
                int transactionNum = CommonUtils.getTransactionsOfWager(wager);
                transactionMap.get(wager.getLotteryMarkSixType().name()).addAndGet(transactionNum);
                transactionMap.get("ALL").addAndGet(transactionNum);
            }
        }

        JSONObject json = new JSONObject();
        json.putAll(transactionMap);
        return json;
    }

    @Transactional
    public List<RealtimeStat> getRealTimeTransactionAllStats(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = new ArrayList<>();
        if (panlei.equalsIgnoreCase("ALL")) {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "A", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "B", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "C", issue));
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, "D", issue));
        } else {
            wagerList.addAll(wagerService.getLotteryMarkSixWagerListOfGroup(groupId, panlei, issue));
        }

        // initialize count map for all types
        Map<LotteryMarkSixType, AtomicInteger> transactionMap = new HashMap<>();
        Map<LotteryMarkSixType, AtomicDouble> stakesMap = new HashMap<>();
        getWagerTypeList().stream().forEach(type -> {
            transactionMap.put(type, new AtomicInteger(0));
            stakesMap.put(type, new AtomicDouble(0));
        });

        for (LotteryMarkSixWager wager : wagerList) {
            LotteryMarkSixType statType = wager.getLotteryMarkSixType(); // use wager's type as default
            double stakes = wager.getTotalStakes();
            int transactionNum = 0;

            switch (wager.getLotteryMarkSixType()) {
                case RED:
                case BLUE:
                case GREEN:
                    statType = COLOR;
                    transactionNum = 1;
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
                    statType = ZODIAC;
                    transactionNum = 1;
                    break;
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
                    statType = HALF_WAVE;
                    transactionNum = 1;
                    break;
                case ZHENG_SPECIFIC_1:
                case ZHENG_SPECIFIC_2:
                case ZHENG_SPECIFIC_3:
                case ZHENG_SPECIFIC_4:
                case ZHENG_SPECIFIC_5:
                case ZHENG_SPECIFIC_6:
                    statType = ZHENG_SPECIFIC;
                    transactionNum = wager.getLotteryMarkSixWagerStubList().size();
                    break;
                case JOINT_ZODIAC_PING:
                case JOINT_ZODIAC_ZHENG:
                    statType = JOINT_ZODIAC;
                    transactionNum = 1;
                    break;
                case JOINT_3_ALL:
                case JOINT_3_2:
                case JOINT_2_ALL:
                case JOINT_2_SPECIAL:
                case JOINT_SPECIAL:
                case NOT_5:
                case NOT_6:
                case NOT_7:
                case NOT_8:
                case NOT_9:
                case NOT_10:
                case NOT_11:
                case NOT_12:
                    transactionNum = 1;
                    break;
                case JOINT_TAIL_2:
                case JOINT_TAIL_3:
                case JOINT_TAIL_4:
                case JOINT_TAIL_NOT_2:
                case JOINT_TAIL_NOT_3:
                case JOINT_TAIL_NOT_4:
                    statType = JOINT_TAIL;
                    transactionNum = 1;
                    break;
                default:
                    transactionNum = wager.getLotteryMarkSixWagerStubList().size();
            }

            if (transactionMap.containsKey(statType) && stakesMap.containsKey(statType)) {
                transactionMap.get(statType).addAndGet(transactionNum);
                stakesMap.get(statType).addAndGet(stakes);
            } else {
                System.out.println(statType.getType());
            }
        }

        List<RealtimeStat> statsList = Lists.newArrayList();
        getWagerTypeList().stream().forEach(type -> {
            RealtimeStat stat = new RealtimeStat();
            stat.setLotteryMarkSixType(type.name());
            stat.setLotteryMarkSixTypeName(type.getType());
            stat.setTransactions(transactionMap.get(type).get());
            stat.setStakes(stakesMap.get(type).get());
            statsList.add(stat);
        });
        return statsList;
    }
    
    private boolean isPanleiAll(String panlei) {
        return "ALL".equalsIgnoreCase(panlei);
    }

}
