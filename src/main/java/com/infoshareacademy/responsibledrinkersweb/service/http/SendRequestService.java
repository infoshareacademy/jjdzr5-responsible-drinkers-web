package com.infoshareacademy.responsibledrinkersweb.service.http;

import com.infoshareacademy.responsibledrinkersweb.domain.Count;
import com.infoshareacademy.responsibledrinkersweb.domain.ListParameter;
import com.infoshareacademy.responsibledrinkersweb.dto.SearchRequestDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendRequestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendRequestService.class);

    private final RestTemplate restTemplate;

    public void sendPostRequest(ListParameter parameter) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<SearchRequestDto> request = new HttpEntity<>(new SearchRequestDto(parameter.getKeyword(),
                LocalDateTime.now()), httpHeaders);
        try {
            ResponseEntity<SearchRequestDto> post = restTemplate.postForEntity("/request",
                    request, SearchRequestDto.class);
            if ((post.getBody() != null)) {
                LOGGER.info(post.getBody().toString());
                LOGGER.info(post.getStatusCode().toString());
            } else {
                LOGGER.info("null");
            }
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<Count> sendPGetRequest() {
        List<Count> counts = new ArrayList<>();
        try {
            ResponseEntity<List<Count>> exchange = restTemplate.exchange("/counts",
                    HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                    });
            counts = exchange.getBody();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage());
        }
        return counts;
    }
}
