package com.nemetabe.solarwatch.client;

import com.nemetabe.solarwatch.model.dto.api.sssr.SolarApiResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class SSSRClient extends BaseApiClient {

    public SSSRClient(@Value("${nemetabe.app.api.sunset-sunrise.base-url}") String baseUrl) {
        super(baseUrl);
    }

    public Mono<SolarApiResponseDto> fetchSolarData(double lat, double lon, LocalDate date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/json")
                        .queryParam("lat", lat)
                        .queryParam("lng", lon)
                        .queryParam("date", date.toString())
                        .build())
                .retrieve()
                .bodyToMono(SolarApiResponseDto.class)
                .onErrorMap(WebClientResponseException.class,
                        ex -> handleApiError(ex, "fetching solar data"));
    }
}