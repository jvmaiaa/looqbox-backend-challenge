package com.jvmaiaa.pokemon_api.service;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ConsomePokeAPI {

    private final WebClient webClient;

    public ConsomePokeAPI(WebClient webClient) {
        this.webClient = webClient;
    }

    public PokeApiResponse requestToPokeApi() { // tô fazendo minha requisação para PokeAPI
        return webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(PokeApiResponse.class)
                .block();
    }
}
