package com.example.kanye.business;

import com.example.kanye.exception.QuoteNotFoundException;
import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.data.QuoteRepository;
import com.example.kanye.quote.service.QuoteService;
import com.example.kanye.quote.util.QuoteType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuoteServiceUnitTest {
    @Mock
    private QuoteRepository quoteRepository;
    @InjectMocks
    private QuoteService quoteService;
    private Quote quote1, quote2, quote3;

    // Initialize frequently used variables
    QuoteServiceUnitTest() {
        setSampleQuotes();
    }

    // ------------------------------------
    // Utilities
    // ------------------------------------
    void setSampleQuotes() {
        quote1 = Quote.builder()
                .setQuoteAuthor("Sample Author")
                .setQuoteString("Hello, World!")
                .setQuoteType(QuoteType.CELEBRITY)
                .build();
        quote2 = Quote.builder()
                .setQuoteAuthor("Sample Author")
                .setQuoteString("This is a Java application.")
                .setQuoteType(QuoteType.ANIME)
                .build();
        quote3 = Quote.builder()
                .setQuoteAuthor("Sample Author")
                .setQuoteString("Anime quote.")
                .setQuoteType(QuoteType.ANIME)
                .build();
    }

    // ------------------------------------
    // Tests
    // ------------------------------------
    @Test
    void saveQuoteFromExternal_success() {
        // Arrange
        when(quoteRepository.save(quote1)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));
        when(quoteRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(quote1)));

        // Act
        quoteService.saveQuoteFromExternal(quote1);

        // Assert
        verify(quoteRepository, times(1)).save(quote1);
        verify(quoteRepository, times(2)).findAll();
    }

    @Test
    void addQuoteByUser_success() {
        // Arrange
        when(quoteRepository.save(quote1)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        quoteService.addQuoteByUser(quote1);

        // Assert
        verify(quoteRepository, times(1)).save(quote1);
    }

    @Test
    void updateQuoteByUser_success() {
        // Arrange
        when(quoteRepository.save(quote1)).thenAnswer(invocationOnMock -> invocationOnMock.getArgument(0));

        // Act
        quoteService.addQuoteByUser(quote1);

        // Assert
        verify(quoteRepository, times(1)).save(quote1);
    }

    @Test
    void getAllQuotesByAuthor_thenReturnQuotes() throws QuoteNotFoundException {
        // Arrange
        List<Quote> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(quote1);
        expectedQuotes.add(quote2);
        when(quoteRepository.findAllQuotesByAuthor(anyString())).thenReturn(expectedQuotes);

        // Act
        List<Quote> results = quoteService.getAllQuotesByAuthor("Sample Author");

        // Assert
        assertIterableEquals(expectedQuotes, results);
        verify(quoteRepository, times(1)).findAllQuotesByAuthor("Sample Author");
    }

    @Test
    void getAllQuotesByAuthor_thenThrowQuoteNotFoundException() {
        // Arrange
        List<Quote> emptyList = new ArrayList<>();
        when(quoteRepository.findAllQuotesByAuthor(anyString())).thenReturn(emptyList);

        // Act
        assertThrows(QuoteNotFoundException.class, () -> quoteService.getAllQuotesByAuthor("Unfound Author"));

        // Assert
        verify(quoteRepository, times(1)).findAllQuotesByAuthor("Unfound Author");
    }

    @Test
    void getAllQuotesByType_thenReturnQuotes() throws QuoteNotFoundException {
        // Arrange
        List<Quote> expectedQuotes = new ArrayList<>();
        expectedQuotes.add(quote1);
        expectedQuotes.add(quote2);
        expectedQuotes.add(quote3);
        when(quoteRepository.findAllQuotesByType(QuoteType.ANIME)).thenReturn(expectedQuotes);

        // Act
        List<Quote> results = quoteService.getAllQuotesByType(QuoteType.ANIME);

        // Assert
        assertIterableEquals(expectedQuotes, results);
        verify(quoteRepository, times(1)).findAllQuotesByType(QuoteType.ANIME);
    }

    @Test
    void getAllQuotesByType_thenThrowQuoteNotFoundException() {
        // Arrange
        List<Quote> emptyList = new ArrayList<>();
        when(quoteRepository.findAllQuotesByType(any(QuoteType.class))).thenReturn(emptyList);

        // Act
        assertThrows(QuoteNotFoundException.class, () -> quoteService.getAllQuotesByType(QuoteType.ANIME));

        // Assert
        verify(quoteRepository, times(1)).findAllQuotesByType(QuoteType.ANIME);
    }
}