package com.example.kanye.quote.api;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.util.QuoteType;

import java.util.Map;
import java.util.Optional;

public interface ExternalQuoteApiConsumer {
    Optional<Quote> getQuoteByTypeFromExternal(QuoteType quoteType, Map<String, Object> properties);
}
