package fortune.job;

import common.Utils;
import fortune.pojo.JobTracker;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryResult;
import fortune.pojo.PGroup;
import fortune.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by tangl9 on 2015-11-23.
 */
@Component
public class LotteryGroupStatJob {
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

    public void calculateGroupStat() {
        String jobName = LotteryGroupStatJob.class.getName();
        Utils.logger.info("{} runs...", jobName);
//        check whether this job has run
        int lotteryIssue = BeanHolder.getLotteryService().getLatestLotteryIssue();
        if (jobService.hasJobRun(jobName, lotteryIssue)) {
            Utils.logger.info("{} has run. skip.", jobName);
            return;
        }
        JobTracker jobTracker = new JobTracker(jobName, lotteryIssue, new Date(), JobTracker.RUNNING);
        int jobId = jobTrackerService.save(jobTracker);

        List<PGroup> groupList = pGroupService.getGroupAll();
        for (PGroup pGroup : groupList) {
//            get wager result of this group
            List<LotteryResult> lotteryResultList = resultService.getLotteryResult4LotteryIssue(lotteryIssue, pGroup.getId());
        }


        jobTrackerService.updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("{} finished", jobName);

    }


}
