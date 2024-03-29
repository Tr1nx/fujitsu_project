package com.example.fujitsuproject.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig{

    /**
     * Creates and configures a RestTemplate bean.
     * RestTemplate is used for making HTTP requests to external services.
     *
     * @return RestTemplate instance configured for synchronous HTTP communication.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}