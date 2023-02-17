package com.example.kanye.quote.model.popculture;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

public class GOTHouse {
    @JsonProperty("name")
    private String quoteAuthorHouse;

    @Id
    @JsonProperty("slug")
    private String quoteAuthorHouseSlug;

    public String getQuoteAuthorHouse() {
        return quoteAuthorHouse;
    }

    public void setQuoteAuthorHouse(String quoteAuthorHouse) {
        this.quoteAuthorHouse = quoteAuthorHouse;
    }

    public String getQuoteAuthorHouseSlug() {
        return quoteAuthorHouseSlug;
    }

    public void setQuoteAuthorHouseSlug(String quoteAuthorHouseSlug) {
        this.quoteAuthorHouseSlug = quoteAuthorHouseSlug;
    }
}
