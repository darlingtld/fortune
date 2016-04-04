package fortune.job;

import common.Utils;
import fortune.pojo.*;
import fortune.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//      构建group id map,走飞中计算需要
        Map<String, PGroup> groupIdMap = new HashMap<>();
        for (PGroup pGroup : groupList) {
            groupIdMap.put(pGroup.getId(), pGroup);
        }
        try {
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
                }
//                zoufei calculation
                if (pGroup.isZoufeiAutoEnabled() && pgroupResult + pGroup.getMaxStakes() < 0) {
//                自动走飞
//                超出代理商最大输赢的部分
                    double spilledResult = -pgroupResult - pGroup.getMaxStakes();
                    zoufeiStakes = spilledResult;
                    zoufeiResult = spilledResult;
//                走飞给父代理商
                    if (pGroup.getParentPGroupID() == null) {
//                    顶级代理商,根代理商
                        PGroup rootGroup = groupIdMap.get(pGroup.getParentPGroupID());
                        rootGroup.setMaxStakes(rootGroup.getMaxStakes() + spilledResult);

                    } else {
                        PGroup fatherPGroup = groupIdMap.get(pGroup.getParentPGroupID());

                        fatherPGroup.setMaxStakes(fatherPGroup.getMaxStakes() - spilledResult);
                    }


                } else if (!pGroup.isZoufeiAutoEnabled() && pgroupResult + pGroup.getMaxStakes() < 0) {
//                手动走飞(目前为止,代理商的自动走飞和手动走飞计算方式一样!!!)
//                TODO
//                超出代理商最大输赢的部分
                    double spilledResult = -pgroupResult - pGroup.getMaxStakes();
                    zoufeiStakes = spilledResult;
                    zoufeiResult = spilledResult;
//                走飞给父代理商
                    if (pGroup.getParentPGroupID() == null) {
//                    顶级代理商,根代理商
                        pGroup.setMaxStakes(pGroup.getMaxStakes() + spilledResult);
                    } else {
                        PGroup fatherPGroup = groupIdMap.get(pGroup.getParentPGroupID());

                        fatherPGroup.setMaxStakes(fatherPGroup.getMaxStakes() - spilledResult);
                    }
                } else {

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
        } catch (Exception e) {
            jobTrackerService.updateEndStatus(jobId, new Date(), String.format("%s[%s]", JobTracker.FAILED, e.getMessage()));
            Utils.logger.info("{} erred", jobName);
        }

        jobTrackerService.updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("{} finished", jobName);

    }


}
