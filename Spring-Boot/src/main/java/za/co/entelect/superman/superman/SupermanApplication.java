package za.co.entelect.superman.superman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SupermanApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupermanApplication.class, args);
    }

}
