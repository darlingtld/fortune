package fortune.job;

import common.Utils;
import fortune.pojo.JobTracker;
import fortune.pojo.LotteryOdds;
import fortune.pojo.PGroup;
import fortune.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-11-26.
 */
@Component
public class LotteryOddsPopulateJob {

    @Autowired
    private ResultService resultService;

    @Autowired
    private PGroupService pGroupService;

    @Autowired
    private JobService jobService;

    @Autowired
    private LotteryService lotteryService;

    @Autowired
    private JobTrackerService jobTrackerService;

    @Autowired
    private OddsService oddsService;

    public void populate() {
        int lotteryIssue = lotteryService.getNextLotteryMarkSixInfo().getIssue();
        if (!jobService.canLotteryOddsPopulateJobStart(lotteryIssue)) {
            return;
        }
        String jobName = LotteryOddsPopulateJob.class.getName();
        Utils.logger.info("{} runs...", jobName);
//        check whether this job has run
        if (jobService.hasJobRun(jobName, lotteryIssue)) {
            Utils.logger.info("{} has run. skip.", jobName);
            return;
        }
        JobTracker jobTracker = new JobTracker(jobName, lotteryIssue, new Date(), JobTracker.RUNNING);
        int jobId = jobTrackerService.save(jobTracker);

        for (PGroup pGroup : pGroupService.getGroupAll()) {
            List<LotteryOdds> oddsList = oddsService.getOdds4LotteryIssue(lotteryIssue - 1, pGroup.getId());
            if (oddsList.isEmpty()) {
                oddsList = oddsService.generateOddsDefault(pGroup.getId(), lotteryIssue);
            }
            for (LotteryOdds odds : oddsList) {
                odds.setLotteryIssue(lotteryIssue);
                oddsService.saveOdds(odds);
            }
        }


        jobTrackerService.updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("{} finished", jobName);

    }

}
