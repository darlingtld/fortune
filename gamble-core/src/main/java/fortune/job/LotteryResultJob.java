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

    public static final int TOTAL_LOTTERY_RESULT_JOBS = 18;
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
    }
}
