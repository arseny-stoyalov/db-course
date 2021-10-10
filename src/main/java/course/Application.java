package course;

import course.dto.Passenger;
import course.repository.PassengerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner loadData(PassengerRepository repository) {
        return (args) -> {
            // fetch all customers
            log.info("Passengers found with findAll():");
            log.info("-------------------------------");
            for (Passenger passenger : repository.findAll()) {
                log.info(passenger.toString());
            }

            log.info("Passenger found with filter:");
            log.info("--------------------------------------------");
            for (Passenger pass : repository.findBySurnameStartsWithIgnoreCase("Стоялов")) {
                log.info(pass.toString());
            }
            log.info("");
        };
    }


}
