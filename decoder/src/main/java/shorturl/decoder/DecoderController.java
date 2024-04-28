package shorturl.decoder;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class DecoderController {
    private final UrlRepository urlRepository;
    public DecoderController(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @GetMapping("/{shortId}")
    private RedirectView decodeUrl(@PathVariable String shortId) {
        Optional<Url> urlOptional = urlRepository.findById(shortId);
        return new RedirectView(urlOptional.orElseThrow().getLongUrl());
    }

}
