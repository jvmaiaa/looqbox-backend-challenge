package com.jvmaiaa.pokemon_api.service;

import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonResultDTO;

public interface PokemonService {

    PokemonResultDTO<String> exibeNomeDosPokemons(String query, String sortType);
    PokemonResultDTO<PokemonHighlightDTO> exibeNomeEPrefixoDosPokemons(String query, String sortType);

}
