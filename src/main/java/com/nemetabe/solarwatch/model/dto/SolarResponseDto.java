package com.nemetabe.solarwatch.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolarResponseDto {

    private Long id;

    @JsonFormat(pattern = "YYYY-mm-dd")
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

    public SolarResponseDto(LocalDate date, LocalTime sunrise, LocalTime sunset, LocalTime solarNoon, Duration dayLength, LocalTime firstLight, LocalTime lastLight, LocalTime dusk, LocalTime dawn, LocalTime nightBegin, LocalTime nightEnd, com.nemetabe.solarwatch.model.dto.city.CityNameDto nameDto, String timeZone) {

    }
}

