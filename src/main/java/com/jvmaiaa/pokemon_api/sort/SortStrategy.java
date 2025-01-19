package com.jvmaiaa.pokemon_api.sort;

import java.util.List;

public interface SortStrategy {

    List<String> sort(List<String> listaDePokemons);
}
