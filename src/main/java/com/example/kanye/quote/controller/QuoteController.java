package com.example.kanye.quote.controller;

import com.example.kanye.exception.QuoteNotFoundException;
import com.example.kanye.quote.api.RestConsumer;
import com.example.kanye.quote.service.QuoteService;
import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.model.popculture.PopCultureType;
import com.example.kanye.quote.util.QuoteType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
            @RequestParam(name="all", required=false) Optional<Boolean> ifAll
            ) {
        // convert the path variable string into a QuoteType enum
        QuoteType quoteType = QuoteType.valueOf(
                quoteTypeString
                        .replaceAll("-", "_") // replace hyphen "-" with underscore "_"
                        .toUpperCase() // change all to uppercase
        );
        if (ifAll.orElse(false)) {
            try {
                return this.quoteService.getAllQuotesByType(quoteType);
            } catch (QuoteNotFoundException e) {
                return e.toString(); // Print the exception on screen for now
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
            @RequestParam(name="author", required=true) String quoteAuthor,
            @RequestParam(name="all", required=false) Optional<Boolean> ifAll) {
        if (ifAll.orElse(false)) {
            try {
                return this.quoteService.getAllQuotesByAuthor(quoteAuthor);
            } catch (QuoteNotFoundException e) {
                return e.toString(); // Print the exception on screen for now
            }
        }
        try {
            return this.quoteService.getRandomQuoteByAuthor(quoteAuthor);
        } catch (QuoteNotFoundException e) {
            return e.toString(); // Print the exception on screen for now
        }
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
            try {
                return this.quoteService.getAllQuotesByType(quoteType);
            } catch (QuoteNotFoundException e) {
                throw new RuntimeException(e.toString()); // FIXME: Print the error message on screen?
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @PostMapping(path="/add")
    public ResponseEntity<Quote> addQuoteByUser(@RequestBody Quote quote) {
        Quote newQuote = this.quoteService.addQuoteByUser(quote);
        return new ResponseEntity<>(newQuote, HttpStatus.CREATED);
    }

    @PutMapping(path="/update")
    public ResponseEntity<Quote> updateQuoteByUser(@RequestBody Quote quote) {
        Quote updatedQuote = this.quoteService.updateQuoteByUser(quote);
        return new ResponseEntity<>(updatedQuote, HttpStatus.OK);
    }
}
