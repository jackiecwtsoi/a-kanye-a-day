package com.example.kanye.webservice;

import com.example.kanye.api.RestConsumer;
import com.example.kanye.business.QuoteService;
import com.example.kanye.data.Quote;
import com.example.kanye.util.QuoteType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuoteController.class)
class QuoteControllerIntegrationTest {
    @MockBean
    private QuoteService quoteService;
    @MockBean
    private RestConsumer restConsumer;
    @Autowired
    private MockMvc mockMvc;

    private Quote quote1, quote2;

    QuoteControllerIntegrationTest() {
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
    void getQuoteFromExternal() throws Exception {
        String expectedString = "Sample Author: \"Hello, World!\"";

        when(restConsumer
                .getQuoteByTypeFromExternal(any(QuoteType.class)))
                .thenReturn(quote1);
        doNothing().when(quoteService).saveQuoteFromExternal(quote1);
        when(quoteService.getStringForPrinting(any(Quote.class)))
                .thenReturn(quote1.toStringForPrinting());

        mockMvc.perform(get("/quote/anime"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedString)));

//        verify(restConsumer).getQuoteByTypeFromExternal(any(QuoteType.class));
    }

    @Test
    void getAllQuotesFromExternal() throws Exception {
        String expectedString = "hello, ifAll is selected as true.";

        mockMvc.perform(get("/quote/anime?all=true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(expectedString)));
    }

    @Test
    void getQuotes() {
    }
}
