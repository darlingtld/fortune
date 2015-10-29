package fortune.thrift.client;

import fortune.thrift.DepositService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class AdminAppClient {

    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 8090;
    public static final int TIMEOUT = 30000;

//    public static void main(String[] args) {
//
//        try {
//            TTransport transport;
//
//            transport = new TSocket(SERVER_IP, SERVER_PORT);
//            transport.open();
//
//            TProtocol protocol = new TBinaryProtocol(transport);
//            DepositService.Client client = new DepositService.Client(protocol);
//
//            System.out.println(client.deposit(2, 200));
//
//            transport.close();
//        } catch (TTransportException e) {
//            e.printStackTrace();
//        } catch (TException x) {
//            x.printStackTrace();
//        }
//    }
//
    public void startClient(int userId, double account) {
        TTransport transport = null;
        try {
            transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
            // 协议要和服务端一致
            TProtocol protocol = new TBinaryProtocol(transport);
            // TProtocol protocol = new TCompactProtocol(transport);
            // TProtocol protocol = new TJSONProtocol(transport);
            DepositService.Client client = new DepositService.Client(protocol);
            transport.open();
            boolean result = client.deposit(userId, account);
            System.out.println("Thrify client result =: " + result);
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }

    public static void main(String[] args) {
        AdminAppClient client = new AdminAppClient();
        client.startClient(2,100);

    }

}