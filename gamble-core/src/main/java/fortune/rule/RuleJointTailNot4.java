package fortune.rule;

import fortune.pojo.LotteryMarkSixType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by tangl9 on 2015-10-26.
 */
//连尾 四尾不中
@Scope("prototype")
@Component
public class RuleJointTailNot4 extends RuleJointTailNotParent {

    public RuleJointTailNot4() {
        super(LotteryMarkSixType.JOINT_TAIL_NOT_4);
    }

    @Override
    public int getJointTailNumber() {
        return 4;
    }
}
