package com.infoshareacademy.responsibledrinkersweb.sevice;

import com.infoshareacademy.drinkers.service.properties.ApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class SortDirection {
    public String getSortDirection() {
        return ApplicationProperties.getSortDirection();
    }

    public boolean isAscending() {
        return ApplicationProperties.isAscendingSort();
    }
}
