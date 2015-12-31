package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//合肖
@Scope("prototype")
@Component
public class RuleSumZodiac extends Rule {

    public RuleSumZodiac() {
        super(LotteryMarkSixType.SUM_ZODIAC);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<LotteryMarkSixType> subTypeList = wager.getSubLotteryMarkSixTypes();
        LotteryMarkSixType specialZodiacType = BeanHolder.getLotteryService().getZodiac(lotteryMarkSix.getIssue(), lotteryMarkSix.getSpecial());
        boolean isIn = stub.getNumber() % 10 == 1;
        if (isIn) {
//            中
            if (subTypeList.contains(specialZodiacType)) {
                return RuleResult.WIN;
            } else {
                return RuleResult.LOSE;
            }
        } else {
//            不中
            if (!subTypeList.contains(specialZodiacType)) {
                return RuleResult.WIN;
            } else {
                return RuleResult.LOSE;
            }
        }

    }

    @Override
    boolean isStubSplit() {
        return true;
    }
}
