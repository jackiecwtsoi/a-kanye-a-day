package com.example.kanye.model.popculture;

import com.example.kanye.data.Quote;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;

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
