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

@Component
public class RestConsumer {
    Logger LOGGER = LoggerFactory.getLogger(RestConsumer.class);

    @Autowired
    private RestTemplate restTemplate;

    public Quote getQuoteByTypeFromExternal(QuoteType quoteType) {
        Quote quote = null;
        switch(quoteType) {
            case CELEBRITY:
                quote = restTemplate.getForObject(
                        "https://api.kanye.rest/",
                        KanyeQuote.class
                );
                quote.setQuoteAuthor("Kanye West");
                break;
            case ANIME:
                quote = restTemplate.getForObject(
                        "https://animechan.vercel.app/api/random",
                        AnimeQuote.class
                );
                break;
            case POP_CULTURE:
                Quote[] quotes = restTemplate.getForObject(
                        "https://api.breakingbadquotes.xyz/v1/quotes", //FIXME: Currently only <Breaking Bad>, need to extend for other series
                        PopCultureQuote[].class
                );
                // game of thrones: https://api.gameofthronesquotes.xyz/v1/random
                if (quotes.length != 0) {
                    quote = quotes[0];
                } else {
                    throw new NullPointerException("The extracted list does not contain any quote.");
                }
                break;
        }
        if (quote != null) {
            quote.setQuoteType(quoteType);
        } else {
            throw new RuntimeException();
        }
        LOGGER.info("Quote of type " + quoteType.toString().toUpperCase() + " retrieved from external API.");
        return quote;
    }

}
