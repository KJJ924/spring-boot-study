package test.demo.profileTest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("product")
@Configuration
public class ProductConfig {

    @Bean
    String hello(){
        return "ProductConfig";
    }
}
