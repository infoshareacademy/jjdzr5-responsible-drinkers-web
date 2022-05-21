package com.infoshareacademy.responsibledrinkersweb.exceptions;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

class ImageNotFoundTest {

    @Test
    void verifyURL_returnsEmptyString_givenInvalidURL()   {
        // given
        String invalidURL = "invalid";
        // when
        String actualResult = ImageNotFound.verifyURL(invalidURL);
        // then
        String expectedResult = "";
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void verifyURL_returnsURL_givenValidURL()   {
        // given
        String validURL = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png";
        // when
        String actualResult = ImageNotFound.verifyURL(validURL);
        // then
        String expectedResult = validURL;
        assertEquals(expectedResult, actualResult);
    }
}