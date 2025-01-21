package com.jvmaiaa.pokemon_api.utils;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
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
        if (isCampoVazioOuNulo(nomeBuscado)){
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

    public static List<PokemonHighlightDTO> filtraPokemonsPeloNomeBuscadoComPrefixo(List<String> pokemons, String nomeBuscado) {
        // Se não houver query, retorna todos com prefixo vazio
        if (isCampoVazioOuNulo(nomeBuscado)) {
            return pokemons.stream()
                    .map(name -> new PokemonHighlightDTO(name, name))
                    .toList();
        }

        // Filtra e cria os objetos destacados
        return pokemons.stream()
                .filter(pokemonAtual -> pokemonAtual != null && pokemonAtual.toLowerCase().contains(nomeBuscado.toLowerCase()))
                .map(pokemonAtual -> new PokemonHighlightDTO(
                        pokemonAtual,
                        destacaCorrespondencia(pokemonAtual, nomeBuscado)
                ))
                .toList();
    }

    public static List<String> extraiNomes(PokeApiResponse response) {
        return response.getResults()
                .stream()
                .map(PokemonNameDTO::getName)
                .collect(Collectors.toList());
    }

    public static List<String> extraiNomesComPrefixo(List<PokemonHighlightDTO> response) {
        return response.stream()
                .map(PokemonHighlightDTO::getName)
                .toList();
    }

    public static boolean isCampoVazioOuNulo(String campo) {
        return (campo == null || campo.isEmpty());
    }

    public static String setaTipoOrdenacao(String tipoDeOrdenacao) {
        if (isCampoVazioOuNulo(tipoDeOrdenacao)) {
            tipoDeOrdenacao = "ALPHABETICAL";
        }
        return switch (tipoDeOrdenacao.toUpperCase()) {
            case "LENGTH" -> "LENGTH";
            case "ALPHABETICAL" -> "ALPHABETICAL";
            default -> throw new SortInvalidException("Tipo de ordenação inválida: " + tipoDeOrdenacao);
        };
    }

    public static String destacaCorrespondencia(String name, String query) {
        if (query == null || query.isEmpty()) return name;
        return name.replaceAll("(?i)(" + query + ")", "<pre>$1</pre>");
    }

}
