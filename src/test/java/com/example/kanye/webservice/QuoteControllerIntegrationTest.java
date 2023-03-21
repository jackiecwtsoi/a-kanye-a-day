package com.example.kanye.webservice;

import com.example.kanye.quote.api.ExternalQuoteApiConsumer;
import com.example.kanye.quote.controller.QuoteController;
import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.model.popculture.PopCultureType;
import com.example.kanye.quote.service.QuoteService;
import com.example.kanye.quote.util.QuoteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuoteController.class)
class QuoteControllerIntegrationTest {
    @MockBean
    private QuoteService quoteService;
    @MockBean
    private ExternalQuoteApiConsumer externalQuoteApiConsumer;
    @Autowired
    private MockMvc mockMvc;

    private List<Quote> sampleQuotes;
    private List<String> quoteTypeStrings;

    QuoteControllerIntegrationTest() {
        setSampleQuotes();
        loadQuoteTypeStrings();
    }

    // ------------------------------------
    // Utilities
    // ------------------------------------
    void setSampleQuotes() {
        sampleQuotes = new ArrayList<>();
        sampleQuotes.add(
                Quote.builder()
                        .setQuoteAuthor("Sample Author")
                        .setQuoteString("Hello, World!")
                        .setQuoteType(QuoteType.CELEBRITY)
                        .build()
        );
        sampleQuotes.add(
                Quote.builder()
                        .setQuoteAuthor("Sample Author")
                        .setQuoteString("This is a Java application.")
                        .setQuoteType(QuoteType.ANIME)
                        .build()
        );
        sampleQuotes.add(
                Quote.builder()
                        .setQuoteAuthor("Sample Author")
                        .setQuoteString("Anime quote.")
                        .setQuoteType(QuoteType.ANIME)
                        .build()
        );
    }

    void loadQuoteTypeStrings() {
        quoteTypeStrings = Stream.of(QuoteType.values())
                .map(Enum::name)
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }

    // ------------------------------------
    // Tests
    // ------------------------------------
    @Test
    void getQuoteFromExternal_forAllQuoteTypes_success() throws Exception {
        // Arrange
        String expectedString = "Sample Author: \"Hello, World!\"";
        when(externalQuoteApiConsumer
                .getQuoteByTypeFromExternal(any(QuoteType.class), any()))
                .thenReturn(Optional.of(sampleQuotes.get(0)));
        doNothing().when(quoteService).saveQuoteFromExternal(sampleQuotes.get(0));
        when(quoteService.getStringForPrinting(any(Quote.class)))
                .thenReturn(sampleQuotes.get(0).toStringForPrinting());

        // Act
        for (String quoteTypeString : quoteTypeStrings) {
            mockMvc.perform(get("/quote/" + quoteTypeString))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().string(equalTo(expectedString)));
        }

        // Assert
        verify(externalQuoteApiConsumer, times(quoteTypeStrings.size())).getQuoteByTypeFromExternal(any(QuoteType.class), any());
        verify(quoteService, times(quoteTypeStrings.size())).saveQuoteFromExternal(any(Quote.class));
        verify(quoteService, times(quoteTypeStrings.size())).getStringForPrinting(any(Quote.class));
    }

    @Test
    void getQuoteFromExternal_forOneQuoteType_failure() throws Exception {
        // Arrange
        String expectedString = "No quote available for your requested type!";

        // FIXME / TODO
        Map<String, Object> properties = new HashMap<>();
        properties.put("popCultureType", PopCultureType.BOOK);
        properties.put("popCultureName", "A Game of ");

        when(externalQuoteApiConsumer
                .getQuoteByTypeFromExternal(any(QuoteType.class), any()))
                .thenReturn(Optional.empty());

        // Act
        mockMvc.perform(get("/quote/" + "pop-culture"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedString)));

        // Assert
        verify(externalQuoteApiConsumer, times(1)).getQuoteByTypeFromExternal(any(QuoteType.class), any());
        verifyNoInteractions(quoteService);
    }

    @Test // TODO: need to correct placeholder string
    void getAllQuotesFromExternal() throws Exception {
        String expectedString = "hello, ifAll is selected as true.";

        mockMvc.perform(get("/quote/anime?all=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedString)));
    }

    @Test
    void getQuotes() {
        // TODO
    }
}
