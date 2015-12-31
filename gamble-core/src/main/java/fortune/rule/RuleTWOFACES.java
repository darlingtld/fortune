package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Created by tangl9 on 2015-10-26.
 */
//两面
@Scope("prototype")
@Component
public class RuleTWOFACES extends Rule {

    public RuleTWOFACES() {
        super(LotteryMarkSixType.TWO_FACES);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        if (lotteryMarkSix.getSpecial() == 49) {
            return RuleResult.DRAW;
        } else {
            switch (stub.getLotteryMarkSixType()) {
                case DAN:
                    if (lotteryMarkSix.getSpecial() % 2 == 1) {
                        return RuleResult.WIN;
                    }
                    break;
                case HEDAN:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) == 1) {
                        return RuleResult.WIN;
                    }
                    break;
                case WEIDA:
                    if (lotteryMarkSix.getSpecial() % 10 >= 5) {
                        return RuleResult.WIN;
                    }
                    break;
                case JIAQIN:
                    LotteryMarkSixType type = BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial());
                    if (Arrays.asList(LotteryMarkSixType.ZODIAC_NIU, LotteryMarkSixType.ZODIAC_MA, LotteryMarkSixType.ZODIAC_YANG, LotteryMarkSixType.ZODIAC_JI, LotteryMarkSixType.ZODIAC_GOU, LotteryMarkSixType.ZODIAC_ZHU).contains(type)) {
                        return RuleResult.WIN;
                    }
                    break;
                case SHUANG:
                    if (lotteryMarkSix.getSpecial() % 2 == 0) {
                        return RuleResult.WIN;
                    }
                    break;
                case HESHUANG:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) == 0) {
                        return RuleResult.WIN;
                    }
                    break;
                case WEIXIAO:
                    if (lotteryMarkSix.getSpecial() % 10 <= 4) {
                        return RuleResult.WIN;
                    }
                    break;
                case YESHOU:
                    LotteryMarkSixType yeshou = BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial());
                    if (Arrays.asList(LotteryMarkSixType.ZODIAC_SHU, LotteryMarkSixType.ZODIAC_HU, LotteryMarkSixType.ZODIAC_LONG, LotteryMarkSixType.ZODIAC_SHE, LotteryMarkSixType.ZODIAC_TU, LotteryMarkSixType.ZODIAC_HOU).contains(yeshou)) {
                        return RuleResult.WIN;
                    }
                    break;
                case DA:
                    if (lotteryMarkSix.getSpecial() >= 25) {
                        return RuleResult.WIN;
                    }
                    break;
                case HEDA:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) >= 7) {
                        return RuleResult.WIN;
                    }
                    break;
                case HEWEIDA:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) % 10 >= 5) {
                        return RuleResult.WIN;
                    }
                    break;

                case XIAO:
                    if (lotteryMarkSix.getSpecial() <= 24) {
                        return RuleResult.WIN;
                    }
                    break;
                case HEXIAO:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) <= 6) {
                        return RuleResult.WIN;
                    }
                    break;
                case HEWEIXIAO:
                    if ((lotteryMarkSix.getSpecial() / 10 + lotteryMarkSix.getSpecial() % 10) % 10 <= 4) {
                        return RuleResult.WIN;
                    }
                    break;
            }
        }
        return RuleResult.LOSE;
    }

}
