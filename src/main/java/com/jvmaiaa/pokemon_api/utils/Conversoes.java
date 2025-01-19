package com.jvmaiaa.pokemon_api.utils;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonNameDTO;

import java.util.List;
import java.util.stream.Collectors;

public class Conversoes {

    public static List<String> extraiNomes(PokeApiResponse response) {
        return response.getResults()
                .stream()
                .map(PokemonNameDTO::getName)
                .collect(Collectors.toList());
    }
}
