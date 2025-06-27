package com.nemetabe.solarwatch.model.payload;

import java.util.List;

public record JwtResponse(String jwt, String userName, List<String> roles) {
}