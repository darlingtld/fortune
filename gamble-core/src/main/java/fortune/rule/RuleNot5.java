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
//自选不中 5不中
@Scope("prototype")
@Component
public class RuleNot5 extends Rule {

    public RuleNot5() {
        super(LotteryMarkSixType.NOT_5);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
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
    boolean isStubSplit() {
        return false;
    }

    @Override
    boolean isStubNumberNeededInOdds() {
        return false;
    }
}
