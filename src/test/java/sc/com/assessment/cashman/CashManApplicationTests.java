package sc.com.assessment.cashman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootApplication(scanBasePackages = "sc.com.assessment.cashman")
@ActiveProfiles("TEST")
public class CashManApplicationTests {

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Bean
    public MockMvc mockMvc() {
        return MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    public static void main(final String... args) {
        SpringApplication.run(CashManApplicationTests.class, args);
    }
}
