package shorturl.encoder;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class EncoderController {
    private final UrlRepository urlRepository;

    public EncoderController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }
}
