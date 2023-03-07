package com.example.kanye.quote.model;

import com.example.kanye.quote.data.Quote;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

@Entity
public class KanyeQuote extends Quote {
    @JsonProperty("quote")
    private String quoteString;
}
