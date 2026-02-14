package com.nemetabe.solarwatch.model.dto.solar;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import lombok.Builder;

import java.time.LocalDate;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public record SolarResponseDto(
        Long id,
        LocalDate date,
        String sunrise,
        String sunset,
        String solarNoon,
        String dayLength,
        String dusk,       // civil_twilight_end
        String dawn,       // civil_twilight_begin
        String firstLight, // nautical_twilight_begin
        String lastLight,  // nautical_twilight_end
        String nightBegin, // astronomical_twilight_end
        String nightEnd,   // astronomical_twilight_begin
        CityNameDto city,
        String timeZone
) {}