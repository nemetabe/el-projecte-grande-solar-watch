package com.nemetabe.solarwatch.client;

import com.nemetabe.solarwatch.model.dto.api.geocoding.GeocodingDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Component
public class GeoCodingClient {
    private final WebClient webClient;
    private static final Logger log = LoggerFactory.getLogger(GeoCodingClient.class);
    private static final String API_KEY = "91186402b10c6e0a9a50c1fd5726825c";

    public GeoCodingClient(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("https://api.openweathermap.org")
                .build();
    }

    public Flux<GeocodingDataDto> fetchCities(String cityName) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/direct")
                        .queryParam("q", cityName)
                        .queryParam("limit", 5)
                        .queryParam("appid", API_KEY)
                        .build())
                .retrieve()
                .bodyToFlux(GeocodingDataDto.class);
    }
}

