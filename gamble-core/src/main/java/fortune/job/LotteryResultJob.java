package fortune.job;

import common.Utils;
import fortune.rule.RuleSPECIALDAN;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by tangl9 on 2015-10-26.
 */
public class LotteryResultJob {

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10));

    @Autowired
    private RuleSPECIALDAN ruleSPECIALDAN;
    public void calculateLotteryResult() {
        Utils.logger.info("start to calculate lottery result at {}", Utils.yyyyMMddHHmmss2Format(new Date()));
        threadPoolExecutor.submit(ruleSPECIALDAN);
    }
}
