package com.nemetabe.solarwatch.client;

import com.nemetabe.solarwatch.model.exception.api.ApiClientException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

public abstract class BaseApiClient {
    protected final WebClient webClient;
    protected final String baseUrl;

    protected BaseApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.USER_AGENT, "SolarWatchApp/1.0")
                .build();
    }

    protected String getBaseUrl() {
        return baseUrl;
    }

    protected RuntimeException handleApiError(WebClientResponseException ex, String operation) {
        return new ApiClientException(
                String.format("API error during %s: %s - %s", operation, ex.getStatusCode(), ex.getResponseBodyAsString()),
                ex
        );
    }
}