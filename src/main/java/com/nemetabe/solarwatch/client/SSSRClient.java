package com.nemetabe.solarwatch.client;

import com.nemetabe.solarwatch.model.dto.api.sssr.SolarApiResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Component
public class SSSRClient {
    private final WebClient webClient;

    public SSSRClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.sunrisesunset.io")
                .build();
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
                .bodyToMono(SolarApiResponseDto.class);
    }
}