package com.jvmaiaa.pokemon_api.service.Impl;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonNameDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonResultDTO;
import com.jvmaiaa.pokemon_api.service.PokemonService;
import com.jvmaiaa.pokemon_api.sort.AlphabeticalSortStrategy;
import com.jvmaiaa.pokemon_api.sort.LengthSortStrategy;
import com.jvmaiaa.pokemon_api.sort.SortStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final WebClient webClient;
    private static final String POKE_API_URL = "https://pokeapi.co/api/v2";

    public PokemonServiceImpl(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl(POKE_API_URL).build();
    }


    @Override
    public PokemonResultDTO<String> exibeNomeDosPokemons(String query, String sortType) {
        List<String> pokemons = fetchAllPokemonNames();
        // Filtra os pokémons pelo nome, se houver query
        List<String> filtered = filtraPokemons(pokemons, query);
        // Ordena conforme o parâmetro
        ordenaPokemons(filtered, sortType);
        return new PokemonResultDTO<>(filtered);
    }

    @Override
    public PokemonResultDTO<PokemonHighlightDTO> exibeNomeEPrefixoDosPokemons(String query, String sortType) {
        List<String> pokemons = fetchAllPokemonNames();

        List<PokemonHighlightDTO> highlighted = new ArrayList<>();
        for (String name : pokemons) {
            if (query == null || name.toLowerCase().contains(query.toLowerCase())) {
                String highlightedName = destacaCorrespondencia(name, query);
                highlighted.add(new PokemonHighlightDTO(name, highlightedName));
            }
        }

        // Ordena os pokémons destacados
        ordenaPokemonsHighlight(highlighted, sortType);

        return new PokemonResultDTO<>(highlighted);
    }

    private List<String> fetchAllPokemonNames() {
        PokeApiResponse response = webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(PokeApiResponse.class)
                .block();

        if (response != null && response.getResults() != null) {
            // Extrai apenas os nomes da lista de objetos
            return response.getResults().stream()
                    .map(PokemonNameDTO::getName)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private PokemonResultDTO<PokemonHighlightDTO> fetchHighlightedPokemon() {
        return webClient.get()
                .uri("/pokemon/highlight")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<PokemonResultDTO<PokemonHighlightDTO>>() {})
                .block();
    }

    private List<String> filtraPokemons(List<String> pokemons, String query) {
        if (query == null || query.isEmpty()) return pokemons;

        List<String> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (String name : pokemons) {
            if (name.toLowerCase().contains(lowerQuery)) {
                filtered.add(name);
            }
        }
        return filtered;
    }

    private void ordenaPokemons(List<String> pokemons, String sortType) {
        SortStrategy sortStrategy = getSortStrategy(sortType);
        sortStrategy.sort(pokemons);
    }

    private void ordenaPokemonsHighlight(List<PokemonHighlightDTO> pokemons, String sortType) {
        SortStrategy sortStrategy = getSortStrategy(sortType);
        // Extrai os nomes, ordena, e reatribui os nomes ordenados.
        List<String> pokemonNames = pokemons.stream()
                .map(PokemonHighlightDTO::getName)
                .toList();

        List<String> sortedNames = sortStrategy.sort(new ArrayList<>(pokemonNames));

        // Atualiza os objetos PokemonHighlightDTO com a ordem correta
        pokemons.sort((a, b) -> sortedNames.indexOf(a.getName()) - sortedNames.indexOf(b.getName()));
    }

    private String destacaCorrespondencia(String name, String query) {
        if (query == null || query.isEmpty()) return name;
        return name.replaceAll("(?i)(" + query + ")", "<pre>$1</pre>");
    }

    private SortStrategy getSortStrategy(String sortType) {
        if (sortType == null || sortType.isEmpty()) {
            sortType = "ALPHABETICAL";
        }
        return switch (sortType.toUpperCase()) {
            case "LENGTH" -> new LengthSortStrategy();
            case "ALPHABETICAL" -> new AlphabeticalSortStrategy();
            default -> throw new IllegalArgumentException("Invalid sort type: " + sortType);
        };
    }

}

