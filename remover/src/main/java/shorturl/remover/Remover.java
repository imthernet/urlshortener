package shorturl.remover;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class Remover {
    private final UrlRepository urlRepository;
    Logger logger = LoggerFactory.getLogger(Remover.class);

    @Autowired
    public Remover(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Scheduled(initialDelay = 15000, fixedDelay = 3600000)
    public void removeOldEntries() {
        int days = 14;

        logger.info("Cleaning entries older than: " + LocalDateTime.now().minusDays(days));
        List<Url> toRemove = urlRepository.findByCreatedAtLessThan(LocalDateTime.now().minusDays(days));

        logger.info("Removing URLs: " + toRemove);
        urlRepository.deleteAll(toRemove);
    }
}
