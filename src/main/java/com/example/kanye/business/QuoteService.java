package com.example.kanye.business;

import com.example.kanye.data.Quote;
import com.example.kanye.data.QuoteRepository;
import com.example.kanye.util.QuoteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class QuoteService {
    Logger LOGGER = LoggerFactory.getLogger(QuoteService.class);
    private final QuoteRepository quoteRepository;

    public QuoteService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public String getStringForPrinting(Quote quote) { // OPTIMIZE
        return quote.toStringForPrinting();
    }

    public void saveQuoteFromExternal(Quote quote) {
        this.quoteRepository.save(quote);
        LOGGER.info("Quote extracted from external API is now saved to internal database:\n" + quote.toString());
        LOGGER.info("Internal database now has " + this.quoteRepository.findAll().size() + " quotes in total.");
        this.quoteRepository.findAll().forEach(
                savedQuote -> LOGGER.debug(savedQuote.toString())
        );
    }

    public List<Quote> getAllQuotesByAuthor(String quoteAuthor) {
        List<Quote> quotes = this.quoteRepository.findAllQuotesByAuthor(quoteAuthor).get();
        if (quotes.isEmpty()) {
            throw new RuntimeException("There are no quotes by " + quoteAuthor + " found in our internal database.");
        }
        return quotes;
    }

    public List<Quote> getAllQuotesByType(QuoteType quoteType) {
        List<Quote> quotes = this.quoteRepository.findAllQuotesByType(quoteType).get();
        if (quotes.isEmpty()) {
            throw new RuntimeException("There are no quotes of type " + quoteType.toString() + " found in our internal database.");
        }
        return quotes;
    }

    // OPTIMIZE: Instead use a more general getRandomQuote function that can take in multiple parameters?
    public Quote getRandomQuoteByAuthor(String quoteAuthor) {
        List<Quote> quotes = getAllQuotesByAuthor(quoteAuthor);
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }
}
