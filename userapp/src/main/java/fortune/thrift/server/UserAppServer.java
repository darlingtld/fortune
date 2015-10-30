package fortune.thrift.server;

import common.Utils;
import fortune.thrift.DepositService;
import fortune.thrift.DepositServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.springframework.stereotype.Component;
import utililty.PropertyHolder;

import javax.annotation.PostConstruct;

@Component
public class UserAppServer {

    public static final int SERVER_PORT = PropertyHolder.THRIFT_USERAPP_PORT;

    @PostConstruct
    private void init() {
        new Thread(this::startServer).start();
    }

    public void startServer() {
        try {
            Utils.logger.info("userapp thrift server start .... listening on port {}", SERVER_PORT);
            TProcessor tprocessor = new DepositService.Processor<DepositService.Iface>(new DepositServiceImpl());

            TNonblockingServerSocket tnbSocketTransport = new TNonblockingServerSocket(SERVER_PORT);
            TNonblockingServer.Args tnbArgs = new TNonblockingServer.Args(tnbSocketTransport);
            tnbArgs.processor(tprocessor);
            tnbArgs.transportFactory(new TFramedTransport.Factory());
            tnbArgs.protocolFactory(new TCompactProtocol.Factory());

            TServer server = new TNonblockingServer(tnbArgs);

            server.serve();

        } catch (Exception e) {
            Utils.logger.error("userapp thrift server start error!!!");
            e.printStackTrace();
        }
    }

}