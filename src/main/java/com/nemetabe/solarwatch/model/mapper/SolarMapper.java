package com.nemetabe.solarwatch.model.mapper;

import com.nemetabe.solarwatch.model.dto.CityNameDto;
import com.nemetabe.solarwatch.model.dto.SolarApiResponseDto;
import com.nemetabe.solarwatch.model.dto.SolarDataDto;
import com.nemetabe.solarwatch.model.dto.SunsetSunriseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarData;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class SolarMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm:ss a"); // handles 12h format like "7:27:02 AM"

    public static SolarData toEntity(SolarApiResponseDto apiDto, City city, LocalDate date) {
        SolarApiResponseDto.SolarResults r = apiDto.getResults();

        SolarData entity = new SolarData();
        setEntitySolarResults(entity, r);
        entity.setDate(date);
        entity.setCity(city);
        entity.setTzid(apiDto.getTzid());
        return entity;
    }

    private static void setEntitySolarResults(SolarData entity, SolarApiResponseDto.SolarResults r) {
        entity.setSunrise(parseTime(r.getSunrise()));
        entity.setSunset(parseTime(r.getSunset()));
        entity.setSolarNoon(parseTime(r.getSolar_noon()));
        entity.setDayLength(parseDuration(r.getDay_length()));
        entity.setAstronomicalTwilightBegin(parseTime(r.getAstronomical_twilight_begin()));
        entity.setAstronomicalTwilightEnd(parseTime(r.getAstronomical_twilight_end()));
        entity.setCivilTwilightBegin(parseTime(r.getCivil_twilight_begin()));
        entity.setCivilTwilightEnd(parseTime(r.getCivil_twilight_end()));
        entity.setNauticalTwilightBegin(parseTime(r.getNautical_twilight_begin()));
        entity.setNauticalTwilightEnd(parseTime(r.getNautical_twilight_end()));
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

    public static SolarDataDto toDto(SolarData entity) {
        return new SolarDataDto(
                entity.getDate(),
                entity.getSunrise(),
                entity.getSunset(),
                entity.getSolarNoon(),
                entity.getDayLength(),
                entity.getCivilTwilightBegin(),
                entity.getCivilTwilightEnd(),
                entity.getNauticalTwilightBegin(),
                entity.getNauticalTwilightEnd(),
                entity.getAstronomicalTwilightBegin(),
                entity.getAstronomicalTwilightEnd(),
                CityMapper.toNameDto(entity.getCity()),
                entity.getTzid()
                );
    }

    public static SunsetSunriseDto toSunsetSunriseDto(SolarData entity) {
        return new SunsetSunriseDto(entity.getSunset(), entity.getSunrise(), new CityNameDto(entity.getCity().getName(), entity.getCity().getCountry()), entity.getDate());
    }
}