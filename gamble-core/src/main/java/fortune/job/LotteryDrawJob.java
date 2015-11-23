package fortune.job;

import common.Utils;
import fortune.dao.JobTrackerDao;
import fortune.dao.LotteryDao;
import fortune.pojo.LotteryDrawTracker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Component
public class LotteryDrawJob {

    @Autowired
    private LotteryDao lotteryDao;

    @Autowired
    private JobTrackerDao jobTrackerDao;

    @Autowired
    private LotteryResultJob lotteryResultJob;

    @Transactional
    public void hasNewLotteryDraw() {
        Utils.logger.info("check if there is a new lottery draw");
        int latestIssue = lotteryDao.getLatestLotteryIssue();
        LotteryDrawTracker lotteryDrawTracker = jobTrackerDao.getLotteryDrawTracker();
        int lastLotteryDrawTrackerIssue = lotteryDrawTracker == null ? 0 : lotteryDrawTracker.getLastLotteryIssue();
        Utils.logger.info("latestIssue {}, lastLotteryDrawTrackerIssue {}", latestIssue, lastLotteryDrawTrackerIssue);
        if (lastLotteryDrawTrackerIssue < latestIssue) {
            Utils.logger.info("a new lottery draw !!!");
//            trigger result and stat jobs
            lotteryResultJob.calculateLotteryResult();
        }
        jobTrackerDao.saveLotteryDrawTracker(new LotteryDrawTracker(latestIssue, new Date()));
    }
}
