package pe.edu.vallegrande.prueba.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${rapidapi.url}")
    private String rapidapiUrl;

    @Value("${rapidapi.host}")
    private String rapidapiHost;

    @Value("${rapidapi.apikey}")
    private String rapidapiApikey;

    @Bean
    public WebClient ageWebClient() {
        return WebClient.builder()
                .baseUrl(rapidapiUrl)
                .defaultHeader("x-rapidapi-host", rapidapiHost)
                .defaultHeader("x-rapidapi-key", rapidapiApikey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
    
}