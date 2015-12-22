package service;

import fortune.job.LotteryResultJob;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by tangl9 on 2015-12-22.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath*:mvc-dispatcher-servlet.xml"})
public class LotteryRuleJobTest {
    @Autowired
    private LotteryResultJob lotteryResultJob;

    @Test
    public void getRuleResult() throws InterruptedException {
        lotteryResultJob.calculateLotteryResult();
        Thread.sleep(1000000);
    }
}
