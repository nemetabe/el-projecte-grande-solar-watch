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
        String[] baseCities = {"Tokyo", "Los Angeles", "London", "Paris", "Chicago", "Toronto", "Frankfurt", "Budapest"};
        for (String city : baseCities) {
            geoClient.fetchCities(city)
                    .next()
                    .map(CityMapper::toEntity)
                    .flatMap(cityEntity -> Mono.fromCallable(() -> cityRepo.save(cityEntity))
                            .subscribeOn(Schedulers.boundedElastic()))
                    .subscribe();
        }
    }

    public Mono<SolarResponseDto> getSolarInfo(String cityName, LocalDate date) {
        return Mono.defer(() ->
                Mono.fromCallable(() -> cityRepo.findCityByNameIgnoreCase(cityName))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(optionalCity -> {
                            if (optionalCity.isPresent()) {
                                return Mono.just(optionalCity.get());
                            } else {
                                return geoClient.fetchCities(cityName)
                                        .next()
                                        .map(CityMapper::toEntity)
                                        .flatMap(cityEntity -> Mono.fromCallable(() -> cityRepo.save(cityEntity))
                                                .subscribeOn(Schedulers.boundedElastic()));
                            }
                        })
        ).flatMap(city ->
                Mono.fromCallable(() -> solarRepo.findFirstByDateAndCity_NameContainingIgnoreCaseOrderByDateDesc(date, city.getName()))
                        .subscribeOn(Schedulers.boundedElastic())
                        .flatMap(optionalSolarData -> {
                            if (optionalSolarData.isPresent()) {
                                return Mono.just(optionalSolarData.get());
                            } else {
                                return sssrClient.fetchSolarData(city.getLatitude(), city.getLongitude(), date)
                                        .map(data -> SolarMapper.toEntity(data, city, date))
                                        .flatMap(solarEntity -> Mono.fromCallable(() -> solarRepo.save(solarEntity))
                                                .subscribeOn(Schedulers.boundedElastic()));
                            }
                        })
        ).map(SolarMapper::toDto);
    }
}