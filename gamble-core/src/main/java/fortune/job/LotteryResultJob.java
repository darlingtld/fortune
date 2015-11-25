package fortune.job;

import common.Utils;
import fortune.rule.*;
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

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 0, TimeUnit.MINUTES, new ArrayBlockingQueue<>(100));

    @Autowired
    private RuleSPECIALDAN ruleSPECIALDAN;

    @Autowired
    private RuleSPECIALDA ruleSPECIALDA;

    @Autowired
    private RuleSPECIALHEDA ruleSPECIALHEDA;

    @Autowired
    private RuleSPECIALHESHUANG ruleSPECIALHESHUANG;

    @Autowired
    private RuleSPECIALHEXIAO ruleSPECIALHEXIAO;

    @Autowired
    private RuleSPECIALSHUANG ruleSPECIALSHUANG;

    @Autowired
    private RuleSPECIALXIAO ruleSPECIALXIAO;


    public void calculateLotteryResult() {
        Utils.logger.info("start to calculate lottery result at {}", Utils.yyyyMMddHHmmss2Format(new Date()));
        threadPoolExecutor.submit(ruleSPECIALDAN);
//        threadPoolExecutor.submit(ruleSPECIALDA);
//        threadPoolExecutor.submit(ruleSPECIALHEDA);
//        threadPoolExecutor.submit(ruleSPECIALHESHUANG);
//        threadPoolExecutor.submit(ruleSPECIALHEXIAO);
//        threadPoolExecutor.submit(ruleSPECIALSHUANG);
//        threadPoolExecutor.submit(ruleSPECIALXIAO);
    }
}
