package fortune.rule;

import fortune.pojo.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import static fortune.pojo.LotteryMarkSixType.*;

/**
 * Created by tangl9 on 2015-12-22.
 */
//正码1~6
@Scope("prototype")
@Component
public class RuleZheng1to6 extends Rule {
    public RuleZheng1to6() {
        super(ZHENG_1_6);
    }

    @Override
    RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub) {
        switch (stub.getNumber()) {
            case 1:
                int number = lotteryMarkSix.getOne();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            case 2:
                number = lotteryMarkSix.getTwo();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            case 3:
                number = lotteryMarkSix.getThree();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            case 4:
                number = lotteryMarkSix.getFour();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            case 5:
                number = lotteryMarkSix.getFive();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            case 6:
                number = lotteryMarkSix.getSix();
                if (number == 49) {
                    return RuleResult.DRAW;
                } else {
                    RuleResult result = calculateType(stub, number);
                    if (result != null) return result;
                }
                return RuleResult.LOSE;
            default:
                return RuleResult.LOSE;
        }

    }

    private RuleResult calculateType(LotteryMarkSixWagerStub stub, int number) {
        switch (stub.getLotteryMarkSixType()) {
            case DAN:
                if (number % 2 == 1) {
                    return RuleResult.WIN;
                }
                break;
            case SHUANG:
                if (number % 2 == 0) {
                    return RuleResult.WIN;
                }
                break;
            case DA:
                if (number >= 25) {
                    return RuleResult.WIN;
                }
                break;
            case XIAO:
                if (number <= 24) {
                    return RuleResult.WIN;
                }
                break;
            case HEDAN:
                if ((number / 10 + number % 10) % 2 == 1) {
                    return RuleResult.WIN;
                }
                break;
            case HESHUANG:
                if ((number / 10 + number % 10) % 2 == 0) {
                    return RuleResult.WIN;
                }
                break;
            case RED:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.RED)) {
                    return RuleResult.WIN;
                }
                break;
            case GREEN:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.GREEN)) {
                    return RuleResult.WIN;
                }
                break;
            case BLUE:
                if (LotteryBall.valueOf("NUM_" + number).getColor().equals(MarkSixColor.BLUE)) {
                    return RuleResult.WIN;
                }
                break;
        }
        return null;
    }
}
