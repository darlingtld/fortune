package thrift.server;

import org.apache.thrift.TException;

/**
 * Created by tangl9 on 2015-10-15.
 */
public class AdditionServiceHandler implements AdditionService.Iface {

    @Override
    public int add(int n1, int n2) throws TException {
        return n1 + n2;
    }

}
