package com.nemetabe.solarwatch.model.mapper;

import com.nemetabe.solarwatch.model.GeocodingData;
import com.nemetabe.solarwatch.model.dto.CityDto;
import com.nemetabe.solarwatch.model.dto.CityNameDto;
import com.nemetabe.solarwatch.model.dto.GeocodingApiResponseDto;
import com.nemetabe.solarwatch.model.entity.City;

import java.util.ArrayList;
import java.util.List;

public class CityMapper {

    public static List<City> toEntity(GeocodingApiResponseDto responseDto) {
        if (responseDto == null) {
            return null;
        }
        List<City> cities = new ArrayList<>();

        for (GeocodingData dto : responseDto.resultCities()) {
            City city = new City();
            city.setName(dto.name());
            city.setLatitude(dto.lat());
            city.setLongitude(dto.lon());
            city.setCountry(dto.country());
            cities.add(city);
        }

        return cities;
    }

    // Converts City entity to City DTO (full information)
    public static CityDto toDto(City city) {
        if (city == null) {
            return null;
        }

        CityDto dto = new CityDto();
        dto.setName(city.getName());
        dto.setLatitude(city.getLatitude());
        dto.setLongitude(city.getLongitude());
        dto.setCountry(city.getCountry());
        //dto.setLocalNames(city.getLocalNames());

        return dto;
    }

    // Converts City entity to a partial DTO (e.g., NameDto)
    public static CityNameDto toNameDto(City city) {
        if (city == null) {
            return null;
        }

        CityNameDto nameDto = new CityNameDto(city.getName(), city.getCountry());
        return nameDto;
    }
}