package pe.edu.vallegrande.imageGenerator.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${rapidapi.url}")
    private String apiUrl;

    @Value("${rapidapi.host}")
    private String apiHost;

    @Value("${rapidapi.apikey}")
    private String apiKey;

    @Bean
    public WebClient imageWebClient() {
        return WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader("x-api-host", apiHost)
                .defaultHeader("x-api-key", apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
