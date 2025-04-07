package com.nemetabe.solarwatch.model.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponseDto(
        List<GeocodingData> resultCities
) {}