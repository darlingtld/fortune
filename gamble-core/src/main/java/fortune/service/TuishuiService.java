package fortune.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.TuishuiDao;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryTuishui;
import fortune.pojo.PGroup;
import fortune.pojo.User;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Service
public class TuishuiService {

    @Autowired
    private TuishuiDao tuishuiDao;

    @Autowired
    private PGroupService pGroupService;

    public void populateTuishui() {
        for (PGroup pGroup : pGroupService.getGroupAll()) {
            for (User user : pGroup.getUserList()) {
                List<LotteryTuishui> existedList = new ArrayList<>();
                for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                    existedList.addAll(getTuishui4User(user.getId(), pGroup.getId(), panlei));
                }

                HashMap<String, LotteryTuishui> existedMap = new HashMap<>();
                for (LotteryTuishui tuishui : existedList) {
                    existedMap.put(getTuishuiKey(tuishui), tuishui);
                }

                List<LotteryTuishui> missingList = generateTuishuiDefault(user.getId(), pGroup.getId()).stream()
                        .filter(tuishui -> !existedMap.containsKey(getTuishuiKey(tuishui)))
                        .collect(Collectors.toList());

                for (LotteryTuishui tuishui : missingList) {
                    saveTuishui(tuishui);
                }
            }
        }

        Utils.logger.info("Populating default tuishui finished");
    }

    private String getTuishuiKey(LotteryTuishui tuishui) {
        return String.format("%s#%s#%s", tuishui.getPanlei(), tuishui.getLotteryMarkSixType(), tuishui.getLotteryBallType());
    }

    @Transactional
    public LotteryTuishui getTuishuiById(String tuishuiId) {
        Utils.logger.info("get tuishui by id {}", tuishuiId);
        return tuishuiDao.getTuishuiById(tuishuiId);
    }

    @Transactional
    public void saveTuishui(LotteryTuishui tuishui) {
        Utils.logger.info("save tuishui");
        tuishuiDao.saveTuishui(tuishui);
    }

    @Transactional
    public List<LotteryTuishui> getAll() {
        Utils.logger.info("get all lottery tuishui");
        return tuishuiDao.getAll();
    }

    @Transactional
    public List<LotteryTuishui> getTuishui4User(String userId, String groupId, String panlei) {
        Utils.logger.info("get tuishui for user {} of group id {} of panlei {}", userId, groupId, panlei);
        List<LotteryTuishui> tuishuiList = tuishuiDao.getTuishui4User(userId, groupId, panlei);

        for (LotteryTuishui tuishui : tuishuiList) {
            tuishui.setLotteryTypeName(tuishui.getLotteryMarkSixType().getType());
        }
        return tuishuiList;
    }

    @Transactional
    public LotteryTuishui getTuishui4UserOfType(String userId, String groupId, String panlei, LotteryMarkSixType type) {
        Utils.logger.info("get tuishui for user {} of group id {} of panlei {} of type {}", userId, groupId, panlei, type);
        return tuishuiDao.getTuishui4UserOfType(userId, groupId, panlei, type);

    }

    public List<LotteryTuishui> generateTuishuiDefault(String userId, String groupId) {
        Utils.logger.info("generate tuishui default for user {} groupId {}", userId, groupId);
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
//        特码退水
        tuishuiList.addAll(generateSpecialLotteryTuishui(userId, groupId));
//        生肖退水
        tuishuiList.addAll(generateZodiacLotteryTuishui(groupId, userId));
//        半波退水
        tuishuiList.addAll(generateHalfWaveLotteryTuishui(groupId, userId));
//        连码退水
        tuishuiList.addAll(generateJointLotteryTuishui(groupId, userId));
//        合肖退水
        tuishuiList.addAll(generateSumZodiacLotteryTuishui(groupId, userId));
//        正码1~6退水
        tuishuiList.addAll(generateZheng16LotteryTuishui(groupId, userId));
//        正码退水
        tuishuiList.addAll(generateZhengLotteryTuishui(groupId, userId));
//        正码特退水
        tuishuiList.addAll(generateZhengSpecificLotteryTuishui(groupId, userId));
//        过关退水
        tuishuiList.addAll(generateOneZodiacLotteryTuishui(groupId, userId));
//      自选不中退水
        tuishuiList.addAll(generateNotLotteryTuishui(groupId, userId));
//        一肖退水
        tuishuiList.addAll(generatePassBallLotteryTuishui(groupId, userId));
//        尾数退水
        tuishuiList.addAll(generateTailBallLotteryTuishui(groupId, userId));
//        色波退水
        tuishuiList.addAll(generateColorLotteryTuishui(groupId, userId));
//        两面退水
        tuishuiList.addAll(generateTwoFaceLotteryTuishui(groupId, userId));
//        连肖退水
        tuishuiList.addAll(generateJointZodiacLotteryTuishui(groupId, userId));
//        连尾退水
        tuishuiList.addAll(generateJointTailLotteryTuishui(groupId, userId));
        return tuishuiList;
    }

    //    生成连尾退水
    private List<LotteryTuishui> generateJointTailLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 10.0;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryMarkSixType.getRealTimeJointTailList().stream().forEach(type -> {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setPanlei(panlei);
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            });
        }
        return tuishuiList;
    }

    //    生成连肖退水
    private List<LotteryTuishui> generateJointZodiacLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 10.0;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryMarkSixType.getRealTimeJointZodiacList().stream().forEach(type -> {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
            });
        }
        return tuishuiList;
    }

    //    生成两面退水
    private List<LotteryTuishui> generateTwoFaceLotteryTuishui(String groupId, String userId) {
        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 3.0);
        tuishuiMap.put("B", 4.0);
        tuishuiMap.put("C", 5.0);
        tuishuiMap.put("D", 6.0);

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setPanlei(panlei);
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.TWO_FACES);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成色波退水
    private List<LotteryTuishui> generateColorLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 3.0;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.COLOR);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;

    }

    private List<LotteryTuishui> generateOneZodiacLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 2.5;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.ONE_ZODIAC);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateTailBallLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 3.0;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.TAIL_NUM);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generatePassBallLotteryTuishui(String groupId, String userId) {
        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 2.5);
        tuishuiMap.put("B", 2.5);
        tuishuiMap.put("C", 2.5);
        tuishuiMap.put("D", 2.5);

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.PASS);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateZhengSpecificLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();

        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 13.0);
        tuishuiMap.put("B", 14.0);
        tuishuiMap.put("C", 15.0);
        tuishuiMap.put("D", 16.0);

        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_SPECIFIC);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateZhengLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 11.5;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_BALL);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateSpecialLotteryTuishui(String userId, String groupId) {
        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 13.0);
        tuishuiMap.put("B", 14.0);
        tuishuiMap.put("C", 15.0);
        tuishuiMap.put("D", 16.0);

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.SPECIAL);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateZodiacLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 3.5;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成半波退水
    private List<LotteryTuishui> generateHalfWaveLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 3.0;

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.HALF_WAVE);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成合肖退水
    private List<LotteryTuishui> generateSumZodiacLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();

        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 3.0);
        tuishuiMap.put("B", 4.0);
        tuishuiMap.put("C", 5.0);
        tuishuiMap.put("D", 6.0);

        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成正码1到6退水
    private List<LotteryTuishui> generateZheng16LotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();

        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 3.0);
        tuishuiMap.put("B", 4.0);
        tuishuiMap.put("C", 5.0);
        tuishuiMap.put("D", 6.0);

        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiMap.get(panlei));
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_1_6);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成连码退水
    private List<LotteryTuishui> generateJointLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 15.0;  //各类型值相同

        List<LotteryMarkSixType> typeList = Arrays.asList(
                LotteryMarkSixType.JOINT_3_ALL,
                LotteryMarkSixType.JOINT_3_2,
                LotteryMarkSixType.JOINT_2_ALL,
                LotteryMarkSixType.JOINT_2_SPECIAL,
                LotteryMarkSixType.JOINT_SPECIAL
        );

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (LotteryMarkSixType type : typeList) {
            for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    // 生成自选不中退水
    private List<LotteryTuishui> generateNotLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 2.0;

        List<LotteryMarkSixType> typeList = Arrays.asList(
                LotteryMarkSixType.NOT_5,
                LotteryMarkSixType.NOT_6,
                LotteryMarkSixType.NOT_7,
                LotteryMarkSixType.NOT_8,
                LotteryMarkSixType.NOT_9,
                LotteryMarkSixType.NOT_10,
                LotteryMarkSixType.NOT_11,
                LotteryMarkSixType.NOT_12
        );

        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (LotteryMarkSixType type : typeList) {
            for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    @Transactional
    public LotteryTuishui changeTuishui(String tuishuiId, double tuishui) {
        Utils.logger.info("change tuishui of tuishui id {} to tuishui {}", tuishuiId, tuishui);
        return tuishuiDao.changeTuishui(tuishuiId, tuishui);
    }
}
