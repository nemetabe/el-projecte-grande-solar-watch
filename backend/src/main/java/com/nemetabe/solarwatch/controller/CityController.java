package com.nemetabe.solarwatch.controller;

import com.nemetabe.solarwatch.model.dto.city.CityLocationDto;
import com.nemetabe.solarwatch.model.dto.city.CityNameDto;
import com.nemetabe.solarwatch.model.dto.city.CityResponseDto;

import com.nemetabe.solarwatch.model.exception.city.InvalidCityException;
import com.nemetabe.solarwatch.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CityResponseDto>> getCityById(@PathVariable Long id) {
        return cityService.getCityResponseDtoById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InvalidCityException("City ID: " + id)));
    }

    @GetMapping
    public Mono<ResponseEntity<CityResponseDto>> getCityByName(@RequestParam String name) {
        return cityService.getCityResponseDtoByName(name)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InvalidCityException("City name: " + name)));
    }

    @GetMapping("/coordinates")
    public Mono<ResponseEntity<CityResponseDto>> getCityByCoordinates(
            @RequestParam double lat,
            @RequestParam double lon
    ) {
        return cityService.getCityResponseDtoByCoordinates(lat, lon)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InvalidCityException(
                        "Coordinates: lat: " + lat + ", lon: " + lon
                )));
    }

    @GetMapping("/{id}/name")
    public Mono<ResponseEntity<CityNameDto>> getCityName(@PathVariable Long id) {
        return cityService.getCityNameDtoById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InvalidCityException("City ID: " + id)));
    }

    @GetMapping("/{id}/location")
    public Mono<ResponseEntity<CityLocationDto>> getCityLocation(@PathVariable Long id) {
        return cityService.getCityLocationDtoById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(Mono.error(new InvalidCityException("City ID: " + id)));
    }

    @GetMapping("/all")
    public Mono<ResponseEntity<List<CityResponseDto>>> getAllCities() {
        return cityService.getAllCities()
                .map(ResponseEntity::ok);
    }
}