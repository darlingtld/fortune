package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波蓝双
@Scope("prototype")
@Component
public class RuleWaveBlueShuang extends Rule {

    public RuleWaveBlueShuang() {
        super(LotteryMarkSixType.WAVE_BLUE_SHUANG);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.BLUE) && lotteryMarkSix.getSpecial() % 2 == 0) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

}
