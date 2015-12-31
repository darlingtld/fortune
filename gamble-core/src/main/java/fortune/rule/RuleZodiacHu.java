package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//生肖虎
@Scope("prototype")
@Component
public class RuleZodiacHu extends Rule {

    public RuleZodiacHu() {
        super(LotteryMarkSixType.ZODIAC_HU);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial()).equals(LotteryMarkSixType.ZODIAC_HU)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
