package fortune.rule;

import fortune.pojo.*;
import fortune.service.BeanHolder;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by tangl9 on 2015-10-26.
 */
//尾数
@Scope("prototype")
@Component
public class RuleTailNum extends Rule {

    public RuleTailNum() {
        super(LotteryMarkSixType.TAIL_NUM);
    }

    @Override
    public RuleResult getRuleResult(LotteryMarkSix lotteryMarkSix, LotteryMarkSixWagerStub stub, LotteryMarkSixWager wager) {
        List<Integer> tailList = Arrays.asList(
                lotteryMarkSix.getOne() % 10,
                lotteryMarkSix.getTwo() % 10,
                lotteryMarkSix.getThree() % 10,
                lotteryMarkSix.getFour() % 10,
                lotteryMarkSix.getFive() % 10,
                lotteryMarkSix.getSix() % 10,
                lotteryMarkSix.getSpecial() % 10
        );
        if (tailList.contains(stub.getNumber())) {
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
        return true;
    }
}
