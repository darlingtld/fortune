package fortune.rule;

import com.google.common.util.concurrent.AtomicDouble;
import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连肖 正肖
@Scope("prototype")
@Component
public class RuleJointZodiacZheng extends Rule {

    public RuleJointZodiacZheng() {
        super(LotteryMarkSixType.JOINT_ZODIAC_ZHENG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        return null;
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
        if (!wagerList.isEmpty()) {
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

                double winningMoney;
                List<LotteryMarkSixType> typeList = Arrays.asList(
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getOne()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getTwo()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getThree()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getFour()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getFive()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getSix()),
                        BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getSpecial())
                );
                AtomicDouble accumulatedOdds = new AtomicDouble(1.0);
                for (LotteryMarkSixWagerStub stub : wager.getLotteryMarkSixWagerStubList()) {
                    String oddsCacheKey = generateOddsCacheKey(stub, wager, isStubNumberNeededInOdds());
                    Double odds = oddsCache.get(oddsCacheKey);
                    if (odds == null) {
                        odds = BeanHolder.getOddsService().getOdds(wager.getLotteryIssue(), wager.getPgroupId(), wager.getLotteryMarkSixWagerStubList().size(), wager.getLotteryMarkSixType(), stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds();
                        oddsCache.put(oddsCacheKey, odds);
                    }
                    if (typeList.contains(stub.getLotteryMarkSixType())) {
                        if (accumulatedOdds.doubleValue() > 0) {
                            accumulatedOdds.set(odds);
                        }
                    } else {
                        accumulatedOdds.set(0);
                    }

                }
                winningMoney = wager.getTotalStakes() * accumulatedOdds.doubleValue();
                lotteryResult.setWinningMoney(winningMoney);
                BeanHolder.getResultService().saveLotteryResult(lotteryResult);
            }
        }
        BeanHolder.getJobTrackerService().updateEndStatus(jobId, new Date(), JobTracker.SUCCESS);
        Utils.logger.info("rule [{}] finished", lotteryMarkSixType);

    }

    @Override
    boolean isStubSplit() {
        return false;
    }

    @Override
    boolean isStubNumberNeededInOdds() {
        return false;
    }

    @Override
    protected String generateOddsCacheKey(LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager, boolean isStubNumberNeededInOdds) {
        String key = String.format("%s#%s#%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), wager.getLotteryMarkSixWagerStubList().size(), stub.getLotteryMarkSixType(), wager.getPanlei());
        return key;

    }
}
