package fortune.thrift;

import fortune.service.BeanHolder;
import org.apache.thrift.TException;

/**
 * Created by tangl9 on 2015-10-29.
 */
public class UserServiceImpl implements UserService.Iface {


    @Override
    public boolean deposit(String userid, double account) throws TException {
        return BeanHolder.getUserService().depositAccount(userid, account);
    }
}
