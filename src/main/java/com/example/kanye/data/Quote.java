package com.example.kanye.data;

import jakarta.persistence.*;

@Inheritance
@Entity
@Table(name="QUOTE")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="QUOTE_ID")
    private long quoteId;

    @Column(name="QUOTE_AUTHOR")
    private String quoteAuthor;

    @Column(name="QUOTE_STRING")
    private String quoteString;

    public long getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(long quoteId) {
        this.quoteId = quoteId;
    }

    public String getQuoteAuthor() {
        return quoteAuthor;
    }

    public void setQuoteAuthor(String quoteAuthor) {
        this.quoteAuthor = quoteAuthor;
    }

    public String getQuote() {
        return quoteString;
    }

    public void setQuote(String quote) {
        this.quoteString = quote;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quoteAuthor=" + quoteAuthor +
                ", quote=" + quoteString +
                '}';
    }

    public String toStringForPrinting() {
        String result = getQuoteAuthor() + ": \"" +
                getQuote() + "\"";
        return result;
    }
}
