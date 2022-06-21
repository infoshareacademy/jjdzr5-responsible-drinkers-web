package com.infoshareacademy.responsibledrinkersweb.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoshareacademy.responsibledrinkersweb.domain.Count;
import com.infoshareacademy.responsibledrinkersweb.service.gson.GsonCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;

@SpringBootTest
class AuthorizedControllerTest {

    public static final String DUMMY_URL = "https://postman-echo.com/get";

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

}