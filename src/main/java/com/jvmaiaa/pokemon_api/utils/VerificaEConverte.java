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
        if (isCampoVazioOuNulo(query)){
            return name;
        }
        return name.replaceAll("(?i)(" + query + ")", "<pre>$1</pre>");
    }

    public static void transformaListaComPrefixo(List<PokemonHighlightDTO> listaDePokemonHighlightDTO,
                                                 List<String> listaDePokemonsBuscadosNaApi,
                                                 String nomeBuscado) {
        for (String name : listaDePokemonsBuscadosNaApi) {
            if (nomeBuscado == null || name.toLowerCase().contains(nomeBuscado.toLowerCase())) {
                String highlightedName = destacaCorrespondencia(name, nomeBuscado);
                listaDePokemonHighlightDTO.add(new PokemonHighlightDTO(name, highlightedName));
            }
        }
    }

    public static List<String> transformaNomesComPrefixoEmString(List<PokemonHighlightDTO> listaDePokemonHighlightDTO) {
        return listaDePokemonHighlightDTO.stream()
                .map(PokemonHighlightDTO::getName)
                .collect(Collectors.toList());
    }

    public static List<PokemonHighlightDTO> OrdenaPokemons(List<String> listaDeNomesComPrefixoBuscado, String nomeBuscado){
        return listaDeNomesComPrefixoBuscado.stream()
                .map(name -> new PokemonHighlightDTO(name, destacaCorrespondencia(name, nomeBuscado)))
                .collect(Collectors.toList());
    }


}
