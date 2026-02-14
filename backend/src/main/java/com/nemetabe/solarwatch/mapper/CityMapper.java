package com.nemetabe.solarwatch.mapper;

import com.nemetabe.solarwatch.model.dto.api.geocoding.GeocodingDataDto;
import com.nemetabe.solarwatch.model.dto.city.CityLocationDto;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.api.geocoding.GeocodingApiResponseDto;
import com.nemetabe.solarwatch.model.entity.City;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityMapper {

    public static List<City> toEntities(GeocodingApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }
        List<City> cities = new ArrayList<>();

        for (GeocodingDataDto dto : responseDto.resultCities()) {
            City city = toEntity(dto);
            cities.add(city);
        }
        return cities;
    }


    public static City toEntity(GeocodingDataDto dto) {
        City city = new City();
        city.setName(dto.name());
        city.setLatitude(dto.lat());
        city.setLongitude(dto.lon());
        city.setCountry(dto.country());
        if (dto.state() == null) {
            city.setState(null);
        }
        return city;
    }
    public static CityResponseDto toDto(City city) {
        if (city == null) {
            return null;
        }
        return new CityResponseDto(
                city.getId(),
                city.getName(),
                city.getCountry(),
                city.getState(),
                city.getLatitude(),
                city.getLongitude(),
                city.getSolarTimes() == null ? List.of() : city.getSolarTimes().stream()
                        .map(SolarMapper::toDto)
                        .toList()
        );
    }

    public static CityNameDto toNameDto(City city) {
        if (city == null) {
            return null;
        }
        CityNameDto nameDto = new CityNameDto(city.getId(), city.getName(), city.getCountry());
        return nameDto;
    }

    public static CityLocationDto toLocationDto(City city) {
        if (city == null) {
            return null;
        }
        CityLocationDto locationDto = new CityLocationDto(
                city.getName(), city.getCountry(), city.getLatitude(), city.getLongitude());
        return locationDto;
    }
}