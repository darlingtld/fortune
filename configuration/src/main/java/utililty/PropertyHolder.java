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
    public static final String KAFKA_SERVER0 = prop.getProperty("kafka.server0");
    public static final String KAFKA_SERVER1 = prop.getProperty("kafka.server1");
    public static final String KAFKA_SERVER2 = prop.getProperty("kafka.server2");
    public static final String KAFKA_METADATA_BROKER_LIST = prop.getProperty("kafka.metadata.broker.list");
    public static final String KAFKA_PARTITIONER_CLASS = prop.getProperty("kafka.partitioner.class");
    public static final String KAFKA_REQUEST_REQUIRED_ACKS = prop.getProperty("kafka.request.required.acks");
    public static final String KAFKA_TOPIC = prop.getProperty("kafka.topic");
    public static final String KAFKA_GROUP = prop.getProperty("kafka.group");
    public static final Integer KAFKA_CONSUMER_THREAD_NUMBER = Integer.parseInt(prop.getProperty("kafka.consumer.thread.number"));
    public static final String ZOOKEEPER_SESSION_TIMEOUT_MS = prop.getProperty("zookeeper.session.timeout.ms");
    public static final String ZOOKEEPER_SYNC_TIME_MS = prop.getProperty("zookeeper.sync.time.ms");
    public static final String AUTO_COMMIT_INTERVAL_MS = prop.getProperty("auto.commit.interval.ms");
    public static final String THRIFT_USERAPP_HOST = prop.getProperty("thrift.userapp.host");
    public static final Integer THRIFT_USERAPP_PORT = Integer.parseInt(prop.getProperty("thrift.userapp.port"));
    public static final String THRIFT_ADMIN_HOST = prop.getProperty("thrift.adminapp.host");
    public static final Integer THRIFT_ADMIN_PORT = Integer.parseInt(prop.getProperty("thrift.adminapp.port"));
    public static final Integer THRIFT_TIMEOUT = Integer.parseInt(prop.getProperty("thrift.timeout"));
    public static final String[] GAMBLE_WAGE_WEEKDAYS = prop.getProperty("gamble.wage.weekday").split(",");
    public static final Integer LOTTERY_DRAW_HOUR = Integer.parseInt(prop.getProperty("lottery.draw.hour"));
    public static final Integer LOTTERY_DRAW_MINUTE = Integer.parseInt(prop.getProperty("lottery.draw.minute"));
    public static final Integer GAMBLE_WAGE_HOUR_START = Integer.parseInt(prop.getProperty("gamble.wage.hour.start"));
    public static final Integer GAMBLE_WAGE_MINUTE_START = Integer.parseInt(prop.getProperty("gamble.wage.minute.start"));
    public static final Integer GAMBLE_WAGE_HOUR_END = Integer.parseInt(prop.getProperty("gamble.wage.hour.end"));
    public static final Integer GAMBLE_WAGE_MINUTE_END = Integer.parseInt(prop.getProperty("gamble.wage.minute.end"));

}
