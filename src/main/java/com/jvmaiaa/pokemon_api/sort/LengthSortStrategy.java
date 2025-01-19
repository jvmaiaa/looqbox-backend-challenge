package com.jvmaiaa.pokemon_api.sort;

import java.util.List;

public class LengthSortStrategy implements SortStrategy {

    @Override
    public List<String> sort(List<String> pokemons) {
        // Implementação manual de Bubble Sort para ordenação por tamanho
        int n = pokemons.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (pokemons.get(j).length() > pokemons.get(j + 1).length()) {
                    String temp = pokemons.get(j);
                    pokemons.set(j, pokemons.get(j + 1));
                    pokemons.set(j + 1, temp);
                }
            }
        }
        return pokemons;
    }
}
