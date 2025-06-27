package com.nemetabe.solarwatch.model.dto.solar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SunriseSunsetResponse(
        String sunrise,
        String sunset,
        String tzid
) {}
