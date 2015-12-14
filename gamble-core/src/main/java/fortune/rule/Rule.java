package fortune.rule;

import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static fortune.pojo.RuleResult.*;

/**
 * Created by tangl9 on 2015-10-28.
 */
public abstract class Rule implements Runnable {


    private LotteryMarkSixType lotteryMarkSixType;

    private int lotteryIssue;

    public Rule(LotteryMarkSixType lotteryMarkSixType) {
        this.lotteryMarkSixType = lotteryMarkSixType;
    }

    abstract RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub);

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

            double winningMoney = 0;
            for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                String oddsCacheKey = String.format("%s#%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), stub.getNumber(), stub.getLotteryMarkSixType());
                Double odds = oddsCache.get(oddsCacheKey);
                if (odds == null) {
                    odds = BeanHolder.getOddsService().getOdds(lotteryIssue, wager.getPgroupId(), stub.getNumber(), lotteryMarkSixType).getOdds();
                    oddsCache.put(oddsCacheKey, odds);
                }
                switch (getRuleResult(lotteryMarkSix, stub)) {
                    case WIN:
                        winningMoney += stub.getStakes() * odds;
                        break;
                    case DRAW:
                        winningMoney += stub.getStakes();
                        break;
                    case LOSE:
                        break;
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