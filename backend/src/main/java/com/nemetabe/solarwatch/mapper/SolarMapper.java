package com.nemetabe.solarwatch.mapper;


import com.nemetabe.solarwatch.model.dto.api.sssr.SolarApiResponseDto;
import com.nemetabe.solarwatch.model.dto.savedCity.SavedCityResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SunsetSunriseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SavedCity;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Locale;

@Component
public class SolarMapper {

    private static final DateTimeFormatter apiInputFormatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US);
    private static final DateTimeFormatter frontendOutputFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.US);

    private static final Logger log = LoggerFactory.getLogger(SolarMapper.class);

    public static SavedCityResponseDto getSavedCityResponseDtoFrom(SavedCity savedCity) {
        return new SavedCityResponseDto(
                savedCity.getId(),
                savedCity.getCity().getCountry(),
                savedCity.getCity().getName(),
                new ArrayList<>(savedCity.getSolarDateIds())
        );
    }
    public static SolarTimes toEntity(SolarApiResponseDto apiDto, City city, LocalDate date) {
        log.debug(apiDto.toString());
        return buildSolarEntity(apiDto, city, date);
    }

    public static SolarResponseDto toDto(SolarTimes entity) {
        if (entity == null) {
            return null;
        }
        return buildSolarDto(entity);
    }
    public static SunsetSunriseDto toSunsetSunriseDto(SolarTimes entity) {
        if (entity == null) {
            return null;
        }
        return new SunsetSunriseDto(
                entity.getSunset(),
                entity.getSunrise(),
                CityMapper.toNameDto(entity.getCity()),
                entity.getDate());
    }

    private static SolarResponseDto buildSolarDto(SolarTimes entity) {
        return SolarResponseDto.builder()
                .id(entity.getId())
                .date(entity.getDate())
                .sunrise(formatTimeForFrontend(entity.getSunrise()))
                .sunset(formatTimeForFrontend(entity.getSunset()))

                .solarNoon(formatTimeForFrontend(entity.getSolarNoon()))
                .dayLength(formatDuration(entity.getDayLength()))

    
                .nightEnd(parseTime(results.getAstronomicalTwilightBegin()))
                .nightBegin(parseTime(results.getAstronomicalTwilightEnd()))
                .timeZone(apiDto.getTzid() != null ? apiDto.getTzid() : "UTC")
                .dusk(formatTimeForFrontend(entity.getDusk()))
                .dawn(formatTimeForFrontend(entity.getDawn()))

                .firstLight(formatTimeForFrontend(entity.getFirstLight()))
                .lastLight(formatTimeForFrontend(entity.getLastLight()))

                .nightBegin(formatTimeForFrontend(entity.getNightBegin()))
                .nightEnd(formatTimeForFrontend(entity.getNightEnd()))
                .city(CityMapper.toNameDto(entity.getCity()))
                .timeZone(entity.getTimeZone())
                .build();
    }

    private static SolarTimes buildSolarEntity(
            SolarApiResponseDto apiResponseDto,
            City city,
            LocalDate date)
    {
        SolarApiResponseDto.SolarResults results = apiResponseDto.getResults();
        SolarTimes sTimes = new SolarTimes();
        sTimes.setDate(date);
        sTimes.setCity(city);
        sTimes.setSunrise(parseTime(results.getSunrise()));
        sTimes.setSunset(parseTime(results.getSunset()));
        sTimes.setSolarNoon(parseTime(results.getSolarNoon()));
        sTimes.setDayLength(parseDuration(results.getDayLength()));
        sTimes.setDawn(parseTime(results.getCivilTwilightBegin()));
        sTimes.setDusk(parseTime(results.getCivilTwilightEnd()));
        sTimes.setFirstLight(parseTime(results.getNauticalTwilightBegin()));
        sTimes.setLastLight(parseTime(results.getNauticalTwilightEnd()));
        sTimes.setNightBegin(parseTime(results.getAstronomicalTwilightBegin()));
        sTimes.setNightEnd(parseTime(results.getAstronomicalTwilightEnd()));
        sTimes.setTimeZone(apiResponseDto.getTzid() != null ? apiResponseDto.getTzid() : "UTC");
        return sTimes;
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
                formatTimeForFrontend(entity.getLastLight()),
                formatTimeForFrontend(entity.getNightBegin()),
                formatTimeForFrontend(entity.getNightEnd()),
                Optional.ofNullable(entity.getCity())//todo
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

    private static String formatDuration(Duration duration) {
        if (duration == null) {
            return "00:00:00";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    //members' solar times display
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