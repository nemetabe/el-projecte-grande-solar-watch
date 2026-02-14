package com.nemetabe.solarwatch.service;


import com.nemetabe.solarwatch.mapper.SolarMapper;
import com.nemetabe.solarwatch.model.dto.savedCity.SavedCityResponseDto;
import com.nemetabe.solarwatch.model.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedCityQueryService {

    private final SavedCityService savedCityService;
    private final MemberService memberService;
    private final CityService cityService;
    private final SolarTimesService solarTimesService;

    @Autowired
    public SavedCityQueryService(
            SavedCityService savedCityService,
            MemberService memberService,
            CityService cityService,
            SolarTimesService solarTimesService) {
        this.savedCityService = savedCityService;
        this.memberService = memberService;
        this.cityService = cityService;
        this.solarTimesService = solarTimesService;
    }

    public Mono<SavedCityResponseDto> saveDate(
            Long memberId,
            Long cityId,
            Long solarTimesId
    ) {
        Member member = getMemberById(memberId);

        return getCityById(cityId)
                .flatMap(city ->
                        getSolarTimesById(solarTimesId)
                                .publishOn(Schedulers.boundedElastic())
                                .map(solarTimes -> {
                                    SavedCity savedCity =
                                            savedCityService.saveSolarDate(member, city, solarTimes);
                                    return SolarMapper.getSavedCityResponseDtoFrom(savedCity);
                                })
                );
    }

    public Mono<SavedCityResponseDto> getSavedCity(Long savedCityId) {
        return Mono.fromCallable(() -> savedCityService.getById(savedCityId))
                .subscribeOn(Schedulers.boundedElastic())
                .map(SolarMapper::getSavedCityResponseDtoFrom);
    }

    public Mono<SavedCityResponseDto> getSavedCity(Long memberId, Long cityId) {
        Member member = getMemberById(memberId);
        return getCityById(cityId)
                .flatMap(city ->
                        Mono.fromCallable(() ->
                                savedCityService.getByMemberAndCity(member, city)
                        ).subscribeOn(Schedulers.boundedElastic())
                ).map(SolarMapper::getSavedCityResponseDtoFrom);
    }

    public Mono<List<SavedCityResponseDto>> getSavedCities(Long memberId) {
        Member member = getMemberById(memberId);

        return Mono.fromCallable(() ->
                savedCityService.getAllSavedCities(member)
                        .stream()
                        .map(SolarMapper::getSavedCityResponseDtoFrom)
                        .toList()
        ).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<List<SolarDateId>> getSolarDates(Long memberId, Long cityId) {
        Member member = getMemberById(memberId);
        return getCityById(cityId)
                .flatMap(city ->
                        Mono.fromCallable(()->
                                savedCityService.getSolarDateIds(member, city)
                        ).subscribeOn(Schedulers.boundedElastic())
                ).map(ArrayList::new);
    }

    public Mono<Void> deleteSavedCityByMemberAndCity(Long memberId, Long cityId) {
        Member member = memberService.findMemberById(memberId);
        return cityService.findById(cityId)
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(city -> savedCityService.deleteSavedCityByMemberAndCity(member, city))
                .then();
    }

    public Mono<Void> deleteSavedCityById(Long savedCityId) {
        return Mono.fromRunnable(() -> savedCityService.deleteSavedCityById(savedCityId))
                .subscribeOn(Schedulers.boundedElastic())
                .then();
    }


    private Member getMemberById(Long memberId) {
        return memberService.findMemberById(memberId);
    }
    private Mono<City> getCityById(Long cityId) {
        return cityService.findById(cityId);
    }
    private Mono<SolarTimes> getSolarTimesById(Long solarTimesId) {
        return solarTimesService.findById(solarTimesId);
    }

}
