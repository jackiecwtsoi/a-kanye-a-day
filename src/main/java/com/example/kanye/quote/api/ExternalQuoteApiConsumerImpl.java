package com.example.kanye.quote.api;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.model.AnimeQuote;
import com.example.kanye.quote.model.KanyeQuote;
import com.example.kanye.quote.model.popculture.GOTQuote;
import com.example.kanye.quote.model.popculture.PopCultureQuote;
import com.example.kanye.quote.util.QuoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Component
public class ExternalQuoteApiConsumerImpl implements ExternalQuoteApiConsumer {
    Logger LOGGER = LoggerFactory.getLogger(ExternalQuoteApiConsumerImpl.class);

    private final RestTemplate restTemplate;
    private final Map<QuoteType, String> externalQuoteApiUrlMap;

    public ExternalQuoteApiConsumerImpl(RestTemplate restTemplate, Map<QuoteType, String> externalQuoteApiUrlMap) {
        this.restTemplate = restTemplate;
        this.externalQuoteApiUrlMap = externalQuoteApiUrlMap;
    }

    @Override
    public Optional<Quote> getQuoteByTypeFromExternal(QuoteType quoteType, Map<String, Object> properties) {
        Optional<Quote> quoteOpt = Optional.empty();
        String apiUrl = externalQuoteApiUrlMap.get(quoteType);
        switch (quoteType) {
            case CELEBRITY -> {
                quoteOpt = Optional.of(restTemplate.getForObject(apiUrl, KanyeQuote.class));
                quoteOpt.ifPresent(x -> x.setQuoteAuthor("Kanye West"));
            }
            case ANIME -> quoteOpt = Optional.of(restTemplate.getForObject(apiUrl, AnimeQuote.class));
            case POP_CULTURE -> {
                if (!properties.isEmpty()) {
                    String popCultureName = (String) properties.get("popCultureName");
                    switch (popCultureName) {
                        case "Breaking Bad" -> {
                            apiUrl = "https://api.breakingbadquotes.xyz/v1/quotes";
                            quoteOpt = Optional.of(restTemplate.getForObject(apiUrl, PopCultureQuote[].class)[0]);
                        }
                        case "A Game of Thrones" -> {
                            apiUrl = "https://api.gameofthronesquotes.xyz/v1/random/5";
                            GOTQuote gotQuote = restTemplate.getForObject(apiUrl, GOTQuote[].class)[0];
                            quoteOpt = Optional.of(
                                    Quote.builder()
                                            .setQuoteString(gotQuote.getQuoteString())
                                            .setQuoteAuthor(gotQuote.getCharacter().getQuoteAuthor())
                                            .build()
                            );
                        }
                    }
                }
            }
        }
        quoteOpt.ifPresentOrElse(
                quote -> {
                    quote.setQuoteType(quoteType);
                    quote.setProperties(properties);
                    LOGGER.info("Quote of type " + quoteType.toString().toUpperCase() + " retrieved from external API.");
                },
                () -> {
                    LOGGER.info("Quote of type " + quoteType.toString().toUpperCase() + " cannot be retrieved from external API.");
                }
        );
        return quoteOpt;
    }
}
