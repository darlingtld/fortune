package fortune.service;

import static fortune.pojo.LotteryMarkSixType.SPECIAL;
import static fortune.pojo.LotteryMarkSixType.SUM_ZODIAC;
import static fortune.pojo.LotteryMarkSixType.TAIL_NUM;
import static fortune.pojo.LotteryMarkSixType.ZHENG_BALL;
import static fortune.pojo.LotteryMarkSixType.getRealTimeAnimalList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeColorTypeList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeJointTailList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeJointZodiacList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeWaveTypeList;
import static fortune.pojo.LotteryMarkSixType.getRealTimeZhengSpecificList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fortune.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

import common.Utils;
import fortune.dao.LotteryDao;
import fortune.dao.WagerDao;

/**
 * Created by Jason on 2016-01-13.
 */
@Service
public class StakeDetailService {

    @Autowired
    private WagerService wagerService;

    @Autowired
    private WagerDao wagerDao;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private OddsService oddsService;

    @Autowired
    private UserService userService;

    @Autowired
    private TuishuiService tuishuiService;

    @Autowired
    private LotteryDao lotteryDao;

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
    public List<RealTimeWager> getAllStakeDetail4User(String userId, String groupId, int issue) {
        if (issue == 0) {
            issue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        }
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(userId, groupId, issue);
        List<RealTimeWager> realTimeWagerList = buildStakeDetails4WagerList(wagerList);
        return realTimeWagerList;
    }

    @Transactional
    public List<RealTimeWager> getAllStakeDetail4User(String userId, String groupId) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(userId, groupId);
        List<RealTimeWager> realTimeWagerList = buildStakeDetails4WagerList(wagerList);
        return realTimeWagerList;
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
    public List<RealTimeWager> getStakeDetailByTimeRange(String groupId, String userId, String type, String fromDate, String toDate) {
        List<LotteryMarkSix> lotteryList = lotteryDao.getLotteryMarkSixByTimeRange(fromDate, toDate);
        List<Integer> issueList = new ArrayList<>();
        lotteryList.stream().forEach(lottery -> {
            issueList.add(lottery.getIssue());
        });

        List<LotteryMarkSixWager> allWagerList = wagerDao.getWagerListByIssues(groupId, issueList);

        List<LotteryMarkSixWager> selectedWagerList = allWagerList.stream()
                .filter(wager -> (type == null || wager.getLotteryMarkSixType().name().equals(type))
                        && (userId == null || wager.getUserId().equals(userId)))
                .collect(Collectors.toList());

        return buildStakeDetails4WagerList(selectedWagerList);
    }

    @Transactional
    private List<RealTimeWager> getStakeDetail4Special(String groupId, String panlei, int issue, int number) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SPECIAL, groupId, panlei, issue, number);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            RealTimeWager realTimeWager = new RealTimeWager();
            realTimeWager.setTs(wager.getTimestamp());
            realTimeWager.setUser(userService.getUserById(wager.getUserId()));
            realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.SPECIAL).getTuishui());
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
            realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.SUM_ZODIAC).getTuishui());
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
                    realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.ZHENG_BALL).getTuishui());
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
                    realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.ZHENG_SPECIFIC).getTuishui());
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
                    realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.TAIL_NUM).getTuishui());
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
     * @param number  optional
     * @param subType optional
     * @return
     */
    @Transactional
    private List<RealTimeWager> getStakeDetail4Zheng16(String groupId, String panlei, int issue, int number, LotteryMarkSixType subType) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.ZHENG_1_6, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        String[] zhengName = {"一", "二", "三", "四", "五", "六"};

        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if ((number < 0 || number == stub.getNumber())
                        && (subType == null || subType.equals(stub.getLotteryMarkSixType()))) {

                    RealTimeWager realTimeWager = new RealTimeWager();
                    realTimeWager.setTs(wager.getTimestamp());
                    realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                    realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.ZHENG_1_6).getTuishui());
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
            realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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
            realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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
                String numStr = Utils.formatNumber00(stub.getNumber());
                sb.append(numStr).append(",");
            }
            String wagerContent = sb.substring(0, sb.length() - 1);

            if (content == null || content.equals(wagerContent)) {
                RealTimeWager realTimeWager = new RealTimeWager();
                realTimeWager.setTs(wager.getTimestamp());
                realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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

        Map<Integer, LotteryMarkSixType> typeMap = new HashMap<>();
        typeMap.put(2, LotteryMarkSixType.JOINT_ZODIAC_2);
        typeMap.put(3, LotteryMarkSixType.JOINT_ZODIAC_3);
        typeMap.put(4, LotteryMarkSixType.JOINT_ZODIAC_4);
        typeMap.put(5, LotteryMarkSixType.JOINT_ZODIAC_5);

        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            LotteryMarkSixType wagerType = typeMap.get(wagerStubList.size());
            Double odds = oddsService.getOdds(issue, groupId, wagerStubList.size(), wager.getLotteryMarkSixType(), null, panlei).getOdds();

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
                    realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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

        Double odds = oddsService.getOdds(issue, groupId, 0, type, null, panlei).getOdds();

        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            List<LotteryMarkSixWagerStub> wagerStubList = wager.getLotteryMarkSixWagerStubList();
            Collections.sort(wagerStubList, (w1, w2) -> (w1.getNumber() - w2.getNumber()));

            StringBuilder sb = new StringBuilder();
            for (LotteryMarkSixWagerStub stub : wagerStubList) {
                String numStr = Utils.formatNumber00(stub.getNumber());
                sb.append(numStr).append(",");
            }
            String wagerContent = sb.substring(0, sb.length() - 1);

            if (content == null || content.equals(wagerContent)) {
                RealTimeWager realTimeWager = new RealTimeWager();
                realTimeWager.setTs(wager.getTimestamp());
                realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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
    private List<RealTimeWager> getAllStakeDetail4Special(String groupId, String panlei, int issue) {
        List<LotteryMarkSixWager> wagerList = wagerService.getLotteryMarkSixWagerList(LotteryMarkSixType.SPECIAL, groupId, panlei, issue, null);
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                RealTimeWager realTimeWager = new RealTimeWager();
                realTimeWager.setTs(wager.getTimestamp());
                realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.SPECIAL).getTuishui());
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
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, LotteryMarkSixType.SUM_ZODIAC).getTuishui());
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
                realTimeWager.setTuishui(tuishuiService.getTuishui4UserOfType(wager.getUserId(), groupId, panlei, type).getTuishui());
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

    private String buildWagerContent(LotteryMarkSixWager wager, LotteryMarkSixWagerStub stub) {
        String wagerContent = "";

        LotteryMarkSixType wagerType = wager.getLotteryMarkSixType();
        switch (wagerType) {
            case SPECIAL:
            case ZHENG_BALL:
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
            case TAIL_NUM:
                wagerContent = String.format("%s %s", wagerType.getType(), stub.getNumber());
                break;
            case TWO_FACES:
            case ONE_ZODIAC:
                wagerContent = String.format("%s %s", wagerType.getType(), stub.getLotteryMarkSixType().getType());
                break;
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
                wagerContent = String.format("%s %s", "特码半波", wagerType.getType());
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
                wagerContent = String.format("%s %s", "特码生肖", wagerType.getType());
                break;
            case RED:
            case BLUE:
            case GREEN:
                wagerContent = String.format("%s %s", "特码", wagerType.getType());
                break;
            case SUM_ZODIAC:
                wagerContent = String.format("%s %s肖 %s", wagerType.getType(), stub.getNumber() / 10, stub.getNumber() % 10 == 1 ? "中" : "不中");
                break;
            case ZHENG_1_6:
                String[] zhengName = {"一", "二", "三", "四", "五", "六"};
                wagerContent = String.format("正码%s %s", zhengName[stub.getNumber() - 1], stub.getLotteryMarkSixType().getType());
                break;
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                StringBuilder sb = new StringBuilder();
                for (LotteryMarkSixWagerStub thisStub : wager.getLotteryMarkSixWagerStubList()) {
                    String num = Utils.formatNumber00(thisStub.getNumber());
                    sb.append(num).append(",");
                }
                wagerContent = String.format("%s %s", wagerType.getType(), sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "");
                break;
            case JOINT_ZODIAC_PING:
            case JOINT_ZODIAC_ZHENG:
                List<LotteryMarkSixWagerStub> stubList = wager.getLotteryMarkSixWagerStubList();
                Collections.sort(stubList, (w1, w2) -> (w1.getLotteryMarkSixType().ordinal() - w2.getLotteryMarkSixType().ordinal()));
                StringBuilder jointZodiacSB = new StringBuilder();
                for (LotteryMarkSixWagerStub thisStub : stubList) {
                    String zodiac = thisStub.getLotteryMarkSixType().getType();
                    jointZodiacSB.append(zodiac).append(",");
                }
                Map<Integer, LotteryMarkSixType> typeMap = new HashMap<>();
                typeMap.put(2, LotteryMarkSixType.JOINT_ZODIAC_2);
                typeMap.put(3, LotteryMarkSixType.JOINT_ZODIAC_3);
                typeMap.put(4, LotteryMarkSixType.JOINT_ZODIAC_4);
                typeMap.put(5, LotteryMarkSixType.JOINT_ZODIAC_5);
                LotteryMarkSixType jointZodiacType = typeMap.get(stubList.size());
                String zodiacStr = jointZodiacSB.length() > 0 ? jointZodiacSB.substring(0, jointZodiacSB.length() - 1) : "";
                wagerContent = String.format("%s %s", jointZodiacType.getType(), zodiacStr);
                break;
            default:
                break;
        }
        return wagerContent;
    }

    private Double getOdds(int issue, String groupId, LotteryMarkSixWager wager, LotteryMarkSixWagerStub stub) {
        Double odds = 0.0;
        LotteryMarkSixType wagerType = wager.getLotteryMarkSixType();
        switch (wagerType) {
            case SPECIAL:
            case SUM_ZODIAC:
            case ZHENG_BALL:
                odds = oddsService.getOdds(issue, groupId, stub.getNumber(), wagerType, null, wager.getPanlei()).getOdds();
                break;
            case ZHENG_SPECIFIC_1:
            case ZHENG_SPECIFIC_2:
            case ZHENG_SPECIFIC_3:
            case ZHENG_SPECIFIC_4:
            case ZHENG_SPECIFIC_5:
            case ZHENG_SPECIFIC_6:
                odds = oddsService.getOdds(issue, groupId, 0, wagerType, null, wager.getPanlei()).getOdds();
                break;
            case TAIL_NUM:
                odds = oddsService.getOdds(issue, groupId, stub.getNumber(), wagerType, null, wager.getPanlei()).getOdds();
                break;
            case ZHENG_1_6:
                odds = oddsService.getOdds(issue, groupId, 0, wagerType, stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds();
                break;
            case TWO_FACES:
            case ONE_ZODIAC:
                odds = oddsService.getOdds4LotteryIssueByBallType(issue, groupId, wagerType.name(), wager.getPanlei(), stub.getLotteryMarkSixType().name()).getOdds();
                break;
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
                odds = oddsService.getOdds4LotteryIssueByType(issue, groupId, wagerType.name(), wager.getPanlei()).get(0).getOdds();
                break;
            case NOT_5:
            case NOT_6:
            case NOT_7:
            case NOT_8:
            case NOT_9:
            case NOT_10:
            case NOT_11:
            case NOT_12:
            case JOINT_3_ALL:
            case JOINT_3_2:
            case JOINT_2_ALL:
            case JOINT_2_SPECIAL:
            case JOINT_SPECIAL:
            case JOINT_TAIL_2:
            case JOINT_TAIL_3:
            case JOINT_TAIL_4:
            case JOINT_TAIL_NOT_2:
            case JOINT_TAIL_NOT_3:
            case JOINT_TAIL_NOT_4:
                odds = oddsService.getOdds(issue, groupId, 0, wagerType, null, wager.getPanlei()).getOdds();
                break;
            case JOINT_ZODIAC_PING:
            case JOINT_ZODIAC_ZHENG:
            default:
                odds = oddsService.getOdds(issue, groupId, wager.getLotteryMarkSixWagerStubList().size(), wagerType, null, wager.getPanlei()).getOdds();
                break;
        }
        return odds;
    }

    private List<RealTimeWager> buildStakeDetails4WagerList(List<LotteryMarkSixWager> wagerList) {
        List<RealTimeWager> realTimeWagerList = new ArrayList<>();
        for (LotteryMarkSixWager wager : wagerList) {
            LotteryMarkSixType wagerType = wager.getLotteryMarkSixType();
            switch (wagerType) {
                case SPECIAL:
                case ZHENG_BALL:
                case ZHENG_SPECIFIC_1:
                case ZHENG_SPECIFIC_2:
                case ZHENG_SPECIFIC_3:
                case ZHENG_SPECIFIC_4:
                case ZHENG_SPECIFIC_5:
                case ZHENG_SPECIFIC_6:
                case TAIL_NUM:
                case TWO_FACES:
                case ONE_ZODIAC:
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
                case SUM_ZODIAC:
                case ZHENG_1_6:
                    for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                        RealTimeWager realTimeWager = new RealTimeWager();
                        realTimeWager.setTs(wager.getTimestamp());
                        realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                        LotteryTuishui tuishui = tuishuiService.getTuishui4UserOfType(wager.getUserId(), wager.getPgroupId(), wager.getPanlei(), wager.getLotteryMarkSixType());
                        realTimeWager.setTuishui(tuishui != null ? tuishui.getTuishui() : 0);
                        realTimeWager.setPanlei(wager.getPanlei());
                        realTimeWager.setIssue(wager.getLotteryIssue());
                        LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
                        Date openTs = lotteryMarkSix != null ? lotteryMarkSix.getTimestamp() : new Date();
                        realTimeWager.setOpenTs(openTs);
                        realTimeWager.setWageContent(buildWagerContent(wager, stub));
                        realTimeWager.setStakes(stub.getStakes());
                        realTimeWager.setOdds(getOdds(wager.getLotteryIssue(), wager.getPgroupId(), wager, stub));
                        realTimeWager.setTuishui2(0);
                        realTimeWager.setResult(0);
                        realTimeWager.setRemark("");
                        realTimeWagerList.add(realTimeWager);
                    }
                    break;
                case NOT_5:
                case NOT_6:
                case NOT_7:
                case NOT_8:
                case NOT_9:
                case NOT_10:
                case NOT_11:
                case NOT_12:
                case JOINT_3_ALL:
                case JOINT_3_2:
                case JOINT_2_ALL:
                case JOINT_2_SPECIAL:
                case JOINT_SPECIAL:
                case JOINT_ZODIAC_PING:
                case JOINT_ZODIAC_ZHENG:
                case JOINT_TAIL_2:
                case JOINT_TAIL_3:
                case JOINT_TAIL_4:
                case JOINT_TAIL_NOT_2:
                case JOINT_TAIL_NOT_3:
                case JOINT_TAIL_NOT_4:
                    RealTimeWager realTimeWager = new RealTimeWager();
                    realTimeWager.setTs(wager.getTimestamp());
                    realTimeWager.setUser(userService.getUserById(wager.getUserId()));
                    LotteryTuishui tuishui = tuishuiService.getTuishui4UserOfType(wager.getUserId(), wager.getPgroupId(), wager.getPanlei(), wager.getLotteryMarkSixType());
                    realTimeWager.setTuishui(tuishui != null ? tuishui.getTuishui() : 0);
                    realTimeWager.setPanlei(wager.getPanlei());
                    realTimeWager.setIssue(wager.getLotteryIssue());
                    LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(wager.getLotteryIssue());
                    Date openTs = lotteryMarkSix != null ? lotteryMarkSix.getTimestamp() : new Date();
                    realTimeWager.setOpenTs(openTs);
                    realTimeWager.setWageContent(buildWagerContent(wager, null));
                    realTimeWager.setStakes(wager.getTotalStakes());
                    realTimeWager.setOdds(getOdds(wager.getLotteryIssue(), wager.getPgroupId(), wager, null));
                    realTimeWager.setTuishui2(0);
                    realTimeWager.setResult(0);
                    realTimeWager.setRemark("");
                    realTimeWagerList.add(realTimeWager);
                    break;
                default:
                    break;
            }
        }
        return realTimeWagerList;
    }
}
