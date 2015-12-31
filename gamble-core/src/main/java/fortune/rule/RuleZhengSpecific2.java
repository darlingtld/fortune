package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特2
@Scope("prototype")
@Component
public class RuleZhengSpecific2 extends Rule {

    public RuleZhengSpecific2() {
        super(LotteryMarkSixType.ZHENG_SPECIFIC_2);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getTwo() == stub.getNumber()) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
