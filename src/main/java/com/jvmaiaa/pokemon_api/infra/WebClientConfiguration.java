package com.jvmaiaa.pokemon_api.infra;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfiguration {

    @Value("${pokemon.api.url}")
    private String POKE_API_URL;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(POKE_API_URL)
                .build();
    }
}
