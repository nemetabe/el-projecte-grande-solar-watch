package com.nemetabe.solarwatch.mapper;

import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.api.sssr.SolarApiResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SunsetSunriseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

@Service
public class SolarMapper {

    private static final DateTimeFormatter apiInputFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US);
    private static final DateTimeFormatter frontendOutputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

    private static final Logger log = LoggerFactory.getLogger(SolarMapper.class);

    public static SolarTimes toEntity(SolarApiResponseDto apiDto, City city, LocalDate date) {
        LocalDate actualDate = Optional.ofNullable(date).orElse(LocalDate.now());
        SolarApiResponseDto.SolarResults results = apiDto.getResults();
        System.out.println(apiDto.getResults().getAstronomicalTwilightBegin());
        log.info(apiDto.getResults().getAstronomicalTwilightBegin());
        return SolarTimes.builder()
                .date(actualDate)
                .city(city)
                .sunrise(parseTime(results.getSunrise()))
                .sunset(parseTime(results.getSunset()))

                .solarNoon(parseTime(results.getSolarNoon()))
                .dayLength(parseDuration(results.getDayLength()))

                .dawn(parseTime(results.getCivilTwilightBegin()))
                .dusk(parseTime(results.getCivilTwilightEnd()))

                .firstLight(parseTime(results.getNauticalTwilightBegin()))
                .lastLight(parseTime(results.getNauticalTwilightEnd()))

                .nightBegin(parseTime(results.getAstronomicalTwilightBegin()))
                .nightEnd(parseTime(results.getAstronomicalTwilightEnd()))
                .timeZone(apiDto.getTzid() != null ? apiDto.getTzid() : "UTC")
                .build();
    }

    private static LocalTime parseTime(String timeStr) {
        if (timeStr == null || timeStr.trim().isEmpty()) {
            log.warn("Attempted to parse null or empty time string.");
            return null;
        }

        String cleaned = timeStr.replace("\u00A0", " ").trim();
        try {
            return LocalTime.parse(cleaned, apiInputFormatter);
        } catch (DateTimeParseException e) {
            log.error("Failed to parse time string: '{}'. Error: {}", cleaned, e.getMessage());
            return null;
        }
    }

    private static Duration parseDuration(String durationStr) {
        if (durationStr == null || durationStr.trim().isEmpty()) {
            log.warn("Attempted to parse null or empty duration string.");
            return Duration.ZERO;
        }

        try {
            String[] parts = durationStr.split(":");
            if (parts.length != 3) {
                log.error("Invalid duration format: '{}'. Expected HH:mm:ss", durationStr);
                return Duration.ZERO;
            }

            return Duration.ofHours(Integer.parseInt(parts[0]))
                    .plusMinutes(Integer.parseInt(parts[1]))
                    .plusSeconds(Integer.parseInt(parts[2]));
        } catch (NumberFormatException e) {
            log.error("Failed to parse duration: '{}'. Error: {}", durationStr, e.getMessage());
            return Duration.ZERO;
        }
    }

    // --- MAPPING FROM ENTITY TO FRONTEND DTO ---
    public static SolarResponseDto toDto(SolarTimes entity) {
        if (entity == null) {
            return null;
        }

        return new SolarResponseDto(
                entity.getId(),
                entity.getDate(),
                formatTimeForFrontend(entity.getSunrise()),
                formatTimeForFrontend(entity.getSunset()),
                formatTimeForFrontend(entity.getSolarNoon()),
                formatDurationForFrontend(entity.getDayLength()),
                formatTimeForFrontend(entity.getDusk()),
                formatTimeForFrontend(entity.getDawn()),
                formatTimeForFrontend(entity.getFirstLight()),
                formatTimeForFrontend(entity.getLastLight()), // Corrected: now maps nautical_twilight_end via entity.getLastLight()
                formatTimeForFrontend(entity.getNightBegin()),
                formatTimeForFrontend(entity.getNightEnd()),
                Optional.ofNullable(entity.getCity())
                        .map(CityMapper::toNameDto)
                        .orElse(null),
                entity.getTimeZone()
        );
    }

    private static String formatTimeForFrontend(LocalTime time) {
        if (time == null) {
            return "";
        }
        try {
            return time.format(frontendOutputFormatter);
        } catch (DateTimeException e) {
            log.error("Failed to format time: {}. Error: {}", time, e.getMessage());
            return "";
        }
    }

    private static String formatDurationForFrontend(Duration duration) {
        if (duration == null) {
            return "00:00:00";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static SunsetSunriseDto toSunsetSunriseDto(SolarTimes entity) {
        if (entity == null) {
            return null;
        }
        return new SunsetSunriseDto(
                entity.getSunset(),
                entity.getSunrise(),
                Optional.ofNullable(entity.getCity())
                        .map(CityMapper::toNameDto)
                        .orElse(null),
                entity.getDate());
    }
}