package fortune.rule;

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
//连尾 父类
public abstract class RuleJointTailParent extends Rule {

    public abstract int getJointTailNumber();

    public RuleJointTailParent(LotteryMarkSixType lotteryMarkSixType) {
        super(lotteryMarkSixType);
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
            List<Integer> tailList = Arrays.asList(
                    lotteryMarkSix.getOne() % 10,
                    lotteryMarkSix.getTwo() % 10,
                    lotteryMarkSix.getThree() % 10,
                    lotteryMarkSix.getFour() % 10,
                    lotteryMarkSix.getFive() % 10,
                    lotteryMarkSix.getSix() % 10,
                    lotteryMarkSix.getSpecial() % 10
            );
            HashMap<String, Double> oddsCache = new HashMap<>();
            for (LotteryMarkSixWager wager : wagerList) {
                LotteryTuishui tuishui = BeanHolder.getTuishuiService().getTuishui4UserOfType(wager.getUserId(), wager.getPgroupId(), wager.getPanlei(), wager.getLotteryMarkSixType());
                String oddsCacheKey = generateOddsCacheKey(wager.getLotteryMarkSixWagerStubList().get(0), wager, isStubNumberNeededInOdds());
                Double odds = getOdds(oddsCache, wager, wager.getLotteryMarkSixWagerStubList().get(0), oddsCacheKey);
                Utils.logger.debug(wager.toString());
                LotteryResult lotteryResult = new LotteryResult();
                lotteryResult.setUserId(wager.getUserId());
                lotteryResult.setGroupId(wager.getPgroupId());
                lotteryResult.setPanlei(wager.getPanlei());
                lotteryResult.setLotteryIssue(lotteryIssue);
                lotteryResult.setLotteryMarkSixWagerId(wager.getId());

                double winningMoney = 0;
                double tuishuiMoney=0;
                int[] array = new int[wager.getLotteryMarkSixWagerStubList().size()];
                for (int i = 0; i < wager.getLotteryMarkSixWagerStubList().size(); i++) {
                    array[i] = wager.getLotteryMarkSixWagerStubList().get(i).getNumber();
                }
                List<List<Integer>> wagerStubCombinationList = Combinator.getCombinationList(array, getJointTailNumber());
                for (List<Integer> combination : wagerStubCombinationList) {
                    if (tailList.containsAll(combination)) {
//                        win
                        winningMoney += wager.getTotalStakes() / wagerStubCombinationList.size() * odds;
                    } else {
//                        lose
                        tuishuiMoney += wager.getTotalStakes() * tuishui.getTuishui() / 100;
                    }
                }

                lotteryResult.setWinningMoney(winningMoney);
                lotteryResult.setTuishui(tuishuiMoney);
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
        return true;
    }
}
