package com.jvmaiaa.pokemon_api.controller;

import com.jvmaiaa.pokemon_api.dto.PokemonHighlightDTO;
import com.jvmaiaa.pokemon_api.dto.PokemonResultDTO;
import com.jvmaiaa.pokemon_api.service.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<PokemonResultDTO<String>> buscaPokemonsExibindoNome(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String sort) {

        PokemonResultDTO<String> pokemons = pokemonService.exibeNomeDosPokemons(query, sort);
        return ResponseEntity.ok(pokemons);
    }

    @GetMapping("/highlight")
    public ResponseEntity<PokemonResultDTO<PokemonHighlightDTO>> buscaPokemonsExibindoNomeEPrefixo(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String sort) {

        PokemonResultDTO<PokemonHighlightDTO> pokemons = pokemonService.exibeNomeEPrefixoDosPokemons(query, sort);
        return ResponseEntity.ok(pokemons);
    }
}
