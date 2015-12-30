package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波红单
@Scope("prototype")
@Component
public class RuleWaveRedDan extends Rule {

    public RuleWaveRedDan() {
        super(LotteryMarkSixType.WAVE_RED_DAN);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.RED) && lotteryMarkSix.getSpecial() % 2 == 1) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
