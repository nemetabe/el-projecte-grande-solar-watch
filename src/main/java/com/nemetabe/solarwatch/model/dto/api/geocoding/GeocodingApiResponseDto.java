package com.nemetabe.solarwatch.model.dto.api.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeocodingApiResponseDto(List<GeocodingDataDto> resultCities) {}