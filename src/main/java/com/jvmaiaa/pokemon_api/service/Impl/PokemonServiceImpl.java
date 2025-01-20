package com.jvmaiaa.pokemon_api.service.Impl;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonResultDTO;
import com.jvmaiaa.pokemon_api.service.PokemonService;
import com.jvmaiaa.pokemon_api.service.strategy.AlphabeticalSortStrategy;
import com.jvmaiaa.pokemon_api.service.strategy.LengthSortStrategy;
import com.jvmaiaa.pokemon_api.service.strategy.SortStrategy;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.extraiNomes;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.filtraPokemonsPeloNomeBuscado;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.responseVazio;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.setaValorDaOrdenacao;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final Map<String, SortStrategy> mapStrategy = Map.of(
            "ALPHABETICAL", new AlphabeticalSortStrategy(),
            "LENGTH", new LengthSortStrategy()
    );

    private final WebClient webClient;

    public PokemonServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public PokemonResultDTO<String> exibeNomeDosPokemons(String query, String sort) {
        List<String> pokemons = buscaNomesPokemonEmApiExterna();
        List<String> pokemonsFiltradosPelaQuery = filtraPokemonsPeloNomeBuscado(pokemons, query); // filtra pokemons pelo nome buscado
        mapStrategy.get(sort).ordenaLista(pokemonsFiltradosPelaQuery); // Ordena a partir do strategy que será definido pelo argumento
        return new PokemonResultDTO<>(pokemonsFiltradosPelaQuery);
    }

    @Override
    public PokemonResultDTO<PokemonHighlightDTO> exibeNomeEPrefixoDosPokemons(String query, String sort) {
        List<String> pokemons = buscaNomesPokemonEmApiExterna();

        List<PokemonHighlightDTO> highlighted = new ArrayList<>();
        for (String name : pokemons) {
            if (query == null || name.toLowerCase().contains(query.toLowerCase())) {
                String highlightedName = destacaCorrespondencia(name, query);
                highlighted.add(new PokemonHighlightDTO(name, highlightedName));
            }
        }

        // Ordena os pokémons destacados
        ordenaPokemonsHighlight(highlighted, sort);

        return new PokemonResultDTO<>(highlighted);
    }

    private List<String> buscaNomesPokemonEmApiExterna() {
        PokeApiResponse response = requestToPokeApi();
        return (responseVazio(response))
                ? extraiNomes(response)
                : Collections.emptyList();
    }

    private void ordenaPokemonsHighlight(List<PokemonHighlightDTO> pokemons, String sortType) {
        setaValorDaOrdenacao(sortType);
        // Extrai os nomes, ordena, e reatribui os nomes ordenados.
        List<String> pokemonNames = pokemons.stream()
                .map(PokemonHighlightDTO::getName)
                .toList();

        List<String> sortedNames = sortStrategy.ordenaLista(new ArrayList<>(pokemonNames));

        // Atualiza os objetos PokemonHighlightDTO com a ordem correta
        pokemons.sort((a, b) -> sortedNames.indexOf(a.getName()) - sortedNames.indexOf(b.getName()));
    }

    private String destacaCorrespondencia(String name, String query) {
        if (query == null || query.isEmpty()) return name;
        return name.replaceAll("(?i)(" + query + ")", "<pre>$1</pre>");
    }

    private PokeApiResponse requestToPokeApi() {
        return webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(PokeApiResponse.class)
                .block();
    }


}

