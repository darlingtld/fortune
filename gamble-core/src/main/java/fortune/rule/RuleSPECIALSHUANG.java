package fortune.rule;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.RuleResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特双，特双,特码双
@Scope("prototype")
@Component
public class RuleSPECIALSHUANG extends Rule {

    public RuleSPECIALSHUANG() {
        super(LotteryMarkSixType.SPECIAL_SHUANG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (lotteryMarkSix.getSpecial() % 2 == 0) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
