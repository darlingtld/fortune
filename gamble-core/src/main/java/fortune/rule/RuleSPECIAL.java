package fortune.rule;

import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//特码
@Scope("prototype")
@Component
public class RuleSPECIAL implements Runnable {

    private LotteryMarkSixType lotteryMarkSixType = LotteryMarkSixType.SPECIAL;

    private int lotteryIssue;

    @Override
    public void run() {
        Utils.logger.info("rule [{}] runs...", lotteryMarkSixType);
//        check whether this job has run
        lotteryIssue = BeanHolder.getLotteryService().getLatestLotteryIssue();
        if (hasJobRun(lotteryMarkSixType, lotteryIssue)) {
            Utils.logger.info("rule [{}] has run. skip.", lotteryMarkSixType);
            return;
        }
        JobTracker jobTracker = new JobTracker(lotteryMarkSixType.name(), lotteryIssue, new Date(), JobTracker.RUNNING);
        int jobId = BeanHolder.getJobTrackerService().save(jobTracker);
//        get all wagers for this issue
        List<LotteryMarkSixWager> wagerList = BeanHolder.getWagerService().getLotteryMarkSixWagerListByType(lotteryIssue, lotteryMarkSixType);
//        run against rules
        LotteryMarkSix lotteryMarkSix = BeanHolder.getLotteryService().getLotteryMarkSix(lotteryIssue);
        HashMap<String, Double> oddsCache = new HashMap<>();
        for (LotteryMarkSixWager wager : wagerList) {
            Utils.logger.debug(wager.toString());
            LotteryResult lotteryResult = new LotteryResult();
            lotteryResult.setUserId(wager.getUserId());
            lotteryResult.setGroupId(wager.getPgroupId());
            lotteryResult.setLotteryIssue(lotteryIssue);
            lotteryResult.setLotteryMarkSixWagerId(wager.getId());
            int winningMoney = 0;
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                if (lotteryMarkSix.getSpecial() == 49) {
                    winningMoney += stub.getStakes();
                } else if (lotteryMarkSix.getSpecial() == stub.getNumber()) {
                    String oddsCacheKey = String.format("%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), wager.getLotteryMarkSixType());
                    Double odds = oddsCache.get(oddsCacheKey);
                    if (odds == null) {
                        odds = BeanHolder.getOddsService().getOdds4LotteryIssueByType(lotteryIssue, wager.getPgroupId(), lotteryMarkSixType.name()).getOdds();
                        oddsCache.put(oddsCacheKey, odds);
                    }
                    winningMoney += stub.getStakes() * odds;
                } else if (lotteryMarkSix.getSpecial() != stub.getNumber()) {

                }
            }
            lotteryResult.setWinningMoney(winningMoney);

            BeanHolder.getResultService().saveLotteryResult(lotteryResult);
        }
        BeanHolder.getJobTrackerService().updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("rule [{}] finished", lotteryMarkSixType);

    }

    private boolean hasJobRun(LotteryMarkSixType lotteryMarkSixType, int lotteryIssue) {
        return BeanHolder.getJobTrackerService().getJobByNameAndIssue(lotteryMarkSixType.name(), lotteryIssue) != null;
    }

}
