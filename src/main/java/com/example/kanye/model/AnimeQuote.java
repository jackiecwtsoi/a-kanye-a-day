package com.example.kanye.model;

import com.example.kanye.data.Quote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnimeQuote extends Quote {
    @JsonProperty("character")
    private String quoteAuthor;

    @JsonProperty("quote")
    private String quoteString;

    @Column(name="ANIME")
    @JsonProperty("anime")
    private String anime;

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + super.getQuoteId() +
                ", anime=" + anime +
                ", character=" + super.getQuoteAuthor() +
                ", quote=" + super.getQuote() +
                ", quoteType=" + super.getQuoteType() +
                '}';
    }
}
