package fortune.service;

import common.Utils;
import fortune.dao.OddsDao;
import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
import fortune.pojo.PGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-16.
 */
@Service
public class OddsService {

    @Autowired
    private OddsDao oddsDao;

    @Transactional
    public LotteryOdds getOddsById(String oddsId) {
        Utils.logger.info("get odds by id {}", oddsId);
        return oddsDao.getOddsById(oddsId);
    }

    @Transactional
    public void saveOdds(LotteryOdds odds) {
        Utils.logger.info("save odds");
        oddsDao.saveOdds(odds);
    }

    @Transactional
    public List<LotteryOdds> getAll() {
        Utils.logger.info("get all lottery odds");
        return oddsDao.getAll();
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssue(int lotteryIssue, String groupId, String panlei) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of panlei {}", lotteryIssue, groupId, panlei);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId, panlei);
    }

    @Transactional
    public LotteryOdds getOdds4LotteryIssue(int lotteryIssue, String groupId, int number, String panlei) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of ball {} of panlei {}", lotteryIssue, groupId, number, panlei);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId, number, panlei);
    }

    @Transactional
    public LotteryOdds getOdds(int lotteryIssue, String groupId, int number, LotteryMarkSixType type, String panlei) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of ball {} of type {} of panlei {}", lotteryIssue, groupId, number, type, panlei);
        return oddsDao.getOdds(lotteryIssue, groupId, number, type, panlei);
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssueByType(int lotteryIssue, String groupId, String lotteryMarkSixType, String panlei) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of type {} of panlei {}", lotteryIssue, groupId, lotteryMarkSixType, panlei);
        return oddsDao.getOdds4LotteryIssueByType(lotteryIssue, groupId, lotteryMarkSixType, panlei);
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssue(int issue, String panlei) {
        Utils.logger.info("get odds for lottery issue {} of panlei {}", issue, panlei);
        return oddsDao.getOdds4LotteryIssue(issue, panlei);
    }

    public List<LotteryOdds> generateOddsDefault(String groupId, int lotteryIssue, String panlei) {
        Utils.logger.info("generate odds default for groupId {} of issue {} of panlei {}", groupId, lotteryIssue, panlei);
        List<LotteryOdds> oddsList = new ArrayList<>();
//        特码赔率
        oddsList.addAll(generateSpecialLotteryOdds(groupId, lotteryIssue, panlei));
//        生肖赔率
        oddsList.addAll(generateZodiacLotteryOdds(groupId, lotteryIssue, panlei));
//        半波赔率
        oddsList.addAll(generateHalfWaveLotteryOdds(groupId, lotteryIssue, panlei));
//        连码赔率
        oddsList.addAll(generateJointLotteryOdds(groupId, lotteryIssue, panlei));
//        自选不中赔率
        oddsList.addAll(generateNotLotteryOdds(groupId, lotteryIssue, panlei));
//        合肖赔率
        oddsList.addAll(generateSumZodiacLotteryOdds(groupId, lotteryIssue, panlei));
//        正码1~6赔率
        oddsList.addAll(generateZheng16LotteryOdds(groupId, lotteryIssue, panlei));
//        正码赔率
        oddsList.addAll(generateZhengLotteryOdds(groupId, lotteryIssue, panlei));
//        正码特赔率
        oddsList.addAll(generateZhengSpecificLotteryOdds(groupId, lotteryIssue, panlei));
//        过关赔率
        oddsList.addAll(generateOneZodiacLotteryOdds(groupId, lotteryIssue, panlei));
//        一肖赔率
        oddsList.addAll(generatePassBallLotteryOdds(groupId, lotteryIssue, panlei));
//        尾数赔率
        oddsList.addAll(generateTailBallLotteryOdds(groupId, lotteryIssue, panlei));
//        色波赔率
        oddsList.addAll(generateColorLotteryOdds(groupId, lotteryIssue, panlei));
        return oddsList;
    }

    // 生成色波赔率
    private List<LotteryOdds> generateColorLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(20);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.BLUE);
        oddsList.add(odds);

        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(21);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.GREEN);
        oddsList.add(odds);

        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(22);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.RED);
        oddsList.add(odds);
        return oddsList;

    }

    private List<LotteryOdds> generatePassBallLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.ZODIAC_SHU, LotteryMarkSixType.ZODIAC_NIU,
                LotteryMarkSixType.ZODIAC_HU, LotteryMarkSixType.ZODIAC_TU, LotteryMarkSixType.ZODIAC_LONG,
                LotteryMarkSixType.ZODIAC_SHE, LotteryMarkSixType.ZODIAC_MA, LotteryMarkSixType.ZODIAC_YANG,
                LotteryMarkSixType.ZODIAC_HOU, LotteryMarkSixType.ZODIAC_JI, LotteryMarkSixType.ZODIAC_GOU,
                LotteryMarkSixType.ZODIAC_ZHU)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(19);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallType(type); // 二级类型
            odds.setLotteryMarkSixType(LotteryMarkSixType.ONE_ZODIAC);
            oddsList.add(odds);
        }
        return oddsList;
    }

    private List<LotteryOdds> generateTailBallLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (int num : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 0)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(12);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallNumber(num); // 表示几尾
            odds.setLotteryMarkSixType(LotteryMarkSixType.TAIL_NUM);
            oddsList.add(odds);
        }
        return oddsList;
    }


    private List<LotteryOdds> generateOneZodiacLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.PASS_BLUE, LotteryMarkSixType.PASS_DA,
                LotteryMarkSixType.PASS_DAN, LotteryMarkSixType.PASS_GREEN, LotteryMarkSixType.PASS_RED,
                LotteryMarkSixType.PASS_SHUANG, LotteryMarkSixType.PASS_XIAO)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(12);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(type);
            oddsList.add(odds);
        }
        return oddsList;
    }

    private List<LotteryOdds> generateZhengSpecificLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.ZHENG_SPECIFIC_1, LotteryMarkSixType.ZHENG_SPECIFIC_2, LotteryMarkSixType.ZHENG_SPECIFIC_3,
                LotteryMarkSixType.ZHENG_SPECIFIC_4, LotteryMarkSixType.ZHENG_SPECIFIC_5, LotteryMarkSixType.ZHENG_SPECIFIC_6)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(22);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(type);
            oddsList.add(odds);
        }
        return oddsList;
    }

    private List<LotteryOdds> generateZhengLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryBall ball : LotteryBall.values()) {
            LotteryOdds odds = new LotteryOdds();
            odds.setLotteryBallNumber(ball.getNumber());
            odds.setGroupId(groupId);
            odds.setOdds(23);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_BALL);
            oddsList.add(odds);
        }
        return oddsList;
    }

    private List<LotteryOdds> generateSpecialLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        oddsList.add(new LotteryOdds(LotteryBall.NUM_1.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_2.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_3.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_4.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_5.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_6.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_7.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_8.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_9.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_10.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_11.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_12.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_13.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_14.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_15.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_16.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_17.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_18.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_19.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_20.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_21.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_22.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_23.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_24.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_25.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_26.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_27.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_28.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_29.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_30.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_31.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_32.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_33.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_34.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_35.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_36.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_37.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_38.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_39.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_40.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_41.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_42.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_43.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_44.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_45.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_46.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_47.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_48.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_49.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL, panlei));
        return oddsList;
    }

    private List<LotteryOdds> generateZodiacLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_GOU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HOU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_JI);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_LONG);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_MA);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_NIU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHE);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_TU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_YANG);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_ZHU);
        oddsList.add(odds);
        return oddsList;
    }


    // 生成半波赔率
    private List<LotteryOdds> generateHalfWaveLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.WAVE_BLUE_DA,
                LotteryMarkSixType.WAVE_BLUE_DAN, LotteryMarkSixType.WAVE_BLUE_SHUANG,
                LotteryMarkSixType.WAVE_BLUE_XIAO, LotteryMarkSixType.WAVE_GREEN_DA,
                LotteryMarkSixType.WAVE_GREEN_DAN, LotteryMarkSixType.WAVE_GREEN_SHUANG,
                LotteryMarkSixType.WAVE_GREEN_XIAO, LotteryMarkSixType.WAVE_RED_DA, LotteryMarkSixType.WAVE_RED_DAN,
                LotteryMarkSixType.WAVE_RED_SHUANG, LotteryMarkSixType.WAVE_RED_XIAO)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(10);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(type);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成合肖赔率
    private List<LotteryOdds> generateSumZodiacLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (int j = 1; j <= 11; j++) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(20);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallNumber(j * 10 + 1);
            odds.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
            oddsList.add(odds);

            odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(20);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallNumber(j * 10);
            odds.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成正码1到6赔率
    private List<LotteryOdds> generateZheng16LotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.DA, LotteryMarkSixType.XIAO,
                LotteryMarkSixType.DAN, LotteryMarkSixType.SHUANG, LotteryMarkSixType.RED, LotteryMarkSixType.BLUE,
                LotteryMarkSixType.GREEN, LotteryMarkSixType.HEDAN, LotteryMarkSixType.HESHUANG)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(30);
            odds.setPanlei(panlei);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallType(type);
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_1_6);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成连码赔率
    private List<LotteryOdds> generateJointLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(10);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_ALL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(20);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_SPECIAL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(30);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_2);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(40);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_ALL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(50);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_SPECIAL);
        oddsList.add(odds);
        return oddsList;
    }

    // 生成自选不中赔率
    private List<LotteryOdds> generateNotLotteryOdds(String groupId, int lotteryIssue, String panlei) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(10);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_5);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(20);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_6);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(30);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_7);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(40);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_8);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(50);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_9);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(60);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_10);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(70);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_11);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(80);
        odds.setPanlei(panlei);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_12);
        oddsList.add(odds);
        return oddsList;
    }

    @Transactional
    public LotteryOdds changeOdds(String oddsId, double odds) {
        Utils.logger.info("change odds of odds id {} to odds {}", oddsId, odds);
        return oddsDao.changeOdds(oddsId, odds);
    }
}
