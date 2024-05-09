package shorturl.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EncoderChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(EncoderChangesProducer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public EncoderChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(){

        String topic = "encoder_recentchange";

        // Event source to read real time data.
    }
}
