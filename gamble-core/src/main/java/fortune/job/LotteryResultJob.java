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

    public static final int TOTAL_LOTTERY_RESULT_JOBS = 30;
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


    public void calculateLotteryResult() {
        Utils.logger.info("start to calculate lottery result at {}", Utils.yyyyMMddHHmmss2Format(new Date()));
        threadPoolExecutor.submit(ruleSPECIAL);
        threadPoolExecutor.submit(ruleZheng1to6);
        threadPoolExecutor.submit(ruleTWOFACES);
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
    }
}
