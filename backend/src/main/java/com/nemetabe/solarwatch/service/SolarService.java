//package com.nemetabe.solarwatch.service;
//
//import com.nemetabe.solarwatch.client.GeoCodingClient;
//import com.nemetabe.solarwatch.client.SSSRClient;
//import com.nemetabe.solarwatch.mapper.CityMapper;
//import com.nemetabe.solarwatch.mapper.SolarMapper;
//import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;
//import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
//import com.nemetabe.solarwatch.model.entity.City;
//import com.nemetabe.solarwatch.model.entity.SolarTimes;
//import com.nemetabe.solarwatch.model.exception.city.InvalidCityException;
//import com.nemetabe.solarwatch.repository.CityRepository;
//import com.nemetabe.solarwatch.repository.SolarRepository;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import reactor.core.publisher.Mono;
//import reactor.core.scheduler.Schedulers;
//
//import java.time.LocalDate;
//import java.util.function.Function;
//
//@Service
//public class SolarService {
//
//    private static final Logger log = LoggerFactory.getLogger(SolarService.class);
//
//    private final CityRepository cityRepo;
//    private final SolarRepository solarRepo;
//    private final GeoCodingClient geoClient;
//    private final SSSRClient sssrClient;
//
//    private static final String[] BASE_CITIES = {
//            "Tokyo", "Los Angeles", "London", "Paris",
//            "Chicago", "Toronto", "Frankfurt", "Budapest"
//    };
//
//    @Autowired
//    public SolarService(CityRepository cityRepo,
//                        SolarRepository solarRepo,
//                        GeoCodingClient geoClient,
//                        SSSRClient sssrClient) {
//        this.cityRepo = cityRepo;
//        this.solarRepo = solarRepo;
//        this.geoClient = geoClient;
//        this.sssrClient = sssrClient;
//
//        initializeBaseCities();
//    }
//
//    private void initializeBaseCities() {
//        if (cityRepo.count() == 0) {
//            fetchAndSaveBaseCitiesSequentially();
//        }
//    }
//
//    private void fetchAndSaveBaseCitiesSequentially() {
//        for (String cityName : BASE_CITIES) {
//            try {
//                geoClient.fetchCities(cityName)
//                        .next()
//                        .map(CityMapper::toEntity)
//                        .flatMap(cityEntity -> Mono.fromCallable(() -> saveCityIfNotExists(cityEntity))
//                                .subscribeOn(Schedulers.boundedElastic()))
//                        .block();
//                log.info("Successfully initialized base city: {}", cityName);
//            } catch (Exception e) {
//                log.warn("Failed to initialize base city: {}. Error: {}", cityName, e.getMessage());
//            }
//        }
//        log.info("Base cities initialization completed");
//    }
//
//    private City saveCityIfNotExists(City cityEntity) {
//        return cityRepo.findCityByNameAndCountryIgnoreCase(cityEntity.getName(), cityEntity.getCountry())
//                .orElseGet(() -> cityRepo.save(cityEntity));
//    }
//
//    public Mono<SolarResponseDto> getSolarInfo(String cityName, LocalDate date) {
//        log.debug("Fetching solar info for city: {} on date: {}", cityName, date);
//        return findOrFetchCity(cityName)
//                .flatMap(getCitySolarDataFunction(date))
//                .map(SolarMapper::toDto)
//                .doOnSuccess(dto -> log.debug("Successfully retrieved solar info for city: {}", cityName))
//                .doOnError(error -> log.error("Error retrieving solar info for city: {}", cityName, error));
//    }
//
//    public CityResponseDto findCityByNameAndCountry(String cityName, String country) {
//        log.debug("Finding city by name: {} and country: {}", cityName, country);
//        return cityRepo.findCityByNameAndCountryIgnoreCase(cityName, country)
//                .map(CityMapper::toDto)
//                .orElseThrow(() -> {
//                    log.warn("City not found: {}, {}", cityName, country);
//                    return new InvalidCityException(cityName);
//                });
//    }
//
//    public CityResponseDto findCityById(Long cityId) {
//        log.debug("Finding city by ID: {}", cityId);
//        return cityRepo.findCityById(cityId)
//                .map(CityMapper::toDto)
//                .orElseThrow(() -> {
//                    log.warn("City not found with ID: {}", cityId);
//                    return new InvalidCityException("");
//                });
//    }
//
//    private Mono<City> findOrFetchCity(String cityName) {
//        return Mono.fromCallable(() -> cityRepo.findCityByNameIgnoreCase(cityName))
//                .subscribeOn(Schedulers.boundedElastic())
//                .flatMap(optionalCity -> optionalCity
//                        .map(Mono::just)
//                        .orElseGet(() -> fetchAndSaveCity(cityName))
//                )
//                .switchIfEmpty(Mono.error(new InvalidCityException(cityName)))
//                .doOnError(error -> log.error("Error finding/fetching city: {}", cityName, error));
//    }
//
//    private Mono<City> fetchAndSaveCity(String cityName) {
//        log.debug("Fetching city from API: {}", cityName);
//        return geoClient.fetchCities(cityName)
//                .next()
//                .switchIfEmpty(Mono.error(new InvalidCityException(cityName)))
//                .map(CityMapper::toEntity)
//                .flatMap(cityEntity ->
//                        Mono.fromCallable(() -> saveCityIfNotExists(cityEntity))
//                                .subscribeOn(Schedulers.boundedElastic())
//                )
//                .doOnSuccess(city -> log.debug("Successfully fetched and saved city: {}", cityName))
//                .doOnError(error -> log.error("Error fetching/saving city: {}", cityName, error));
//    }
//
//    private Function<City, Mono<SolarTimes>> getCitySolarDataFunction(LocalDate date) {
//        return city -> Mono.fromCallable(() ->
//                        solarRepo.findFirstByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(date, city.getName())
//                )
//                .subscribeOn(Schedulers.boundedElastic())
//                .flatMap(optionalSolarData -> optionalSolarData
//                        .map(Mono::just)
//                        .orElseGet(() -> fetchAndSaveSolarData(city, date))
//                )
//                .doOnError(error -> log.error("Error getting solar data for city: {} on date: {}", city.getName(), date, error));
//    }
//
//    private Mono<SolarTimes> fetchAndSaveSolarData(City city, LocalDate date) {
//        log.debug("Fetching solar data from API for city: {} on date: {}", city.getName(), date);
//        return sssrClient.fetchSolarData(city.getLatitude(), city.getLongitude(), date)
//                .map(data -> SolarMapper.toEntity(data, city, date))
//                .flatMap(solarEntity ->
//                        Mono.fromCallable(() -> solarRepo.save(solarEntity))
//                                .subscribeOn(Schedulers.boundedElastic())
//                )
//                .doOnSuccess(solar -> log.debug("Successfully fetched and saved solar data for city: {}", city.getName()))
//                .doOnError(error -> log.error("Error fetching/saving solar data for city: {}", city.getName(), error));
//    }
//}
