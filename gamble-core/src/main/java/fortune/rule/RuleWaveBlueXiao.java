package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//半波蓝小
@Scope("prototype")
@Component
public class RuleWaveBlueXiao extends Rule {

    public RuleWaveBlueXiao() {
        super(LotteryMarkSixType.WAVE_BLUE_XIAO);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else if (LotteryBall.valueOf("NUM_" + lotteryMarkSix.getSpecial()).getColor().equals(MarkSixColor.BLUE) && lotteryMarkSix.getSpecial() <= 24) {
            return RuleResult.WIN;
        } else {
            return RuleResult.LOSE;
        }
    }

    @Override
    boolean isStubSplit() {
        return true;
    }
    @Override
    boolean isStubNumberNeededInOdds() {
        return false;
    }
}
