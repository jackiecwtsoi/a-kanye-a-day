package com.example.kanye.quote.data;

import com.example.kanye.quote.util.QuoteType;
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

    public String getQuoteString() {
        return quoteString;
    }

    public void setQuoteString(String quoteString) {
        this.quoteString = quoteString;
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
        return getQuoteAuthor() + ": \"" +
                getQuoteString() + "\"";
    }

    public static Quote.Builder builder() {
        return new Quote.Builder();
    }

    public static class Builder {
        private final Quote quote;

        // Constructor of the Quote.Builder class
        private Builder() {
            quote = new Quote();
        }

        public Builder setQuoteAuthor(final String quoteAuthor) {
            quote.quoteAuthor = quoteAuthor;
            return this;
        }

        public Builder setQuoteString(final String quoteString) {
            quote.quoteString = quoteString;
            return this;
        }

        public Builder setQuoteType(final QuoteType quoteType) {
            quote.quoteType = quoteType;
            return this;
        }

        public Quote build() {
            return quote;
        }
    }
}
