package com.infoshareacademy.responsibledrinkersweb.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Count {
    private final String word;
    private final Long counts;

    public Count(String word, Long counts) {
        this.word = word;
        this.counts = counts;
    }
}
