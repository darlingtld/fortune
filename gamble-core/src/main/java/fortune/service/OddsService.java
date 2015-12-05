package fortune.service;

import common.Utils;
import fortune.dao.OddsDao;
import fortune.pojo.LotteryBall;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryOdds;
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
    public List<LotteryOdds> getOdds4LotteryIssue(int lotteryIssue, String groupId) {
        Utils.logger.info("get odds for lottery issue {} of group id {}", lotteryIssue, groupId);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId);
    }

    @Transactional
    public LotteryOdds getOdds4LotteryIssue(int lotteryIssue, String groupId, int number) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of ball {}", lotteryIssue, groupId, number);
        return oddsDao.getOdds4LotteryIssue(lotteryIssue, groupId, number);
    }

    @Transactional
    public LotteryOdds getOdds4LotteryIssueByType(int lotteryIssue, String groupId, String lotteryMarkSixType) {
        Utils.logger.info("get odds for lottery issue {} of group id {} of type {}", lotteryIssue, groupId, lotteryMarkSixType);
        return oddsDao.getOdds4LotteryIssueByType(lotteryIssue, groupId, lotteryMarkSixType);
    }

    @Transactional
    public List<LotteryOdds> getOdds4LotteryIssue(int issue) {
        Utils.logger.info("get odds for lottery issue {}", issue);
        return oddsDao.getOdds4LotteryIssue(issue);
    }

    public List<LotteryOdds> generateOddsDefault(String groupId, int lotteryIssue) {
        Utils.logger.info("get odds default for groupId {} of issue {}", groupId, lotteryIssue);
        List<LotteryOdds> oddsList = new ArrayList<>();
        oddsList.addAll(generateSpecialLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateZodiacLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateHalfWaveLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateJointLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateNotLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateSumZodiacLotteryOdds(groupId, lotteryIssue));
        oddsList.addAll(generateZheng16LotteryOdds(groupId, lotteryIssue));
        return oddsList;
    }

    private List<LotteryOdds> generateSpecialLotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        oddsList.add(new LotteryOdds(LotteryBall.NUM_1.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_2.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_3.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_4.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_5.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_6.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_7.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_8.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_9.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_10.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_11.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_12.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_13.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_14.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_15.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_16.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_17.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_18.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_19.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_20.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_21.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_22.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_23.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_24.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_25.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_26.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_27.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_28.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_29.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_30.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_31.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_32.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_33.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_34.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_35.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_36.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_37.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_38.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_39.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_40.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_41.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_42.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_43.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_44.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_45.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_46.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_47.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_48.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        oddsList.add(new LotteryOdds(LotteryBall.NUM_49.getNumber(), LotteryMarkSixType.SPECIAL, 42.5, groupId, lotteryIssue, new Date(), LotteryMarkSixType.SPECIAL));
        return oddsList;
    }

    private List<LotteryOdds> generateZodiacLotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_GOU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HOU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_HU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_JI);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_LONG);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_MA);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_NIU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHE);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_SHU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_TU);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_YANG);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(1);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.ZODIAC_ZHU);
        oddsList.add(odds);
        return oddsList;
    }


    // 生成半波赔率
    private List<LotteryOdds> generateHalfWaveLotteryOdds(String groupId, int lotteryIssue) {
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
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryMarkSixType(type);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成合肖赔率
    private List<LotteryOdds> generateSumZodiacLotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (int j = 1; j <= 11; j++) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(20);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallNumber(j);
            odds.setLotteryMarkSixType(LotteryMarkSixType.SUM_ZODIAC);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成正码1到6赔率
    private List<LotteryOdds> generateZheng16LotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        for (LotteryMarkSixType type : Arrays.asList(LotteryMarkSixType.DA, LotteryMarkSixType.XIAO,
                LotteryMarkSixType.DAN, LotteryMarkSixType.SHUANG, LotteryMarkSixType.RED, LotteryMarkSixType.BLUE,
                LotteryMarkSixType.GREEN, LotteryMarkSixType.HEDAN, LotteryMarkSixType.HESHUANG)) {
            LotteryOdds odds = new LotteryOdds();
            odds.setGroupId(groupId);
            odds.setOdds(30);
            odds.setLotteryIssue(lotteryIssue);
            odds.setTimestamp(new Date());
            odds.setLotteryBallType(type);
            odds.setLotteryMarkSixType(LotteryMarkSixType.ZHENG_1_6);
            oddsList.add(odds);
        }
        return oddsList;
    }

    // 生成连码赔率
    private List<LotteryOdds> generateJointLotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(10);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_ALL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(20);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_2_SPECIAL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(30);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_2);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(40);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_3_ALL);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(50);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.JOINT_SPECIAL);
        oddsList.add(odds);
        return oddsList;
    }

    // 生成自选不中赔率
    private List<LotteryOdds> generateNotLotteryOdds(String groupId, int lotteryIssue) {
        List<LotteryOdds> oddsList = new ArrayList<>();
        LotteryOdds odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(10);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_5);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(20);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_6);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(30);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_7);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(40);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_8);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(50);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_9);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(60);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_10);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(70);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_11);
        oddsList.add(odds);
        odds = new LotteryOdds();
        odds.setGroupId(groupId);
        odds.setOdds(80);
        odds.setLotteryIssue(lotteryIssue);
        odds.setTimestamp(new Date());
        odds.setLotteryMarkSixType(LotteryMarkSixType.NOT_12);
        oddsList.add(odds);
        return oddsList;
    }
}
