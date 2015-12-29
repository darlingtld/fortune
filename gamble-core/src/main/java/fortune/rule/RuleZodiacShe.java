package fortune.rule;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.RuleResult;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//生肖蛇
@Scope("prototype")
@Component
public class RuleZodiacShe extends Rule {

    public RuleZodiacShe() {
        super(LotteryMarkSixType.ZODIAC_SHE);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        if (BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial()).equals(LotteryMarkSixType.ZODIAC_SHE)) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
