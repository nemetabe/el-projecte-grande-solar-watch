package com.nemetabe.solarwatch.model.payload;

import java.util.List;

public record JwtResponseDto(String jwt, String userName, List<String> roles) {
}