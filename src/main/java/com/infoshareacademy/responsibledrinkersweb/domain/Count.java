package com.infoshareacademy.responsibledrinkersweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Count {
    private String word;
    private Long counts;

    public Count(String word, long counts) {
        this.word = word;
        this.counts = counts;
    }
}
