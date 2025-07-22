package com.nemetabe.solarwatch.model.dto.api.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingDataDto(
        String name,
        double lat,
        double lon,
        String country,
        String state
) {}