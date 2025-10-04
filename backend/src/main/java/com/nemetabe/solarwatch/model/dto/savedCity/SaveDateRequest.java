package com.nemetabe.solarwatch.model.dto.savedCity;

import java.time.LocalDate;

public record SaveDateRequest(Long cityId,
                              LocalDate savedDate) {
}
