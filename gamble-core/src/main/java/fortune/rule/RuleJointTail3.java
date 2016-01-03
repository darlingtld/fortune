package fortune.rule;

import fortune.pojo.LotteryMarkSixType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 三尾中
@Scope("prototype")
@Component
public class RuleJointTail3 extends RuleJointTailParent {

    public RuleJointTail3() {
        super(LotteryMarkSixType.JOINT_TAIL_3);
    }

    @Override
    public int getJointTailNumber() {
        return 3;
    }
}
