package com.example.kanye.webservice;

import com.example.kanye.api.RestConsumer;
import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import com.example.kanye.model.popculture.PopCultureType;
import com.example.kanye.util.QuoteType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/quote")
public class QuoteController {
    private final QuoteService quoteService;
    private final RestConsumer restConsumer;

    public QuoteController(QuoteService quoteService, RestConsumer restConsumer) {
        this.quoteService = quoteService;
        this.restConsumer = restConsumer;
    }

    // Access a random quote based on a specified quote type
    @GetMapping(path="/{quoteTypeString}")
    public Object getQuoteFromExternal(
            @PathVariable String quoteTypeString,
            @RequestParam(name="all", required = false) Boolean ifAll
            ) {
        // convert the path variable string into a QuoteType enum
        QuoteType quoteType = QuoteType.valueOf(
                quoteTypeString
                        .replaceAll("-", "_") // replace hyphen "-" with underscore "_"
                        .toUpperCase() // change all to uppercase
        );
        if (ifAll != null) {
            if (ifAll) {
                List<Quote> quotes = this.quoteService.getAllQuotesByType(quoteType);
                return quotes;
            }
        }
        // FIXME: delete hardcorded properties
        Map<String, Object> properties = new HashMap<>();
        properties.put("popCultureType", PopCultureType.TV_SERIES);
        properties.put("popCultureName", "Breaking Bad");
        Quote quote = this.restConsumer.getQuoteByTypeFromExternal(quoteType, properties);
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.toStringForPrinting();
    }

    // Get a random quote or all quotes by a specific author
    @GetMapping(path="")
    public Object getQuoteByAuthor(
            @RequestParam(name="author", required = true) String quoteAuthor,
            @RequestParam(name="all", required = false) Boolean ifAll) {
        if (ifAll != null) {
            if (ifAll) {
                List<Quote> quotes = this.quoteService.getAllQuotesByAuthor(quoteAuthor);
                return quotes;
            }
        }
        Quote randomQuote = this.quoteService.getRandomQuoteByAuthor(quoteAuthor);
        return randomQuote.toStringForPrinting();
    }

    // FIXME
    // Access a specified number of random quotes based on a specific quote type
    @GetMapping(path="/{quoteTypeString}/{numberOfQuotes}")
    public List<Quote> getQuotes(
            @PathVariable String quoteTypeString,
            @PathVariable int numberOfQuotes) { // FIXME: do we need number of quotes?
        // convert the path variable string into a QuoteType enum OPTIMIZE
        QuoteType quoteType = QuoteType.valueOf(
                quoteTypeString
                        .replaceAll("-", "_") // replace hyphen "-" with underscore "_"
                        .toUpperCase() // change all to uppercase
        );
        if (numberOfQuotes > 0) {
            List<Quote> quotes = this.quoteService.getAllQuotesByType(quoteType);
            return quotes;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
