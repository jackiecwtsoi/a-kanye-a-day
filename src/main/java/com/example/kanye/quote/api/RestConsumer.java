package com.example.kanye.quote.api;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.model.KanyeQuote;
import com.example.kanye.quote.model.AnimeQuote;
import com.example.kanye.quote.model.popculture.GOTQuote;
import com.example.kanye.quote.model.popculture.PopCultureQuote;
import com.example.kanye.quote.util.QuoteType;
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
                    Quote[] quotes = null;
                    switch (popCultureName) {
                        case "Breaking Bad":
                            apiUrl = "https://api.breakingbadquotes.xyz/v1/quotes";
                            quotes = restTemplate.getForObject(
                                    apiUrl,
                                    PopCultureQuote[].class
                            );
                            if (quotes.length != 0) {
                                quote = quotes[0];
                            } else {
                                throw new NullPointerException("The extracted list does not contain any quote.");
                            }
                            break;
                        case "A Game of Thrones":
                            apiUrl = "https://api.gameofthronesquotes.xyz/v1/random/5";
                            GOTQuote[] gotQuotes = restTemplate.getForObject(
                                    apiUrl,
                                    GOTQuote[].class
                            );
                            if (gotQuotes.length != 0) {
                                quote = new Quote();
                                quote.setQuote(gotQuotes[0].getQuoteString());
                                quote.setQuoteAuthor(gotQuotes[0].getCharacter().getQuoteAuthor());
                            } else {
                                throw new NullPointerException("The extracted list does not contain any quote.");
                            }
                            break;
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
