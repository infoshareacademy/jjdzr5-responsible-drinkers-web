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
import java.util.Optional;

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
            LOGGER.info(post.getStatusCode().toString());
            LOGGER.info(post.getBody().toString());
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public List<Count> sendPGetRequest() {
        List<Count> counts = new ArrayList<>();
        try {
            ResponseEntity<List<Count>> exchange = restTemplate.exchange("/counts",
                    HttpMethod.GET, null, new ParameterizedTypeReference<List<Count>>() {
                    });
            counts = exchange.getBody();
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return counts;
    }
}
