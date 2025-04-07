package com.nemetabe.solarwatch.model.mapper;

import com.nemetabe.solarwatch.model.dto.geocoding.GeocodingData;
import com.nemetabe.solarwatch.model.dto.CityResponseDto;
import com.nemetabe.solarwatch.model.dto.CityNameDto;
import com.nemetabe.solarwatch.model.dto.geocoding.GeocodingApiResponseDto;
import com.nemetabe.solarwatch.model.entity.City;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
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
    public static CityResponseDto toDto(City city) {
        if (city == null) {
            return null;
        }

        CityResponseDto dto = new CityResponseDto();
        dto.setName(city.getName());
        dto.setLatitude(city.getLatitude());
        dto.setLongitude(city.getLongitude());
        dto.setState(city.getState());
        dto.setCountry(city.getCountry());
        dto.setLocalNames(city.getLocalNames());
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