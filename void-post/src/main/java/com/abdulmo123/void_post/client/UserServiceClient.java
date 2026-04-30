package com.abdulmo123.void_post.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class UserServiceClient {
    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public Long validate(String authHeader) {
        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.set("Authorization", authHeader);
            HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

            ResponseEntity<Long> response = restTemplate.exchange(
                    userServiceUrl + "/api/v1/auth/validate",
                    HttpMethod.GET,
                    httpEntity,
                    Long.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Unauthorized!!!");
        }
    }
}
