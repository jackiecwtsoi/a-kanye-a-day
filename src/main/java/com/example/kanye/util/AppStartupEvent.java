package com.example.kanye.util;

import com.example.kanye.quote.data.Quote;
import com.example.kanye.quote.data.QuoteRepository;
import com.example.kanye.user.data.User;
import com.example.kanye.user.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    Logger LOGGER = LoggerFactory.getLogger(AppStartupEvent.class);

    private final QuoteRepository quoteRepository;
    private final UserRepository userRepository;

    public AppStartupEvent(QuoteRepository quoteRepository, UserRepository userRepository) {
        this.quoteRepository = quoteRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Quote> quotes = this.quoteRepository.findAll();
        quotes.forEach(quote -> LOGGER.info(quote.toString()));
        Iterable<User> users = this.userRepository.findAll();
        users.forEach(user -> LOGGER.info(user.toString()));
    }
}
