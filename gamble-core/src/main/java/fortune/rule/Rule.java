package fortune.rule;

import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-28.
 */
public abstract class Rule implements Runnable {


    private LotteryMarkSixType lotteryMarkSixType;

    private int lotteryIssue;

    public Rule(LotteryMarkSixType lotteryMarkSixType) {
        this.lotteryMarkSixType = lotteryMarkSixType;
    }

    abstract RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager);

    abstract boolean isStubSplit();

    boolean isStubNumberNeededInOdds() {
        return true;
    }

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
            lotteryResult.setPanlei(wager.getPanlei());
            lotteryResult.setLotteryIssue(lotteryIssue);
            lotteryResult.setLotteryMarkSixWagerId(wager.getId());

            double winningMoney = 0;
            if (isStubSplit()) {
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    String oddsCacheKey = generateOddsCacheKey(stub, wager, isStubNumberNeededInOdds());
                    Double odds = getOdds(oddsCache, wager, stub, oddsCacheKey);
                    switch (getRuleResult(lotteryMarkSix, stub, wager)) {
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
            } else {
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    String oddsCacheKey = generateOddsCacheKey(stub, wager, isStubNumberNeededInOdds());
                    Double odds = getOdds(oddsCache, wager, stub, oddsCacheKey);
                    switch (getRuleResult(lotteryMarkSix, stub, wager)) {
                        case WIN:
                            winningMoney += wager.getTotalStakes() * odds;
                            break;
                        case DRAW:
                            winningMoney += wager.getTotalStakes();
                            break;
                        case LOSE:
                            break;
                    }
                }
                winningMoney = winningMoney / wager.getLotteryMarkSixWagerStubList().size();
            }
            lotteryResult.setWinningMoney(winningMoney);
            BeanHolder.getResultService().saveLotteryResult(lotteryResult);
        }
        BeanHolder.getJobTrackerService().updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("rule [{}] finished", lotteryMarkSixType);

    }

    private Double getOdds(HashMap<String, Double> oddsCache, LotteryMarkSixWager wager, LotteryMarkSixWagerStub stub, String oddsCacheKey) {
        Double odds = oddsCache.get(oddsCacheKey);
        if (odds == null) {
            if (isStubNumberNeededInOdds()) {
                odds = BeanHolder.getOddsService().getOdds(lotteryIssue, wager.getPgroupId(), stub.getNumber(), lotteryMarkSixType, stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds();
            } else {
                odds = BeanHolder.getOddsService().getOdds(lotteryIssue, wager.getPgroupId(), 0, lotteryMarkSixType, stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds();
            }
            oddsCache.put(oddsCacheKey, odds);
        }
        return odds;
    }

    private String generateOddsCacheKey(LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager, boolean isStubNumberNeededInOdds) {
        String key = String.format("%s#%s#%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), stub.getNumber(), stub.getLotteryMarkSixType(), wager.getPanlei());
        if (!isStubNumberNeededInOdds) {
            key = String.format("%s#%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), stub.getLotteryMarkSixType(), wager.getPanlei());
        }
        return key;
    }

    private boolean hasJobRun(LotteryMarkSixType lotteryMarkSixType, int lotteryIssue) {
        return BeanHolder.getJobTrackerService().getJobByNameAndIssue(lotteryMarkSixType.name(), lotteryIssue) != null;
    }
}