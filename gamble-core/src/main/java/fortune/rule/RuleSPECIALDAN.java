package fortune.rule;

import fortune.pojo.LotteryMarkSix;
import fortune.pojo.LotteryMarkSixType;
import fortune.pojo.LotteryMarkSixWagerStub;
import fortune.pojo.RuleResult;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//正码特单，特单,特码单
@Scope("prototype")
@Component
public class RuleSPECIALDAN extends Rule {

    public RuleSPECIALDAN() {
        super(LotteryMarkSixType.SPECIAL_DAN);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        if (lotteryMarkSix.getSpecial() == 49 && stub.getNumber() == 49) {
            return RuleResult.DRAW;
        } else if (lotteryMarkSix.getSpecial() % 2 == 1 && stub.getNumber() % 2 == 1) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
