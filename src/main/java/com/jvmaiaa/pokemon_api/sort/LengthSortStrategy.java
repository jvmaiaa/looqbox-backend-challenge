package com.jvmaiaa.pokemon_api.sort;

import java.util.List;

public class LengthSortStrategy implements SortStrategy {

    @Override
    public List<String> sort(List<String> listaDePokemons) {
        int n = listaDePokemons.size(); // n
        for (int i = 0; i < n - 1; i++) { // n - 1
            for (int j = 0; j < n - i - 1; j++) { // n^2 - 1
                if (listaDePokemons.get(j).length() > listaDePokemons.get(j + 1).length()) { // n^2 - 1
                    trocaPosicao(listaDePokemons, j, j+1); // n^2
                }
            }
        }
        return listaDePokemons; // 1
    }
    // G(n) = n + n - 1 + n^2 - 1 + n^2 - 1 + n^2 + 1
    // G(n) = 3n^2 + 2n + 1 - 2
    // O(n) = n^2 -> esse vai ser o resultado da complexidade do código no PIOR CASO

    private void trocaPosicao(List<String> lista, int i, int j){
        String variavelAuxiliar = lista.get(i); // 1
        lista.set(i, lista.get(j)); // 1 - substitui o elemento do índice atual pelo elemento do próximo índice
        lista.set(j, variavelAuxiliar); // 1
    }
    // G(n) = 1 + 1 + 1
    // O(1) -> esse vai ser o resultado da complexidade do código no PIOR CASO
}
