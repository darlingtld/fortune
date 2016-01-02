package fortune.job;

import common.Utils;
import fortune.pojo.JobTracker;
import fortune.rule.*;
import fortune.service.JobTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangl9 on 2015-10-26.
 */
@Component
public class LotteryResultJob {

    public static final int TOTAL_LOTTERY_RESULT_JOBS = 39;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(TOTAL_LOTTERY_RESULT_JOBS));

    @Autowired
    private RuleSPECIAL ruleSPECIAL;
    @Autowired
    private RuleZheng1to6 ruleZheng1to6;
    @Autowired
    private RuleTWOFACES ruleTWOFACES;
    @Autowired
    private RuleZodiacShu ruleZodiacShu;
    @Autowired
    private RuleZodiacNiu ruleZodiacNiu;
    @Autowired
    private RuleZodiacHu ruleZodiacHu;
    @Autowired
    private RuleZodiacTu ruleZodiacTu;
    @Autowired
    private RuleZodiacLong ruleZodiacLong;
    @Autowired
    private RuleZodiacShe ruleZodiacShe;
    @Autowired
    private RuleZodiacMa ruleZodiacMa;
    @Autowired
    private RuleZodiacYang ruleZodiacYang;
    @Autowired
    private RuleZodiacHou ruleZodiacHou;
    @Autowired
    private RuleZodiacJi ruleZodiacJi;
    @Autowired
    private RuleZodiacGou ruleZodiacGou;
    @Autowired
    private RuleZodiacZhu ruleZodiacZhu;
    @Autowired
    private RuleRed ruleRed;
    @Autowired
    private RuleGreen ruleGreen;
    @Autowired
    private RuleBlue ruleBlue;
    @Autowired
    private RuleWaveBlueDa ruleWaveBlueDa;
    @Autowired
    private RuleWaveBlueDan ruleWaveBlueDan;
    @Autowired
    private RuleWaveBlueShuang ruleWaveBlueShuang;
    @Autowired
    private RuleWaveBlueXiao ruleWaveBlueXiao;
    @Autowired
    private RuleWaveGreenDa ruleWaveGreenDa;
    @Autowired
    private RuleWaveGreenDan ruleWaveGreenDan;
    @Autowired
    private RuleWaveGreenShuang ruleWaveGreenShuang;
    @Autowired
    private RuleWaveGreenXiao ruleWaveGreenXiao;
    @Autowired
    private RuleWaveRedDa ruleWaveRedDa;
    @Autowired
    private RuleWaveRedDan ruleWaveRedDan;
    @Autowired
    private RuleWaveRedShuang ruleWaveRedShuang;
    @Autowired
    private RuleWaveRedXiao ruleWaveRedXiao;
    @Autowired
    private RuleSumZodiac ruleSumZodiac;
    @Autowired
    private RuleZhengBall ruleZhengBall;
    @Autowired
    private RuleZhengSpecific1 ruleZhengSpecific1;
    @Autowired
    private RuleZhengSpecific2 ruleZhengSpecific2;
    @Autowired
    private RuleZhengSpecific3 ruleZhengSpecific3;
    @Autowired
    private RuleZhengSpecific4 ruleZhengSpecific4;
    @Autowired
    private RuleZhengSpecific5 ruleZhengSpecific5;
    @Autowired
    private RuleZhengSpecific6 ruleZhengSpecific6;
    @Autowired
    private RuleJoint3All ruleJoint3All;
    @Autowired
    private RuleJoint32 ruleJoint32;
    @Autowired
    private RuleJoint2All ruleJoint2All;
    @Autowired
    private RuleJoint2Special ruleJoint2Special;
    @Autowired
    private RuleJointSpecial ruleJointSpecial;
    @Autowired
    private RuleNot5 ruleNot5;
    @Autowired
    private RuleNot6 ruleNot6;
    @Autowired
    private RuleNot7 ruleNot7;
    @Autowired
    private RuleNot8 ruleNot8;
    @Autowired
    private RuleNot9 ruleNot9;
    @Autowired
    private RuleNot10 ruleNot10;
    @Autowired
    private RuleNot11 ruleNot11;
    @Autowired
    private RuleNot12 ruleNot12;
    @Autowired
    private RulePass rulePass;
    @Autowired
    private RuleOneZodiac ruleOneZodiac;
    @Autowired
    private RuleTailNum ruleTailNum;


    public void calculateLotteryResult() {
        Utils.logger.info("start to calculate lottery result at {}", Utils.yyyyMMddHHmmss2Format(new Date()));
        threadPoolExecutor.submit(ruleSPECIAL);
        threadPoolExecutor.submit(ruleTWOFACES);
        threadPoolExecutor.submit(ruleZheng1to6);
        threadPoolExecutor.submit(ruleZodiacShu);
        threadPoolExecutor.submit(ruleZodiacNiu);
        threadPoolExecutor.submit(ruleZodiacHu);
        threadPoolExecutor.submit(ruleZodiacTu);
        threadPoolExecutor.submit(ruleZodiacLong);
        threadPoolExecutor.submit(ruleZodiacShe);
        threadPoolExecutor.submit(ruleZodiacMa);
        threadPoolExecutor.submit(ruleZodiacYang);
        threadPoolExecutor.submit(ruleZodiacHou);
        threadPoolExecutor.submit(ruleZodiacJi);
        threadPoolExecutor.submit(ruleZodiacGou);
        threadPoolExecutor.submit(ruleZodiacZhu);
        threadPoolExecutor.submit(ruleRed);
        threadPoolExecutor.submit(ruleGreen);
        threadPoolExecutor.submit(ruleBlue);
        threadPoolExecutor.submit(ruleWaveBlueDa);
        threadPoolExecutor.submit(ruleWaveBlueDan);
        threadPoolExecutor.submit(ruleWaveBlueShuang);
        threadPoolExecutor.submit(ruleWaveBlueXiao);
        threadPoolExecutor.submit(ruleWaveGreenDa);
        threadPoolExecutor.submit(ruleWaveGreenDan);
        threadPoolExecutor.submit(ruleWaveGreenShuang);
        threadPoolExecutor.submit(ruleWaveGreenXiao);
        threadPoolExecutor.submit(ruleWaveRedDa);
        threadPoolExecutor.submit(ruleWaveRedDan);
        threadPoolExecutor.submit(ruleWaveRedShuang);
        threadPoolExecutor.submit(ruleWaveRedXiao);
        threadPoolExecutor.submit(ruleSumZodiac);
        threadPoolExecutor.submit(ruleZhengBall);
        threadPoolExecutor.submit(ruleZhengSpecific1);
        threadPoolExecutor.submit(ruleZhengSpecific2);
        threadPoolExecutor.submit(ruleZhengSpecific3);
        threadPoolExecutor.submit(ruleZhengSpecific4);
        threadPoolExecutor.submit(ruleZhengSpecific5);
        threadPoolExecutor.submit(ruleZhengSpecific6);
        threadPoolExecutor.submit(ruleJoint3All);
        threadPoolExecutor.submit(ruleJoint32);
        threadPoolExecutor.submit(ruleJoint2All);
        threadPoolExecutor.submit(ruleJoint2Special);
        threadPoolExecutor.submit(ruleJointSpecial);
        threadPoolExecutor.submit(ruleNot5);
        threadPoolExecutor.submit(ruleNot6);
        threadPoolExecutor.submit(ruleNot7);
        threadPoolExecutor.submit(ruleNot8);
        threadPoolExecutor.submit(ruleNot9);
        threadPoolExecutor.submit(ruleNot10);
        threadPoolExecutor.submit(ruleNot11);
        threadPoolExecutor.submit(ruleNot12);
        threadPoolExecutor.submit(rulePass);
        threadPoolExecutor.submit(ruleOneZodiac);
        threadPoolExecutor.submit(ruleTailNum);
    }
}
