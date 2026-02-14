package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.client.GeoCodingClient;
import com.nemetabe.solarwatch.mapper.CityMapper;
import com.nemetabe.solarwatch.model.dto.city.CityLocationDto;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.exception.city.CityNotFoundException;
import com.nemetabe.solarwatch.repository.CityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final GeoCodingClient geoCodingClient;

    @Autowired
    public CityService(CityRepository cityRepository,
                       GeoCodingClient geoCodingClient) {
        this.cityRepository = cityRepository;
        this.geoCodingClient = geoCodingClient;
    }

    public Mono<City> findOrCreate(String cityName) {
        return geoCodingClient.fetchCity(cityName)
                .map(CityMapper::toEntity)
                .flatMap(city ->
                        findByLatLon(city.getLatitude(), city.getLongitude())
                            .switchIfEmpty(saveCity(city))
                );
    }

    private Mono<City> saveCity(City city) {
        return Mono.fromCallable(() -> cityRepository.save(city))
                .subscribeOn(Schedulers.boundedElastic())
                .onErrorResume(DataIntegrityViolationException.class,
                        ex -> findByLatLon(city.getLatitude(), city.getLongitude())
                );
    }

    public Mono<City> findOrCreate(double latitude, double longitude) {
        return findByLatLon(latitude, longitude)
                .switchIfEmpty(fetchAndSave(latitude, longitude));
    }


    public Mono<City> fetchAndSave(String cityName) {
        return geoCodingClient.fetchCity(cityName)
                .map(CityMapper::toEntity)
                .flatMap(city ->
                        Mono.fromCallable(() -> {
                                    try {
                                        return cityRepository.save(city);
                                    } catch (DataIntegrityViolationException e) {
                                        return cityRepository
                                                .findByCountryIgnoreCaseAndNameIgnoreCase(
                                                        city.getCountry(),
                                                        city.getName()
                                                )
                                                .orElseThrow();
                                    }
                                })
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }
    public Mono<City> fetchAndSave(double latitude, double longitude) {
        return geoCodingClient.fetchCity(latitude, longitude)
                .map(CityMapper::toEntity)
                .flatMap(city ->
                        Mono.fromCallable(() -> {
                                    try {
                                        return cityRepository.save(city);
                                    } catch (DataIntegrityViolationException e) {
                                        return cityRepository
                                                .findCityByLatitudeAndLongitude(
                                                        city.getLatitude(),
                                                        city.getLongitude()
                                                )
                                                .orElseThrow();
                                    }
                                })
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }

    public Mono<City> findById(Long cityId) {
        return Mono.fromCallable(() -> cityRepository.findById(cityId))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional
                        .map(Mono::just)
                        .orElseGet(() -> Mono.error(new CityNotFoundException(cityId.toString()))));
    }
    public Mono<City> findByName(String cityName) {
        return Mono.fromCallable(() -> cityRepository.findCityByNameIgnoreCase(cityName))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty);
    }
    public Mono<City> findByLatLon(double lat, double lon) {
        return Mono.fromCallable(() -> cityRepository.findCityByLatitudeAndLongitude(lat, lon))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(Mono::justOrEmpty);
    }


    public Mono<CityResponseDto> getCityResponseDtoById(Long id) {
        return findById(id)
                .map(CityMapper::toDto);
    }

    public Mono<CityResponseDto> getCityResponseDtoByName(String name) {
        return findByName(name)
                .map(CityMapper::toDto);
    }

    public Mono<CityResponseDto> getCityResponseDtoByCoordinates(double lat, double lon) {
        return findByLatLon(lat, lon)
                .switchIfEmpty(fetchAndSave(lat, lon))
                .map(CityMapper::toDto);
    }

    public Mono<CityNameDto> getCityNameDtoById(Long id) {
        return findById(id)
                .map(CityMapper::toNameDto);
    }

    public Mono<CityLocationDto> getCityLocationDtoById(Long id) {
        return findById(id)
                .map(CityMapper::toLocationDto);
    }

    public Mono<List<CityResponseDto>> getAllCities() {
        return Mono.fromCallable(cityRepository::findAll)
                .subscribeOn(Schedulers.boundedElastic())
                .map(list -> list.stream().map(CityMapper::toDto).toList());
    }
}