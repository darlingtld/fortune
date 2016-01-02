package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by tangl9 on 2015-10-26.
 */
//一肖
@Scope("prototype")
@Component
public class RuleOneZodiac extends Rule {

    public RuleOneZodiac() {
        super(LotteryMarkSixType.ONE_ZODIAC);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<LotteryMarkSixType> typeList = Arrays.asList(
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getOne()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getTwo()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getThree()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getFour()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getFive()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getSix()),
                BeanHolder.getLotteryService().getZodiac(wager.getLotteryIssue(), lotteryMarkSix.getSpecial())
        );
        if (typeList.contains(stub.getLotteryMarkSixType())) {
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
