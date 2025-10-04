package com.nemetabe.solarwatch.model.dto.savedCity;

import java.time.LocalDate;
import java.util.Set;

public record SavedCityDto(
        Long id,
        String memberName,
        String cityName,
        Set<LocalDate> savedDates
) {
}
