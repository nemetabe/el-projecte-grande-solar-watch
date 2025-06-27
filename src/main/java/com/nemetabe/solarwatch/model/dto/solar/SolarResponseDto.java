package com.nemetabe.solarwatch.model.dto.solar;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.nemetabe.solarwatch.mapper.formatter.DurationFromStringDeserializer;
import com.nemetabe.solarwatch.mapper.formatter.LocalTimeTo12HourDeserializer;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SolarResponseDto {

    private Long id;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate date;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime sunrise;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime sunset;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime solarNoon;

    @JsonDeserialize(using = DurationFromStringDeserializer.class)
    private String dayLength;


    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime dusk;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime dawn;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime firstLight;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime lastLight;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime nightBegin;

    @JsonDeserialize(using = LocalTimeTo12HourDeserializer.class)
    private LocalTime nightEnd;

    private CityNameDto city;
    private String timeZone;
}

