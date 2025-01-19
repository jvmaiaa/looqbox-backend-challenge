package com.jvmaiaa.pokemon_api.sort;

import java.util.List;

public class AlphabeticalSortStrategy implements SortStrategy {
    @Override
    public List<String> sort(List<String> listaDePokemons) {
        int n = listaDePokemons.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (isPrimeiraMaior(listaDePokemons.get(j), listaDePokemons.get(j + 1))) {
                    String variavelAuxiliar = listaDePokemons.get(j);
                    listaDePokemons.set(j, listaDePokemons.get(j + 1));
                    listaDePokemons.set(j + 1, variavelAuxiliar);
                }
            }
        }
        return listaDePokemons;
    }

    private boolean isPrimeiraMaior(String primeiraPalavra, String segundaPalavra) {
        int comprimentoPrimeiraPalavra = primeiraPalavra.length();
        int comprimentoSegundaPalavra = segundaPalavra.length();
        int comprimentoMinimo = Math.min(comprimentoPrimeiraPalavra, comprimentoSegundaPalavra);

        for (int indice = 0; indice < comprimentoMinimo; indice++) {
            char caracterePrimeiraPalavra = Character.toLowerCase(primeiraPalavra.charAt(indice));
            char caractereSegundaPalavra = Character.toLowerCase(segundaPalavra.charAt(indice));
            if (caracterePrimeiraPalavra != caractereSegundaPalavra) {
                return caracterePrimeiraPalavra > caractereSegundaPalavra; // Retorna verdadeiro se o primeiro for maior
            }
        }

        // Se os prefixos forem iguais, o maior comprimento é considerado "maior"
        return comprimentoPrimeiraPalavra > comprimentoSegundaPalavra;
    }
}
