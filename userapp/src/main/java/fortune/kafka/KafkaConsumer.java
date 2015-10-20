package fortune.kafka;

/**
 * Created by tangl9 on 2015-10-20.
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import utililty.PropertyHolder;

public class KafkaConsumer {
    private final ConsumerConnector consumer;
    private final String topic;
    private ExecutorService executor;

    public KafkaConsumer(String zookeeper, String groupId, String topic) {
        consumer = Consumer.createJavaConsumerConnector(createConsumerConfig(zookeeper, groupId));
        this.topic = topic;
    }

    public void shutdown() {
        if (consumer != null)
            consumer.shutdown();
        if (executor != null)
            executor.shutdown();
    }

    public void run(int numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        executor = Executors.newFixedThreadPool(numThreads);

        // now create an object to consume the messages
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new ConsumerMsgTask(stream, threadNumber));
            threadNumber++;
        }
    }

    private static ConsumerConfig createConsumerConfig(String zookeeper, String groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", zookeeper);
        props.put("group.id", groupId);
        props.put("zookeeper.session.timeout.ms", PropertyHolder.ZOOKEEPER_SESSION_TIMEOUT_MS);
        props.put("zookeeper.sync.time.ms", PropertyHolder.ZOOKEEPER_SYNC_TIME_MS);
        props.put("auto.commit.interval.ms", PropertyHolder.AUTO_COMMIT_INTERVAL_MS);

        return new ConsumerConfig(props);
    }

    public static void main(String[] arg) {
        String zooKeeper = PropertyHolder.KAFKA_ZOOKEEPER;
        String groupId = PropertyHolder.KAFKA_GROUP;
        String topic = PropertyHolder.KAFKA_TOPIC;
        int threads = PropertyHolder.KAFKA_CONSUMER_THREAD_NUMBER;

        KafkaConsumer kafkaConsumer = new KafkaConsumer(zooKeeper, groupId, topic);
        kafkaConsumer.run(threads);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        kafkaConsumer.shutdown();
    }
}
