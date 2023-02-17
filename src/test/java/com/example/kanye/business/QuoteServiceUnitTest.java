package com.example.kanye.business;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.data.QuoteRepository;
import com.example.kanye.quote.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuoteServiceUnitTest {
    @Mock
    private QuoteRepository quoteRepository;
    @InjectMocks
    private QuoteService quoteService;
    private Quote quote1, quote2;

    QuoteServiceUnitTest() {
        setSampleQuotes();
    }

    void setSampleQuotes() {
        quote1 = new Quote();
        quote1.setQuoteAuthor("Sample Author");
        quote1.setQuote("Hello, World!");
        quote2 = new Quote();
        quote2.setQuoteAuthor("Sample Author");
        quote2.setQuote("This is a Java application.");
    }

    @Test
    void getAllQuotesByAuthorNotNull() {
        List<Quote> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(quote1);
        expectedQuotes.add(quote2);

        when(quoteRepository.findAllQuotesByAuthor(anyString())).thenReturn(Optional.of(expectedQuotes));
        assertEquals(expectedQuotes, quoteService.getAllQuotesByAuthor("Sample Author"));
    }

}