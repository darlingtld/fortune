package fortune.rule;

import fortune.pojo.LotteryMarkSixType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 二尾中
@Scope("prototype")
@Component
public class RuleJointTail2 extends RuleJointTailParent {

    public RuleJointTail2() {
        super(LotteryMarkSixType.JOINT_TAIL_2);
    }

    @Override
    public int getJointTailNumber() {
        return 2;
    }
}
