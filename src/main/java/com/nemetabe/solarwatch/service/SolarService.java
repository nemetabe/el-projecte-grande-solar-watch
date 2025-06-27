package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.client.GeoCodingClient;
import com.nemetabe.solarwatch.client.SSSRClient;
import com.nemetabe.solarwatch.model.dto.solar.SolarResponseDto;
import com.nemetabe.solarwatch.mapper.CityMapper;
import com.nemetabe.solarwatch.mapper.SolarMapper;
import com.nemetabe.solarwatch.repository.CityRepository;
import com.nemetabe.solarwatch.repository.SolarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SolarService {

    private final CityRepository cityRepo;
    private final SolarRepository solarRepo;
    private final GeoCodingClient geoClient;
    private final SSSRClient sssrClient;


    @Autowired
    public SolarService(CityRepository cityRepo, SolarRepository solarRepo,
                        GeoCodingClient geoClient, SSSRClient sssrClient) {
        this.cityRepo = cityRepo;
        this.solarRepo = solarRepo;
        this.geoClient = geoClient;
        this.sssrClient = sssrClient;
        if (cityRepo.count() == 0) {
            fetchBaseCities();
        }
    }

    private void fetchBaseCities() {
        String[] baseCities = {"Tokyo","Los Angeles","London", "Paris", "Chicago", "Toronto", "Frankfurt", "Budapest"};
        for (String city : baseCities ) {
            geoClient.fetchCities(city)
                    .next()
                    .map(CityMapper::toEntity)
                    .map(cityRepo::save)
                    .flatMap(cityEntity -> Mono.fromCallable(() -> cityRepo.save(cityEntity))
                            .subscribeOn(Schedulers.boundedElastic()))
                    .subscribe();
        }
    }


    public Mono<SolarResponseDto> getSolarInfo(String cityName, LocalDate date) {
        return Mono.defer(() ->
                Mono.fromCallable(() -> cityRepo.findCityByNameIgnoreCase(cityName))
                        .map(optionalCity -> optionalCity.orElse(null))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(city -> {
                            if (city == null) {
                                return geoClient.fetchCities(cityName)
                                        .next()
                                        .map(CityMapper::toEntity)
                                        .flatMap(cityEntity -> Mono.fromCallable(() -> cityRepo.save(cityEntity))
                                                .subscribeOn(Schedulers.boundedElastic()));
                            } else {
                                return Mono.just(city);
                            }
                        })
        ).flatMap(city ->
                Mono.fromCallable(() -> solarRepo.findByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(date, city.getName()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(optionalSolarData -> { // Change solarData to optionalSolarData to reflect Optional type
                            if (optionalSolarData.isPresent()) { // Check if Optional contains a value
                                return Mono.just(optionalSolarData.get()); // Get the SolarTimes object from Optional
                            } else {
                                return sssrClient.fetchSolarData(city.getLatitude(), city.getLongitude(), date)
                                        .map(data -> SolarMapper.toEntity(data, city))
                                        .flatMap(solarEntity -> Mono.fromCallable(() -> solarRepo.save(solarEntity))
                                                .subscribeOn(Schedulers.boundedElastic()));
                            }
                        })
        ).map(SolarMapper::toDto); // Removed .cast(SolarTimes.class) as it's no longer needed
    }
}