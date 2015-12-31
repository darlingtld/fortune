package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波绿双
@Scope("prototype")
@Component
public class RuleWaveGreenShuang extends Rule {

    public RuleWaveGreenShuang() {
        super(LotteryMarkSixType.WAVE_GREEN_SHUANG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.GREEN) && lotteryMarkSix.getSpecial() % 2 == 0) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
