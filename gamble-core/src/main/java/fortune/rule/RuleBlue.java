package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//色波蓝
@Scope("prototype")
@Component
public class RuleBlue extends Rule {

    public RuleBlue() {
        super(LotteryMarkSixType.BLUE);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.BLUE)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
