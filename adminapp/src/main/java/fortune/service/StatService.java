package fortune.service;

import static fortune.pojo.LotteryMarkSixType.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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
import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixGroupStat;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWager;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.LotteryOdds;
import fortune.pojo.RealTimeWager;
import fortune.pojo.RealtimeStat;

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

    public List<List<RealtimeStat>> getRealTimeTransactionResult4NotTop(String groupid, String panlei, int top) {
        List<List<RealtimeStat>> resultList = Lists.newArrayList();
        
        getRealTimeNotList().stream().forEach(type -> {
            List<RealtimeStat> statList = getRealTimeTransactionResult4Not(groupid, panlei, type);
            //FIXME order by 'remark' desc
            Collections.sort(statList, (stat1, stat2) -> (int)(stat2.getStakes() - stat1.getStakes()));
            int end = statList.size() > top ? top : statList.size();
            resultList.add(statList.subList(0, end));
        });
        
        return resultList;
    }
    
    @Transactional
    public List<RealtimeStat> getRealTimeTransactionResult(LotteryMarkSixType type, String groupid, String panlei) {
        switch (type) {
            case SPECIAL:
                return getRealTimeTransactionResult4Special(groupid, panlei);
            case TWO_FACES:
                return getRealTimeTransactionResult4TwoFaces(groupid, panlei);
            case ZODIAC:
            case HALF_WAVE:
            case COLOR:
                return getRealTimeTransactionResultByType(groupid, panlei, type);
            case SUM_ZODIAC:
                return getRealTimeTransactionResult4SumZodiac(groupid, panlei);
            case ZHENG_BALL:
                return getRealTimeTransactionResult4ZhengBall(groupid, panlei);
            case ZHENG_1_6:
                return getRealTimeTransactionResult4Zheng1To6(groupid, panlei);
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                return getRealTimeTransactionResult4ZhengSpecific(groupid, panlei, type);
            case ONE_ZODIAC:
                return getRealTimeTransactionResult4OneZodiac(groupid, panlei);
            case TAIL_NUM:
                return getRealTimeTransactionResult4TailNum(groupid, panlei);
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
                return getRealTimeTransactionResult4Joint(groupid, panlei, type);
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
                // already check panlei inside
                return getRealTimeTransactionResult4Not(groupid, panlei, type);
            case JOINT_ZODIAC_PING:
            case JOINT_ZODIAC_ZHENG:
                return getRealTimeTransactionResult4JointZodiac(groupid, panlei, type);
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                return getRealTimeTransactionResult4JointTail(groupid, panlei, type);
            default:
                return null;
        }
    }

    @Transactional
    public List<RealTimeWager> getAllStakeDetail4Type(LotteryMarkSixType type, String groupId, String panlei, int issue) {
        switch (type) {
            case SPECIAL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getAllStakeDetail4Special(groupId, pan, issue));
                    }
                    return wagerList;
                } else {
                    return getAllStakeDetail4Special(groupId, panlei, issue);
                }
            case TWO_FACES:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getAllStakeDetail4StubByType(groupId, pan, issue, type));
                    }
                    return wagerList;
                } else {
                    return getAllStakeDetail4StubByType(groupId, panlei, issue, type);
                }
            case HALF_WAVE:
                List<RealTimeWager> halfWaveWagerList = new ArrayList<>();
                getRealTimeWaveTypeList().stream().forEach(waveType -> {
                    if (panlei.equalsIgnoreCase("ALL")) {
                        for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                            halfWaveWagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, waveType, "特码半波"));
                        }
                    } else {
                        halfWaveWagerList.addAll(getStakeDetailByLotteryType(groupId, panlei, issue, waveType, "特码半波"));
                    }
                });
                return halfWaveWagerList;
            case ZODIAC:
                List<RealTimeWager> zodiacWagerList = new ArrayList<>();
                getRealTimeAnimalList().stream().forEach(zodiacType -> {
                    if (panlei.equalsIgnoreCase("ALL")) {
                        for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                            zodiacWagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, zodiacType, "特码生肖"));
                        }
                    } else {
                        zodiacWagerList.addAll(getStakeDetailByLotteryType(groupId, panlei, issue, zodiacType, "特码生肖"));
                    }
                });
                return zodiacWagerList;
            case COLOR:
                List<RealTimeWager> colorWagerList = new ArrayList<>();
                getRealTimeColorTypeList().stream().forEach(colorType -> {
                    if (panlei.equalsIgnoreCase("ALL")) {
                        for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                            colorWagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, colorType, "特码"));
                        }
                    } else {
                        colorWagerList.addAll(getStakeDetailByLotteryType(groupId, panlei, issue, colorType, "特码"));
                    }
                });
                return colorWagerList;
            case SUM_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getAllStakeDetail4SumZodiac(groupId, pan, issue));
                    }
                    return wagerList;
                } else {
                    return getAllStakeDetail4SumZodiac(groupId, panlei, issue);
                }
            case ZHENG_BALL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4ZhengBall(groupId, pan, issue, -1));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4ZhengBall(groupId, panlei, issue, -1);
                }
            case ZHENG_SPECIFIC:
                List<RealTimeWager> zhengSpecificWagerList = new ArrayList<>();
                getRealTimeZhengSpecificList().stream().forEach(zhengSpecificType -> {
                    if (panlei.equalsIgnoreCase("ALL")) {
                        for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                            zhengSpecificWagerList.addAll(getStakeDetail4ZhengSpecific(groupId, pan, issue, -1, zhengSpecificType));
                        }
                    } else {
                        zhengSpecificWagerList.addAll(getStakeDetail4ZhengSpecific(groupId, panlei, issue, -1, zhengSpecificType));
                    }
                });
                return zhengSpecificWagerList;
            case ONE_ZODIAC:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getAllStakeDetail4StubByType(groupId, pan, issue, type));
                    }
                    return wagerList;
                } else {
                    return getAllStakeDetail4StubByType(groupId, panlei, issue, type);
                }
            case TAIL_NUM:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4TailNum(groupId, pan, issue, -1));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4TailNum(groupId, panlei, issue, -1);
                }
            case ZHENG_1_6:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Zheng16(groupId, pan, issue, -1, null));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4Zheng16(groupId, panlei, issue, -1, null);
                }
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Not(groupId, pan, issue, type, null));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4Not(groupId, panlei, issue, type, null);
                }
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Joint(groupId, pan, issue, type, null));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4JointZodiac(groupId, panlei, issue, type, null);
                }   
            case JOINT_ZODIAC:
                List<RealTimeWager> jointZodiacWagerList = new ArrayList<>();
                getRealTimeJointZodiacList().stream().forEach(jointZodiacType -> {
                    if (panlei.equalsIgnoreCase("ALL")) {
                        for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                            jointZodiacWagerList.addAll(getStakeDetail4JointZodiac(groupId, pan, issue, jointZodiacType, null));
                        }
                    } else {
                        jointZodiacWagerList.addAll(getStakeDetail4JointZodiac(groupId, panlei, issue, jointZodiacType, null));
                    }
                });
                return jointZodiacWagerList;
          case JOINT_TAIL:
              List<RealTimeWager> jointTailWagerList = new ArrayList<>();
              getRealTimeJointTailList().stream().forEach(jointTailType -> {
                  if (panlei.equalsIgnoreCase("ALL")) {
                      for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                          jointTailWagerList.addAll(getStakeDetail4JointTail(groupId, pan, issue, jointTailType, null));
                      }
                  } else {
                      jointTailWagerList.addAll(getStakeDetail4JointTail(groupId, panlei, issue, jointTailType, null));
                  }
              });
              return jointTailWagerList;
        default:
            return new ArrayList<>();
        }
    }
    
    @Transactional
    public List<RealTimeWager> getStakeDetail(LotteryMarkSixType type, String groupId, String panlei, int issue, int number, LotteryMarkSixType ballType, String wagerContent) {
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
            case TWO_FACES:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetailByBallType(groupId, pan, issue, type, ballType));
                    }
                    return wagerList;
                } else {
                    return getStakeDetailByBallType(groupId, panlei, issue, type, ballType);
                }
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
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, type, "特码生肖"));
                    }
                    return wagerList;
                } else {
                    return getStakeDetailByLotteryType(groupId, panlei, issue, type, "特码生肖");
                }
            case WAVE_RED_DA:
            case WAVE_RED_XIAO:
            case WAVE_RED_DAN:
            case WAVE_RED_SHUANG:
            case WAVE_BLUE_DA:
            case WAVE_BLUE_XIAO:
            case WAVE_BLUE_DAN:
            case WAVE_BLUE_SHUANG:
            case WAVE_GREEN_DA:
            case WAVE_GREEN_XIAO:
            case WAVE_GREEN_DAN:
            case WAVE_GREEN_SHUANG:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, type, "特码半波"));
                    }
                    return wagerList;
                } else {
                    return getStakeDetailByLotteryType(groupId, panlei, issue, type, "特码半波");
                }
            case RED:
            case BLUE:
            case GREEN:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetailByLotteryType(groupId, pan, issue, type, "特码"));
                    }
                    return wagerList;
                } else {
                    return getStakeDetailByLotteryType(groupId, panlei, issue, type, "特码");
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
            case ZHENG_1_6:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Zheng16(groupId, pan, issue, number, ballType));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4Zheng16(groupId, panlei, issue, number, ballType);
                }
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Not(groupId, pan, issue, type, wagerContent));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4Not(groupId, panlei, issue, type, wagerContent);
                }
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4Joint(groupId, pan, issue, type, wagerContent));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4JointZodiac(groupId, panlei, issue, type, wagerContent);
                }    
            case JOINT_ZODIAC_2:
            case JOINT_ZODIAC_3:
            case JOINT_ZODIAC_4:
            case JOINT_ZODIAC_5:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4JointZodiac(groupId, pan, issue, type, wagerContent));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4JointZodiac(groupId, panlei, issue, type, wagerContent);
                }
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                if (panlei.equalsIgnoreCase("ALL")) {
                    List<RealTimeWager> wagerList = new ArrayList<>();
                    for (String pan : Lists.newArrayList("A", "B", "C", "D")) {
                        wagerList.addAll(getStakeDetail4JointTail(groupId, pan, issue, type, wagerContent));
                    }
                    return wagerList;
                } else {
                    return getStakeDetail4JointTail(groupId, panlei, issue, type, wagerContent);
                }
        default:
            return new ArrayList<>();
        }
    }
    
    @Transactional
    private List<RealtimeStat> getRealTimeTransactionResult4Special(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type special", groupid);
        
        HashMap<Integer, RealtimeStat> statMap = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

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
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4TwoFaces(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for two faces", groupid);

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
        return realtimeStatList;
    }
    
    /**
     * 根据下注类型获取注单统计信息，适用于以下类型(赔率需按一级类型设置)：<br>
     *   - 生肖 <br>
     *   - 半波 <br>
     *   - 色波 <br>
     *   
     * @param groupid
     * @param panlei
     * @param lotteryType
     * @return
     */
    private List<RealtimeStat> getRealTimeTransactionResultByType(String groupid, String panlei, LotteryMarkSixType lotteryType) {
        Utils.logger.info("get real time transaction result of group id {} for half wave", groupid);

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
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

        return realtimeStatList;
    }
    
    private List<RealtimeStat> getRealTimeTransactionResult4SumZodiac(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type sum zodiac", groupid);
        HashMap<Integer, RealtimeStat> realtimeStatHashMap = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
        return realtimeStatList;
    }
    
    private List<RealtimeStat> getRealTimeTransactionResult4ZhengBall(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatMap = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

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
                realtimeStat.setOdds(oddsMap.get(number));
                realtimeStat.setTransactions(0);
                realTimeStatMap.put(number, realtimeStat);
            }
        }
        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4ZhengSpecific(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng ball", groupid);
        HashMap<Integer, RealtimeStat> realTimeStatHashMap4Number = new HashMap<>();
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

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
        Collections.sort(realtimeStatList, (o1, o2) -> (int) (o2.getBalance() - o1.getBalance()));
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4Zheng1To6(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type zheng 1 - 6", groupid);
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();

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
                    realTimeStatMap.put(stub.getNumber(), new HashMap<LotteryMarkSixType, RealtimeStat>());
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
        for (int i = 1; i <= 6; i ++) {
            realtimeStatList.addAll(Lists.newArrayList(realTimeStatMap.get(i).values().iterator()));
        }
        
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4OneZodiac(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type one zodiac", groupid);
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4TailNum(String groupid, String panlei) {
        Utils.logger.info("get real time transaction result of group id {} for type tail number", groupid);
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
        Collections.sort(realtimeStatList, (o1, o2) -> o1.getNumber() - o2.getNumber());
        return realtimeStatList;
    }

    private List<RealtimeStat> getRealTimeTransactionResult4Joint(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
        
        LinkedHashMap<Integer, RealtimeStat> realTimeStatHashMap = new LinkedHashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            if (wager.getLotteryMarkSixType().equals(type)) {
                List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
                
                int wagerNum = 0;
                if (type.equals(JOINT_3_ALL) || type.equals(JOINT_3_2)) {
                    int num1 = wagerStubList.get(0).getNumber();
                    int num2 = wagerStubList.get(1).getNumber();
                    int num3 = wagerStubList.get(2).getNumber();
                    wagerNum = num1 * 10000 + num2 * 100 + num3;    // 12, 16, 29 -> 121629   1, 2, 3 -> 10203
                
                } else if (type.equals(JOINT_2_ALL) || type.equals(JOINT_2_SPECIAL) || type.equals(JOINT_SPECIAL)) {
                    int num1 = wagerStubList.get(0).getNumber();
                    int num2 = wagerStubList.get(1).getNumber();
                    wagerNum = num1 * 100 + num2;    // 12, 16 -> 1216
                }
                
                if (realTimeStatHashMap.containsKey(wagerNum)) {
                    realTimeStatHashMap.get(wagerNum).addStakes(wager.getTotalStakes());
                    realTimeStatHashMap.get(wagerNum).addTransactions(1);
                } else {
                    RealtimeStat realtimeStat = new RealtimeStat();
                    realtimeStat.setGroupId(groupid);
                    realtimeStat.setNumber(wagerNum);
                    realtimeStat.setBalance(0);
                    realtimeStat.setStakes(wager.getTotalStakes());
                    realtimeStat.setOdds(odds);
                    realtimeStat.setTransactions(1);
                    realtimeStat.setLotteryMarkSixType(type.name());
                    realtimeStat.setLotteryMarkSixTypeName(type.getType());
                    realTimeStatHashMap.put(wagerNum, realtimeStat);
                }
                
            }
        }

        List<RealtimeStat> realtimeStatList = Lists.newArrayList(realTimeStatHashMap.values().iterator());
        Collections.sort(realtimeStatList, (o1, o2) -> (int)(o1.getStakes() - o2.getStakes()));
        return realtimeStatList;
    }
    
    private List<RealtimeStat> getRealTimeTransactionResult4Not(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            
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
        Collections.sort(realtimeStatList, (o1, o2) -> (int)(o1.getStakes() - o2.getStakes()));
        return realtimeStatList;
    }    

    private List<RealtimeStat> getRealTimeTransactionResult4JointZodiac(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
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
            for (LotteryMarkSixWagerStub stub :wagerStubList) {
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
        Collections.sort(realtimeStatList, (o1, o2) -> (int)(o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }    
    
    private List<RealtimeStat> getRealTimeTransactionResult4JointTail(String groupid, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get real time transaction result of group id {} for type {}", groupid, type.name());

        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        
        //TODO determine odds if panlei is 'ALL', currently use panlei 'A'
        String pan4Odds = isPanleiAll(panlei) ? "A" : panlei;
        List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssueByType(lotteryIssue, groupid, type.name(), pan4Odds);
        
        Double odds = 8.3; //oddsList.get(0).getOdds();
        
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
                String numStr = genNumberStr(stub.getNumber());
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
        Collections.sort(realtimeStatList, (o1, o2) -> (int)(o2.getStakes() - o1.getStakes()));
        return realtimeStatList;
    }  

    @Transactional
    private List<RealTimeWager> getStakeDetail4Special(String groupId, String panlei, int issue, int number) {
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
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, SPECIAL, null, panlei).getOdds());
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
            realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, SUM_ZODIAC, null, wager.getPanlei()).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4ZhengBall(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.ZHENG_BALL, groupId, panlei, issue, null);
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number < 0 || number == stub.getNumber()) {
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
                    realTimeWager.setStakes(stub.getStakes());
                    realTimeWager.setWageContent(String.format("%s %s", ZHENG_BALL.getType(), stub.getNumber()));
                    realTimeWager.setOdds(oddsService.getOdds(issue, groupId, stub.getNumber(), ZHENG_BALL, null, wager.getPanlei()).getOdds());
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                }
            }
            
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4ZhengSpecific(String groupId, String panlei, int issue, int number, LotteryMarkSixType type) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number < 0 || number == stub.getNumber()) {
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
                    realTimeWager.setWageContent(String.format("%s %s", type.getType(), stub.getNumber()));
                    realTimeWager.setStakes(stub.getStakes());
                    realTimeWager.setOdds(oddsService.getOdds(issue, groupId, 0, type, null, wager.getPanlei()).getOdds());
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                }
            }
            
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4TailNum(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.TAIL_NUM, groupId, panlei, issue, null);
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (number < 0 || number == stub.getNumber()) {
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
                    realTimeWager.setStakes(stub.getStakes());
                    realTimeWager.setWageContent(String.format("%s %s", TAIL_NUM.getType(), stub.getNumber()));
                    realTimeWager.setOdds(oddsService.getOdds(issue, groupId, stub.getNumber(), TAIL_NUM, null, wager.getPanlei()).getOdds());
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                }
            }
            
        }
        return realTimeWagerList;
    }

    /**
     * 获取正码1到6的注单详情
     * 
     * @param groupId
     * @param panlei
     * @param issue
     * @param number optional
     * @param subType optional
     * @return
     */
    @Transactional
    private List<RealTimeWager> getStakeDetail4Zheng16(String groupId, String panlei, int issue, int number, LotteryMarkSixType subType) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.ZHENG_1_6, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        String[] zhengName = {"一","二","三","四","五","六"};
        
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if ( (number < 0 || number == stub.getNumber()) 
                        && (subType == null || subType.equals(stub.getLotteryMarkSixType()))) {
                    
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
                    realTimeWager.setStakes(stub.getStakes());
                    realTimeWager.setWageContent(String.format("正码%s %s", zhengName[stub.getNumber() - 1], stub.getLotteryMarkSixType().getType()));
                    realTimeWager.setOdds(oddsService.getOdds(issue, groupId, 0, LotteryMarkSixType.ZHENG_1_6, stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds());
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                }
            }
            
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetailByBallType(String groupId, String panlei, int issue, LotteryMarkSixType type, LotteryMarkSixType ballType) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, ballType);
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
    private List<RealTimeWager> getStakeDetailByLotteryType(String groupId, String panlei, int issue, LotteryMarkSixType type, String contentPrefix) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        
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
            realTimeWager.setWageContent(String.format("%s %s", contentPrefix, type.getType()));
            realTimeWager.setStakes(wager.getTotalStakes());
            realTimeWager.setOdds(oddsService.getOdds4LotteryIssueByType(issue, groupId, type.name(), wager.getPanlei()).get(0).getOdds());
            realTimeWager.setTuishui2(0);
            realTimeWager.setResult(0);
            realTimeWager.setRemark("");
            realTimeWagerList.add(realTimeWager);
        }
        return realTimeWagerList;
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4Not(String groupId, String panlei, int issue, LotteryMarkSixType type, String content) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        
        Double odds = oddsService.getOdds(issue, groupId, 0, type, null, panlei).getOdds();
        
        for (LotteryMarkSixWager wager : wagerList) {
            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                String num = (stub.getNumber() < 10 ? "0" : "") + stub.getNumber();
                sb.append(num).append(",");
            }
            String wagerContent = sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
            
            if (content == null || content.equals(wagerContent)) {
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
                realTimeWager.setWageContent(String.format("%s %s", type.getType(), wagerContent));
                realTimeWager.setStakes(wager.getTotalStakes());
                realTimeWager.setOdds(odds);
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }
    
    @Transactional
    private List<RealTimeWager> getStakeDetail4Joint(String groupId, String panlei, int issue, LotteryMarkSixType type, String content) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        
        Double odds = oddsService.getOdds(issue, groupId, 0, type, null, panlei).getOdds();
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            Collections.sort(wagerStubList, (w1, w2) -> (w1.getNumber() - w2.getNumber()));

            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                String numStr = genNumberStr(stub.getNumber());
                sb.append(numStr).append(",");
            }
            String wagerContent = sb.substring(0, sb.length() - 1);

            if (content == null || content.equals(wagerContent)) {
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
                realTimeWager.setWageContent(String.format("%s %s", type.getType(), wagerContent));
                realTimeWager.setStakes(wager.getTotalStakes());
                realTimeWager.setOdds(odds);
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }
    
    @Transactional
    private List<RealTimeWager> getStakeDetail4JointZodiac(String groupId, String panlei, int issue, LotteryMarkSixType type, String content) {
        List<LotteryMarkSixWager> wagerList = Lists.newArrayList();
        wagerList.addAll(wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.JOINT_ZODIAC_PING, groupId, panlei, issue, null));
        wagerList.addAll(wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.JOINT_ZODIAC_ZHENG, groupId, panlei, issue, null));
        
        Map<LotteryMarkSixType, Double> oddsMap = new HashMap<>();
        oddsMap.put(JOINT_ZODIAC_PING, oddsService.getOdds(issue, groupId, 0, JOINT_ZODIAC_PING, null, panlei).getOdds());
        oddsMap.put(JOINT_ZODIAC_ZHENG, oddsService.getOdds(issue, groupId, 0, JOINT_ZODIAC_ZHENG, null, panlei).getOdds());
        
        Map<Integer, LotteryMarkSixType> typeMap = new HashMap<>();
        typeMap.put(2, LotteryMarkSixType.JOINT_ZODIAC_2);
        typeMap.put(3, LotteryMarkSixType.JOINT_ZODIAC_3);
        typeMap.put(4, LotteryMarkSixType.JOINT_ZODIAC_4);
        typeMap.put(5, LotteryMarkSixType.JOINT_ZODIAC_5);
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();

        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            LotteryMarkSixType wagerType = typeMap.get(wagerStubList.size());
            
            if (wagerType.equals(type)) {
                Collections.sort(wagerStubList, (w1, w2) -> (w1.getLotteryMarkSixType().ordinal() - w2.getLotteryMarkSixType().ordinal()));
                
                StringBuilder sb = new StringBuilder();
                for (LotteryMarkSixWagerStub stub : wagerStubList) {
                    String zodiac = stub.getLotteryMarkSixType().getType();
                    sb.append(zodiac).append(",");
                }
                String wagerContent = sb.substring(0, sb.length() - 1);
                
                if (content == null || content.equals(wagerContent)) {
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
                    realTimeWager.setWageContent(String.format("%s %s", type.getType(), wagerContent));
                    realTimeWager.setStakes(wager.getTotalStakes());
                    realTimeWager.setOdds(oddsMap.get(wager.getLotteryMarkSixType()));
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                }
            }
        }
        return realTimeWagerList;
    }
    
    /**
     * 获取连尾的注单详情
     * 
     * @param groupId
     * @param panlei
     * @param issue
     * @param type
     * @param content 如果为null，返回所有连尾注单
     * @return
     */
    @Transactional
    private List<RealTimeWager> getStakeDetail4JointTail(String groupId, String panlei, int issue, LotteryMarkSixType type, String content) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        
        Double odds = 8.3; //TODO oddsService.getOdds(issue, groupId, 0, type, null, panlei).getOdds();
        
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            Collections.sort(wagerStubList, (w1, w2) -> (w1.getNumber() - w2.getNumber()));

            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                String numStr = genNumberStr(stub.getNumber());
                sb.append(numStr).append(",");
            }
            String wagerContent = sb.substring(0, sb.length() - 1);

            if (content == null || content.equals(wagerContent)) {
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
                realTimeWager.setWageContent(String.format("%s %s", type.getType(), wagerContent));
                realTimeWager.setStakes(wager.getTotalStakes());
                realTimeWager.setOdds(odds);
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }

    @Transactional
    public JSONObject getRealTimeTransactionTotalCount(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupId, panlei, issue);
        Map<String, AtomicInteger> transactionMap = new HashMap<>();
        
        // initialize count map for all types
        Arrays.asList(LotteryMarkSixType.values()).stream().forEach(type -> {
            transactionMap.put(type.name(), new AtomicInteger(0));
        });
        transactionMap.put("ALL", new AtomicInteger(0));

        for (LotteryMarkSixWager wager : wagerList) {
            if (transactionMap.containsKey(wager.getLotteryMarkSixType().name())) {
                int transactionNum = 0;
                switch (wager.getLotteryMarkSixType()) {
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
                    case WAVE_RED_DA:
                    case WAVE_RED_XIAO:
                    case WAVE_RED_DAN:
                    case WAVE_RED_SHUANG:
                    case WAVE_BLUE_DA:
                    case WAVE_BLUE_XIAO:
                    case WAVE_BLUE_DAN:
                    case WAVE_BLUE_SHUANG:
                    case WAVE_GREEN_DA:
                    case WAVE_GREEN_XIAO:
                    case WAVE_GREEN_DAN:
                    case WAVE_GREEN_SHUANG:
                    case JOINT_ZODIAC_PING:
                    case JOINT_ZODIAC_ZHENG:
                    case JOINT_TAIL_2:
                    case JOINT_TAIL_3:
                    case JOINT_TAIL_4:
                    case JOINT_TAIL_NOT_2:
                    case JOINT_TAIL_NOT_3:
                    case JOINT_TAIL_NOT_4:
                        transactionNum = 1;
                        break;
                    default:
                        transactionNum = wager.getLotteryMarkSixWagerStubList().size();
                }
                
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
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerListOfGroup(groupId, panlei, issue);
        
        // initialize count map for all types
        Map<LotteryMarkSixType, AtomicInteger> transactionMap = new HashMap<>();
        Map<LotteryMarkSixType, AtomicDouble> stakesMap = new HashMap<>();
        getTypeList4AllStats().stream().forEach(type -> {
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
        getTypeList4AllStats().stream().forEach(type -> {
            RealtimeStat stat = new RealtimeStat();
            stat.setLotteryMarkSixType(type.name());
            stat.setLotteryMarkSixTypeName(type.getType());
            stat.setTransactions(transactionMap.get(type).get());
            stat.setStakes(stakesMap.get(type).get());
            statsList.add(stat);
        });
        return statsList;
    }

    @Transactional
    private List<RealTimeWager> getAllStakeDetail4Special(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SPECIAL, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
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

                realTimeWager.setWageContent(String.format("%s %s", SPECIAL.getType(), stub.getNumber()));
                realTimeWager.setStakes(stub.getStakes());
                realTimeWager.setOdds(oddsService.getOdds(issue, groupId, stub.getNumber(), SPECIAL, null, panlei).getOdds());
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }
    
    @Transactional
    private List<RealTimeWager> getAllStakeDetail4SumZodiac(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SUM_ZODIAC, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
           for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
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
                
                int number = stub.getNumber();
                realTimeWager.setWageContent(String.format("%s %s肖 %s", SUM_ZODIAC.getType(), number / 10, number % 10 == 1 ? "中" : "不中"));
                realTimeWager.setStakes(stub.getStakes());
                realTimeWager.setOdds(oddsService.getOdds(issue, groupId, number, SUM_ZODIAC, null, wager.getPanlei()).getOdds());
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }
    

    /**
     * 根据wager类型获取所有该类型wager下所有stub详情，目前适用于 两面, 一肖
     * 
     * @param groupId
     * @param panlei
     * @param issue
     * @param type
     * @return
     */
    @Transactional
    private List<RealTimeWager> getAllStakeDetail4StubByType(String groupId, String panlei, int issue, LotteryMarkSixType type) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(type, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
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
                realTimeWager.setWageContent(String.format("%s %s", type.getType(), stub.getLotteryMarkSixType().getType()));
                realTimeWager.setStakes(stub.getStakes());
                realTimeWager.setOdds(oddsService.getOdds4LotteryIssueByBallType(issue, groupId, type.name(), wager.getPanlei(), stub.getLotteryMarkSixType().name()).getOdds());
                realTimeWager.setTuishui2(0);
                realTimeWager.setResult(0);
                realTimeWager.setRemark("");
                realTimeWagerList.add(realTimeWager);
            }
        }
        return realTimeWagerList;
    }
    
    private boolean isPanleiAll(String panlei) {
        return "ALL".equalsIgnoreCase(panlei);
    }

    private String genNumberStr(int number) {
        return (0 <= number && number < 10 ? "0" : "") + number;
    }
    
}
