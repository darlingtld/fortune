package fortune.thrift.server;

import fortune.thrift.DepositService;
import fortune.thrift.DepositServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

public class UserAppServer {
    public static final int SERVER_PORT = 8090;

//    public static void StartsimpleServer(DepositService.Processor<DepositService.Iface> processor) {
//        try {
//            TServerTransport serverTransport = new TServerSocket(SERVER_PORT);
////            TServer server = new TSimpleServer(
////                    new TServer.Args(serverTransport).processor(processor));
//
////             Use this for a multithreaded server
//            TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
//
//            System.out.println("Starting the simple server...");
//            server.serve();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        StartsimpleServer(new DepositService.Processor<>(new DepositServiceImpl()));
//    }

    public void startServer() {
        try {
            System.out.println("HelloWorld TSimpleServer start ....");

            TProcessor tprocessor = new DepositService.Processor<DepositService.Iface>(new DepositServiceImpl());

            // 简单的单线程服务模型，一般用于测试
            TServerSocket serverTransport = new TServerSocket(SERVER_PORT);
            TServer.Args tArgs = new TServer.Args(serverTransport);
            tArgs.processor(tprocessor);
            tArgs.protocolFactory(new TBinaryProtocol.Factory());
            // tArgs.protocolFactory(new TCompactProtocol.Factory());
            // tArgs.protocolFactory(new TJSONProtocol.Factory());
            TServer server = new TSimpleServer(tArgs);
            server.serve();

        } catch (Exception e) {
            System.out.println("Server start error!!!");
            e.printStackTrace();
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        UserAppServer server = new UserAppServer();
        server.startServer();
    }

}