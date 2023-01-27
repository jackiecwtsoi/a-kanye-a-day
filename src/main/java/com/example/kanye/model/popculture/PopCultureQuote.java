package com.example.kanye.model.popculture;

import com.example.kanye.data.Quote;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopCultureQuote extends Quote {
    @JsonProperty("author")
    private String quoteAuthor;

    @JsonProperty("quote")
    private String quoteString;

//    @Column(name="POP_CULTURE_TYPE")
//    private PopCultureType popCultureType;
//
//    @Column(name="POP_CULTURE_NAME")
//    private String popCultureName;
//
//    public PopCultureType getPopCultureType() {
//        return popCultureType;
//    }
//
//    public void setPopCultureType(PopCultureType popCultureType) {
//        this.popCultureType = popCultureType;
//    }
//
//    public String getPopCultureName() {
//        return popCultureName;
//    }
//
//    public void setPopCultureName(String popCultureName) {
//        this.popCultureName = popCultureName;
//    }

    @Override
    public String toString() {
        return "This is a Pop Culture quote:\n" + super.toString();
    }
}
