package fortune.thrift.client;

import common.Utils;
import fortune.thrift.DepositService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.springframework.stereotype.Component;
import utililty.PropertyHolder;

@Component
public class AdminAppClient {

    public static final String SERVER_IP = PropertyHolder.THRIFT_USERAPP_HOST;
    public static final int SERVER_PORT = PropertyHolder.THRIFT_USERAPP_PORT;
    public static final int TIMEOUT = PropertyHolder.THRIFT_TIMEOUT;

    public boolean deposit(int userId, double account) {
        Utils.logger.info("[thrift call] deposit user id {}, account {}", userId, account);
        TTransport transport = null;
        boolean result = false;
        try {
            transport = new TFramedTransport(new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT));
            TProtocol protocol = new TCompactProtocol(transport);
            DepositService.Client client = new DepositService.Client(protocol);
            transport.open();
            result = client.deposit(userId, account);
            Utils.logger.debug("Thrift client result = {} ", result);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
        return result;
    }


//    public static void main(String[] args) {
//        AdminAppClient client = new AdminAppClient();
//        client.deposit(2, 100);
//
//    }

}