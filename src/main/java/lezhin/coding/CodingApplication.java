package lezhin.coding;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodingApplication {
    public static void main(String[] args) {
        SpringApplication.run(CodingApplication.class, args);
    }

}
