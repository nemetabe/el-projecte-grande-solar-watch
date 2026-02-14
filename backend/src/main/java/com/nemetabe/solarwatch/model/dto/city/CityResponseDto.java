package com.nemetabe.solarwatch.model.dto.city;

import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;

import java.util.List;

public record CityResponseDto(
        Long id,
        String name,
        String country,
        String state,
        double latitude,
        double longitude,
        List<SolarResponseDto> solarTimes
) {}