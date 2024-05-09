package shorturl.logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaURLLogger {
    private static final String KAFKA_TOPIC = "zakazane_url";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092"; // Adresy serwerów Kafka

    private Producer<String, String> producer;

    public KafkaURLLogger() {
        Properties props = new Properties();
        props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producer = new KafkaProducer<>(props);
    }

    public void logForbiddenURL(String url, String forbiddenWord) {
        String message = "Zakazane słowo '" + forbiddenWord + "' znalezione w URL: " + url;
        producer.send(new ProducerRecord<>(KAFKA_TOPIC, null, message));
    }

    public void close() {
        producer.close();
    }
}


