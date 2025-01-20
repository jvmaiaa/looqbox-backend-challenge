package com.jvmaiaa.pokemon_api.utils;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonNameDTO;
import com.jvmaiaa.pokemon_api.infra.exception.SortInvalidException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VerificaEConverte {

    public static boolean responseVazio(PokeApiResponse response) {
        return response != null && response.getResults() != null; // tô verificando se nem o JSON nem os nomes estão vazios
    }

    public static List<String> filtraPokemonsPeloNomeBuscado(List<String> pokemons, String nomeBuscado) {
        if (campoVazioOuNulo(nomeBuscado)){
            return pokemons;
        }

        List<String> pokemonsFiltrados = new ArrayList<>();
        for (String name : pokemons) {
            if (name.toLowerCase().contains(nomeBuscado.toLowerCase())) {
                pokemonsFiltrados.add(name);
            }
        }
        return pokemonsFiltrados;
    }

    public static List<String> extraiNomes(PokeApiResponse response) {
        return response.getResults()
                .stream()
                .map(PokemonNameDTO::getName)
                .collect(Collectors.toList());
    }

    public static boolean campoVazioOuNulo(String campo) {
        return (campo != null && !campo.isEmpty());
    }

    public static String setaValorDaOrdenacao(String tipoDeOrdenacao) {
        if (campoVazioOuNulo(tipoDeOrdenacao)) {
            tipoDeOrdenacao = "ALPHABETICAL";
        }
        return switch (tipoDeOrdenacao.toUpperCase()) {
            case "LENGTH" -> "LENGTH";
            case "ALPHABETICAL" -> "ALPHABETICAL";
            default -> throw new SortInvalidException("Tipo de ordenação inválida: " + tipoDeOrdenacao);
        };
    }

}
