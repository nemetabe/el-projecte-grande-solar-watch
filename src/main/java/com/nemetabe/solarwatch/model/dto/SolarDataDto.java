package com.nemetabe.solarwatch.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolarDataDto {
    private LocalDate date;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sunrise;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime sunset;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime solar_noon;

    @JsonFormat(pattern = "HH:mm:ss")
    private Duration day_length;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime civil_twilight_begin;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime civil_twilight_end;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime nautical_twilight_begin;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime nautical_twilight_end;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime astronomical_twilight_begin;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime astronomical_twilight_end;

    private CityNameDto city;
    private String tzid;
}

