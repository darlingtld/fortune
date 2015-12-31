package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码
@Scope("prototype")
@Component
public class RuleZhengBall extends Rule {

    public RuleZhengBall() {
        super(LotteryMarkSixType.ZHENG_BALL);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (Arrays.asList(lotteryMarkSix.getOne(), lotteryMarkSix.getTwo(), lotteryMarkSix.getThree(), lotteryMarkSix.getFour(), lotteryMarkSix.getFive(), lotteryMarkSix.getSix()).contains(stub.getNumber())) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }

    }

}
