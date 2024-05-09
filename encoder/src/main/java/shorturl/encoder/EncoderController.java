package shorturl.encoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.apache.commons.text.RandomStringGenerator;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Random;

@RestController
public class EncoderController {
    private static final Logger logger = LoggerFactory.getLogger(EncoderController.class);
    private final UrlRepository urlRepository;
    private final Random random = new Random();
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(Character::isLetterOrDigit)
            .get();


    public EncoderController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    @PostMapping
    private ResponseEntity<Void> encodeUrl(@RequestBody String newUrl, UriComponentsBuilder ucb) {
        logger.info("Received URL to encode: {}", newUrl);

        String shortId = generator.generate(8);
        while (urlRepository.existsById(shortId)) {
            shortId = generator.generate(8);
            logger.info("Regenerating short ID as the previous was already in use.");
        }
        Url url = new Url(shortId,newUrl,LocalDateTime.now());
        urlRepository.save(url);
        logger.info("Short URL created: {} -> {}", shortId, newUrl);

        URI locationOfNewUrl = ucb
                .port(8082)
                .path("/{shortId}")
                .buildAndExpand(shortId)
                .toUri();

        logger.info("Location of new URL: {}", locationOfNewUrl);
        return ResponseEntity.created(locationOfNewUrl).build();
    }

}
