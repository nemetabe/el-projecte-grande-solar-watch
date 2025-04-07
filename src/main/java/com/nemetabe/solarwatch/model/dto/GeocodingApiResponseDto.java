package com.nemetabe.solarwatch.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nemetabe.solarwatch.model.GeocodingData;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponseDto(
        List<GeocodingData> resultCities
) {}