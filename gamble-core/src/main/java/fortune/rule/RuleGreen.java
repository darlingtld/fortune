package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//色波绿
@Scope("prototype")
@Component
public class RuleGreen extends Rule {

    public RuleGreen() {
        super(LotteryMarkSixType.GREEN);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.GREEN)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }
    @Override
    boolean isStubSplit() {
        return true;
    }

    @Override
    boolean isStubNumberNeededInOdds() {
        return false;
    }
}
