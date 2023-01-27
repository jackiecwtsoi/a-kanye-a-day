package com.example.kanye.util;

import com.example.kanye.data.Quote;
import com.example.kanye.data.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    Logger LOGGER = LoggerFactory.getLogger(AppStartupEvent.class);

    private final QuoteRepository quoteRepository;

    public AppStartupEvent(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Quote> quotes = this.quoteRepository.findAll();
        quotes.forEach(quote -> LOGGER.info(quote.toString()));
    }
}
