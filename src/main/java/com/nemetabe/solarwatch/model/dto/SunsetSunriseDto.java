package com.nemetabe.solarwatch.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record SunsetSunriseDto(LocalTime sunset, LocalTime sunrise, CityNameDto cityName, LocalDate date) {
}
