package shorturl.encoder;

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
    private final UrlRepository urlRepository;
    private final UrlCensorer urlCensorer;
    private final Random random = new Random();
    private final RandomStringGenerator generator = new RandomStringGenerator.Builder()
            .withinRange('0', 'z')
            .filteredBy(Character::isLetterOrDigit)
            .get();


    public EncoderController(UrlRepository urlRepository, UrlCensorer urlCensorer) {
        this.urlRepository = urlRepository;
        this.urlCensorer = urlCensorer;
    }


    @PostMapping
    private ResponseEntity<Void> encodeUrl(@RequestBody String newUrl, UriComponentsBuilder ucb) {
        String shortId = generator.generate(8);
        while (urlRepository.existsById(shortId)) {
            shortId = generator.generate(8);
        }
        Url url = new Url(shortId,newUrl,LocalDateTime.now());
        urlRepository.save(url);
        urlCensorer.analyzeUrl(url);
        URI locationOfNewUrl = ucb
                .port(8082)
                .path("/{shortId}")
                .buildAndExpand(shortId)
                .toUri();
        return ResponseEntity.created(locationOfNewUrl).build();
    }

}
