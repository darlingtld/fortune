package fortune.rule;

import common.Utils;
import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//特码
@Scope("prototype")
@Component
public class RuleSPECIAL extends Rule {

    public RuleSPECIAL() {
        super(LotteryMarkSixType.SPECIAL);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        if (lotteryMarkSix.getSpecial() == 49 && stub.getNumber() == 49) {
            return RuleResult.DRAW;
        } else if (lotteryMarkSix.getSpecial() == stub.getNumber()) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
