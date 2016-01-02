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
//连码 二全中
@Scope("prototype")
@Component
public class RuleJoint2All extends Rule {

    public RuleJoint2All() {
        super(LotteryMarkSixType.JOINT_2_ALL);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<Integer> ballList = Arrays.asList(lotteryMarkSix.getOne(), lotteryMarkSix.getTwo(), lotteryMarkSix.getThree(), lotteryMarkSix.getFour(), lotteryMarkSix.getFive(), lotteryMarkSix.getSix());
        List<Integer> wagerBallList = wager.getLotteryMarkSixWagerStubList().stream().map(LotteryMarkSixWagerStub::getNumber).collect(Collectors.toList());
        if (ballList.containsAll(wagerBallList)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
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
