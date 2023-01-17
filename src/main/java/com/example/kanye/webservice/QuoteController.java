package com.example.kanye.webservice;

import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/quote")
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping(path="/kanye")
    public String getKanyeQuoteFromExternal(){
        Quote quote = this.quoteService.getQuoteByAuthorFromExternal("KANYE");
        this.quoteService.saveQuoteFromExternal(quote);
        return quote.getQuote();
    }

    @GetMapping(path="/kanye-all")
    public List<Quote> getAllKanyeQuotes() {
        List<Quote> quotes = this.quoteService.getAllQuotesByAuthor("KANYE");
        quotes.forEach(quote -> System.out.println(quote.toString()));
        return quotes;
    }
}
