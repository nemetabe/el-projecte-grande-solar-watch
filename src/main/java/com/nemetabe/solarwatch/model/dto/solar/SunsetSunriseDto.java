package com.nemetabe.solarwatch.model.dto.solar;

import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import java.time.LocalDate;
import java.time.LocalTime;

public record SunsetSunriseDto(LocalTime sunset, LocalTime sunrise, CityNameDto cityName, LocalDate date) {
}
