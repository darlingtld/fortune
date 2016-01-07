package fortune.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import common.Utils;
import fortune.dao.TuishuiDao;
import fortune.pojo.LotteryBall;
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

    @PostConstruct
    public void populateTuishui() {
        tuishuiDao.removeAll();
        
        for (PGroup pGroup : pGroupService.getGroupAll()) {
            for (User user : pGroup.getUserList()) {
                List<LotteryTuishui> tuishuiList = generateTuishuiDefault(user.getId(), pGroup.getId());
                for (LotteryTuishui tuishui : tuishuiList) {
                    saveTuishui(tuishui);
                }
            }
        }

        Utils.logger.info("Populating default tuishui finished");
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
        Utils.logger.info("get odds for user {} of group id {} of panlei {}", userId, groupId, panlei);
        return tuishuiDao.getTuishui4User(userId, groupId, panlei);
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
            for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.JOINT_TAIL_2,
                    LotteryMarkSixType.JOINT_TAIL_3, LotteryMarkSixType.JOINT_TAIL_4,
                    LotteryMarkSixType.JOINT_TAIL_NOT_2, LotteryMarkSixType.JOINT_TAIL_NOT_3,
                    LotteryMarkSixType.JOINT_TAIL_NOT_4)) {
                for (int ball : Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)) {
                    LotteryTuishui tuishui = new LotteryTuishui();
                    tuishui.setGroupId(groupId);
                    tuishui.setTuishui(tuishuiVal);
                    tuishui.setUserId(userId);
                    tuishui.setTimestamp(new Date());
                    tuishui.setPanlei(panlei);
                    tuishui.setLotteryMarkSixType(type);
                    tuishui.setLotteryBallNumber(ball);
                    tuishuiList.add(tuishui);
                }
            }
        }
        return tuishuiList;
    }

    //    生成连肖退水
    private List<LotteryTuishui> generateJointZodiacLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 10.0;
        
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.ZODIAC_SHU, LotteryMarkSixType.ZODIAC_NIU,
                    LotteryMarkSixType.ZODIAC_HU, LotteryMarkSixType.ZODIAC_TU, LotteryMarkSixType.ZODIAC_LONG,
                    LotteryMarkSixType.ZODIAC_SHE, LotteryMarkSixType.ZODIAC_MA, LotteryMarkSixType.ZODIAC_YANG,
                    LotteryMarkSixType.ZODIAC_HOU, LotteryMarkSixType.ZODIAC_JI, LotteryMarkSixType.ZODIAC_GOU,
                    LotteryMarkSixType.ZODIAC_ZHU)) {
    //            平肖
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_PING);
                tuishui.setLotteryBallNumber(2); // 2连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_PING);
                tuishui.setLotteryBallNumber(3); // 3连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_PING);
                tuishui.setLotteryBallNumber(4); // 4连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_PING);
                tuishui.setLotteryBallNumber(5); // 5连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
    //            正肖
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_ZHENG);
                tuishui.setLotteryBallNumber(2); // 2连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_ZHENG);
                tuishui.setLotteryBallNumber(3); // 3连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_ZHENG);
                tuishui.setLotteryBallNumber(4); // 4连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_ZODIAC_ZHENG);
                tuishui.setLotteryBallNumber(5); // 5连肖
                tuishui.setPanlei(panlei);
                tuishuiList.add(tuishui);
            }
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
            for (LotteryMarkSixType subType : Arrays.asList(LotteryMarkSixType.DAN, LotteryMarkSixType.SHUANG, LotteryMarkSixType.DA, LotteryMarkSixType.XIAO, LotteryMarkSixType.HEDAN, LotteryMarkSixType.HESHUANG, LotteryMarkSixType.HEDA, LotteryMarkSixType.HEXIAO, LotteryMarkSixType.WEIDA, LotteryMarkSixType.WEIXIAO, LotteryMarkSixType.HEWEIDA, LotteryMarkSixType.HEWEIXIAO, LotteryMarkSixType.JIAQIN, LotteryMarkSixType.YESHOU)) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setPanlei(panlei);
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.TWO_FACES);
                tuishui.setLotteryBallType(subType);
                tuishuiList.add(tuishui);
            }
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
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.BLUE);
            tuishuiList.add(tuishui);
    
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.GREEN);
            tuishuiList.add(tuishui);
    
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.RED);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;

    }

    private List<LotteryTuishui> generatePassBallLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 2.5;
        
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.ZODIAC_SHU, LotteryMarkSixType.ZODIAC_NIU,
                    LotteryMarkSixType.ZODIAC_HU, LotteryMarkSixType.ZODIAC_TU, LotteryMarkSixType.ZODIAC_LONG,
                    LotteryMarkSixType.ZODIAC_SHE, LotteryMarkSixType.ZODIAC_MA, LotteryMarkSixType.ZODIAC_YANG,
                    LotteryMarkSixType.ZODIAC_HOU, LotteryMarkSixType.ZODIAC_JI, LotteryMarkSixType.ZODIAC_GOU,
                    LotteryMarkSixType.ZODIAC_ZHU)) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type); // 二级类型
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.ONE_ZODIAC);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateTailBallLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 0.0;
        
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            for (int num : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallNumber(num); // 表示几尾
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.TAIL_NUM);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateOneZodiacLotteryTuishui(String groupId, String userId) {
        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 3.0);
        tuishuiMap.put("B", 4.0);
        tuishuiMap.put("C", 5.0);
        tuishuiMap.put("D", 6.0);
        
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.PASS_BLUE, LotteryMarkSixType.PASS_DA,
                    LotteryMarkSixType.PASS_DAN, LotteryMarkSixType.PASS_GREEN, LotteryMarkSixType.PASS_RED,
                    LotteryMarkSixType.PASS_SHUANG, LotteryMarkSixType.PASS_XIAO)) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            }
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
            for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.ZHENG_SPECIFIC_1, LotteryMarkSixType.ZHENG_SPECIFIC_2, LotteryMarkSixType.ZHENG_SPECIFIC_3,
                    LotteryMarkSixType.ZHENG_SPECIFIC_4, LotteryMarkSixType.ZHENG_SPECIFIC_5, LotteryMarkSixType.ZHENG_SPECIFIC_6)) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateZhengLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        Double tuishuiVal = 11.5;
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            for (LotteryBall ball : LotteryBall.values()) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setLotteryBallNumber(ball.getNumber());
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiVal);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_BALL);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    private List<LotteryTuishui> generateSpecialLotteryTuishui(String userId, String groupId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        Arrays.asList(LotteryBall.values()).stream().forEach(ball -> {
            tuishuiList.add(new LotteryTuishui(ball.getNumber(), LotteryMarkSixType.SPECIAL, 13, groupId, userId, new Date(), LotteryMarkSixType.SPECIAL, "A"));
            tuishuiList.add(new LotteryTuishui(ball.getNumber(), LotteryMarkSixType.SPECIAL, 14, groupId, userId, new Date(), LotteryMarkSixType.SPECIAL, "B"));
            tuishuiList.add(new LotteryTuishui(ball.getNumber(), LotteryMarkSixType.SPECIAL, 15, groupId, userId, new Date(), LotteryMarkSixType.SPECIAL, "C"));
            tuishuiList.add(new LotteryTuishui(ball.getNumber(), LotteryMarkSixType.SPECIAL, 16, groupId, userId, new Date(), LotteryMarkSixType.SPECIAL, "D"));
        });
        return tuishuiList;
    }

    private List<LotteryTuishui> generateZodiacLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        LotteryMarkSixType.getRealTimeAnimalList().stream().forEach(type -> {
            for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(3.5);
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryMarkSixType(type);
                tuishuiList.add(tuishui);
            }
        });
        return tuishuiList;
    }

    // 生成半波退水
    private List<LotteryTuishui> generateHalfWaveLotteryTuishui(String groupId, String userId) {
        Double tuishuiVal = 3.0;
        
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.WAVE_BLUE_DA,
                LotteryMarkSixType.WAVE_BLUE_DAN, LotteryMarkSixType.WAVE_BLUE_SHUANG,
                LotteryMarkSixType.WAVE_BLUE_XIAO, LotteryMarkSixType.WAVE_GREEN_DA,
                LotteryMarkSixType.WAVE_GREEN_DAN, LotteryMarkSixType.WAVE_GREEN_SHUANG,
                LotteryMarkSixType.WAVE_GREEN_XIAO, LotteryMarkSixType.WAVE_RED_DA, LotteryMarkSixType.WAVE_RED_DAN,
                LotteryMarkSixType.WAVE_RED_SHUANG, LotteryMarkSixType.WAVE_RED_XIAO)) {
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

    // 生成合肖退水
    private List<LotteryTuishui> generateSumZodiacLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        
        Map<String, Double> tuishuiMap = new HashMap<>();
        tuishuiMap.put("A", 3.0);
        tuishuiMap.put("B", 4.0);
        tuishuiMap.put("C", 5.0);
        tuishuiMap.put("D", 6.0);
        
        for (int j = 1; j <= 11; j++) {
            for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallNumber(j * 10 + 1);
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
                tuishuiList.add(tuishui);
    
                tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallNumber(j * 10);
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
                tuishuiList.add(tuishui);
            }
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
        
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.DA, LotteryMarkSixType.XIAO,
                LotteryMarkSixType.DAN, LotteryMarkSixType.SHUANG, LotteryMarkSixType.RED, LotteryMarkSixType.BLUE,
                LotteryMarkSixType.GREEN, LotteryMarkSixType.HEDAN, LotteryMarkSixType.HESHUANG)) {
            for (String panlei : Arrays.asList("A", "B", "C", "D")) {
                LotteryTuishui tuishui = new LotteryTuishui();
                tuishui.setGroupId(groupId);
                tuishui.setTuishui(tuishuiMap.get(panlei));
                tuishui.setPanlei(panlei);
                tuishui.setUserId(userId);
                tuishui.setTimestamp(new Date());
                tuishui.setLotteryBallType(type);
                tuishui.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_1_6);
                tuishuiList.add(tuishui);
            }
        }
        return tuishuiList;
    }

    // 生成连码退水
    private List<LotteryTuishui> generateJointLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        
        Double tuishuiVal = 15.0;  //各类型值相同
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_ALL);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_SPECIAL);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_2);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_ALL);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.JOINT_SPECIAL);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    // 生成自选不中退水
    private List<LotteryTuishui> generateNotLotteryTuishui(String groupId, String userId) {
        List<LotteryTuishui> tuishuiList = new ArrayList<>();
        
        Double tuishuiVal = 2.0;
        for (String panlei : Arrays.asList("A", "B", "C", "D")) {
            LotteryTuishui tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_5);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_6);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_7);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_8);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_9);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_10);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_11);
            tuishuiList.add(tuishui);
            tuishui = new LotteryTuishui();
            tuishui.setGroupId(groupId);
            tuishui.setTuishui(tuishuiVal);
            tuishui.setPanlei(panlei);
            tuishui.setUserId(userId);
            tuishui.setTimestamp(new Date());
            tuishui.setLotteryMarkSixType(LotteryMarkSixType.NOT_12);
            tuishuiList.add(tuishui);
        }
        return tuishuiList;
    }

    @Transactional
    public LotteryTuishui changeTuishui(String tuishuiId, double tuishui) {
        Utils.logger.info("change tuishui of tuishui id {} to tuishui {}", tuishuiId, tuishui);
        return tuishuiDao.changeTuishui(tuishuiId, tuishui);
    }
}
