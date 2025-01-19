package com.jvmaiaa.pokemon_api.dto;

import java.util.List;

public class PokeApiResponse {

    private List<PokemonNameDTO> results;

    public List<PokemonNameDTO> getResults() {
        return results;
    }

    public void setResults(List<PokemonNameDTO> results) {
        this.results = results;
    }
}