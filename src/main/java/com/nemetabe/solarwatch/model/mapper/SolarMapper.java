package com.nemetabe.solarwatch.model.mapper;

import com.nemetabe.solarwatch.mapper.CityMapper;
import com.nemetabe.solarwatch.model.dto.api.sssr.SolarApiResponseDto;
import com.nemetabe.solarwatch.model.dto.SolarResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SunsetSunriseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class SolarMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a"); // handles 12h format like "7:27:02 AM"

    public static SolarTimes toEntity(SolarApiResponseDto apiDto, City city, LocalDate date) {
        SolarApiResponseDto.SolarResults r = apiDto.getResults();

        SolarTimes entity = new SolarTimes();
        setEntitySolarResults(entity, r);
        entity.setDate(date);
        entity.setCity(city);
        entity.setTimeZone(apiDto.getTzid());
        return entity;
    }

    private static void setEntitySolarResults(SolarTimes entity, SolarApiResponseDto.SolarResults results) {
        entity.setSunrise(parseTime(results.getSunrise()));
        entity.setSunset(parseTime(results.getSunset()));
        entity.setSolarNoon(parseTime(results.getSolar_noon()));
        entity.setDayLength(parseDuration(results.getDay_length()));
        entity.setNightBegin(parseTime(results.getAstronomical_twilight_begin()));
        entity.setNightEnd(parseTime(results.getAstronomical_twilight_end()));
        entity.setFirstLight(parseTime(results.getCivil_twilight_begin()));
        entity.setLastLight(parseTime(results.getCivil_twilight_end()));
        entity.setDawn(parseTime(results.getNautical_twilight_begin()));
        entity.setDusk(parseTime(results.getNautical_twilight_end()));
    }

    private static LocalTime parseTime(String timeStr) {
        // "9:38:53" → HH:mm:ss
        try {
            return LocalTime.parse(timeStr, formatter);
        } catch (DateTimeParseException e) {
            return LocalTime.parse(timeStr);
        }
    }

    private static Duration parseDuration(String durationStr) {
        // "9:38:53" → HH:mm:ss
        String[] parts = durationStr.split(":");
        return Duration.ofHours(Integer.parseInt(parts[0]))
                .plusMinutes(Integer.parseInt(parts[1]))
                .plusSeconds(Integer.parseInt(parts[2]));
    }


    public static SolarResponseDto toDto(SolarTimes entity) {
        return new SolarResponseDto(
                entity.getDate(),
                entity.getSunrise(),
                entity.getSunset(),
                entity.getSolarNoon(),
                entity.getDayLength(),
                entity.getFirstLight(),
                entity.getLastLight(),
                entity.getDusk(),
                entity.getDawn(),
                entity.getNightBegin(),
                entity.getNightEnd(),
                CityMapper.toNameDto(entity.getCity()),
                entity.getTimeZone());
    }

    public static SunsetSunriseDto toSunsetSunriseDto(SolarTimes entity) {
        return new SunsetSunriseDto(
                entity.getSunset(),
                entity.getSunrise(),
               CityMapper.toNameDto(entity.getCity()),
                entity.getDate());
    }
}