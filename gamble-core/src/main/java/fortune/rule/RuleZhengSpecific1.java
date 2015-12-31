package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特1
@Scope("prototype")
@Component
public class RuleZhengSpecific1 extends Rule {

    public RuleZhengSpecific1() {
        super(LotteryMarkSixType.ZHENG_SPECIFIC_1);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getOne() == stub.getNumber()) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

    @Override
    boolean isStubSplit() {
        return true;
    }
}
