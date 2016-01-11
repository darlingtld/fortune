package fortune.job;

import common.Utils;
import fortune.pojo.*;
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

    @Autowired
    private WagerService wagerService;

    @Autowired
    private StatService statService;

    public void calculateGroupStat() {
        int lotteryIssue = lotteryService.getLatestLotteryIssue();
        if (!jobService.canGroupStatJobStart(lotteryIssue)) {
            return;
        }
        String jobName = LotteryGroupStatJob.class.getName();
        Utils.logger.info("{} runs...", jobName);
//        check whether this job has run
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
            double totalStakes = 0;
            double userResult = 0;
            double pgroupResult = 0;
            double zoufeiStakes = 0;
            double zoufeiResult = 0;
            for (LotteryResult lotteryResult : lotteryResultList) {
                LotteryMarkSixWager wager = wagerService.getLotteryMarkSixWager(lotteryResult.getLotteryMarkSixWagerId());
                totalStakes += wager.getTotalStakes();
                userResult += lotteryResult.getWinningMoney() + lotteryResult.getTuishui() - wager.getTotalStakes();
                pgroupResult += wager.getTotalStakes() - lotteryResult.getWinningMoney() - lotteryResult.getTuishui();

                //// TODO: 2015-11-26  
            }
            LotteryMarkSixGroupStat lotteryMarkSixGroupStat = new LotteryMarkSixGroupStat();
            lotteryMarkSixGroupStat.setPgroupId(pGroup.getId());
            lotteryMarkSixGroupStat.setLotteryMarkSix(lotteryService.getLotteryMarkSix(lotteryIssue));
            lotteryMarkSixGroupStat.setTotalStakes(totalStakes);
            lotteryMarkSixGroupStat.setUserResult(userResult);
            lotteryMarkSixGroupStat.setPgroupResult(pgroupResult);
            lotteryMarkSixGroupStat.setZoufeiStakes(zoufeiStakes);
            lotteryMarkSixGroupStat.setZoufeiResult(zoufeiResult);
            lotteryMarkSixGroupStat.setRemark("");
            statService.saveLotteryMarkSixStat(lotteryMarkSixGroupStat);
        }


        jobTrackerService.updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("{} finished", jobName);

    }


}
