package com.nemetabe.solarwatch.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "solar_times", uniqueConstraints = @UniqueConstraint(columnNames = {"date", "city_id"}))
@Getter
@Setter
@NoArgsConstructor // Required for JPA
@AllArgsConstructor // Required for @Builder
@Builder // Enables the builder pattern for clean object creation
public class SolarTimes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(nullable = false)
    private LocalTime sunrise; // Corresponds to API "sunrise"

    @Column(nullable = false)
    private LocalTime sunset;  // Corresponds to API "sunset"

    @Column(nullable = false)
    private LocalTime solarNoon; // Corresponds to API "solar_noon"

    @Column(nullable = false)
    private Duration dayLength; // Corresponds to API "day_length"

    // Civil
    private LocalTime dawn; // Corresponds to API "civil_twilight_begin"
    private LocalTime dusk; // Corresponds to API "civil_twilight_end"

    // Nautical
    private LocalTime firstLight; // Corresponds to API "nautical_twilight_begin"
    @Column(nullable = false)
    private LocalTime lastLight;  // Corresponds to API "nautical_twilight_end"

    // Astronomical
    private LocalTime nightBegin; // Corresponds to API "astronomical_twilight_end"
    private LocalTime nightEnd;   // Corresponds to API "astronomical_twilight_begin"

    @Column(nullable = false)
    private String timeZone; // Corresponds to API "tzid"
}