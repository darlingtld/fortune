package fortune.kafka;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerMsgTask implements Runnable {
    private KafkaStream kafkaStream;
    private int mThreadNumber;

    public ConsumerMsgTask(KafkaStream stream, int threadNumber) {
        mThreadNumber = threadNumber;
        kafkaStream = stream;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = kafkaStream.iterator();
        while (it.hasNext()) {
            System.out.println("Thread " + mThreadNumber + ": " + new String(it.next().message()));
        }
        System.out.println("Shutting down Thread: " + mThreadNumber);
    }
}