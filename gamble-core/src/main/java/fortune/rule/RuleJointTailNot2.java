package fortune.rule;

import fortune.pojo.LotteryMarkSixType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 二尾不中
@Scope("prototype")
@Component
public class RuleJointTailNot2 extends RuleJointTailNotParent {

    public RuleJointTailNot2() {
        super(LotteryMarkSixType.JOINT_TAIL_NOT_2);
    }

    @Override
    public int getJointTailNumber() {
        return 2;
    }
}
