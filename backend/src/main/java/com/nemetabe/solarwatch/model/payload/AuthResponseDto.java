package com.nemetabe.solarwatch.model.payload;

public record AuthResponseDto(
        String token, String username,String email)
{}
