package com.nemetabe.solarwatch.model.payload;

import com.nemetabe.solarwatch.model.dto.city.CityNameDto;

public record AuthResponseDto(
        String jwt, String username,Long memberId, CityNameDto favCity)
{}
