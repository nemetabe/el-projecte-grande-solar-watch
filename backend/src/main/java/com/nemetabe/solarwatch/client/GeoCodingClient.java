package com.nemetabe.solarwatch.client;

import com.nemetabe.solarwatch.model.dto.api.geocoding.GeocodingDataDto;
import com.nemetabe.solarwatch.model.exception.api.ApiClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;

@Component
public class GeoCodingClient extends BaseApiClient {
    private static final Logger log = LoggerFactory.getLogger(GeoCodingClient.class);

    @Value("${nemetabe.app.api.openweather.api-key}")
    private String API_KEY;

    public GeoCodingClient(@Value("${nemetabe.app.api.openweather.base-url}") String baseUrl) {
        super(baseUrl);
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
                .bodyToFlux(GeocodingDataDto.class)
                .onErrorMap(WebClientResponseException.class,
                        ex -> handleApiError(ex, "fetching geocoding data"));
    }

    public Flux<GeocodingDataDto> fetch(double lat, double lon, int limit) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/reverse")
                        .queryParam("lat", lat)
                        .queryParam("lon", lon)
                        .queryParam("limit", limit)
                        .queryParam("appid", API_KEY)
                        .build())
                .retrieve()
                .bodyToFlux(GeocodingDataDto.class)
                .onErrorMap(WebClientResponseException.class,
                        ex -> handleApiError(ex, "fetching geocoding data"));
    }


    public Mono<GeocodingDataDto> fetchCity(String cityName) {
        return fetch(cityName, 1)
                .single()
                .onErrorMap(IndexOutOfBoundsException.class,
                        ex -> new ApiClientException("City not found: " + cityName));
    }

    public Mono<GeocodingDataDto> fetchCity(double lat, double lon) {
        return fetch(lat, lon, 1)
                .single()
                .onErrorMap(IndexOutOfBoundsException.class,
                        ex -> new ApiClientException(
                                "No city found for coordinates: lat:" + lat + ", lon: " + lon));
    }

    public Flux<GeocodingDataDto> fetchCities(String cityName) {
        return fetch(cityName, 5);
    }
}
