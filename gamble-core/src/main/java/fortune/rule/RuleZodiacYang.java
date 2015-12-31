package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//生肖羊
@Scope("prototype")
@Component
public class RuleZodiacYang extends Rule {

    public RuleZodiacYang() {
        super(LotteryMarkSixType.ZODIAC_YANG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial()).equals(LotteryMarkSixType.ZODIAC_YANG)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
