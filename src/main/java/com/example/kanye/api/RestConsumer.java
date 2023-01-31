package com.example.kanye.api;

import com.example.kanye.data.Quote;
import com.example.kanye.model.KanyeQuote;
import com.example.kanye.model.AnimeQuote;
import com.example.kanye.model.popculture.PopCultureQuote;
import com.example.kanye.util.QuoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RestConsumer {
    Logger LOGGER = LoggerFactory.getLogger(RestConsumer.class);

    @Autowired
    private RestTemplate restTemplate;

    public Quote getQuoteByTypeFromExternal(QuoteType quoteType, Map properties) {
        Quote quote = null;
        String apiUrl = "";
        switch (quoteType) {
            case CELEBRITY -> {
                quote = restTemplate.getForObject(
                        "https://api.kanye.rest/",
                        KanyeQuote.class
                );
                quote.setQuoteAuthor("Kanye West");
            }
            case ANIME -> {
                quote = restTemplate.getForObject(
                        "https://animechan.vercel.app/api/random",
                        AnimeQuote.class
                );
            }
            case POP_CULTURE -> {
                try {
                    String popCultureName = (String) properties.get("popCultureName");
                    switch (popCultureName) {
                        case "Breaking Bad" -> apiUrl = "https://api.breakingbadquotes.xyz/v1/quotes";
                        // FIXME: GOT quotes do not use the same identifiers as breaking bad
                        case "A Game of Thrones" -> apiUrl = "https://api.gameofthronesquotes.xyz/v1/random/5";
                    }
                    Quote[] quotes = restTemplate.getForObject(
                            apiUrl,
                            PopCultureQuote[].class
                    );
                    if (quotes.length != 0) {
                        quote = quotes[0];
                    } else {
                        throw new NullPointerException("The extracted list does not contain any quote.");
                    }
                } catch (NullPointerException e) {
                    LOGGER.error("No properties specified so we cannot infer the correct pop culture item.");
                }
            }
        }
        if (quote != null) {
            quote.setQuoteType(quoteType);
            quote.setProperties(properties);
        } else {
            throw new RuntimeException();
        }
        LOGGER.info("Quote of type " + quoteType.toString().toUpperCase() + " retrieved from external API.");
        return quote;
    }

}
