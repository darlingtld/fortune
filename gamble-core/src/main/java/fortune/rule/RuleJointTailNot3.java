package fortune.rule;

import fortune.pojo.LotteryMarkSixType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 三尾不中
@Scope("prototype")
@Component
public class RuleJointTailNot3 extends RuleJointTailNotParent {

    public RuleJointTailNot3() {
        super(LotteryMarkSixType.JOINT_TAIL_NOT_3);
    }

    @Override
    public int getJointTailNumber() {
        return 3;
    }
}
