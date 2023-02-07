package com.example.kanye.model.popculture;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

public class GOTCharacter {
    @JsonProperty("name")
    private String quoteAuthor;

    @JsonProperty("slug")
    private String quoteAuthorSlug;

    @JsonProperty("house")
    private GOTHouse house;

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuoteAuthorSlug() {
        return quoteAuthorSlug;
    }

    public void setQuoteAuthorSlug(String quoteAuthorSlug) {
        this.quoteAuthorSlug = quoteAuthorSlug;
    }

    public GOTHouse getHouse() {
        return house;
    }

    public void setHouse(GOTHouse house) {
        this.house = house;
    }
}
