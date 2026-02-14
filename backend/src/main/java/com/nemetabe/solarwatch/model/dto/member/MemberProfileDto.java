package com.nemetabe.solarwatch.model.dto.member;

import com.nemetabe.solarwatch.model.dto.city.CityNameDto;

public record MemberProfileDto(Long id, String username, String email, CityNameDto city) {
}
