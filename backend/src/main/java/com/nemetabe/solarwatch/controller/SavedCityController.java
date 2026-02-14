package com.nemetabe.solarwatch.controller;


import com.nemetabe.solarwatch.model.dto.savedCity.SaveSolarTimesRequest;
import com.nemetabe.solarwatch.model.dto.savedCity.SavedCityResponseDto;
import com.nemetabe.solarwatch.model.entity.SolarDateId;
import com.nemetabe.solarwatch.service.SavedCityQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/saved-cities")
public class SavedCityController {

    private final SavedCityQueryService savedCityQueryService;

    @Autowired
    public SavedCityController(SavedCityQueryService savedCityQueryService) {
        this.savedCityQueryService = savedCityQueryService;
    }

    @PostMapping("/members/{memberId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<SavedCityResponseDto> saveCity(
            @PathVariable Long memberId,
            @RequestBody SaveSolarTimesRequest request) {
        return savedCityQueryService.saveDate(memberId, request.cityId(), request.solarTimesId());
    }

    @GetMapping("/members/{memberId}")
    public Mono<List<SavedCityResponseDto>> getSavedCities(@PathVariable Long memberId) {
        return savedCityQueryService.getSavedCities(memberId);
    }

    @GetMapping("/{savedCityId}")
    public Mono<SavedCityResponseDto> getSavedCity(@PathVariable Long savedCityId) {
        return savedCityQueryService.getSavedCity(savedCityId);
    }

    @GetMapping("/members/{memberId}/cities/{cityId}")
    public Mono<SavedCityResponseDto> getSavedCity(
            @PathVariable Long memberId,
            @PathVariable Long cityId
    ){
        return savedCityQueryService.getSavedCity(memberId, cityId);
    }

    @GetMapping("/{savedCityId}/members/{memberId}/dates")
    public Mono<List<SolarDateId>> getDatesForCity(
            @PathVariable Long memberId,
            @PathVariable Long savedCityId) {
        return savedCityQueryService.getSolarDates(memberId, savedCityId);
    }

    @DeleteMapping("/{savedCityId}")
    public Mono<Void> deleteCity(@PathVariable Long savedCityId) {
        return savedCityQueryService.deleteSavedCityById(savedCityId);
    }

    @DeleteMapping("/{savedCityId}/members/{memberId}")
    public Mono<Void> deleteCity(
            @PathVariable Long memberId,
            @PathVariable Long savedCityId) {
        return savedCityQueryService.deleteSavedCityByMemberAndCity(memberId, savedCityId);
    }
}
