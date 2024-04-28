package shorturl.decoder;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDateTime;

@Table
@Data
@AllArgsConstructor
public class Url {
    @PrimaryKey
    private String shortUrl;
    @NotNull
    private String longUrl;
    // SASI index
    private LocalDateTime createdAt;
}
