package com.infoshareacademy.responsibledrinkersweb.sevice;

import com.infoshareacademy.drinkers.service.properties.ApplicationProperties;
import org.springframework.stereotype.Controller;

@Controller
public class DateFormat {

    public String getDatePatter() {
        return ApplicationProperties.getDatePattern();
    }
}
