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
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Locale;

@Service
public class SolarMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.US);
    private static final Logger log = LoggerFactory.getLogger(SolarMapper.class);

    public static SolarTimes toEntity(SolarApiResponseDto apiDto, City city) {
        SolarTimes entity = new SolarTimes();
        setEntitySolarResults(entity, apiDto);
        entity.setCity(city);
        return entity;
    }

    private static void setEntitySolarResults(SolarTimes entity, SolarApiResponseDto response) {
        entity.setSunrise(parseTime(response.getResults().getSunrise()));
        entity.setSunset(parseTime(response.getResults().getSunset()));
        entity.setSolarNoon(parseTime(response.getResults().getSolar_noon()));
        entity.setDayLength(parseDuration(response.getResults().getDay_length()));
        entity.setDusk(parseTime(response.getResults().getCivil_twilight_end()));
        entity.setTimeZone(response.getTzid());
        entity.setNightBegin(parseTime(response.getResults().getAstronomical_twilight_end()));
        entity.setNightEnd(parseTime(response.getResults().getAstronomical_twilight_begin()));
        entity.setDawn(parseTime(response.getResults().getCivil_twilight_begin()));
        entity.setLastLight(parseTime(response.getResults().getCivil_twilight_end()));
        entity.setFirstLight(parseTime(response.getResults().getNautical_twilight_begin()));
    }

    private static LocalTime parseTime(String timeStr) {
        if (timeStr == null) {
            log.info("Parsing time string: null");
            return null;
        }

        log.info("Parsing time string: [{}] (chars: {})", timeStr, Arrays.toString(timeStr.toCharArray()));
        try {
            String cleaned = timeStr.replace("\u00A0", " ").trim();
            return LocalTime.parse(cleaned, formatter);
        } catch (DateTimeParseException e) {
            log.error("Failed to parse time string: [{}]. Error: {}", timeStr, e.getMessage());
            try {
                return LocalTime.parse(timeStr.trim(), formatter);
            } catch (DateTimeParseException e2) {
                log.error("Fallback parsing also failed for: [{}]. Error: {}", timeStr, e2.getMessage());
                return null;
            }
        }
    }

    private static Duration parseDuration(String durationStr) {
        if (durationStr == null) {
            return Duration.ZERO;
        }

        try {
            String[] parts = durationStr.split(":");
            if (parts.length != 3) {
                log.error("Invalid duration format: [{}]. Expected HH:mm:ss", durationStr);
                return Duration.ZERO;
            }

            return Duration.ofHours(Integer.parseInt(parts[0]))
                    .plusMinutes(Integer.parseInt(parts[1]))
                    .plusSeconds(Integer.parseInt(parts[2]));
        } catch (NumberFormatException e) {
            log.error("Failed to parse duration: [{}]. Error: {}", durationStr, e.getMessage());
            return Duration.ZERO;
        }
    }

    public static SolarResponseDto toDto(SolarTimes entity) {
        return new SolarResponseDto(
                entity.getId(),
                entity.getDate(),
                entity.getSunrise(),
                entity.getSunset(),
                entity.getSolarNoon(),
                durationToString(entity.getDayLength()),
                entity.getDusk(),
                entity.getDawn(),
                entity.getFirstLight(),
                entity.getLastLight(),
                entity.getNightBegin(),
                entity.getNightEnd(),
                CityMapper.toNameDto(entity.getCity()),
                entity.getTimeZone() != null ? entity.getTimeZone() : "UTC"
        );
    }

    private String timeToString(LocalTime time) {
        if (time == null) {
            return "";
        }
        try {
            return time.format(formatter);
        } catch (DateTimeException e) {
            log.error("Failed to format time: {}. Error: {}", time, e.getMessage());
            return "";
        }
    }

    public static Duration stringToDuration(String timeString) {
        if (timeString == null || timeString.trim().isEmpty()) {
            return Duration.ZERO;
        }
        try {
            String[] parts = timeString.split(":");
            if (parts.length != 3) {
                log.error("Invalid time string format: [{}]. Expected HH:mm:ss", timeString);
                return Duration.ZERO;
            }

            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return Duration.of(hours, ChronoUnit.HOURS)
                    .plus(minutes, ChronoUnit.MINUTES)
                    .plus(seconds, ChronoUnit.SECONDS);

        } catch (NumberFormatException e) {
            log.error("Failed to parse time string: [{}]. Error: {}", timeString, e.getMessage());
            return Duration.ZERO;
        }
    }

    public static String durationToString(Duration duration) {
        if (duration == null) {
            return "00:00:00";
        }

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public static SunsetSunriseDto toSunsetSunriseDto(SolarTimes entity) {
        return new SunsetSunriseDto(
                entity.getSunset(),
                entity.getSunrise(),
                new CityNameDto(entity.getCity().getName(), entity.getCity().getCountry()),
                entity.getDate());
    }
}