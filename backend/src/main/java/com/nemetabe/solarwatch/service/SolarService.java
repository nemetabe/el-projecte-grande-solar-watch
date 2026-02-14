package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.client.GeoCodingClient;
import com.nemetabe.solarwatch.client.SSSRClient;
import com.nemetabe.solarwatch.mapper.CityMapper;
import com.nemetabe.solarwatch.mapper.SolarMapper;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import com.nemetabe.solarwatch.model.exception.city.InvalidCityException;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.repository.SolarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Function;

@Service
public class SolarService {

    private final CityRepository cityRepo;
    private final SolarRepository solarRepo;
    private final GeoCodingClient geoClient;
    private final SSSRClient sssrClient;

    private static final String[] BASE_CITIES = {
            "Tokyo", "Los Angeles", "London", "Paris",
            "Chicago", "Toronto", "Frankfurt", "Budapest"
    };

    @Autowired
    public SolarService(CityRepository cityRepo,
                        SolarRepository solarRepo,
                        GeoCodingClient geoClient,
                        SSSRClient sssrClient) {
        this.cityRepo = cityRepo;
        this.solarRepo = solarRepo;
        this.geoClient = geoClient;
        this.sssrClient = sssrClient;

        initializeBaseCities();
    }

    /* ---------------------------------------------------------
     *  INITIALIZATION
     * --------------------------------------------------------- */
    private void initializeBaseCities() {
        if (cityRepo.count() == 0) {
            fetchAndSaveBaseCitiesSequentially();
        }
    }

    private void fetchAndSaveBaseCitiesSequentially() {
        for (String cityName : BASE_CITIES) {
            geoClient.fetchCities(cityName)
                    .next()
                    .map(CityMapper::toEntity)
                    .flatMap(cityEntity -> Mono.fromCallable(() -> saveCityIfNotExists(cityEntity))
                            .subscribeOn(Schedulers.boundedElastic()))
                    .block(); // ensures sequential execution
        }
    }

    private City saveCityIfNotExists(City cityEntity) {
        return cityRepo.findCityByNameIgnoreCase(cityEntity.getName())
                .orElseGet(() -> cityRepo.save(cityEntity));
    }

    /* ---------------------------------------------------------
     *  PUBLIC METHODS
     * --------------------------------------------------------- */

    public Mono<SolarResponseDto> getSolarInfo(String cityName, LocalDate date) {
        return findOrFetchCity(cityName)
                .flatMap(getCitySolarDataFunction(date))
                .map(SolarMapper::toDto);
    }

    public CityResponseDto findCityByName(String cityName) {
        return cityRepo.findCityByNameIgnoreCase(cityName)
                .map(CityMapper::toDto)
                .orElseThrow(InvalidCityException::new);
    }

    public CityResponseDto findCityById(Long cityId) {
        return cityRepo.findCityById(cityId)
                .map(CityMapper::toDto)
                .orElseThrow(InvalidCityException::new);
    }

    /* ---------------------------------------------------------
     *  PRIVATE REACTIVE HELPERS
     * --------------------------------------------------------- */

    /**
     * Finds a city from the DB or fetches it via GeoCoding API if not found.
     */
    private Mono<City> findOrFetchCity(String cityName) {
        return Mono.fromCallable(() -> cityRepo.findCityByNameIgnoreCase(cityName))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalCity -> optionalCity
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSaveCity(cityName))
                );
    }

    /**
     * Fetches a city via API and saves it to the DB if not already existing.
     */
    private Mono<City> fetchAndSaveCity(String cityName) {
        return geoClient.fetchCities(cityName)
                .next()
                .map(CityMapper::toEntity)
                .flatMap(cityEntity ->
                        Mono.fromCallable(() -> saveCityIfNotExists(cityEntity))
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }

    /**
     * Returns a function that, given a City, returns Mono<SolarTimes> for the date.
     */
    private Function<City, Mono<SolarTimes>> getCitySolarDataFunction(LocalDate date) {
        return city -> Mono.fromCallable(() ->
                        solarRepo.findFirstByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(date, city.getName())
                )
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optionalSolarData -> optionalSolarData
                        .map(Mono::just)
                        .orElseGet(() -> fetchAndSaveSolarData(city, date))
                );
    }

    /**
     * Fetches solar data from API, maps it, and saves to DB.
     */
    private Mono<SolarTimes> fetchAndSaveSolarData(City city, LocalDate date) {
        return sssrClient.fetchSolarData(city.getLatitude(), city.getLongitude(), date)
                .map(data -> SolarMapper.toEntity(data, city, date))
                .flatMap(solarEntity ->
                        Mono.fromCallable(() -> solarRepo.save(solarEntity))
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }
}