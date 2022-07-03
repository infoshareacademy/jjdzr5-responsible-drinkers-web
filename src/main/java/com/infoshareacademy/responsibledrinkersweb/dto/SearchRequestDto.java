package com.infoshareacademy.responsibledrinkersweb.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SearchRequestDto {

    private UUID id;
    private String word;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;

    public SearchRequestDto(String word, LocalDateTime date) {
        this.word = word;
        this.date = date;
    }
}
