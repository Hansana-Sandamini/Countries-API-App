package lk.ijse.countriesapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class CountriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CountriesApiApplication.class, args);
    }

}
