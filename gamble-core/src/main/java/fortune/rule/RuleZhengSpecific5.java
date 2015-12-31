package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特5
@Scope("prototype")
@Component
public class RuleZhengSpecific5 extends Rule {

    public RuleZhengSpecific5() {
        super(LotteryMarkSixType.ZHENG_SPECIFIC_5);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getFive() == stub.getNumber()) {
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
