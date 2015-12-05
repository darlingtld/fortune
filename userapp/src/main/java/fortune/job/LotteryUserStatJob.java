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
public class LotteryUserStatJob {

    @Autowired
    private ResultService resultService;

    @Autowired
    private PGroupService pGroupService;

    @Autowired
    private UserService userService;

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

    public void calculateUserStat() {
        int lotteryIssue = lotteryService.getLatestLotteryIssue();
        if (!jobService.canUserStatJobStart(lotteryIssue)) {
            return;
        }
        String jobName = LotteryUserStatJob.class.getName();
        Utils.logger.info("{} runs...", jobName);
//        check whether this job has run
        if (jobService.hasJobRun(jobName, lotteryIssue)) {
            Utils.logger.info("{} has run. skip.", jobName);
            return;
        }
        JobTracker jobTracker = new JobTracker(jobName, lotteryIssue, new Date(), JobTracker.RUNNING);
        int jobId = jobTrackerService.save(jobTracker);
        LotteryMarkSix lotteryMarkSix = lotteryService.getLotteryMarkSix(lotteryIssue);

        List<User> userList = userService.getAll();
        for (User user : userList) {
//            get wager result of this user
            List<LotteryResult> lotteryResultList = resultService.getLotteryResult4LotteryIssueAndUser(lotteryIssue, user.getId());
            double totalStakes = 0;
            double validStakes = 0;
            double tuishiStakes = 0;
            double result = 0;
            for (LotteryResult lotteryResult : lotteryResultList) {
                LotteryMarkSixWager wager = wagerService.getLotteryMarkSixWager(lotteryResult.getLotteryMarkSixWagerId());
                totalStakes += wager.getTotalStakes();
                result += lotteryResult.getWinningMoney();
            }
            validStakes = totalStakes;
            LotteryMarkSixUserStat lotteryMarkSixUserStat = new LotteryMarkSixUserStat();
            lotteryMarkSixUserStat.setIssue(lotteryIssue);
            lotteryMarkSixUserStat.setOpenTs(lotteryMarkSix.getTimestamp());
            lotteryMarkSixUserStat.setUserId(user.getId());
            lotteryMarkSixUserStat.setStakes(totalStakes);
            lotteryMarkSixUserStat.setValidStakes(validStakes);
            lotteryMarkSixUserStat.setTuishui(tuishiStakes);
            lotteryMarkSixUserStat.setResult(result);
            statService.saveLotteryMarkSixStat(lotteryMarkSixUserStat);
        }


        jobTrackerService.updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("{} finished", jobName);

    }
}
