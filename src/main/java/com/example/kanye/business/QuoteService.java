package com.example.kanye.business;

import com.example.kanye.data.Quote;
import com.example.kanye.data.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {
    Logger logger = LoggerFactory.getLogger(QuoteService.class);
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    // FIXME
    public Quote getQuoteByAuthorFromExternal(String quoteAuthor) {
        RestTemplate restTemplate = new RestTemplate();

        Quote quote = restTemplate.getForObject(
                "https://api.kanye.rest/", Quote.class
        );
        quote.setQuoteAuthor("KANYE");
        return quote;
    }

    public void saveQuoteFromExternal(Quote quote) {
        this.quoteRepository.save(quote);
        logger.info("Quote extracted from external API is now saved to internal database.");
        logger.info("Internal database now has " + this.quoteRepository.findAll().size() + " quotes in total.");
    }

    // FIXME
    public List<Quote> getAllQuotesByAuthor(String quoteAuthor) {
        Optional<List<Quote>> quotes = this.quoteRepository.findAllQuotesByAuthor(quoteAuthor);
        if (quotes == null) {
            throw new RuntimeException("No quotes found for this author.");
        }
        return quotes.get();
    }

}
