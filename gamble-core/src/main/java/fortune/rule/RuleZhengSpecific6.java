package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特6
@Scope("prototype")
@Component
public class RuleZhengSpecific6 extends Rule {

    public RuleZhengSpecific6() {
        super(LotteryMarkSixType.ZHENG_SPECIFIC_6);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSix() == stub.getNumber()) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
