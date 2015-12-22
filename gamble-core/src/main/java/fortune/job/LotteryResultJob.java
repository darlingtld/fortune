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

    public static final int TOTAL_LOTTERY_RESULT_JOBS = 2;
    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(TOTAL_LOTTERY_RESULT_JOBS));


    @Autowired
    private RuleSPECIAL ruleSPECIAL;

    @Autowired
    private RuleZheng1to6 ruleZheng1to6;

    public void calculateLotteryResult() {
        Utils.logger.info("start to calculate lottery result at {}", Utils.yyyyMMddHHmmss2Format(new Date()));
        threadPoolExecutor.submit(ruleSPECIAL);
        threadPoolExecutor.submit(ruleZheng1to6);
    }
}
