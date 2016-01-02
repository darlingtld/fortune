package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 二尾中
@Scope("prototype")
@Component
public class RuleJointTail2 extends Rule {

    public RuleJointTail2() {
        super(LotteryMarkSixType.JOINT_TAIL_2);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<Integer> tailList = Arrays.asList(
                lotteryMarkSix.getOne() % 10,
                lotteryMarkSix.getTwo() % 10,
                lotteryMarkSix.getThree() % 10,
                lotteryMarkSix.getFour() % 10,
                lotteryMarkSix.getFive() % 10,
                lotteryMarkSix.getSix() % 10,
                lotteryMarkSix.getSpecial() % 10
        );
        int combinationSize = Combination.getCombinationSize(wager.getLotteryMarkSixWagerStubList().size(), 2);
        int hits = 0;
        for (LotteryMarkSixWagerStub wagerStub : wager.getLotteryMarkSixWagerStubList()) {
            if (tailList.contains(wagerStub.getNumber())) {
                hits++;
            }
        }
        return hits >= 2 ? RuleResult.WIN : RuleResult.LOSE;
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
