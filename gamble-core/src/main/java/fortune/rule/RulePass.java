package fortune.rule;

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

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        for(LotteryMarkSixWagerStub wagerStub : wager.getLotteryMarkSixWagerStubList()){

        }
        List<Integer> wagerBallList = wager.getLotteryMarkSixWagerStubList().stream().map(LotteryMarkSixWagerStub::getNumber).collect(Collectors.toList());
        if (wagerBallList.contains(lotteryMarkSix.getOne())
                || wagerBallList.contains(lotteryMarkSix.getTwo())
                || wagerBallList.contains(lotteryMarkSix.getThree())
                || wagerBallList.contains(lotteryMarkSix.getFour())
                || wagerBallList.contains(lotteryMarkSix.getFive())
                || wagerBallList.contains(lotteryMarkSix.getSix())
                || wagerBallList.contains(lotteryMarkSix.getSpecial())) {
            return RuleResult.LOSE;
        } else {
            return RuleResult.WIN;
        }
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
//                            连码三中二特殊处理
                            case WIN_JOINT_3_2_ZHONG_2:
                                winningMoney += wager.getTotalStakes() * odds;
                                break;
                            case WIN_JOINT_3_2_ZHONG_3:
                                String jKey = String.format("%s#%s#%s#%s", wager.getLotteryIssue(), wager.getPgroupId(), LotteryMarkSixType.JOINT_3_ALL, wager.getPanlei());
                                Double jOdds = oddsCache.get(jKey);
                                if (jOdds == null) {
                                    BeanHolder.getOddsService().getOdds(lotteryIssue, wager.getPgroupId(), 0, LotteryMarkSixType.JOINT_3_ALL, stub.getLotteryMarkSixType(), wager.getPanlei()).getOdds();
                                    oddsCache.put(jKey, jOdds);
                                }
                                winningMoney += wager.getTotalStakes() * jOdds;
                                break;
                        }
                    }
                    winningMoney = winningMoney / wager.getLotteryMarkSixWagerStubList().size();
                }
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
}
