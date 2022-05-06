package com.infoshareacademy.responsibledrinkersweb.domain;

import com.infoshareacademy.drinkers.domain.drink.Alcoholic;
import com.infoshareacademy.drinkers.domain.drink.Status;
import com.infoshareacademy.drinkers.service.filtering.FilterElements;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ListParameter {
    private String sort;
    private String keyword;
    private Alcoholic alcoholic;
    private FilterElements filterElements;
    private Status status;
}
