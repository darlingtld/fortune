package fortune.service;

import fortune.pojo.User;
import fortune.thrift.client.AdminAppClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tangl9 on 2015-10-30.
 */
@Service
public class ThriftService {

    @Autowired
    private AdminAppClient adminAppClient;

    public boolean deposit(String userId, double account) {
        return adminAppClient.deposit(userId, account);
    }
}
