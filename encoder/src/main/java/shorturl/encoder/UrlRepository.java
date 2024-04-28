package shorturl.encoder;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface UrlRepository extends CassandraRepository<Url, String> {
    List<Url> findByCreatedAtLessThan(LocalDateTime createdAt);
}
