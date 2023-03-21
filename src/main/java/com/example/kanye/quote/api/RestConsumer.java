package com.example.kanye.quote.api;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.util.QuoteType;

import java.util.Map;

public interface RestConsumer {
    Quote getQuoteByTypeFromExternal(QuoteType quoteType, Map properties);
}
