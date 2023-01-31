package com.example.kanye.data;

import com.example.kanye.util.QuoteType;
import jakarta.persistence.*;

import java.util.Map;

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

    @Column(name="QUOTE_TYPE")
    @Enumerated(EnumType.STRING)
    private QuoteType quoteType;

    @Transient
    private Map properties;

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

    public QuoteType getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(QuoteType quoteType) {
        this.quoteType = quoteType;
    }

    public Map getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "quoteId=" + quoteId +
                ", quoteAuthor='" + quoteAuthor + '\'' +
                ", quoteString='" + quoteString + '\'' +
                ", quoteType=" + quoteType +
                ", properties=" + properties +
                '}';
    }

    public String toStringForPrinting() {
        String result = getQuoteAuthor() + ": \"" +
                getQuote() + "\"";
        return result;
    }
}
