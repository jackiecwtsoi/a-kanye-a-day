package com.example.kanye.webservice;

import com.example.kanye.api.RestConsumer;
import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import com.example.kanye.util.QuoteType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(path="/{quoteTypeString}")
    public String getQuoteFromExternal(@PathVariable String quoteTypeString) {
        // convert the path variable string into a QuoteType enum
        QuoteType quoteType = QuoteType.valueOf(
                quoteTypeString
                        .replaceAll("-", "_") // replace hyphen "-" with underscore "_"
                        .toUpperCase() // change all to uppercase
        );

        Quote quote = this.restConsumer.getQuoteByTypeFromExternal(quoteType);
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.getQuote();
    }

    // FIXME: Is there a better way to organize searching quotes by author?
    @GetMapping(path="/celebrity/kanye")
    public List<Quote> getAllKanyeQuotes() {
        List<Quote> quotes = this.quoteService.getAllQuotesByAuthor("Kanye West");
//        quotes.forEach(quote -> System.out.println(quote.toString()));
        return quotes;
    }
}
