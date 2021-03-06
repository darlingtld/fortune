package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连码 三中二
@Scope("prototype")
@Component
public class RuleJoint32 extends Rule {

    public RuleJoint32() {
        super(LotteryMarkSixType.JOINT_3_2);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<Integer> ballList = Arrays.asList(lotteryMarkSix.getOne(), lotteryMarkSix.getTwo(), lotteryMarkSix.getThree(), lotteryMarkSix.getFour(), lotteryMarkSix.getFive(), lotteryMarkSix.getSix());
        List<Integer> wagerBallList = wager.getLotteryMarkSixWagerStubList().stream().map(LotteryMarkSixWagerStub::getNumber).collect(Collectors.toList());
        List<Integer> wagerBallList1 = Arrays.asList(wager.getLotteryMarkSixWagerStubList().get(0).getNumber(), wager.getLotteryMarkSixWagerStubList().get(1).getNumber());
        List<Integer> wagerBallList2 = Arrays.asList(wager.getLotteryMarkSixWagerStubList().get(0).getNumber(), wager.getLotteryMarkSixWagerStubList().get(2).getNumber());
        List<Integer> wagerBallList3 = Arrays.asList(wager.getLotteryMarkSixWagerStubList().get(1).getNumber(), wager.getLotteryMarkSixWagerStubList().get(2).getNumber());

        if (ballList.containsAll(wagerBallList)) {
            return RuleResult.WIN_JOINT_3_2_ZHONG_3;
        } else if (ballList.containsAll(wagerBallList1) || ballList.containsAll(wagerBallList2) || ballList.containsAll(wagerBallList3)) {
            return RuleResult.WIN_JOINT_3_2_ZHONG_2;
        }
        return RuleResult.LOSE;
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
