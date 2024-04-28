package shorturl.decoder;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UrlRepository extends CassandraRepository<Url, String> {
}
