package com.jvmaiaa.pokemon_api.service.Impl;

import com.jvmaiaa.pokemon_api.dto.PokeApiResponse;
import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonResultDTO;
import com.jvmaiaa.pokemon_api.service.ConsomePokeAPI;
import com.jvmaiaa.pokemon_api.service.PokemonService;
import com.jvmaiaa.pokemon_api.service.strategy.AlphabeticalSortStrategy;
import com.jvmaiaa.pokemon_api.service.strategy.LengthSortStrategy;
import com.jvmaiaa.pokemon_api.service.strategy.SortStrategy;
import com.jvmaiaa.pokemon_api.utils.VerificaEConverte;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.OrdenaPokemons;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.extraiNomes;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.filtraPokemonsPeloNomeBuscado;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.responseVazio;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.setaTipoOrdenacao;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.transformaListaComPrefixo;
import static com.jvmaiaa.pokemon_api.utils.VerificaEConverte.transformaNomesComPrefixoEmString;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final ConsomePokeAPI consomePokeAPI;

    public PokemonServiceImpl(ConsomePokeAPI consomePokeAPI) {
        this.consomePokeAPI = consomePokeAPI;
    }

    // tô implementando o padrão Strategy com auxília do Map para palavras-chave
    private final Map<String, SortStrategy> mapStrategy = Map.of(
            "ALPHABETICAL", new AlphabeticalSortStrategy(),
            "LENGTH", new LengthSortStrategy()
    );


    @Override // tipoOrdenacao -> faz requestAPI -> filtra -> ordena -> retorna
    public PokemonResultDTO<String> exibeNomeDosPokemons(String query, String sort) {
        String tipoOrdenacao = setaTipoOrdenacao(sort);
        List<String> pokemons = buscaNomesPokemonEmApiExterna();
        List<String> pokemonsFiltradosPelaQuery = filtraPokemonsPeloNomeBuscado(pokemons, query); // filtra pokemons pelo nome buscado
        mapStrategy.get(tipoOrdenacao).ordenaLista(pokemonsFiltradosPelaQuery); // ordena a partir do strategy que será definido pelo argumento
        return new PokemonResultDTO<>(pokemonsFiltradosPelaQuery);
    }

    // 1 defino o tipo de ordenacao
    // 2 Pego nomes em API externa
    // 3 crio lista vazia e adiciono apenas os pokemons que possuem a parte do nome buscado
    // 4 tranformo os pokemons que possuem a parte do nome buscado em string
    // 5 ordeno a lista de pokemons que possuem a parte do nome buscado
    // 6 Transformo a lista de String em PokemonHighlightDTO pora ter o retorno desejado
    @Override
    public PokemonResultDTO<PokemonHighlightDTO> exibeNomeEPrefixoDosPokemons(String query, String sort) {
        String tipoOrdenacao = setaTipoOrdenacao(sort);
        List<String> pokemons = buscaNomesPokemonEmApiExterna();
        List<PokemonHighlightDTO> highlightDTOS = new ArrayList<>();
        transformaListaComPrefixo(highlightDTOS, pokemons, query);
        List<String> nomesPrefixoToString = transformaNomesComPrefixoEmString(highlightDTOS);
        mapStrategy.get(tipoOrdenacao).ordenaLista(nomesPrefixoToString);
        List<PokemonHighlightDTO> pokemonsOrdenados = OrdenaPokemons(nomesPrefixoToString, query);
        return new PokemonResultDTO<>(pokemonsOrdenados);
    }

    private List<String> buscaNomesPokemonEmApiExterna() { // busca os nomes a partir do JSON retornado
        PokeApiResponse response = consomePokeAPI.requestToPokeApi();
        return (responseVazio(response))
                ? extraiNomes(response)
                : Collections.emptyList();
    }
}

