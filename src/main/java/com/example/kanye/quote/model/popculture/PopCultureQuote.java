package com.example.kanye.quote.model.popculture;

import com.example.kanye.quote.data.Quote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopCultureQuote extends Quote {
    @JsonProperty("author")
    private String quoteAuthor;

    @JsonProperty("quote")
    private String quoteString;

    @Override
    public String toString() {
        return "This is a Pop Culture quote:\n" + super.toString();
    }
}
