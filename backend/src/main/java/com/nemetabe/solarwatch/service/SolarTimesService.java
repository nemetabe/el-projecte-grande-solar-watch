package com.nemetabe.solarwatch.service;

import com.nemetabe.solarwatch.client.SSSRClient;
import com.nemetabe.solarwatch.mapper.SolarMapper;
import com.nemetabe.solarwatch.model.entity.City;
import com.nemetabe.solarwatch.model.entity.SolarTimes;
import com.nemetabe.solarwatch.model.exception.solar.SolarTimesNotFoundException;
import com.nemetabe.solarwatch.repository.SolarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDate;

@Service
public class SolarTimesService {

    private final SolarRepository solarRepository;
    private final SSSRClient sssrClient;

    @Autowired
    public SolarTimesService(SolarRepository solarRepository, SSSRClient sssrClient) {
        this.solarRepository = solarRepository;
        this.sssrClient = sssrClient;
    }

    public Mono<SolarTimes> findOrCreate(City city, LocalDate date) {
        return findByCityAndDate(city, date)
                .switchIfEmpty(fetchAndSave(city, date));

    }

    public Mono<SolarTimes> findById(Long id) {
        return Mono.fromCallable(() -> solarRepository.findById(id))
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional
                        .map(Mono::just)
                        .orElseGet(() -> Mono.error(new SolarTimesNotFoundException(id.toString(), "ID"))));
    }

    public Mono<SolarTimes> findByCityAndDate(City city, LocalDate date) {
        return Mono.fromCallable(() ->
                        solarRepository.findByCityAndDate(city, date)
                )
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(optional -> optional.map(Mono::just).orElseGet(Mono::empty));
    }

    public Mono<SolarTimes> fetchAndSave(City city, LocalDate date) {
        return sssrClient.fetchSolarData(city.getLatitude(), city.getLongitude(), date)
                .map(apiResponse -> SolarMapper.toEntity(apiResponse, city, date))
                .flatMap(solarTimes ->
                        Mono.fromCallable(() -> solarRepository.save(solarTimes))
                                .subscribeOn(Schedulers.boundedElastic())
                );
    }
}
