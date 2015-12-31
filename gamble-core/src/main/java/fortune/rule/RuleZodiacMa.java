package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//生肖马
@Scope("prototype")
@Component
public class RuleZodiacMa extends Rule {

    public RuleZodiacMa() {
        super(LotteryMarkSixType.ZODIAC_MA);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial()).equals(LotteryMarkSixType.ZODIAC_MA)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
