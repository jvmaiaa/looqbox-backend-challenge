package com.jvmaiaa.pokemon_api.utils;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;

import java.util.ArrayList;
import java.util.List;

public class Verificacoes {

    public static boolean responseVazio(PokeApiResponse response) {
        return response != null && response.getResults() != null;
    }

    public static List<String> filtraPokemonsPeloNomeBuscado(List<String> pokemons, String nomeBuscado) {
        if (nomeBuscado == null || nomeBuscado.isEmpty()){
            return pokemons;
        }

        List<String> pokemonsFiltrados = new ArrayList<>();
        String nomeBuscadoLower = nomeBuscado.toLowerCase();

        for (String name : pokemons) {
            if (name.toLowerCase().contains(nomeBuscadoLower)) {
                pokemonsFiltrados.add(name);
            }
        }
        return pokemonsFiltrados;
    }

}
