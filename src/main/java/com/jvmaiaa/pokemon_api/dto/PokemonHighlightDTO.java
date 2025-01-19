package com.jvmaiaa.pokemon_api.dto;

public class PokemonHighlightDTO {

    private String name;
    private String highlight;

    public PokemonHighlightDTO() {}

    public PokemonHighlightDTO(String name, String highlight) {
        this.name = name;
        this.highlight = highlight;
    }

    public String getName() {
        return name;
    }

    public String getHighlight() {
        return highlight;
    }
}
