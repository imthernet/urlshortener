package shorturl.encoder;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

@Component
public class UrlCensorer {

    @Autowired
    private KafkaTemplate<Url, String> kafkaTemplate;

    private static final String FORBIDDEN_WORDS_FILE = "/forbidden_words.txt";
    private Set<String> forbiddenWords;

    public UrlCensorer() {
        forbiddenWords = new HashSet<>();
        loadForbiddenWords();
    }

    private void loadForbiddenWords() {
        try (InputStream inputStream = getClass().getResourceAsStream(FORBIDDEN_WORDS_FILE);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                forbiddenWords.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(Url key, String value) {
        kafkaTemplate.send("censored_url", key, value);
    }

    public void analyzeUrl(Url url){
        for(String word:forbiddenWords){
            if(url.getLongUrl().toLowerCase().contains(word)){
                sendMessage(url,word);
                return;
            }
        }
    }
}
