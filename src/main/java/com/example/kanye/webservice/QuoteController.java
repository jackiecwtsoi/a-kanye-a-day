package com.example.kanye.webservice;

import com.example.kanye.api.RestConsumer;
import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import com.example.kanye.util.QuoteType;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
    public String getQuoteFromExternal(
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
                return "hello, ifAll is selected as true."; // TODO: Do we need this???
            }
        }
        Quote quote = this.restConsumer.getQuoteByTypeFromExternal(quoteType);
        this.quoteService.saveQuoteFromExternal(quote);
        return this.quoteService.getStringForPrinting(quote);
    }

    // Access a specified number of random quotes based on a specific quote type
    @GetMapping(path="/{quoteTypeString}/{numberOfQuotes}")
    public List<Quote> getQuotes(
            @PathVariable String quoteTypeString,
            @PathVariable int numberOfQuotes) {
        // convert the path variable string into a QuoteType enum OPTIMIZE
        QuoteType quoteType = QuoteType.valueOf(
                quoteTypeString
                        .replaceAll("-", "_") // replace hyphen "-" with underscore "_"
                        .toUpperCase() // change all to uppercase
        );
        if (numberOfQuotes > 0) {
            List<Quote> quotes = this.quoteService.getQuotesByType(quoteType, numberOfQuotes);
            return quotes;
        } else {
            throw new IllegalArgumentException();
        }
    }

    // FIXME: Is there a better way to organize searching quotes by author?
    @GetMapping(path="/celebrity/kanye")
    public List<Quote> getAllKanyeQuotes() {
        List<Quote> quotes = this.quoteService.getAllQuotesByAuthor("Kanye West");
//        quotes.forEach(quote -> System.out.println(quote.toString()));
        return quotes;
    }
}
