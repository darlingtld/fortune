package fortune.thrift;

import fortune.service.BeanHolder;
import fortune.service.UserService;
import org.apache.thrift.TException;

/**
 * Created by tangl9 on 2015-10-29.
 */
public class DepositServiceImpl implements DepositService.Iface {

    @Override
    public boolean deposit(int userid, double account) throws TException {
        return true;
//        UserService userService = BeanHolder.getUserService();
//        userService.depositAccount(userid, account);
//        return true;
    }
}
