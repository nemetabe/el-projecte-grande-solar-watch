package com.nemetabe.solarwatch.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.TimeZoneSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.nemetabe.solarwatch.mapper.formatter.LocalTimeTo12HourSerializer;
import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TimeZone;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "solar_times")
public class SolarTimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;

    @Column(nullable = false)
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime sunrise;

    @Column(nullable = false)
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime sunset;

    @Column(name = "solar_noon")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime solarNoon;


    @Column(name = "day_length")
    //@JsonSerialize(using = DurationSerializer.class)
    private Duration dayLength;

    @Column(name = "dusk")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime dusk;

    @Column(name = "dawn")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime dawn;

    @Column(name = "first_light")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime firstLight;

    @Column(name = "last_light")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime lastLight;

    @Column(name = "night_begin")
    //@JsonSerialize(using = LocalTimeTo12HourSerializer.class)
    private LocalTime nightBegin;

    @Column(name = "night_end")
    private LocalTime nightEnd;

    @Column(name = "time_zone")
    //@JsonSerialize(using = TimeZoneSerializer.class)
    private String timeZone;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}