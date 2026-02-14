package com.nemetabe.solarwatch.model.dto.savedCity;

import com.nemetabe.solarwatch.model.entity.SolarDateId;
import java.util.List;


public record SavedCityResponseDto(
        Long id,
        String country,
        String city,
        List<SolarDateId> solarDateIds
) {
}
