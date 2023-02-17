package com.example.kanye.quote.model.popculture;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GOTQuote {
    @JsonProperty("sentence")
    private String quoteString;

    @JsonProperty("character")
    private GOTCharacter character;

    @Override
    public String toString() {
        return quoteString + ": " + character.getQuoteAuthor();
    }

    public String getQuoteString() {
        return quoteString;
    }

    public void setQuoteString(String quoteString) {
        this.quoteString = quoteString;
    }

    public GOTCharacter getCharacter() {
        return character;
    }

    public void setCharacter(GOTCharacter character) {
        this.character = character;
    }
}
