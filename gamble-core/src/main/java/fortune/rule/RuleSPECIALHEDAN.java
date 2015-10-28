package fortune.rule;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.RuleResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//特码合单
@Scope("prototype")
@Component
public class RuleSPECIALHEDAN extends Rule {

    public RuleSPECIALHEDAN() {
        super(LotteryMarkSixType.SPECIAL_HEDAN);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) % 2 == 1) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
