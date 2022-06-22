package com.infoshareacademy.responsibledrinkersweb.service.http;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDateTime;

public class SendGetRequest {
    private static final Logger LOGGER = LoggerFactory.getLogger(SendGetRequest.class);

    private final OkHttpClient httpClient = new OkHttpClient();

    private final String url;
    private String keyword;
    private LocalDateTime now;

    public SendGetRequest(String url) {
        this.url = url;
    }

    public SendGetRequest(String url, String keyword, LocalDateTime now) {
        this.url = url;
        this.keyword = keyword;
        this.now = now;
    }

    public void sendGet() throws Exception {

        String urlRequest = url + "?word=" + keyword + "&dateTime=" + now.toString();
        Request request = new Request.Builder()
                .url(urlRequest)
//                .addHeader("custom-key", "mkyong")  // add request headers
//                .addHeader("User-Agent", "OkHttp Bot")
                .build();
        LOGGER.info("urlRequest: " + urlRequest);
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Get response body
//            System.out.println(response.body().string());
        }
    }
}
