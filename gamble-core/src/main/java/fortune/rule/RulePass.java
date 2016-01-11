package fortune.rule;

import com.google.common.util.concurrent.AtomicDouble;
import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-10-26.
 */
//过关
@Scope("prototype")
@Component
public class RulePass extends Rule {

    public RulePass() {
        super(LotteryMarkSixType.PASS);
    }

    //    not applicable to pass ball
    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        return null;
    }

    @Override
    public void run() {
        Utils.logger.info("rule [{}] runs...", super.lotteryMarkSixType);
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
        if (!wagerList.isEmpty()) {
//        run against rules
            LotteryMarkSix lotteryMarkSix = BeanHolder.getLotteryService().getLotteryMarkSix(lotteryIssue);
            HashMap<String, Double> oddsCache = new HashMap<>();
            for (LotteryMarkSixWager wager : wagerList) {
                LotteryTuishui tuishui = BeanHolder.getTuishuiService().getTuishui4UserOfType(wager.getUserId(), wager.getPgroupId(), wager.getPanlei(), wager.getLotteryMarkSixType());
                Utils.logger.debug(wager.toString());
                LotteryResult lotteryResult = new LotteryResult();
                lotteryResult.setUserId(wager.getUserId());
                lotteryResult.setGroupId(wager.getPgroupId());
                lotteryResult.setPanlei(wager.getPanlei());
                lotteryResult.setLotteryIssue(lotteryIssue);
                lotteryResult.setLotteryMarkSixWagerId(wager.getId());

                double winningMoney = 0;
                double tuishuiMoney = 0;
                AtomicDouble accumulatedOdds = new AtomicDouble(1.0);
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    String oddsCacheKey = generateOddsCacheKey(stub, wager, isStubNumberNeededInOdds());
                    Double odds = oddsCache.get(oddsCacheKey);
                    if (odds == null) {
                        odds = BeanHolder.getOddsService().getOdds(lotteryIssue, wager.getPgroupId(), 0, stub.getLotteryMarkSixType(), null, wager.getPanlei()).getOdds();
                        oddsCache.put(oddsCacheKey, odds);
                    }
                    switch (stub.getNumber()) {
                        case 1:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getOne(), accumulatedOdds, odds);
                            break;
                        case 2:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getTwo(), accumulatedOdds, odds);
                            break;
                        case 3:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getThree(), accumulatedOdds, odds);
                            break;
                        case 4:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getFour(), accumulatedOdds, odds);
                            break;
                        case 5:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getFive(), accumulatedOdds, odds);
                            break;
                        case 6:
                            process(stub.getLotteryMarkSixType(), lotteryMarkSix.getSix(), accumulatedOdds, odds);
                            break;
                    }
                }
                if (accumulatedOdds.doubleValue() < 0.1) {
                    tuishuiMoney = wager.getTotalStakes() * tuishui.getTuishui() / 100;
                } else {
                    winningMoney = wager.getTotalStakes() * accumulatedOdds.doubleValue();
                }
                lotteryResult.setWinningMoney(winningMoney);
                lotteryResult.setTuishui(tuishuiMoney);
                BeanHolder.getResultService().saveLotteryResult(lotteryResult);
            }
        }
        BeanHolder.getJobTrackerService().updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("rule [{}] finished", lotteryMarkSixType);

    }

    private void process(LotteryMarkSixType lotteryMarkSixType, int number, AtomicDouble accumulatedOdds, Double odds) {
        switch (lotteryMarkSixType) {
            case PASS_DAN:
                if (number % 2 == 1) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_SHUANG:
                if (number == 0) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_DA:
                if (number >= 25) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_XIAO:
                if (number <= 24) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_RED:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.RED)) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_BLUE:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.BLUE)) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
            case PASS_GREEN:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.GREEN)) {
                    accumulatedOdds.set(accumulatedOdds.doubleValue() * odds);
                } else {
                    accumulatedOdds.set(0);
                }
                break;
        }
    }

    @Override
    boolean isStubSplit() {
        return false;
    }

    @Override
    boolean isStubNumberNeededInOdds() {
        return false;
    }
}
