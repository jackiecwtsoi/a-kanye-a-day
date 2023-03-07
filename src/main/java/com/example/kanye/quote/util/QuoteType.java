package com.example.kanye.quote.util;

public enum QuoteType {
    CELEBRITY,
    ANIME,
    POP_CULTURE
    ;

    @Override
    public String toString() {
        return switch (this) {
            case CELEBRITY -> "Celebrity";
            case ANIME -> "Anime";
            case POP_CULTURE -> "Pop Culture";
        };
    }
}
