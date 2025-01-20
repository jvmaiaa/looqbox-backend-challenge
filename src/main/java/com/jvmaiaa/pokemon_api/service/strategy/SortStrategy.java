package com.jvmaiaa.pokemon_api.service.strategy;

import java.util.List;

public interface SortStrategy {

    List<String> ordenaLista(List<String> listaDePokemons); // garante que todos que implementem, deve usar uma ordenação
}
