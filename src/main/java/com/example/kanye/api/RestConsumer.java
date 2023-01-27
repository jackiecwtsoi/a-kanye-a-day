package com.example.kanye.api;

import com.example.kanye.data.Quote;
import com.example.kanye.model.KanyeQuote;
import com.example.kanye.model.AnimeQuote;
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

    public Quote getQuoteByAuthorFromExternal(String quoteType) {
        Quote quote = null;
        switch(quoteType) {
            case "CELEBRITY":
                quote = restTemplate.getForObject(
                        "https://api.kanye.rest/", KanyeQuote.class
                );
                quote.setQuoteAuthor("Kanye West");
                break;
            case "ANIME":
                quote = restTemplate.getForObject(
                        "https://animechan.vercel.app/api/random", AnimeQuote.class
                );
                break;
        }
        LOGGER.info("Quote of type" + quoteType + " retrieved from external API.");
        return quote;
    }

}
