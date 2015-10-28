package fortune.rule;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.RuleResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特大，特大,特码大
@Scope("prototype")
@Component
public class RuleSPECIALDA extends Rule {

    public RuleSPECIALDA() {
        super(LotteryMarkSixType.SPECIAL_DA);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (lotteryMarkSix.getSpecial() >= 25) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
