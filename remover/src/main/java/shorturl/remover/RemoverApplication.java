package shorturl.remover;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RemoverApplication {

	public static void main(String[] args) {
		SpringApplication.run(RemoverApplication.class, args);
	}

}
