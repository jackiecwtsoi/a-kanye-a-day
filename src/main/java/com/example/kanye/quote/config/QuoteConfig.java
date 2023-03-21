package com.example.kanye.quote.config;

import com.example.kanye.quote.api.ExternalQuoteApiConsumer;
import com.example.kanye.quote.api.ExternalQuoteApiConsumerImpl;
import com.example.kanye.quote.util.QuoteType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class QuoteConfig {
    @Bean(name="restTemplate")
    public RestTemplate restTemplate() {
        return new RestTemplate();
    };

    @Bean(name="externalQuoteApiUrlMap")
    public Map<QuoteType, String> externalQuoteApiUrlMap() {
        Map<QuoteType, String> map = new HashMap<>();
        map.put(QuoteType.CELEBRITY, "https://api.kanye.rest/");
        map.put(QuoteType.ANIME, "https://animechan.vercel.app/api/random");
        return map;
    }

    @Bean(name="externalQuoteApiConsumer")
    public ExternalQuoteApiConsumer externalQuoteApiConsumer(@Qualifier("restTemplate") RestTemplate restTemplate,
                                                             @Qualifier("externalQuoteApiUrlMap") Map<QuoteType, String> externalQuoteApiUrlMap) {
        return new ExternalQuoteApiConsumerImpl(restTemplate, externalQuoteApiUrlMap);
    }
}
