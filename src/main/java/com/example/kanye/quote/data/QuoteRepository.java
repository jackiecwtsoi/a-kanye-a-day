package com.example.kanye.quote.data;


import com.example.kanye.quote.util.QuoteType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    @Query("SELECT q FROM Quote q WHERE q.quoteAuthor = ?1")
    Optional<List<Quote>> findAllQuotesByAuthor(String quoteAuthor);

    @Query("SELECT q from Quote q WHERE q.quoteType = ?1")
    Optional<List<Quote>> findAllQuotesByType(QuoteType quoteType);
}
