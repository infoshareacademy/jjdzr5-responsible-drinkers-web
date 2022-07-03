package com.infoshareacademy.responsibledrinkersweb.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoshareacademy.responsibledrinkersweb.domain.Count;
import com.infoshareacademy.responsibledrinkersweb.service.gson.GsonCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest
class AuthorizedControllerTest {

    public static final String DUMMY_URL = "https://postman-echo.com/get";

    private RestTemplate restTemplate;

    @Test
    void httpTest() {
        WebClient webClient = WebClient.create(DUMMY_URL);
        Mono<String> body = webClient.get().retrieve().bodyToMono(String.class);
        String s = body.block();
        System.out.println(s);
    }

    @Test
    void httpTest2() {
        WebClient webClient = WebClient.create("http://localhost:8081/count");
        Mono<String> body = webClient.get().retrieve().bodyToMono(String.class);
        String s;
        try {
            s = body.block();

        } catch (Exception e) {
            s = "";
        }
        System.out.println(s);
        Assertions.assertNotNull(s);
        Gson gson = GsonCreator.getGson();
        Type typeToken = new TypeToken<List<Count>>() {
        }.getType();
        List<Count> counts = gson.fromJson(s, typeToken);
        counts.forEach(count -> System.out.println(count.getCounts() + ": " + count.getWord()));
    }

    @Test
    void getJsonFromRequest() {
        restTemplate = new RestTemplate();
        ResponseEntity<Iterable<Count>> exchange = restTemplate.exchange("http://localhost:8081/counts",
                HttpMethod.GET, null, new ParameterizedTypeReference<Iterable<Count>>() {
                });
        System.out.println(exchange.getBody());
        System.out.println(exchange.getStatusCode());
        Iterable<Count> counts = exchange.getBody();
        counts.forEach(count -> System.out.println(count.getWord() + " *** " + count.getCounts()));
        Assertions.assertNotNull(exchange);
        Assertions.assertEquals(HttpStatus.OK, exchange.getStatusCode());
    }

}