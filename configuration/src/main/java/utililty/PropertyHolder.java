package utililty;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by tangl9 on 2015-10-20.
 */
public class PropertyHolder {

    private static Properties prop = new Properties();

    static {
        try {
            prop.load(PropertyHolder.class.getClassLoader().getResourceAsStream("fortune.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final String KAFKA_ZOOKEEPER = prop.getProperty("kafka.zookeeper");
    public static final String KAFKA_SERVER1 = prop.getProperty("kafka.server1");
    public static final String KAFKA_SERVER2 = prop.getProperty("kafka.server2");
    public static final String KAFKA_METADATA_BROKER_LIST = prop.getProperty("kafka.metadata.broker.list");
    public static final String KAFKA_PARTITIONER_CLASS = prop.getProperty("kafka.partitioner.class");
    public static final String KAFKA_REQUEST_REQUIRED_ACKS = prop.getProperty("kafka.request.required.acks");
    public static final String KAFKA_TOPIC = prop.getProperty("kafka.topic");
    public static final String KAFKA_GROUP = prop.getProperty("kafka.group");
    public static final Integer KAFKA_CONSUMER_THREAD_NUMBER = Integer.parseInt(prop.getProperty("kafka.consumer.thread.number"));
    public static final Integer ZOOKEEPER_SESSION_TIMEOUT_MS = Integer.parseInt(prop.getProperty("zookeeper.session.timeout.ms"));
    public static final Integer ZOOKEEPER_SYNC_TIME_MS = Integer.parseInt(prop.getProperty("zookeeper.sync.time.ms"));
    public static final Integer AUTO_COMMIT_INTERVAL_MS = Integer.parseInt(prop.getProperty("auto.commit.interval.ms"));
    public static final String THRIFT_USERAPP_HOST = prop.getProperty("thrift.userapp.host");
    public static final Integer THRIFT_USERAPP_PORT = Integer.parseInt(prop.getProperty("thrift.userapp.port"));
    public static final String THRIFT_ADMIN_HOST = prop.getProperty("thrift.admin.host");
    public static final Integer THRIFT_ADMIN_PORT = Integer.parseInt(prop.getProperty("thrift.admin.port"));
    public static final Integer THRIFT_TIMEOUT = Integer.parseInt(prop.getProperty("thrift.timeout"));

}
