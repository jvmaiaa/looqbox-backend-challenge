package com.jvmaiaa.pokemon_api.service.strategy;

import java.util.List;

public class AlphabeticalSortStrategy implements SortStrategy {

    @Override
    public List<String> ordenaLista(List<String> listaDePokemons) {
        int tamanhoDaLista = listaDePokemons.size(); // n -> pega o tamanho da lista
        for (int i = 0; i < tamanhoDaLista - 1; i++) { // n-1 -> percorre a lista
            for (int j = 0; j < tamanhoDaLista - i - 1; j++) { // (n^2) - 1 -> pega o elemento atual e o próximo
                if (isPrimeiraMaior(listaDePokemons.get(j), listaDePokemons.get(j + 1))) { // (n^2 - n) + 1 -> compara se primeira palavra é maior que segunda
                    trocaPosicao(listaDePokemons, j, j + 1); // n^2 -- realiza a troca
                }
            }
        }
        return listaDePokemons; // 1 --> retorna a lista ordenada
    }
    // G(n) = n + n-1 + n^2 - 1 + n^2 - n + n^2 + 1
    // G(n) = 3n^2 - n + n + 1
    // O(n^2) -> esse vai ser o resultado da complexidade do código no PIOR CASO

    private void trocaPosicao(List<String> lista, int i, int j){
        String variavelAuxiliar = lista.get(i); // 1 -> guarda o valor a ser trocado temporariamente
        lista.set(i, lista.get(j)); // 1 - substitui o elemento do índice atual pelo elemento do próximo índice
        lista.set(j, variavelAuxiliar); // 1 -> coloca o valor do elemento da variável auxiliar no índice do elemento que foi trocado antes
    }
    // G(n) = 1 + 1 + 1
    // O(1) -> esse vai ser o resultado da complexidade do código no PIOR CASO

    private boolean isPrimeiraMaior(String primeiraPalavra, String segundaPalavra) {
        int tamanhoPrimeira = primeiraPalavra.length(); // 1 -> pega o tamanho da primeira palavra
        int tamanhoSegunda = segundaPalavra.length(); // 1 -> pega o tamanho da segunda palavra
        int comprimentoMinimo = (tamanhoPrimeira <= tamanhoSegunda) // 1 -> pega o comprimento mínimo
                ? tamanhoPrimeira
                : tamanhoSegunda;

        for (int i = 0; i < comprimentoMinimo; i++) { // n -> percorre a palavra
            char caracterePrimeiraPalavra = Character.toLowerCase(primeiraPalavra.charAt(i)); // n -> pega o caractere da primeira palavra
            char caractereSegundaPalavra = Character.toLowerCase(segundaPalavra.charAt(i)); // n -> pega o caractere da segunda palavra
            if (caracterePrimeiraPalavra != caractereSegundaPalavra) { // n + 1 -> compara se os caracteres são diferentes
                return caracterePrimeiraPalavra > caractereSegundaPalavra; // n -> retorna se a primeira palavra é maior que a segunda como um boolean
            }
        }

        return tamanhoPrimeira > tamanhoSegunda; // 1 -> retorna se o tamanho da primeira palavra é maior que o da segunda como um boolean
    }

    // G(n) = 1 + 1 + 1 + n + n + n + n + 1 + n + 1
    // G(n) = 5n + 5
    // O(n) -> esse vai ser o resultado da complexidade do código no PIOR CASO
}
