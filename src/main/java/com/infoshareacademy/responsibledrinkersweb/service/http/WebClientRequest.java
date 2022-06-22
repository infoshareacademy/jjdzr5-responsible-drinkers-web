package com.infoshareacademy.responsibledrinkersweb.service.http;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WebClientRequest {

    public static String getRequest(String url) {
        WebClient webClient = WebClient.create(url);
        Mono<String> body = webClient.get().retrieve().bodyToMono(String.class);
        String requestBlock;
        try {
            requestBlock = body.block();

        } catch (Exception e) {
            requestBlock = "";
        }
        return requestBlock;
    }

    private WebClientRequest() {
    }
}
