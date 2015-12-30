package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波红双
@Scope("prototype")
@Component
public class RuleWaveRedShuang extends Rule {

    public RuleWaveRedShuang() {
        super(LotteryMarkSixType.WAVE_RED_SHUANG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.RED) && lotteryMarkSix.getSpecial() % 2 == 0) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
