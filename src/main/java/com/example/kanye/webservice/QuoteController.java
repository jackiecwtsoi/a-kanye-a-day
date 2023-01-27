package com.example.kanye.webservice;

import com.example.kanye.api.RestConsumer;
import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import com.example.kanye.util.QuoteType;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping(path="/anime")
    public String getAnimeQuoteFromExternal(){
        Quote quote = this.restConsumer.getQuoteByAuthorFromExternal(QuoteType.ANIME);
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.getQuote();
    }

    @GetMapping(path="/celebrity")
    public String getKanyeQuoteFromExternal(){
        Quote quote = this.restConsumer.getQuoteByAuthorFromExternal(QuoteType.CELEBRITY);
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.getQuote();
    }

    @GetMapping(path="/pop-culture")
    public String getPopCultureQuoteFromExternal(){
        Quote quote = this.restConsumer.getQuoteByAuthorFromExternal(QuoteType.POP_CULTURE);
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.getQuote();
    }

    @GetMapping(path="/kanye-all")
    public List<Quote> getAllKanyeQuotes() {
        List<Quote> quotes = this.quoteService.getAllQuotesByAuthor("Kanye West");
//        quotes.forEach(quote -> System.out.println(quote.toString()));
        return quotes;
    }
}
