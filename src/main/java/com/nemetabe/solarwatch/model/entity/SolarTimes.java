package com.nemetabe.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;


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

    private LocalDate date;

    @Column(nullable = false)
    private LocalTime sunrise;
    @Column(nullable = false)
    private LocalTime sunset;

    @Column(name = "solar_noon", nullable = false)
    private LocalTime solarNoon;
    @Column(name = "day_length", nullable = false)
    private Duration dayLength;

    @Column(name = "civil_twilight_begin", nullable = false)
    private LocalTime civilTwilightBegin;
    @Column(name = "civil_twilight_end", nullable = false)
    private LocalTime civilTwilightEnd;

    @Column(name = "nautical_twilight_end", nullable = false)
    private LocalTime nauticalTwilightBegin;
    @Column(name = "nautical_twilight_begin", nullable = false)
    private LocalTime nauticalTwilightEnd;

    @Column(name = "astronomical_twilight_end", nullable = false)
    private LocalTime astronomicalTwilightBegin;
    @Column(name = "astronomical_twilight_begin", nullable = false)
    private LocalTime astronomicalTwilightEnd;

    private String tzid; // "UTC"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

}