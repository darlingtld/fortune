package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特4
@Scope("prototype")
@Component
public class RuleZhengSpecific4 extends Rule {

    public RuleZhengSpecific4() {
        super(LotteryMarkSixType.ZHENG_SPECIFIC_4);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getFour() == stub.getNumber()) {
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
