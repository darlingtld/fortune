package fortune.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * Created by tangl9 on 2015-10-20.
 */
public class KafkaProducer {
    public static void main(String[] args) {
        int events = 100;

        Properties props = new Properties();
        props.put("metadata.broker.list", "localhost:9093,localhost:9094");
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("key.serializer.class", "kafka.serializer.StringEncoder");
        props.put("partitioner.class", "fortune.kafka.KafkaPartitioner");
        /**
         * 0, which means that the producer never waits for an acknowledgement from the broker (the same behavior as 0.7). This option provides the lowest latency but the weakest durability guarantees (some data will be lost when a server fails).
         * 1, which means that the producer gets an acknowledgement after the leader replica has received the data. This option provides better durability as the client waits until the server acknowledges the request as successful (only messages that were written to the now-dead leader but not yet replicated will be lost).
         * -1, which means that the producer gets an acknowledgement after all in-sync replicas have received the data. This option provides the best durability, we guarantee that no messages will be lost as long as at least one in sync replica remains.
         */
        props.put("request.required.acks", "1");
        ProducerConfig config = new ProducerConfig(props);

        Producer<String, String> producer = new Producer<String, String>(config);
        // produce messages
        long start = System.currentTimeMillis();
        for (long i = 0; i < events; i++) {
            long runtime = new Date().getTime();
            String ip = "192.168.2." + i;
            String msg = runtime + ", kafka example, " + ip;
            // create topic if there does not exist, replication-factor defaults to 1 and partitions defaults to 0
            KeyedMessage<String, String> data = new KeyedMessage<String, String>("lingda-test", ip, msg);
            producer.send(data);
        }
        System.out.println("time spent:" + (System.currentTimeMillis() - start));
        producer.close();
    }
}
