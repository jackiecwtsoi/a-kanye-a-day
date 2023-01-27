package com.example.kanye.util;

public enum QuoteType {
    CELEBRITY,
    ANIME,
    POP_CULTURE
    ;

    @Override
    public String toString() {
        switch(this) {
            case CELEBRITY: return "Celebrity";
            case ANIME: return "Anime";
            case POP_CULTURE: return "Pop Culture";
            default: throw new IllegalArgumentException();
        }
    }
}
