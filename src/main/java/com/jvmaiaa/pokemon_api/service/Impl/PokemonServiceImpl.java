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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.extraiNomes;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.filtraPokemonsPeloNomeBuscado;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.filtraPokemonsPeloNomeBuscadoComPrefixo;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.responseVazio;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.setaTipoOrdenacao;

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

    private PokeApiResponse requestToPokeApi() { // tô fazendo minha requisação para PokeAPI
        return webClient.get()
                .uri("/pokemon?limit=2000")
                .retrieve()
                .bodyToMono(PokeApiResponse.class)
                .block();
    }

    private List<String> buscaNomesPokemonEmApiExterna() { // busca os nomes a partir do JSON retornado
        PokeApiResponse response = requestToPokeApi();
        return (responseVazio(response))
                ? extraiNomes(response)
                : Collections.emptyList();
    }

    @Override // tipoOrdenacao -> faz requestAPI -> filtra -> ordena -> retorna
    public PokemonResultDTO<String> exibeNomeDosPokemons(String query, String sort) {
        String tipoOrdenacao = setaTipoOrdenacao(sort);
        List<String> pokemons = buscaNomesPokemonEmApiExterna();
        List<String> pokemonsFiltradosPelaQuery = filtraPokemonsPeloNomeBuscado(pokemons, query); // filtra pokemons pelo nome buscado
        mapStrategy.get(tipoOrdenacao).ordenaLista(pokemonsFiltradosPelaQuery); // ordena a partir do strategy que será definido pelo argumento
        return new PokemonResultDTO<>(pokemonsFiltradosPelaQuery);
    }

    @Override
    public PokemonResultDTO<PokemonHighlightDTO> exibeNomeEPrefixoDosPokemons(String query, String sort) {
        String tipoOrdenacao = setaTipoOrdenacao(sort);
        List<String> pokemons = buscaNomesPokemonEmApiExterna();
        List<PokemonHighlightDTO> pokemonsFiltradosPelaQuery = filtraPokemonsPeloNomeBuscadoComPrefixo(pokemons, query);

        mapStrategy.get(tipoOrdenacao).ordenaLista(
                pokemonsFiltradosPelaQuery.stream()
                        .map(PokemonHighlightDTO::getName)
                        .collect(Collectors.toList()));
        return new PokemonResultDTO<>(pokemonsFiltradosPelaQuery);
    }

}

