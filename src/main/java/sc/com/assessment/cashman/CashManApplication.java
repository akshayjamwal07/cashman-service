package sc.com.assessment.cashman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CashManApplication {

    public static void main(String[] args) {
        SpringApplication.run(CashManApplication.class, args);
    }

}
