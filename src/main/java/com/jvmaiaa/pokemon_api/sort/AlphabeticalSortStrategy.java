package com.jvmaiaa.pokemon_api.sort;

import java.util.List;

public class AlphabeticalSortStrategy implements SortStrategy {

    @Override
    public List<String> sort(List<String> listaDePokemons) {
        int tamanhoDaLista = listaDePokemons.size(); // n
        for (int i = 0; i < tamanhoDaLista - 1; i++) { // n-1
            for (int j = 0; j < tamanhoDaLista - i - 1; j++) { // (n^2) - 1
                if (isPrimeiraMaior(listaDePokemons.get(j), listaDePokemons.get(j + 1))) { // (n^2 - n) + 1
                    trocaPosicao(listaDePokemons, j, j + 1); // n^2
                }
            }
        }
        return listaDePokemons; // 1
    }
    // G(n) = n + n-1 + n^2 - 1 + n^2 - n + n^2 + 1
    // G(n) = 3n^2 - n + n + 1
    // O(n^2) -> esse vai ser o resultado da complexidade do código no PIOR CASO

    private void trocaPosicao(List<String> lista, int i, int j){
        String variavelAuxiliar = lista.get(i); // 1
        lista.set(i, lista.get(j)); // 1 - substitui o elemento do índice atual pelo elemento do próximo índice
        lista.set(j, variavelAuxiliar); // 1
    }
    // G(n) = 1 + 1 + 1
    // O(1) -> esse vai ser o resultado da complexidade do código no PIOR CASO

    private boolean isPrimeiraMaior(String primeiraPalavra, String segundaPalavra) {
        int tamanhoPrimeira = primeiraPalavra.length(); // 1
        int tamanhoSegunda = segundaPalavra.length(); // 1
        int comprimentoMinimo = (tamanhoPrimeira <= tamanhoSegunda) // 1
                ? tamanhoPrimeira
                : tamanhoSegunda;

        for (int i = 0; i < comprimentoMinimo; i++) { // n
            char caracterePrimeiraPalavra = Character.toLowerCase(primeiraPalavra.charAt(i)); // n
            char caractereSegundaPalavra = Character.toLowerCase(segundaPalavra.charAt(i)); // n
            if (caracterePrimeiraPalavra != caractereSegundaPalavra) { // n + 1
                return caracterePrimeiraPalavra > caractereSegundaPalavra; // n
            }
        }

        return tamanhoPrimeira > tamanhoSegunda; // 1
    }

    // G(n) = 1 + 1 + 1 + n + n + n + n + 1 + n + 1
    // G(n) = 5n + 5
    // O(n) -> esse vai ser o resultado da complexidade do código no PIOR CASO
}
