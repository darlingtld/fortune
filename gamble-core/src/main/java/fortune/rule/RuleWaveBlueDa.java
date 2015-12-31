package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波蓝大
@Scope("prototype")
@Component
public class RuleWaveBlueDa extends Rule {

    public RuleWaveBlueDa() {
        super(LotteryMarkSixType.WAVE_BLUE_DA);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.BLUE) && lotteryMarkSix.getSpecial() >=25) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

    @Override
    boolean isStubSplit() {
        return true;
    }
}
