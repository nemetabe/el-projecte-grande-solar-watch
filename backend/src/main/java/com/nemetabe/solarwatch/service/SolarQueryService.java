package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.mapper.SolarMapper;
import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import com.nemetabe.solarwatch.model.exception.city.InvalidCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class SolarQueryService {

    private final CityService cityService;
    private final SolarTimesService solarTimesService;

    @Autowired
    public SolarQueryService(CityService cityService,
                             SolarTimesService solarTimesService) {
        this.cityService = cityService;
        this.solarTimesService = solarTimesService;
    }

    public Mono<SolarTimes> getSolarTimes(String cityName, LocalDate date) {
        return cityService.findOrCreate(cityName)
                .switchIfEmpty(Mono.error(new InvalidCityException(cityName)))
                .flatMap(city -> solarTimesService.findOrCreate(city, date));
    }

    public Mono<SolarTimes> getSolarTimes(Long cityId, LocalDate date) {
        return cityService.findById(cityId)
                .switchIfEmpty(Mono.error(new InvalidCityException("City ID: " + cityId)))
                .flatMap(city -> solarTimesService.findOrCreate(city, date));
    }

    public Mono<SolarTimes> getSolarTimes(double lat, double lon, LocalDate date) {
        return cityService.findByLatLon(lat, lon)
                .switchIfEmpty(cityService.fetchAndSave(lat, lon)
                        .switchIfEmpty(Mono.error(new InvalidCityException("Lat*: " + lat + ", Lon: " + lon))))
                .flatMap(city -> solarTimesService.findOrCreate(city, date));
    }

    public Mono<SolarTimes> getSolarTimesById(Long id){
        return solarTimesService.findById(id);
    }

    public Mono<SolarResponseDto> getSolarTimesDto(String cityName, LocalDate date) {
        return getSolarTimes(cityName, date)
                .map(SolarMapper::toDto);
    }

    public Mono<SolarResponseDto> getSolarTimesDto(Long cityId, LocalDate date) {
        return getSolarTimes(cityId, date)
                .map(SolarMapper::toDto);
    }

    public Mono<SolarResponseDto> getSolarTimesDto(double lat, double lon, LocalDate date) {
        return getSolarTimes(lat, lon, date)
                .map(SolarMapper::toDto);
    }

    public Mono<SolarResponseDto> getSolarTimesDtoById(Long solarTimesId) {
        return getSolarTimesById(solarTimesId)
                .map(SolarMapper::toDto);
    }
}
