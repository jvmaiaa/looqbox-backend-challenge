package com.jvmaiaa.pokemon_api.dto;

import java.util.List;

public class PokemonResultDTO<T> {

    // /pokemons pega apenas os nomes
    // /pokemons/highlight pega os nomes e o prefixo pesquisado
    private List<T> result;

    public PokemonResultDTO() {}

    public PokemonResultDTO(List<T> result) {
         this.result = result;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}
