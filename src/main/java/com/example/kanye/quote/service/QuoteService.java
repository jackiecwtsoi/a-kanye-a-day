package com.example.kanye.quote.service;

import com.example.kanye.exception.QuoteNotFoundException;
import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.data.QuoteRepository;
import com.example.kanye.quote.util.QuoteType;
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
        LOGGER.info("Quote extracted from external API is now saved to internal database:\n" + quote);
        LOGGER.info("Internal database now has " + this.quoteRepository.findAll().size() + " quotes in total.");
        this.quoteRepository.findAll().forEach(
                savedQuote -> LOGGER.debug(savedQuote.toString())
        );
    }

    public Quote addQuoteByUser(Quote quote) {
        this.quoteRepository.save(quote);
        LOGGER.info("User has successfully added a new quote to internal database. The quote type is: " + quote.getClass());
        return quote;
    }

    public Quote updateQuoteByUser(Quote quote) {
        this.quoteRepository.save(quote);
        LOGGER.info("User has successfully updated a quote to internal database.");
        return quote;
    }

    public List<Quote> getAllQuotesByAuthor(String quoteAuthor) throws QuoteNotFoundException {
        List<Quote> quotes = this.quoteRepository.findAllQuotesByAuthor(quoteAuthor);
        if (quotes.isEmpty()) {
            throw new QuoteNotFoundException("There are no quotes by " + quoteAuthor + " found in our internal database.");
        }
        return quotes;
    }

    public List<Quote> getAllQuotesByType(QuoteType quoteType) throws QuoteNotFoundException {
        List<Quote> quotes = this.quoteRepository.findAllQuotesByType(quoteType);
        if (quotes.isEmpty()) {
            throw new QuoteNotFoundException("There are no quotes of type " + quoteType.toString() + " found in our internal database.");
        }
        return quotes;
    }

    // OPTIMIZE: Instead use a more general getRandomQuote function that can take in multiple parameters?
    public Quote getRandomQuoteByAuthor(String quoteAuthor) throws QuoteNotFoundException {
        List<Quote> quotes = getAllQuotesByAuthor(quoteAuthor);
        Random rand = new Random();
        return quotes.get(rand.nextInt(quotes.size()));
    }
}
